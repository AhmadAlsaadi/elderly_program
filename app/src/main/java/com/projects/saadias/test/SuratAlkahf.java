package com.projects.saadias.test;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.InputStream;

public class SuratAlkahf extends AppCompatActivity implements View.OnClickListener {
    private String text;
    private String [] verse;
    private MediaPlayer quran=new MediaPlayer();
    private View clickedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssetManager myAssetManager=getAssets();

        try {
            InputStream textFile=myAssetManager.open("quran.txt");
            int size = textFile.available();
            byte[] buffer=new byte[size];
            textFile.read(buffer);
            textFile.close();
            text=new String(buffer);
            verse=text.split("\n");
            //Log.d("this example",verse[0] + "," + verse[1]);
        }catch (Exception e){
            e.printStackTrace();
        }


        LinearLayout myLinearLayout = new LinearLayout(this);
        ScrollView myscroll =new ScrollView(this);
        myLinearLayout.setOrientation(LinearLayout.VERTICAL);
        myLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
        for (int i=0;i<verse.length;i++){
            Button mybutton=new Button(this);
            mybutton.setText(verse[i]);
            mybutton.setTextSize(30f);
            mybutton.setBackgroundColor(Color.parseColor("#c9c9c9"));
            mybutton.setId(i);
            LinearLayout.LayoutParams buttonParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParam.rightMargin=140;
            buttonParam.bottomMargin=5;
            buttonParam.topMargin=5;
            mybutton.setOnClickListener(this);
            myLinearLayout.addView(mybutton,buttonParam);
        }
        myscroll.addView(myLinearLayout);
        setContentView(myscroll);
    }

    @Override
    protected void onPause() {
        super.onPause();
        quran.release();

    }

    public void playSound(String myID){

        int resID=getResources().getIdentifier(myID, "raw", getPackageName());
        if (quran.isPlaying()){
            quran.stop();
            quran=MediaPlayer.create(this, resID) ;
            quran.start();
            //Log.d("this example", "this " );
        }
        else {
            quran=MediaPlayer.create(this,resID) ;
            quran.start();
        }

        quran.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                clickedButton.setBackgroundColor(Color.parseColor("#1CDAD3"));
            }


        });

    }

    public void onClick(View v) {

        String audioID="a"+Integer.toString(v.getId());
        playSound(audioID);
        v.setBackgroundColor(Color.parseColor("#FFE5CC"));
        clickedButton=v;

    }
}
