package com.example.android.musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Button nowPlaying = (Button) findViewById(R.id.playlist_now_playing);
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nowPlayingIntent = new Intent(PlaylistActivity.this, MainActivity.class);
                startActivity(nowPlayingIntent);
            }
        });

        Button song = (Button) findViewById(R.id.playlist_song_details);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songIntent = new Intent(PlaylistActivity.this, SongDetailsActivity.class);
                startActivity(songIntent);
            }
        });

        Button buy = (Button) findViewById(R.id.playlist_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buyIntent = new Intent(PlaylistActivity.this, MusicStoreActivity.class);
                startActivity(buyIntent);
            }
        });
    }
}
