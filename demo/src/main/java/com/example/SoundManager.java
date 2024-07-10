package com.example;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> soundEffects;
    private Clip backgroundMusic;

    private SoundManager() {
        soundEffects = new HashMap<>();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void loadSounds() {
        // Load sound effects
        loadSoundEffect("jump", "/audio/jump.wav");

        // Load background music
        loadBackgroundMusic("/audio/Starting.wav");
    }

    private void loadSoundEffect(String name, String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedInputStream bis = new BufferedInputStream(is);
             AudioInputStream ais = AudioSystem.getAudioInputStream(bis)) {

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            soundEffects.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Could not load sound file: " + path);
            e.printStackTrace();
        }
    }

    private void loadBackgroundMusic(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                System.err.println("Could not find audio file: " + path);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(ais);

            // Don't close the AudioInputStream here, as the Clip is still using it
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Could not load background music: " + path);
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void setBackgroundMusicVolume(float volume) {
        if (backgroundMusic != null) {
            FloatControl gainControl =
                    (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}