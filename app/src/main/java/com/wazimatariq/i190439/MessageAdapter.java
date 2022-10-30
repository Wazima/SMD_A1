package com.wazimatariq.i190439;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context c;
    private List<MessageModel> messageModelList;

    public MessageAdapter(Context c){
        this.c=c;
        messageModelList=new ArrayList<>();
    }

    public void add(MessageModel userModel){
        messageModelList.add(userModel);
        notifyDataSetChanged();
    }

    public void clear(){
        messageModelList.clear();
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.savedchat,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel=messageModelList.get(position);
        holder.msg.setText(messageModel.getMsg());
        if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.msgLayout.setBackgroundColor(c.getResources().getColor(R.color.teal_700));
            holder.msg.setBackgroundColor(c.getResources().getColor(R.color.white));
        }else{
            holder.msgLayout.setBackgroundColor(c.getResources().getColor(R.color.black));
            holder.msg.setBackgroundColor(c.getResources().getColor(R.color.white));

        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout msgLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.msg);
            msgLayout=itemView.findViewById(R.id.msgLayout);
        }
    }
}
