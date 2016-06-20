package com.wqt.individual.rotateimageviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wqt.individual.rotateimageviewdemo.library.RotateImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RotateImageView mRotateImageView;
    Button mButtonStart;
    Button mButtonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRotateImageView= (RotateImageView) findViewById(R.id.rotateImageView);
        mButtonStart= (Button) findViewById(R.id.button_start);
        mButtonStop= (Button) findViewById(R.id.button_stop);
        mButtonStop.setOnClickListener(this);
        mButtonStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start:
                mRotateImageView.startRotate();
                break;
            case R.id.button_stop:
                mRotateImageView.stopRotate();
        }
    }
}
