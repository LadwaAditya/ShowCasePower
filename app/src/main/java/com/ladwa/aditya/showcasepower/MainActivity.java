package com.ladwa.aditya.showcasepower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ladwa.aditya.library.ShowCasePower;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDemo, txtBottom, txtHello;
    private Button btnReveal;
    private View mCurrentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDemo = (TextView) findViewById(R.id.txt_demo);
        txtBottom = (TextView) findViewById(R.id.txt_bottom);
        txtHello = (TextView) findViewById(R.id.textView);
        btnReveal = (Button) findViewById(R.id.btn_reveal);
        mCurrentView = txtDemo;

        txtDemo.setOnClickListener(this);
        txtBottom.setOnClickListener(this);
        txtHello.setOnClickListener(this);
        btnReveal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reveal:
                new ShowCasePower.Builder(this)
                        .setTarget(mCurrentView)
                        .setTitle("Title")
                        .setContent("Content").show();
                break;
            case R.id.txt_bottom:
                mCurrentView = txtBottom;
                break;
            case R.id.txt_demo:
                mCurrentView = txtDemo;
                break;
            case R.id.textView:
                mCurrentView = txtHello;
                break;
        }
    }
}
