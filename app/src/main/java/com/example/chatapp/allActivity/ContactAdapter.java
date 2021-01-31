package com.example.chatapp.allActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>
{

    private Context context;
    private ArrayList<Users> userList;

    public ContactAdapter(Context context, ArrayList<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    public ContactAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_contact,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userName.setText(userList.get(position).getUserName());
        holder.setProfileImage(userList.get(position).getUserProfileUrl());
        holder.fullBodyClick(userList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private CircleImageView profileImage1;
        private CardView fullBody;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.profile_name);
            profileImage1=itemView.findViewById(R.id.profile_image_contact);
            fullBody=itemView.findViewById(R.id.fullBody);

        }
        public void fullBodyClick(Users user){
            fullBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "click "+user, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,MessageActivity.class);
                    intent.putExtra("url",user.getUserProfileUrl());
                    intent.putExtra("name",user.getUserName());
                    intent.putExtra("Uid",user.getUserId());
                    context.startActivity(intent);
                }
            });
        }

        public void setProfileImage(String url){
            Glide.with(context.getApplicationContext()).load(url).placeholder(R.drawable.facebook).into(profileImage1);
            //profileImage.setImageURI(Uri.parse(url));
        }
    }
}
