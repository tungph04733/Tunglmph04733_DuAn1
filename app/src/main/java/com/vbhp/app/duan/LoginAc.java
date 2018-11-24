package com.vbhp.app.duan;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginAc extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    Typeface typeface;
    TextView txtSlogan, txtDuBaoThoiTiet;
    ImageView imgExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSlogan = findViewById(R.id.txtSlogan);
        imgExit = findViewById(R.id.imgExit);

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
       //txtDuBaoThoiTiet = findViewById(R.id.txtDuBaoThoiTiet);
        //typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/VALLERGO.TTF");
        txtSlogan.setTypeface(typeface);
        //txtDuBaoThoiTiet.setTypeface(typeface);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAc.this, SignInAc.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAc.this,SignUpAc.class));
            }
        });

    }
}
