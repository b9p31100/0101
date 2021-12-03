package com.example.a0101;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttppost extends AsyncTask<String, Void, String> {

    String responseBody;

    @Override
    protected String doInBackground(String... params) {
        String url = "https://athena.abe-lab.jp/~b9p31081/zemi2021/post/pass_check.php";
        MediaType media = MediaType.parse("text/plain");
        String text = "word"+params[0];

        try {
            RequestBody requestBody = RequestBody.create(media,text);

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();

            return responseBody;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("a",result);
    }
}


