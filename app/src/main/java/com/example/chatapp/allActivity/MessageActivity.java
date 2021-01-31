package com.example.chatapp.allActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppCompatImageView backspace;
    private CircleImageView profileImage;
    private ImageView messageSend;
    private TextView name1;
    private AppCompatEditText enterMessage;
    private FirebaseUser firebaseUser;
    private RecyclerView messageRecycleView;
    private FirebaseDatabase firebaseDatabase;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


/////////////////////////////////////////////////////////////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        backspace = (AppCompatImageView) findViewById(R.id.backspace);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        messageSend = (ImageView) findViewById(R.id.message_send);
        name1=findViewById(R.id.profile_name);
        enterMessage=findViewById(R.id.enter_message);
        messageRecycleView=(RecyclerView)findViewById(R.id.message_list);

        //////////////////////////////////////////////////////////

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

///////////////////////////////////////////////////////////////////////////
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        Glide.with(this).load(url).placeholder(R.drawable.facebook).into(profileImage);

        String name=intent.getStringExtra("name");
        name1.setText(name);

        UserId=intent.getStringExtra("Uid");

        ///////////////////////////////////////////////////////////////////

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //////////////////////////////////////////////////////////////////

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        /////////////////////////////////////////////////////////////////////

        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date=df.format(Calendar.getInstance().getTime());

        ///////////////////////////////////////////////////////////////////////
        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=enterMessage.getText().toString().trim();

                if(!message.equals(""))
                {
                   sendmessage(firebaseUser.getUid(),UserId,message,firebaseUser.getEmail(),date);
                   enterMessage.setText("");
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "please enter message", Toast.LENGTH_SHORT).show();
                }
            }
        });



        ///////////////////////////////////////////////////////////////////////////////////
        messageRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageRecycleView.setLayoutManager(linearLayoutManager);
        readData(UserId,firebaseUser.getUid());

    }

    private void sendmessage(String uid, String userId, String message, String email, String date) {

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

//        HashMap<String,Object> data=new HashMap<>();
//        data.put("sender",uid);
//        data.put("receiver",userId);
//        data.put("message",message);
//        data.put("userEmail",email);
//        data.put("date",date);


        reference.child("Chats").push().setValue(new Message(uid,userId,message,email,date));


    }

    private void readData(String userID,String myID)
    {
        ArrayList<Message> usersChats=new ArrayList<>();

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Message chats = dataSnapshot.getValue(Message.class);

                    if((userID.equals(chats.getReceiverId()) && myID.equals(chats.getSenderId())) ||
                            (myID.equals(chats.getReceiverId()) && userID.equals(chats.getSenderId()) ))
                    {
                        usersChats.add(chats);
                    }

                }
                MessageAdapter messageAdapter=new MessageAdapter(getApplicationContext(),usersChats,myID);
                messageRecycleView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}