package com.example.android.musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MusicStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_store);

        Button song = (Button) findViewById(R.id.store_song_details);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songIntent = new Intent(MusicStoreActivity.this, SongDetailsActivity.class);
                startActivity(songIntent);
            }
        });

        Button playlist = (Button) findViewById(R.id.store_playlist);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playlistIntent = new Intent(MusicStoreActivity.this, PlaylistActivity.class);
                startActivity(playlistIntent);
            }
        });
    }
}
