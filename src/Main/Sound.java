package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[]=new URL[30];

    public Sound(){
        soundURL[0]=getClass().getResource("/sounds/stalker.wav");
        soundURL[1]=getClass().getResource("/sounds/menu.wav");
        soundURL[2]=getClass().getResource("/sounds/damage.wav");
        soundURL[3]=getClass().getResource("/sounds/menuBump.wav");

    }

    public void setFile(int i){
        try{
            AudioInputStream ais= AudioSystem.getAudioInputStream(soundURL[i]);
            clip =AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){clip.stop();}
}