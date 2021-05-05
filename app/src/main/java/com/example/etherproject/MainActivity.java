package com.example.etherproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnimportkey, btncreate;

    // Urls of our images.
    String url1 = "https://s.yimg.com/ny/api/res/1.2/n55HG2UKRq9dmU3h_KaarQ--/YXBwaWQ9aGlnaGxhbmRlcjt3PTk2MDtoPTU0MA--/https://s.yimg.com/uu/api/res/1.2/VWrDk.T8dnZe3O0iL8u_fw--~B/aD0xMDgwO3c9MTkyMDthcHBpZD15dGFjaHlvbg--/https://media.zenfs.com/en/gobankingrates_644/d131a0d9a67a1c35de32fe4997c20165";
    String url2 = "https://www.justetf.com/images/thumbnails/etf-investment-guide-theme-ethereum.jpg";
    String url3 = "data:image/png;base64,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";
    String url4="https://miro.medium.com/max/2560/0*MGBh-qPFB9AGeKEB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#0000FF"));
        getSupportActionBar().setBackgroundDrawable(cd);

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        sliderDataArrayList.add(new SliderData(url4));
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        btnimportkey = (Button) findViewById(R.id.btnimportkey);
        btncreate = findViewById(R.id.btncreate);


        btnimportkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(MainActivity.this, importprivatekey.class);
                startActivity(in);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is the login page.You can create a wallet or import a private key to continue", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, wallet.class);
                startActivity(in);

            }
        });

    }
}
