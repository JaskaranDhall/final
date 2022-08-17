package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText mEditText;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.GERMAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TouchEvents","Touch Happened");
                int eventType = event.getActionMasked();

                int count = 0;

                switch(eventType){
                    case MotionEvent.ACTION_POINTER_DOWN:
                        count+=1;
                        Log.i("TouchEvents","Double Touch Happened");
                        String text = getString(R.string.pr);
                        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                        if(text.length()>0){
                            if(eventType == MotionEvent.ACTION_POINTER_DOWN){
                                count+=1;
                            }
                            if(count%2==0) {
                                mTTS.stop();
                            }
                        }

                        break;
                }
                return true;
            }
        });


    }

}


