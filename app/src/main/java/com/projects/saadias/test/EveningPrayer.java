package com.projects.saadias.test;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.InputStream;

public class EveningPrayer extends SuratAlkahf implements View.OnClickListener {
    private String [] ePrayer;
    private MediaPlayer eveningPrayer=new MediaPlayer();
    private View clickedButton;
    private int numLooping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ePrayer=openFile("evening_prayer.txt");
        makeLayout(ePrayer);

    }

    public String [] openFile(String f) {
        AssetManager myAssetManager = getAssets();
        try {
            InputStream textFile = myAssetManager.open(f);
            int size = textFile.available();
            byte[] buffer = new byte[size];
            textFile.read(buffer);
            textFile.close();
            String text = new String(buffer);
            return text.split("\n");
            //Log.d("this example",ePrayer[0] + "," + ePrayer[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void makeLayout(String [] textFile) {
        LinearLayout myLinearLayout = new LinearLayout(this);
        ScrollView myscroll = new ScrollView(this);
        myLinearLayout.setOrientation(LinearLayout.VERTICAL);
        myLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
        for (int i = 1; i < textFile.length; i = i + 2) {
            Button mybutton = new Button(this);
            mybutton.setText(ePrayer[i]);
            mybutton.setTextSize(30f);
            mybutton.setBackgroundColor(Color.parseColor("#c9c9c9"));
            mybutton.setId(i);
            LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParam.rightMargin = 140;
            buttonParam.bottomMargin = 5;
            buttonParam.topMargin = 5;
            mybutton.setOnClickListener(this);
            myLinearLayout.addView(mybutton, buttonParam);
        }
        myscroll.addView(myLinearLayout);
        setContentView(myscroll);
    }
    @Override
    protected void onPause() {
        super.onPause();
        eveningPrayer.release();

    }


    public void playSound(String myID){

        int resID=getResources().getIdentifier(myID, "raw", getPackageName());

        if (eveningPrayer.isPlaying()){
            eveningPrayer.stop();
            eveningPrayer= MediaPlayer.create(this, resID) ;
            eveningPrayer.start();
            //Log.d("this example", "this " );
        }
        else {
            eveningPrayer=MediaPlayer.create(this,resID) ;
            eveningPrayer.start();

        }

        eveningPrayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            int n = 1;

            @Override
            public void onCompletion(MediaPlayer eveningPrayer) {
                if (n < numLooping) {
                    n+=1;
                    eveningPrayer.start();
                }else {
                    clickedButton.setBackgroundColor(Color.parseColor("#1CDAD3"));
                }
            }
        });
    }




    public String [] audioTitle(int buttonID) {

        String[] numRepeate = ePrayer[buttonID - 1].split(",");

        return numRepeate;
    }
    @Override
    public void onClick(View v) {

            String [] audioName=audioTitle(v.getId());
            playSound(audioName[1]);
            numLooping=Integer.parseInt(audioName[0]);
            v.setBackgroundColor(Color.parseColor("#FFE5CC"));
            clickedButton=v;


    }


}
