package com.example.pendeteksikebisingan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button bHistory, bLamp;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private ImageView img;
    private TextView stats, dbVal;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    int flag =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bHistory = findViewById(R.id.btnHistory);
        bLamp = findViewById(R.id.btnLampCont);
        stats = findViewById(R.id.status_tv);
        dbVal = findViewById(R.id.db_text);
        img = findViewById(R.id.NewPict);

        myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float Val = snapshot.child("db").getValue(Float.class);
                String image = snapshot.child("image").getValue(String.class);
                new DownloadImageTask(img)
                        .execute(image);

                Integer dbx = Math.round(Val);

                dbVal.setText(dbx.toString());
                if (Val >= 80) {
                    stats.setText("Motor Berisik");
                } else {
                    stats.setText("Motor Normal");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LampControl.class);
                startActivity(i);

            }

        });
        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}