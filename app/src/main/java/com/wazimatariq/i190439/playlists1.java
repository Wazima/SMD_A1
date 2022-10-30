package com.wazimatariq.i190439;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class playlists1 extends AppCompatActivity {
    RecyclerView rv;
    TextView no_song_found;
    ArrayList<AudioModel> songlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists1);

        rv=findViewById(R.id.recyclerview);
        no_song_found=findViewById(R.id.no_song_found);

        if(checkPermission() == false){
            requestPermission();
            return;
        }

        String[] projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection=MediaStore.Audio.Media.IS_MUSIC +" !=0";

        Cursor cursor=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while (cursor.moveToNext()){
            AudioModel songData=new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2) );
            if(new File(songData.getPath()).exists())
                songlist.add(songData);
        }

        if(songlist.size()==0){
            no_song_found.setVisibility(View.VISIBLE);
        }else{
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(new MusicListAdapter(songlist,getApplicationContext()));
        }
    }

    boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(playlists1.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(playlists1.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(playlists1.this, "Read permission is required, please allow from settings", Toast.LENGTH_SHORT).show();

        } else {
            ActivityCompat.requestPermissions(playlists1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rv!=null){
            rv.setAdapter(new MusicListAdapter(songlist,getApplicationContext()));
        }
    }
}