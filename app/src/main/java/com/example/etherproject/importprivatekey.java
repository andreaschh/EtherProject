package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muddzdev.styleabletoast.StyleableToast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class importprivatekey extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importprivatekey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        Button submit = findViewById(R.id.btn_submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FileInputStream fin = openFileInput("pk.txt");
                    DataInputStream din = new DataInputStream(fin);
                    InputStreamReader isr = new InputStreamReader(din);
                    BufferedReader br = new BufferedReader(isr);

                    int i = 0;
                    String lines[] = new String[3];
                    String line1;

                    while ((line1 = br.readLine()) != null) {
                        lines[i] = line1;
                        i++;
                    }
                    TextView name = findViewById(R.id.txt_name);
                    TextView password = findViewById(R.id.txt_Password);
                    TextView line1matching=findViewById(R.id.line1matching);
                    TextView line2matching=findViewById(R.id.line2matching);
                    String p;
                    p = name.getText().toString();
                    String p2;
                    p2 = password.getText().toString();

                    line2matching.setText(lines[1]);
                    line1matching.setText(lines[2]);

                    String p3 = line1matching.getText().toString();
                    String p4 = line2matching.getText().toString();



                    if ((p.equals(p4)) && (p2.equals(p3))) {
                        StyleableToast.makeText(getApplicationContext(), "You have successfully sign-in!", Toast.LENGTH_LONG, R.style.customToast).show();
                                Intent in=new Intent(importprivatekey.this,Dashboard.class);
                        startActivity(in);
                    } else {
                        StyleableToast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG, R.style.mistakeToast).show();
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}