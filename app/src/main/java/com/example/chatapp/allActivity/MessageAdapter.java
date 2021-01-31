package com.example.chatapp.allActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyView> {

    private Context context;
    private ArrayList<Message> messages;
    private FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();;
    String currentUser;

    public static int MSG_TYPE_LEFT=0;
    public static int MSG_TYPE_RIGHT=1;

    public MessageAdapter(Context context, ArrayList<Message> messages,String currentUser) {
        this.context = context;
        this.messages = messages;
        currentUser=this.currentUser;
    }

    @NonNull
    @Override
    public MessageAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==MSG_TYPE_RIGHT){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout,parent,false);
            return new MyView(view);
        }
        else{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_layout,parent,false);
            return new MyView(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyView holder, int position) {


        Message message=messages.get(position);
        holder.senderMessage.setText(message.getMessage());
        //holder.reciverDateandTime.setText(message.getDate());

        //String s=firebaseUser.getUid().trim();
//
//        if(messages.get(position).getSenderId().equals(currentUser)){
//
//            holder.receiver.setVisibility(View.GONE);
//            holder.senderMessage.setText(messages.get(position).getMessage());
//            holder.senderDateandTime.setText(messages.get(position).getTime());
//      }
//       else{
//            holder.sender.setVisibility(View.GONE);
//            holder.receiverMessage.setText(messages.get(position).getMessage());
//            holder.reciverDateandTime.setText(messages.get(position).getTime());
//        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyView extends RecyclerView.ViewHolder {

        private CardView receiver;
        private TextView receiverMessage;
        private TextView reciverDateandTime;
        private CardView sender;
        private TextView senderMessage;
        private TextView senderDateandTime;


        public MyView(@NonNull View itemView) {
            super(itemView);

            sender = itemView.findViewById(R.id.sender);
            senderMessage = itemView. findViewById(R.id.senderMessage);
            senderDateandTime =itemView. findViewById(R.id.senderDateandTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(firebaseUser.getUid().equals(messages.get(position).getSenderId())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
