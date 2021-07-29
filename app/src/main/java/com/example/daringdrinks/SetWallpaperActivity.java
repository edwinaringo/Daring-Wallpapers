package com.example.daringdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SetWallpaperActivity extends AppCompatActivity {

    private ImageView wallpaperIV;
    private Button setWallpaperBtn;
    private String imgUrl;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

        wallpaperIV = findViewById(R.id.idIVWallpaper);
        setWallpaperBtn = findViewById(R.id.idBtnSetWallpaper);
        imgUrl = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(wallpaperIV);
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        setWallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}