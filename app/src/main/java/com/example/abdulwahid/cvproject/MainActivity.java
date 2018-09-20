package com.example.abdulwahid.cvproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.isityou.sdk.IsItYouSdk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String MY_CODE = "R3JoeBpA271NzmQ8";
    private static final int CAMERA_ROTATION =0 ;
    private IsItYouSdk iiy;
    int REQUEST_CODE = 0;
    public static int count = 0;

    private Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iiy = IsItYouSdk.getInstance(getApplicationContext());
        iiy.setAS(false);
        int initResult = iiy.init(MY_CODE, CAMERA_ROTATION);
        Toast.makeText(this, ""+initResult, Toast.LENGTH_SHORT).show();
        Button capture = (Button) findViewById(R.id.btnCapture);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            myBitmap=photo;
            int response=iiy.saveEnrollment(getBytesFromBitmap(myBitmap),0);

            Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();


        }
    }
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}
