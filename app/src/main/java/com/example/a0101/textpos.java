package com.example.a0101;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;

public class textpos extends AppCompatActivity {
    private HttpAccess httpAccess;
    private AssetManager assetManager;
    private ErrorText errorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textpos);
        httpAccess = new HttpAccess("");
        assetManager = textpos.this.getResources().getAssets();
// PHP のエラーメッセージのテキストを定義した JSON 文字列の取得
        try {
            InputStream inputStream = assetManager.open("error.json");
            String json = HttpAccess.readTextAll(inputStream,"UTF-8");
            Gson gson = new Gson();
            errorText = gson.fromJson(json,ErrorText.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textpos.this.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputStream inputStream = assetManager.open("image.png");
                    httpAccess.sendUpload(
// URL ( 必ず指定 )
                            "https://athena.abe-lab.jp/~b9p31081/zemi2021/post/file_upload.php",
// フィールド名
                            "target",
// ファイル名
                            "image.png",
                            inputStream,
// MIME
                            "image/png",
                            new HttpAccess.OnAsyncTaskListener() {
                                @Override
                                public void onAsyncTaskListener(String s) {

                                    JsonParser parser = new JsonParser();
                                    JsonElement element = parser.parse(s);
                                    JsonObject root = element.getAsJsonObject();
                                    String result = root.get("result").getAsString();
                                    JsonObject files = root.get("files").getAsJsonObject();
                                    JsonObject target = files.get("target").getAsJsonObject();
                                    int error = target.get("error").getAsInt();

                                    String ErrorText = errorText.error[error];
                                    Log.i("lightbox", s);
                                    Log.i("lightbox", result);
                                    Log.i("lightbox", ErrorText);


                                }
                            }
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    // *************************************
// assets フォルダの error.json 用
// *************************************
    private class ErrorText {
        String[] error;
    }
}