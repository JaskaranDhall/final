package com.example.afinal

import androidx.appcompat.app.AppCompatActivity
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.afinal.R
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View.OnTouchListener
import android.view.MotionEvent
import android.view.View
import java.util.*

class Main : AppCompatActivity() {
    private var mTTS: TextToSpeech? = null
    private val mEditText: EditText? = null
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTTS = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS!!.setLanguage(Locale.GERMAN)
                if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language not supported")
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }
        val myLayout = findViewById<View>(R.id.main_layout) as ConstraintLayout
        myLayout.setOnTouchListener { v, event ->
            Log.i("TouchEvents", "Touch Happened")
            val eventType = event.actionMasked
            var count = 0
            when (eventType) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    count += 1
                    Log.i("TouchEvents", "Double Touch Happened")
                    val text = getString(R.string.pr)
                    mTTS!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                    if (text.length > 0) {
                        if (eventType == MotionEvent.ACTION_POINTER_DOWN) {
                            count += 1
                        }
                        if (count % 2 == 0) {
                            mTTS!!.stop()
                        }
                    }
                }
            }
            true
        }
    }
}