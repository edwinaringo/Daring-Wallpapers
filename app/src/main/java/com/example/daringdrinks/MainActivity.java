package com.example.daringdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signUp;
    private Button logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        logIn = (Button) findViewById(R.id.logIn);
        logIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.logIn:
                startActivity(new Intent(this, LoginActivity.class));
        }

    }
}