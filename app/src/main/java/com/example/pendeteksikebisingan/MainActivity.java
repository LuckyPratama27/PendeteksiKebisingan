package com.example.pendeteksikebisingan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button bWarn,bHistory, blight;
    private ImageView bLamp;
    private SeekBar seekBar;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch simpleSwitch1, simpleSwitch2,simpleSwitch3;
    private TextView stats, dbVal, lamptxt;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private boolean switchState1, switchState2, switchState3;
    int flag =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bHistory = findViewById(R.id.btnHistory);
        stats = findViewById(R.id.status_tv);
        dbVal = findViewById(R.id.db_text);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myRef = database.getReference("light");
                myRef.setValue(progress);
                //Toast.makeText(getApplicationContext(), "seekbar progress: " + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(getApplicationContext(), "seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        myRef = database.getReference("db");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float Val = snapshot.getValue(Float.class);
                Integer dbx = Math.round(Val);

                dbVal.setText(dbx.toString());
                if(Val >= 80){
                    stats.setText("Motor Berisik");
                    //bHistory.setText("DB BESAR");
                    //bHistory.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    stats.setText("AMAN");
                    //bHistory.setText("DB KECIL");
                    //bHistory.setBackgroundColor(getResources().getColor(R.color.pigeon));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });

        //ToggleButton toggle = (ToggleButton) findViewById(R.id.btnLamp);
        //toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if (isChecked) {
                   //myRef = database.getReference("light");
                    // The toggle is enabled
                    //myRef.setValue(1);
              //  } else {
                //    // The toggle is disabled
                 //   myRef = database.getReference("light");
                   // myRef.setValue(0);
               // }
           // }
        //});



//        bWarn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myRef = database.getReference("muffler");
//                myRef.setValue(1);
//            }
//        });

    }
}