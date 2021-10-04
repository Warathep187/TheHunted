import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class playmusic {
    private Clip clip;
    void playmusic(String musicLocation){
        try {
            File MusicPath = new File(musicLocation);
            if (MusicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(MusicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                if(musicLocation.equals("./src/sound/BGSONG.wav")&&musicLocation!="./src/sound/rickroll.wav"){clip.loop(clip.LOOP_CONTINUOUSLY);};
                if (musicLocation.equals("./src/sound/rickroll.wav")){clip.loop(clip.LOOP_CONTINUOUSLY);}
            }
            else{
                System.out.println("asd");
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
    void stopmusic(){
        clip.stop();
        clip.flush();
    }
}
