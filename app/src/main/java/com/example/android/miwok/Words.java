package com.example.android.miwok;

/**
 * Created by HP on 2/24/2017.
 */

public class Words {
    private String english , miwok;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int image_id = NO_IMAGE_PROVIDED;
    private int audio_id;

    public Words(String english1 , String miwok1 , int image_id , int audio_id) {
        this.setEnglish(english1);
        this.setMiwok(miwok1);
        this.setImage_id(image_id);
        this.setAudio_id(audio_id);
    }

    public Words(String english1 , String miwok1 , int audio_id) {
        this.setEnglish(english1);
        this.setMiwok(miwok1);
        this.setAudio_id(audio_id);
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }


    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMiwok() {
        return miwok;
    }

    public void setMiwok(String miwok) {
        this.miwok = miwok;
    }

    public boolean hasImage() {
        return image_id!=NO_IMAGE_PROVIDED;
    }

    public int getAudio_id() {
        return audio_id;
    }

    public void setAudio_id(int audio_id) {
        this.audio_id = audio_id;
    }
}
