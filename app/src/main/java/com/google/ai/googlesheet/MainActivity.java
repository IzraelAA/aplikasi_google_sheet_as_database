package com.google.ai.googlesheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button activitynewqr,jj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activitynewqr = findViewById(R.id.btn_genereted_code);
        jj = findViewById(R.id.btn_Look_Qr_Code);
        jj.setOnClickListener(this);
        activitynewqr.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_genereted_code:
                Intent moveIntent = new Intent(MainActivity.this,Generated_Code.class);
               startActivity(moveIntent);
                break;

            case R.id.btn_Look_Qr_Code:
                Intent m =  new Intent(MainActivity.this,ListView.class);
                startActivity(m);
                break;
        }
    }

}
