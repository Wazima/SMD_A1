package com.wazimatariq.i190439;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class horizontalAdapter extends RecyclerView.Adapter<horizontalAdapter.HorizontalViewHolder>{

    private String[] songs;

    public horizontalAdapter(String[] songs) {
        this.songs = songs;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_playlists1,parent,false);


        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.s1.setText(songs[position]);
    }


    @Override
    public int getItemCount() {
        return songs.length;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        TextView s1;
        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            s1=itemView.findViewById(R.id.pl1);
        }
    }

}
