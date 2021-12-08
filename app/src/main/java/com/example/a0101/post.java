package com.example.a0101;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class post extends AppCompatActivity {
    private final static int RESULT_CAMERA = 1001;
    private ImageView imageView;
    private Uri cameraUri;
    private Button camera,ocr;
    private HttpAccess httpAccess;
    private AssetManager assetManager;
    private ErrorText errorText;
    private String file,fileName;
    private InputStream image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        camera=findViewById(R.id.camera_button);
        ocr=findViewById(R.id.post_button);
        imageView = findViewById(R.id.image_view);

        httpAccess = new HttpAccess("");
        assetManager = post.this.getResources().getAssets();
        // PHP のエラーメッセージのテキストを定義した JSON 文字列の取得
        try {
            InputStream inputStream = assetManager.open("error.json");
            String json = HttpAccess.readTextAll(inputStream,"UTF-8");
            Gson gson = new Gson();
            errorText = gson.fromJson(json, ErrorText.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        camera.setOnClickListener(v ->{
            if(isExternalStorageWritable()){
                cameraIntent();
            }
        });

        ocr.setOnClickListener(v -> {
            if(cameraUri == null){
                Toast.makeText(post.this,"写真を撮影してください",Toast.LENGTH_SHORT).show();
            }else{
                try {
                    image =getContentResolver().openInputStream(cameraUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    httpAccess.sendUpload(
                            // URL ( 必ず指定 )
                            "https://athena.abe-lab.jp/~b9p31081/zemi2021/post/file_upload.php",
                            // フィールド名
                            "target",
                            // ファイル名
                            fileName,
                            image,
                            // MIME
                            "image/jpeg",
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
                                    if(result == "ocrが実行されませんでした"){
                                        Toast.makeText(post.this,"読み取れませんでした。もう一度取り直してください。",Toast.LENGTH_SHORT).show();
                                    }
                                    Log.d("ocr",result);
                                }
                            }
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cameraIntent(){
        Context context = getApplicationContext();
        //保存先のフォルダー
        File cFolder = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        SimpleDateFormat dataFormat =new SimpleDateFormat("yyyyMMddHHmmss");
        Date now =new Date(System.currentTimeMillis());
        String fileDate = dataFormat.format(now);
        // ファイル名
        fileName = "Camara_"+fileDate+".jpg";
        Log.d("log",fileName);

        File cameraFile = new File(cFolder, fileName);
        cameraUri = FileProvider.getUriForFile(
                post.this,
                context.getPackageName() + ".fileprovider",cameraFile);
        file =new File(cameraUri.getPath()).getName();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        //noinspection deprecation
        startActivityForResult(intent, RESULT_CAMERA);
    }
    @Override

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == RESULT_CAMERA) {
            if(cameraUri != null && isExternalStorageReadable()){
                imageView.setImageURI(cameraUri);
            }
            else{
                Log.d("debug","cameraUri == null");
            }
        }
    }
    //外部ストレージが読み書き可能かどうかの確認
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    //外部ストレージが少なくとも読み取り可能であるかどうか
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }
    private class ErrorText {
        String[] error;
    }
}