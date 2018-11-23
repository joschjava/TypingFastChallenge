package gui;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

	private List<File> files;
	private int counter = 6;
	
	
	
	
	
	private MediaPlayer mediaPlayer;
	
	public Sound() {
		files = (List<File>) FileUtils.listFiles(new File("bin/"), FileFilterUtils.suffixFileFilter(".mp3"), null);
		files.addAll((List<File>) FileUtils.listFiles(new File("bin/"), FileFilterUtils.suffixFileFilter(".MP3"), null));
		files.forEach(file-> System.out.println(file.getName()));
		prepareSound();
	}
	
	public void prepareSound() {
		try {
			Media sound = new Media(files.get(counter++%files.size()).toURI().toString());
			mediaPlayer = new MediaPlayer(sound);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playSound() {
		mediaPlayer.play();
	}
	
	public double getTimeStamp() {
		return mediaPlayer.getCurrentTime().toMillis();
	}
	
	public double getMaximumTime() {
		return mediaPlayer.getTotalDuration().toMillis();
	}
	
	public void stopSound() {
		if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
			mediaPlayer.stop();
		}
	}
}
