package com.example.chatapp.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar myToolbar;
    private CircleImageView profileImage;
    private RecyclerView chatList;
    private FirebaseDatabase firebaseDatabase;
    private ContactAdapter contactAdapter;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        myToolbar=findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ////////////////////////////////////////////////////////////////////////////////
        profileImage=findViewById(R.id.profile_image);

        ////////////////////////////////////////////////////////////////////////////////
        chatList=findViewById(R.id.chatList);

        chatList.setHasFixedSize(true);
        chatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ArrayList<Users> usersArrayList=new ArrayList<>();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersArrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Users user = dataSnapshot.getValue(Users.class);
                    if(!user.UserId.equals(firebaseUser.getUid()))
                         usersArrayList.add(user);
                }
                contactAdapter=new ContactAdapter(getApplicationContext(),usersArrayList);
                chatList.setAdapter(contactAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //////////////////////////////////////////////////////////
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        String url=intent.getStringExtra("profileUrl");
        Glide.with(getApplicationContext()).load(url).placeholder(R.drawable.facebook).into(profileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}