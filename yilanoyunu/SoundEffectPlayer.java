/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Tunali
 */
// Ses efektlerini oynatmak için thread sınıfı
class SoundEffectPlayer implements Runnable {
    private String soundEffectFilePath;

    public SoundEffectPlayer(String soundEffectFilePath) {
        this.soundEffectFilePath = soundEffectFilePath;
    }
    @Override
    public void run() {
        playSoundEffect();
    }

    private void playSoundEffect() {
        try {
            File soundFile = new File(soundEffectFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            // Ses efektinin bitmesini bekler
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}