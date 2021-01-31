package com.example.chatapp.allActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity  {
    private SignInButton googleSignIn;
    private GoogleSignInClient googleSignInClient;
    private static final int SIGN_IN_KEY=10;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("670062193238-ieoooei9bdfgjdtd7g3hrhsu0hdib1lm.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient= GoogleSignIn.getClient(getApplicationContext(),gso);

        googleSignIn=findViewById(R.id.google_signIn);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=googleSignInClient.getSignInIntent();
                //intent.putExtra("SIGN_IN_KEY",SIGN_IN_KEY);
                startActivityForResult(intent,SIGN_IN_KEY);
            }
        });

        /////////////////////////////////////////////////////////////////////
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10){
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);

            if(signInAccountTask.isSuccessful())
            {
                try {
                    GoogleSignInAccount googleSignInAccount=signInAccountTask.getResult(ApiException.class);

                    if(googleSignInAccount!=null)
                    {
                        AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "login sucessfull", Toast.LENGTH_LONG).show();

                                            FirebaseUser user= firebaseAuth.getCurrentUser();
                                            Users users=new Users(user.getUid(),user.getDisplayName(),user.getPhotoUrl().toString());
                                            firebaseDatabase.getReference().child("Users").child(user.getUid()).setValue(users);

                                            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                                            intent.putExtra("profileUrl",user.getPhotoUrl().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "LogIn Failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "LogIn Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(firebaseAuth.getCurrentUser()!=null)
//        {
//            //Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
//            //intent.putExtra("profileUrl",user.getPhotoUrl().toString());
//            //startActivity(intent);
//            //finish();
//        }
    }
}