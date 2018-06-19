package com.example.pm.assistant.assistant;

/**
 * Classe responsável pelo reconhecimento facial.
 */

public abstract class FacialRecognition {


    /**
     * Tenta reconhecer a face de uma pessoa.
     * @param photo é uma foto de um rosto.
     * @return o id da pessoa no banco de dados caso conhecida e -1 caso contrário.
     */
    public static int recognize(String photo) {
        return -1;
    }
}
