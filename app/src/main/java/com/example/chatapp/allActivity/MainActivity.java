package com.example.chatapp.allActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Thread.*;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                   // if(null != firebaseUser.getUid()){
                       // startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                    //}else{
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    //}
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}