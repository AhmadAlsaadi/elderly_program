package com.projects.saadias.test;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.InputStream;

public class MorningPrayer extends AppCompatActivity implements View.OnClickListener{

    private String [] mPrayer;
    private MediaPlayer morningPrayer=new MediaPlayer();
    private View clickedButton;
    private int numLooping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrayer=openFile("morning_prayer.txt");
        makeLayout(mPrayer);

    }
    // creating public function that return text array
    // that is read from a file named f.
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
            //Log.d("this example",mPrayer[0] + "," + mPrayer[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    // public function for making the activity layout.
    // it takes a string array to set the text properties of the buttons.
    public void makeLayout(String [] textFile) {
        LinearLayout myLinearLayout = new LinearLayout(this);
        ScrollView myscroll = new ScrollView(this);
        myLinearLayout.setOrientation(LinearLayout.VERTICAL);
        myLinearLayout.setBackgroundColor(Color.parseColor("#000000"));
        for (int i = 1; i < textFile.length; i = i + 2) {
            Button mybutton = new Button(this);
            mybutton.setText(mPrayer[i]);
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
        morningPrayer.release();

    }

    public void playSound(String myID) {

        int resID = getResources().getIdentifier(myID.replaceAll("\\r\\n", ""), "raw", getPackageName());


        if (morningPrayer.isPlaying()) {
            morningPrayer.stop();
            morningPrayer = MediaPlayer.create(this, resID);
            morningPrayer.start();
            //Log.d("this example", "this " );
        } else {
            morningPrayer = MediaPlayer.create(this, resID);
            morningPrayer.start();

        }


        morningPrayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            int n = 1;

            @Override
            public void onCompletion(MediaPlayer morningPrayer) {
                if (n < numLooping) {
                    n+=1;
                    morningPrayer.start();
                }else {
                    clickedButton.setBackgroundColor(Color.parseColor("#1CDAD3"));
                }
            }
        });
    }

    public String [] audioTitle(int buttonID){
        String [] numRepeate = mPrayer[buttonID-1].split(",");

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
