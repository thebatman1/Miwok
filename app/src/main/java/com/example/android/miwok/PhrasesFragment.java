package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by HP on 2/26/2017.
 */

public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.
                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    public PhrasesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list , container , false);
        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Words("What is your name?", "tinnǝ oyasse'nǝ", R.raw.phrase_what_is_your_name));
        words.add(new Words("My name is...", "oyasset...", R.raw.phrase_my_name_is));
        words.add(new Words("How are you feeling?", "michǝksǝs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Words("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Words("Are you coming?", "ǝǝnǝs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Words("Yes, I'm coming.", "hǝǝ'ǝǝnǝm", R.raw.phrase_im_coming));
        words.add(new Words("Let's go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Words("Come here.", "anni'nem", R.raw.phrase_come_here));

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(new WordAdapter(getActivity(), words, R.color.category_phrases));

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer = MediaPlayer.create(getActivity(), words.get(position).getAudio_id());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
