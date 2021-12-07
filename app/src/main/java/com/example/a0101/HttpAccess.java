package com.example.a0101;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
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
import okhttp3.tls.Certificates;
import okhttp3.tls.HandshakeCertificates;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class HttpAccess {

	private OkHttpClient client;
	private String url;
	private X509Certificate ISRGRootX1;
	private X509Certificate ISRGRootX2;
	private X509Certificate E1;


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
		this.client = new OkHttpClient();
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

				ISRGRootX1= Certificates.decodeCertificatePem(""
						+ "-----BEGIN CERTIFICATE-----\n"
						+"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\n"
						+"-----END CERTIFICATE-----");
				//ISRG Root X2
				ISRGRootX2= Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICGzCCAaGgAwIBAgIQQdKd0XLq7qeAwSxs6S+HUjAKBggqhkjOPQQDAzBPMQsw\n"
						+"CQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJuZXQgU2VjdXJpdHkgUmVzZWFyY2gg\n"
						+"R3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBYMjAeFw0yMDA5MDQwMDAwMDBaFw00\n"
						+"MDA5MTcxNjAwMDBaME8xCzAJBgNVBAYTAlVTMSkwJwYDVQQKEyBJbnRlcm5ldCBT\n"
						+"ZWN1cml0eSBSZXNlYXJjaCBHcm91cDEVMBMGA1UEAxMMSVNSRyBSb290IFgyMHYw\n"
						+"EAYHKoZIzj0CAQYFK4EEACIDYgAEzZvVn4CDCuwJSvMWSj5cz3es3mcFDR0HttwW\n"
						+"+1qLFNvicWDEukWVEYmO6gbf9yoWHKS5xcUy4APgHoIYOIvXRdgKam7mAHf7AlF9\n"
						+"ItgKbppbd9/w+kHsOdx1ymgHDB/qo0IwQDAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0T\n"
						+"AQH/BAUwAwEB/zAdBgNVHQ4EFgQUfEKWrt5LSDv6kviejM9ti6lyN5UwCgYIKoZI\n"
						+"zj0EAwMDaAAwZQIwe3lORlCEwkSHRhtFcP9Ymd70/aTSVaYgLXTWNLxBo1BfASdW\n"
						+"tL4ndQavEi51mI38AjEAi/V3bNTIZargCyzuFJ0nN6T5U6VR5CmD1/iQMVtCnwr1\n"
						+"/q4AaOeMSQ+2b1tbFfLn\n"
						+"-----END CERTIFICATE-----");
				//Let’s Encrypt E1
				E1=Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICxjCCAk2gAwIBAgIRALO93/inhFu86QOgQTWzSkUwCgYIKoZIzj0EAwMwTzEL\n"
						+"MAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2VhcmNo\n"
						+"IEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDIwHhcNMjAwOTA0MDAwMDAwWhcN\n"
						+"MjUwOTE1MTYwMDAwWjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3MgRW5j\n"
						+"cnlwdDELMAkGA1UEAxMCRTEwdjAQBgcqhkjOPQIBBgUrgQQAIgNiAAQkXC2iKv0c\n"
						+"S6Zdl3MnMayyoGli72XoprDwrEuf/xwLcA/TmC9N/A8AmzfwdAVXMpcuBe8qQyWj\n"
						+"+240JxP2T35p0wKZXuskR5LBJJvmsSGPwSSB/GjMH2m6WPUZIvd0xhajggEIMIIB\n"
						+"BDAOBgNVHQ8BAf8EBAMCAYYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMB\n"
						+"MBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFFrz7Sv8NsI3eblSMOpUb89V\n"
						+"yy6sMB8GA1UdIwQYMBaAFHxClq7eS0g7+pL4nozPbYupcjeVMDIGCCsGAQUFBwEB\n"
						+"BCYwJDAiBggrBgEFBQcwAoYWaHR0cDovL3gyLmkubGVuY3Iub3JnLzAnBgNVHR8E\n"
						+"IDAeMBygGqAYhhZodHRwOi8veDIuYy5sZW5jci5vcmcvMCIGA1UdIAQbMBkwCAYG\n"
						+"Z4EMAQIBMA0GCysGAQQBgt8TAQEBMAoGCCqGSM49BAMDA2cAMGQCMHt01VITjWH+\n"
						+"Dbo/AwCd89eYhNlXLr3pD5xcSAQh8suzYHKOl9YST8pE9kLJ03uGqQIwWrGxtO3q\n"
						+"YJkgsTgDyj2gJrjubi1K9sZmHzOa25JK1fUpE8ZwYii6I4zPPS/Lgul/\n"
						+"-----END CERTIFICATE-----");

				HandshakeCertificates certificates = new HandshakeCertificates.Builder()
						.addTrustedCertificate(ISRGRootX1)
						.addTrustedCertificate(ISRGRootX2)
						.addTrustedCertificate(E1)
						.build();

				client = new OkHttpClient.Builder()
						.sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
						.build();

				Request.Builder builder = new Request.Builder();
				builder.url(HttpAccess.this.url);
				Request request = builder.build();

				Response response = null;
				try {
					response = HttpAccess.this.client.newCall(request).execute();
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
			@SuppressLint("StaticFieldLeak")
			@Override
			protected String doInBackground(HashMap... params) {
				String result = "[\"error\"]";

				//ISRG Root X1
				ISRGRootX1= Certificates.decodeCertificatePem(""
						+ "-----BEGIN CERTIFICATE-----\n"
						+"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\n"
						+"-----END CERTIFICATE-----");
				//ISRG Root X2
				ISRGRootX2= Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICGzCCAaGgAwIBAgIQQdKd0XLq7qeAwSxs6S+HUjAKBggqhkjOPQQDAzBPMQsw\n"
						+"CQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJuZXQgU2VjdXJpdHkgUmVzZWFyY2gg\n"
						+"R3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBYMjAeFw0yMDA5MDQwMDAwMDBaFw00\n"
						+"MDA5MTcxNjAwMDBaME8xCzAJBgNVBAYTAlVTMSkwJwYDVQQKEyBJbnRlcm5ldCBT\n"
						+"ZWN1cml0eSBSZXNlYXJjaCBHcm91cDEVMBMGA1UEAxMMSVNSRyBSb290IFgyMHYw\n"
						+"EAYHKoZIzj0CAQYFK4EEACIDYgAEzZvVn4CDCuwJSvMWSj5cz3es3mcFDR0HttwW\n"
						+"+1qLFNvicWDEukWVEYmO6gbf9yoWHKS5xcUy4APgHoIYOIvXRdgKam7mAHf7AlF9\n"
						+"ItgKbppbd9/w+kHsOdx1ymgHDB/qo0IwQDAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0T\n"
						+"AQH/BAUwAwEB/zAdBgNVHQ4EFgQUfEKWrt5LSDv6kviejM9ti6lyN5UwCgYIKoZI\n"
						+"zj0EAwMDaAAwZQIwe3lORlCEwkSHRhtFcP9Ymd70/aTSVaYgLXTWNLxBo1BfASdW\n"
						+"tL4ndQavEi51mI38AjEAi/V3bNTIZargCyzuFJ0nN6T5U6VR5CmD1/iQMVtCnwr1\n"
						+"/q4AaOeMSQ+2b1tbFfLn\n"
						+"-----END CERTIFICATE-----");
				//Let’s Encrypt E1
				E1=Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICxjCCAk2gAwIBAgIRALO93/inhFu86QOgQTWzSkUwCgYIKoZIzj0EAwMwTzEL\n"
						+"MAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2VhcmNo\n"
						+"IEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDIwHhcNMjAwOTA0MDAwMDAwWhcN\n"
						+"MjUwOTE1MTYwMDAwWjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3MgRW5j\n"
						+"cnlwdDELMAkGA1UEAxMCRTEwdjAQBgcqhkjOPQIBBgUrgQQAIgNiAAQkXC2iKv0c\n"
						+"S6Zdl3MnMayyoGli72XoprDwrEuf/xwLcA/TmC9N/A8AmzfwdAVXMpcuBe8qQyWj\n"
						+"+240JxP2T35p0wKZXuskR5LBJJvmsSGPwSSB/GjMH2m6WPUZIvd0xhajggEIMIIB\n"
						+"BDAOBgNVHQ8BAf8EBAMCAYYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMB\n"
						+"MBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFFrz7Sv8NsI3eblSMOpUb89V\n"
						+"yy6sMB8GA1UdIwQYMBaAFHxClq7eS0g7+pL4nozPbYupcjeVMDIGCCsGAQUFBwEB\n"
						+"BCYwJDAiBggrBgEFBQcwAoYWaHR0cDovL3gyLmkubGVuY3Iub3JnLzAnBgNVHR8E\n"
						+"IDAeMBygGqAYhhZodHRwOi8veDIuYy5sZW5jci5vcmcvMCIGA1UdIAQbMBkwCAYG\n"
						+"Z4EMAQIBMA0GCysGAQQBgt8TAQEBMAoGCCqGSM49BAMDA2cAMGQCMHt01VITjWH+\n"
						+"Dbo/AwCd89eYhNlXLr3pD5xcSAQh8suzYHKOl9YST8pE9kLJ03uGqQIwWrGxtO3q\n"
						+"YJkgsTgDyj2gJrjubi1K9sZmHzOa25JK1fUpE8ZwYii6I4zPPS/Lgul/\n"
						+"-----END CERTIFICATE-----");
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

				HandshakeCertificates certificates = new HandshakeCertificates.Builder()
						.addTrustedCertificate(ISRGRootX1)
						.addTrustedCertificate(ISRGRootX2)
						.addTrustedCertificate(E1)
						.build();

				client = new OkHttpClient.Builder()
						.sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
						.build();

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
					response = HttpAccess.this.client.newCall(request).execute();
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

				ISRGRootX1= Certificates.decodeCertificatePem(""
						+ "-----BEGIN CERTIFICATE-----\n"
						+"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\n"
						+"-----END CERTIFICATE-----");
				//ISRG Root X2
				ISRGRootX2= Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICGzCCAaGgAwIBAgIQQdKd0XLq7qeAwSxs6S+HUjAKBggqhkjOPQQDAzBPMQsw\n"
						+"CQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJuZXQgU2VjdXJpdHkgUmVzZWFyY2gg\n"
						+"R3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBYMjAeFw0yMDA5MDQwMDAwMDBaFw00\n"
						+"MDA5MTcxNjAwMDBaME8xCzAJBgNVBAYTAlVTMSkwJwYDVQQKEyBJbnRlcm5ldCBT\n"
						+"ZWN1cml0eSBSZXNlYXJjaCBHcm91cDEVMBMGA1UEAxMMSVNSRyBSb290IFgyMHYw\n"
						+"EAYHKoZIzj0CAQYFK4EEACIDYgAEzZvVn4CDCuwJSvMWSj5cz3es3mcFDR0HttwW\n"
						+"+1qLFNvicWDEukWVEYmO6gbf9yoWHKS5xcUy4APgHoIYOIvXRdgKam7mAHf7AlF9\n"
						+"ItgKbppbd9/w+kHsOdx1ymgHDB/qo0IwQDAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0T\n"
						+"AQH/BAUwAwEB/zAdBgNVHQ4EFgQUfEKWrt5LSDv6kviejM9ti6lyN5UwCgYIKoZI\n"
						+"zj0EAwMDaAAwZQIwe3lORlCEwkSHRhtFcP9Ymd70/aTSVaYgLXTWNLxBo1BfASdW\n"
						+"tL4ndQavEi51mI38AjEAi/V3bNTIZargCyzuFJ0nN6T5U6VR5CmD1/iQMVtCnwr1\n"
						+"/q4AaOeMSQ+2b1tbFfLn\n"
						+"-----END CERTIFICATE-----");
				//Let’s Encrypt E1
				E1=Certificates.decodeCertificatePem(""
						+"-----BEGIN CERTIFICATE-----\n"
						+"MIICxjCCAk2gAwIBAgIRALO93/inhFu86QOgQTWzSkUwCgYIKoZIzj0EAwMwTzEL\n"
						+"MAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2VhcmNo\n"
						+"IEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDIwHhcNMjAwOTA0MDAwMDAwWhcN\n"
						+"MjUwOTE1MTYwMDAwWjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3MgRW5j\n"
						+"cnlwdDELMAkGA1UEAxMCRTEwdjAQBgcqhkjOPQIBBgUrgQQAIgNiAAQkXC2iKv0c\n"
						+"S6Zdl3MnMayyoGli72XoprDwrEuf/xwLcA/TmC9N/A8AmzfwdAVXMpcuBe8qQyWj\n"
						+"+240JxP2T35p0wKZXuskR5LBJJvmsSGPwSSB/GjMH2m6WPUZIvd0xhajggEIMIIB\n"
						+"BDAOBgNVHQ8BAf8EBAMCAYYwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMB\n"
						+"MBIGA1UdEwEB/wQIMAYBAf8CAQAwHQYDVR0OBBYEFFrz7Sv8NsI3eblSMOpUb89V\n"
						+"yy6sMB8GA1UdIwQYMBaAFHxClq7eS0g7+pL4nozPbYupcjeVMDIGCCsGAQUFBwEB\n"
						+"BCYwJDAiBggrBgEFBQcwAoYWaHR0cDovL3gyLmkubGVuY3Iub3JnLzAnBgNVHR8E\n"
						+"IDAeMBygGqAYhhZodHRwOi8veDIuYy5sZW5jci5vcmcvMCIGA1UdIAQbMBkwCAYG\n"
						+"Z4EMAQIBMA0GCysGAQQBgt8TAQEBMAoGCCqGSM49BAMDA2cAMGQCMHt01VITjWH+\n"
						+"Dbo/AwCd89eYhNlXLr3pD5xcSAQh8suzYHKOl9YST8pE9kLJ03uGqQIwWrGxtO3q\n"
						+"YJkgsTgDyj2gJrjubi1K9sZmHzOa25JK1fUpE8ZwYii6I4zPPS/Lgul/\n"
						+"-----END CERTIFICATE-----");

				HandshakeCertificates certificates = new HandshakeCertificates.Builder()
						.addTrustedCertificate(ISRGRootX1)
						.addTrustedCertificate(ISRGRootX2)
						.addTrustedCertificate(E1)
						.build();

				client = new OkHttpClient.Builder()
						.sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
						.build();

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
				Call call = HttpAccess.this.client.newCall(request);

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
					response = HttpAccess.this.client.newCall(request).execute();
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
