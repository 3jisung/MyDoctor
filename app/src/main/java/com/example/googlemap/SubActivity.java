package com.example.googlemap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    Intent intent;
    ImageView imageView;
    TextView title;
    TextView address;
    TextView type;
    TextView simpleMap;
    TextView name;
    TextView mainNumber;
    TextView emergencyNumber;
    Button btn_main;
    Button btn_emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Toast.makeText(getApplicationContext(), tel, Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        address = findViewById(R.id.txt_distance);
        type = findViewById(R.id.type);
        simpleMap = findViewById(R.id.simpleMap);
        name = findViewById(R.id.name);
        mainNumber = findViewById(R.id.mainNumber);
        emergencyNumber = findViewById(R.id.emergencyNumber);
        btn_main = findViewById(R.id.btn_main);
        btn_emergency = findViewById(R.id.btn_emergency);

        btn_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!mainNumber.getText().toString().equals("해당사항 없음."))
                {
                    String number = mainNumber.getText().toString();
                    String[] telArray = number.split("-");

                    String tel = "tel:";
                    for(int i = 0; i < telArray.length; i++)
                        tel += telArray[i];

                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                }
                else
                    Toast.makeText(getApplicationContext(), "대표 번호가 없습니다.",
                            Toast.LENGTH_SHORT).show();
            }
        });

        btn_emergency.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(!emergencyNumber.getText().toString().equals("해당사항 없음."))
                {
                    String number = emergencyNumber.getText().toString();
                    String[] telArray = number.split("-");

                    String tel = "tel:";
                    for(int i = 0; i < telArray.length; i++)
                        tel += telArray[i];

                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                }
                else
                    Toast.makeText(getApplicationContext(), "응급실 번호가 없습니다.",
                            Toast.LENGTH_SHORT).show();
            }
        });

        intent = getIntent();

        address.setText(intent.getStringExtra("address"));
        type.setText(intent.getStringExtra("type"));

        if(intent.getStringExtra("simpleMap") != null)
            simpleMap.setText(intent.getStringExtra("simpleMap"));
        else
            simpleMap.setText("해당사항 없음.");

        name.setText(intent.getStringExtra("name"));
        mainNumber.setText(intent.getStringExtra("mainNumber"));

        if(intent.getStringExtra("emergencyNumber") != null)
            emergencyNumber.setText(intent.getStringExtra("emergencyNumber"));
        else
            emergencyNumber.setText("해당사항 없음.");
    }
}
