package com.app.imgurapi;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.app.imguruploader.ImgurAPI;
import com.app.imguruploader.Response.Image;
import com.app.imguruploader.UploadImageListener;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements UploadImageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImgurAPI imgurAPI = new ImgurAPI(this);
        imgurAPI.init("your IMGR_CLIENT_ID"," your IMGR_CLIENT_SECRT");

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.out);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        Log.d("Bytes" , Arrays.toString(bitMapData));
        imgurAPI.uploadImage(bitMapData);

    }

    @Override
    public void ImgurResponse(boolean status, Image result) {
        if (status) {
            Log.d("link", result.getData().getLink());
        } else {
            Log.d("link", "Error");
        }
    }
}
