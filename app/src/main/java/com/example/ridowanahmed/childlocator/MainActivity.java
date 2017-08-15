package com.example.ridowanahmed.childlocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void parentLoginBtn(View view){
        Intent intent = new Intent(MainActivity.this,ParentLoginActivity.class);
        startActivity(intent);
    }

    public void childLoginBtn(View view){
        Intent intent = new Intent(MainActivity.this,ChildLoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
