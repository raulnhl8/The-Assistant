package com.example.pm.assistant.assistant;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Classe responsável por falar as dicas e respostas sobre a pessoa que na qual a foto foi tirada.
 */

public class Speaker {

    private TextToSpeech tts;

    /**
     *
     * @param context Contexto da aplicação, basta chama o método getApplicationContext().
     * @param speechRate Velocidade da fala.
     */
    public Speaker(Context context, float speechRate) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale localeBR = new Locale("pt","br");
                    tts.setLanguage(localeBR);
                }
            }
        });

        tts.setSpeechRate(speechRate);
    }

    /**
     * Responsável por pegar uma string e “falar” ela.
     * @param string é a fala que deseja ser reproduzida.
     */
    public void speak(String string) {
        tts.speak(string, TextToSpeech.QUEUE_FLUSH, null);
    }
}