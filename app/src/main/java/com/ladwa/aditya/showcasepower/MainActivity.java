package com.ladwa.aditya.showcasepower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ladwa.aditya.library.ShowCasePower;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ShowCasePower.Builder(this).setTitle("title").setContent("Content").show();
    }
}
