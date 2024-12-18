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
// Müzik çalma için thread sınıfı
class MusicPlayer implements Runnable {
    private String musicFilePath;

    public MusicPlayer(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

    @Override
    public void run() {
        while (true) {
            playMusic();
        }
    }

    private void playMusic() {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Müziği sürekli çalar
            clip.start();
            // Arka planda sonsuza kadar müzik çalacağı için thread beklemeye geçiyor
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}