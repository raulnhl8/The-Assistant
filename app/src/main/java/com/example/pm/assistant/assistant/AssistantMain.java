package com.example.pm.assistant.assistant;


//import android.util.Log;

/**
 * Classe responsável por usar as classes do assistente de forma integrada para realizar a função de
 * assistência ao reconhecimento e recordação de pessoas. Ela implementa a interface Runnable, pois
 * deve ser usada para se executar uma thread.
 */

public class AssistantMain implements Runnable {

    private boolean active;
    private Camera camera;
    private Speaker speaker;

    public AssistantMain() {
        active = false;
    }


    /**
     * Desativa o assistente.
     */
    public void deactivate() {
        this.active = false;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //int i = 0;
        while(active) {
            if(camera.isFace()) {
                int recognizedId = FacialRecognition.recognize(camera.getPicture());
                if(recognizedId != -1) {
                    //Procura no banco de dados
                    //Verifica se as dicas estão ativadas
                    //Speaker.speak("");
                } else {
                    speaker.speak("Você não conhece esta pessoa.");
                }
            }
            //Log.d("AssistantMain", "run: " + i++);
        }
    }
}
