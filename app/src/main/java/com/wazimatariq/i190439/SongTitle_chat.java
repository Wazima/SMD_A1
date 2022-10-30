package com.wazimatariq.i190439;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wazimatariq.i190439.databinding.ActivitySongTitleChatBinding;

import java.util.UUID;

public class SongTitle_chat extends AppCompatActivity {

    ActivitySongTitleChatBinding binding;
    String recieverId;
    DatabaseReference databaseReferenceSender,databaseReferenceReciever;
    String senderRoom,recieverRoom;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySongTitleChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recieverId=getIntent().getStringExtra("id");

        senderRoom= FirebaseAuth.getInstance().getUid()+recieverId;
        recieverRoom= recieverId+FirebaseAuth.getInstance().getUid();

        messageAdapter=new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReciever= FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=binding.comment.getText().toString();
                if(message.trim().length()>0){
                    sendMessage(message);
                }
            }
        });
    }

    private void sendMessage(String message){
        String msgId= UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(msgId,FirebaseAuth.getInstance().getUid(),message);

        messageAdapter.add(messageModel);

        databaseReferenceSender
                .child(msgId)
                .setValue(messageModel);
        databaseReferenceReciever
                .child(msgId)
                .setValue(messageModel);
    }
}