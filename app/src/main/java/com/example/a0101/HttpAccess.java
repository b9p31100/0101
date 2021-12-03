package com.example.a0101;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class HttpAccess {

	private OkHttpClient okHttpClient;
	private String url;

	public interface OnAsyncTaskListener {
		abstract public void onAsyncTaskListener(String s);
	}

	// url の Getter と Setter
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	// *******************************
	// コンストラクタ
	// *******************************
	public HttpAccess(String url) {
		this.okHttpClient = new OkHttpClient();
		this.url = url;
	}

	// *******************************
	// GET
	// *******************************
	public void sendGet(final OnAsyncTaskListener listener ) {

		new AsyncTask<Void,Void,String>(){
			@Override
			protected String doInBackground(Void... params) {
				String result = "[\"error\"]";

				Request.Builder builder = new Request.Builder();
				builder.url(HttpAccess.this.url);
				Request request = builder.build();

				Response response = null;
				try {
					response = HttpAccess.this.okHttpClient.newCall(request).execute();
					result = response.body().string();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				return result;
			}

			// UI スレッド処理
			@Override
			protected void onPostExecute(String s) {
				super.onPostExecute(s);

				listener.onAsyncTaskListener(s);

			}
		}.execute();

	}

	// *******************************
	// POST
	// *******************************
	public void sendPost(HashMap<String,String> postData, final OnAsyncTaskListener listener ) {

		new AsyncTask<HashMap,Void,String>(){
			@Override
			protected String doInBackground(HashMap... params) {
				String result = "[\"error\"]";

				HashMap<String,String> postData = (HashMap<String,String>)params[0];

				// POST 用 FormBody の内容の作成
				FormBody.Builder formbodyBuilder = new FormBody.Builder();
				for(Map.Entry<String, String> e : postData.entrySet()) {
					if (  !(e.getKey()).equals("url") ) {
						formbodyBuilder.add(e.getKey(), e.getValue());
					}
				}

				// 送信用ユニットの作成
				FormBody formbody = formbodyBuilder.build();
				Request.Builder builder = new Request.Builder();

				// 引数の Map に url が無い場合は、コンストラクタの url を使用する
				if ( postData.get("url") == null ) {
					builder.url(HttpAccess.this.url) ;
				}
				else {
					builder.url( postData.get("url") );
				}

				builder.post(formbody);
				Request request = builder.build();

				Response response = null;
				try {
					response = HttpAccess.this.okHttpClient.newCall(request).execute();
					result = response.body().string();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				return result;
			}

			// UI スレッド処理
			@Override
			protected void onPostExecute(String s) {

				listener.onAsyncTaskListener(s);

			}
		}.execute(postData);

	}

	// *******************************
	// １ファイルのアップロード(  Byte 配列使うので一つ )
	// *******************************
	public void sendUpload(String url, String field, String name, InputStream inputStream, String mime, final OnAsyncTaskListener listener ) {

		// 引数は、専用クラス
		UploadParam uploadParam = new UploadParam(url,field,name,inputStream,mime);
		new AsyncTask<UploadParam,Void,String>(){
			@Override
			protected String doInBackground(UploadParam... params) {
				String result = "[\"error\"]";

				UploadParam uploadParam = params[0];
				InputStream inputStream = uploadParam.getInputStream();
				Source source = Okio.source(inputStream);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				BufferedSink bufferedSink = Okio.buffer(Okio.sink(byteArrayOutputStream));
				try {
					bufferedSink.writeAll(source);
					bufferedSink.close();
					source.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
				multipartBodyBuilder.setType(MultipartBody.FORM);
				// PHP の ファイルアップロードサイズ制限の仕様
				// multipartBodyBuilder.addFormDataPart("MAX_FILE_SIZE","2097152");
				multipartBodyBuilder.addFormDataPart(
						uploadParam.getField(),
						uploadParam.getName(),
						RequestBody.create(MediaType.parse(uploadParam.getMime()),byteArrayOutputStream.toByteArray())
				);
				RequestBody requestBody = multipartBodyBuilder.build();

				// 送信用のデータを作成
				Request.Builder requestBuilder = new Request.Builder();
				requestBuilder.url(uploadParam.getUrl());
				requestBuilder.post(requestBody);
				Request request = requestBuilder.build();

				// 受信用のオブジェクトの準備
				Call call = HttpAccess.this.okHttpClient.newCall(request);

				// 送信と受信
				try {

					Response response = call.execute();
					result = response.body().string();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				return result;

			}

			@Override
			protected void onPostExecute(String s) {

				listener.onAsyncTaskListener(s);

			}

		}.execute(uploadParam);


	}

	private class UploadParam {
		private String url;
		private String mime;
		private String field;
		private String name;
		private InputStream inputStream;

		public UploadParam(String url, String field, String name, InputStream inputStream, String mime) {
			this.url = url;
			this.mime = mime;
			this.field = field;
			this.name = name;
			this.inputStream = inputStream;
		}

		public String getUrl() {
			return url;
		}

		public String getMime() {
			return mime;
		}

		public String getField() {
			return field;
		}

		public String getName() {
			return name;
		}

		public InputStream getInputStream() {
			return inputStream;
		}
	}

	private class DownloadParam {
		private String url;
		private OutputStream outputStream;

		public DownloadParam(String url, OutputStream outputStream) {
			this.url = url;
			this.outputStream = outputStream;
		}

		public String getUrl() {
			return url;
		}

		public OutputStream getOutputStream() {
			return outputStream;
		}
	}


	// *************************
	// ファイルコピー
	// *************************
	public static void streamCopy(InputStream inputStream, OutputStream outputStream) throws Exception {

		Source source = Okio.source(inputStream);
		BufferedSink bufferedSink = Okio.buffer(Okio.sink(outputStream));
		bufferedSink.writeAll(source);

		bufferedSink.close();
		source.close();

	}

	// *******************************
	// GET & Save this.url で 普通に
	// *******************************
	public void download(OutputStream outputStream, final OnAsyncTaskListener listener ) {

		new AsyncTask<OutputStream,Void,String>(){
			@Override
			protected String doInBackground(OutputStream... params) {
				String result = null;

				try {
					// インターネット上のデータを保存
					URL url = new URL(HttpAccess.this.url);
					InputStream inputStream = (InputStream) url.getContent();
					streamCopy(inputStream,params[0]);
					result = "ok";

				} catch (Exception e) {
					e.printStackTrace();
				}

				// エラーの場合は null
				return result;
			}

			// UI スレッド処理
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				listener.onAsyncTaskListener(result);

			}
		}.execute(outputStream);

	}

	// *******************************
	// OkHttp で GET & Save
	// *******************************
	public void download(String url, OutputStream outputStream, final OnAsyncTaskListener listener ) {

		DownloadParam downloadParam = new DownloadParam(url,outputStream);

		new AsyncTask<DownloadParam,Void,Integer>(){
			@Override
			protected Integer doInBackground(DownloadParam... params) {
				Integer result = null;

				Request.Builder builder = new Request.Builder();
				builder.url(params[0].getUrl());
				Request request = builder.build();

				Response response = null;
				try {
					response = HttpAccess.this.okHttpClient.newCall(request).execute();
					result = response.code();
					HttpAccess.streamCopy(response.body().byteStream(), params[0].getOutputStream());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				if ( response != null ) {
					response.close();
				}

				// エラーの場合は null
				return result;
			}

			// UI スレッド処理
			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);

				String status = null;
				if ( result != null ) {
					status = String.format("%s", result.intValue() );
				}
				listener.onAsyncTaskListener(status);

			}
		}.execute(downloadParam);

	}


	// *************************
	// テキストファイル読込み
	// *************************
	public static String readTextAll(InputStream inputStream, String charset) throws Exception {
		String result;

		BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
		result = bufferedSource.readString(Charset.forName(charset));

		bufferedSource.close();
		inputStream.close();

		return result;
	}


}
