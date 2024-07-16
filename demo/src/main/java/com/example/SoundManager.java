package com.example;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, Clip> soundEffects;
    private Map<String, Clip> backgroundMusics;
    private Clip currentBackgroundMusic;
    private float volume;
    private boolean isMuted;

    private SoundManager() {
        soundEffects = new HashMap<>();
        backgroundMusics = new HashMap<>();
        volume = 1.0f;
        isMuted = false;
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
        loadBackgroundMusic("main", "/audio/Starting.wav");
        loadBackgroundMusic("mainmenu", "/audio/MainMenu.wav");

        loadBackgroundMusic("Crystal", "/audio/Crystal.wav");
        loadBackgroundMusic("Snowy", "/audio/Snowy.wav");
        loadBackgroundMusic("Taylors", "/audio/Taylors.wav");

        loadBackgroundMusic("Win", "/audio/Win.wav");
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

    private void loadBackgroundMusic(String name, String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                System.err.println("Could not find audio file: " + path);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            backgroundMusics.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Could not load background music: " + path);
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null && !isMuted) {
            clip.stop();
            clip.setFramePosition(0);
            setClipVolume(clip, volume);
            clip.start();
        }
    }

    public void playBackgroundMusic(String name) {
        stopBackgroundMusic(); // Stop current background music if any
        Clip clip = backgroundMusics.get(name);
        if (clip != null) {
            currentBackgroundMusic = clip;
            setClipVolume(clip, volume);
            if (!isMuted) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void stopBackgroundMusic() {
        if (currentBackgroundMusic != null) {
            currentBackgroundMusic.stop();
            currentBackgroundMusic.setFramePosition(0);
        }
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(1, volume));
        if (currentBackgroundMusic != null) {
            setClipVolume(currentBackgroundMusic, this.volume);
        }
        for (Clip clip : soundEffects.values()) {
            setClipVolume(clip, this.volume);
        }
    }

    public void setMute(boolean mute) {
        this.isMuted = mute;
        if (currentBackgroundMusic != null) {
            if (mute) {
                currentBackgroundMusic.stop();
            } else {
                currentBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    private void setClipVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain;
            if (volume > 0) {
                gain = (range * volume) + gainControl.getMinimum();
            } else {
                gain = gainControl.getMinimum();
            }
            gainControl.setValue(gain);
        }
    }
}