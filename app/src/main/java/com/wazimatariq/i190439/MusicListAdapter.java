package com.wazimatariq.i190439;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    ArrayList<AudioModel> songlist;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songlist, Context context) {
        this.songlist = songlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.songs_layout,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData=songlist.get(position);
        holder.titletv.setText(songData.getTitle());
        if(MyMediaPlayer.currentIndex==position){
            holder.titletv.setTextColor(Color.parseColor("@white"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer
                        .currentIndex=position;
                Intent intent=new Intent(context,songTitle1.class);
                intent.putExtra("LIST",songlist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titletv;
        ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            titletv =itemView.findViewById(R.id.song_name);
            icon=itemView.findViewById(R.id.pl1);
        }
    }
}
