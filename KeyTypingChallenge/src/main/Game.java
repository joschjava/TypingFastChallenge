package main;

import org.apache.commons.math3.util.Precision;

import gui.Sound;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Game {

	private Timeline timeline;
	private DoubleProperty timeProperty;
	public static enum Gamestatus { RUNNING, STOPPED };
//	private ObjectProperty<Gamestatus> gamestatus = new SimpleObjectProperty<Gamestatus>(Gamestatus.STOPPED);
	private IntegerProperty keystrokes = new SimpleIntegerProperty(0);
	private DoubleProperty keysPerSecond = new SimpleDoubleProperty(0);
	private double maxKeysPerSecond = 0.0;
	private long gamestartms = 0;
	private GameMenu gameMenu;
	
	public Game (DoubleProperty timeProperty, GameMenu gameMenu){
		this.timeProperty = timeProperty;
		this.gameMenu = gameMenu;
	}
	
	public IntegerProperty keystrokesProperty() {
		return keystrokes;
	}
	
	public DoubleProperty keysPerSecProperty() {
		return keysPerSecond;
	}
	
	public int getScore() {
		return keystrokes.get();
	}
	
	public void startGame(Sound sound) {
		keystrokes.set(0);
		maxKeysPerSecond = 30.0;
		startTimer(sound);
	}
	
	public double getMaxKeysPerSecond() {
		return maxKeysPerSecond;
	}
	
	public void increaseKeystrokes() {
		int numKeystrokes = keystrokes.get();
		keystrokes.set(numKeystrokes+1);
		double keysPerSecondValue = ((double)numKeystrokes)/(System.currentTimeMillis()-gamestartms)*1000;
		keysPerSecond.set(keysPerSecondValue);
		if(keysPerSecondValue > maxKeysPerSecond) {
			maxKeysPerSecond = keysPerSecondValue;
		}
	}
	
	private void startTimer(Sound sound) {
        if (timeline != null) {
            timeline.stop();
        }
       
		if (sound.getMaximumTime() >= Constants.SECONDS_PER_GAME * 1000) {
			AnimationTimer countdown = new AnimationTimer() {
				private long countdownTime = Constants.SECONDS_PER_GAME * 1000;

				@Override
				public void handle(long timestamp) {
					timeProperty.set(Precision.round((countdownTime - sound.getTimeStamp())/1000, 1));
					if (timeProperty.get() <= 0) {
						timeProperty.set(0);
						stop();
					}
				}

				@Override
				public void stop() {
					gameMenu.setFinished();
					super.stop();
				}
			};
			countdown.start();
        } else {
            AnimationTimer countdown = new AnimationTimer() {

                private static final long STOPPED = -1 ;
                private long lastTime = STOPPED;
                private long countdownTime = Constants.SECONDS_PER_GAME*1000;

                @Override
                public void handle(long timestamp) {
                    if (lastTime == STOPPED) {
                        gameMenu.setRunning();
                        lastTime = timestamp;
                        timeProperty.set(countdownTime);
                    }
                    long elapsedNanos = timestamp - lastTime ;
                    long elapsedMillis = elapsedNanos / 1_000_000 ;
                    countdownTime -= elapsedMillis;

                    timeProperty.set(Precision.round(countdownTime/1000.0, 1));
                    if(countdownTime <= 0) {
                    	timeProperty.set(0);
                    	stop();
                    }
                    lastTime = timestamp;
                }

                @Override
                public void stop() {
                	gameMenu.setFinished();
                    super.stop();
                }
            };
            countdown.start();
        }
        
       
        
//        timeline = new Timeline(new KeyFrame(Duration.millis(100),
//        		  		new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	timeProperty.set(Math.floor((timeProperty.get()-0.1)*10)/10);
//            	if(timeProperty.get() == 0) {
//            		timeline.stop();
//            	}
//            }
//            
//        }));
//        timeline.getStatus();
//		timeline.statusProperty().addListener(new ChangeListener<Status>() {
//			@Override
//			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {
//				if(newValue.equals(Status.PAUSED) || newValue.equals(Status.STOPPED)) {
//					gameMenu.setFinished();
//				} else {
//					gameMenu.setRunning();
//				}
//			}
//		});
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
        gamestartms  = System.currentTimeMillis();
	}

}
