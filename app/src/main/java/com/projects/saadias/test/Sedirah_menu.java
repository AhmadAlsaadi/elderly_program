package com.projects.saadias.test;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Sedirah_menu extends AppCompatActivity implements View.OnClickListener{
    MediaPlayer voice = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedirah_menu);


    }
    public void playSound(String myID) {

        int resID = getResources().getIdentifier(myID, "raw", getPackageName());

        voice = MediaPlayer.create(this, resID);
        voice.start();

    }


    public void onClick(View view){
        Intent myActivity;

        switch (view.getId()){
            case R.id.quran_button:

                myActivity = new Intent(this, SuratAlkahf.class);
                startActivity(myActivity);
                break;

            case R.id.morning_prayer:
                myActivity = new Intent(this, MorningPrayer.class);
                startActivity(myActivity);
                break;
            case R.id.evening_prayer:
                myActivity = new Intent(this, EveningPrayer.class);
                startActivity(myActivity);
                break;
            case R.id.voice_button:
                playSound("alkahfvoice");
                break;

            case R.id.voice_button2:
                playSound("mprayer");
                break;

            case R.id.voice_button3:
                playSound("eprayer");
                break;


        }
    }

}
