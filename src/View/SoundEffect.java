/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author FPTSHOP
 */
 public enum SoundEffect {

        MessageReceive("/SoundEffect/messageSound.wav", false), //  Ringtone for Chat message receive
        FileSharing("/SoundEffect/messageSound.wav", false); //   Ringtone for income file
        private Clip clip;
        private boolean loop;

        SoundEffect(String filename, boolean loop){
            try {
                this.loop = loop;
                URL url = this.getClass().getResource(filename);
                AudioInputStream audioIS = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioIS);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.out.println("[SoundEffect]" +e.getMessage());
            }
        }

        public void play(){
            if(clip.isRunning()){
                clip.stop(); //  Stop Audio
            }
            //  Reset Audio from the beginning
            clip.setFramePosition(0);
            clip.start();
            //  Check if audio play contineously
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }

        public void stop(){
            if(clip.isRunning()){
                clip.stop(); //   Stop Audio
            }
        }
    }