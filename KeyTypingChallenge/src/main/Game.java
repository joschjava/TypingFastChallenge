package main;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Game {

	private Timeline timeline;
	private DoubleProperty timeProperty;
	public static enum Gamestatus { RUNNING, STOPPED };
	private ObjectProperty<Gamestatus> gamestatus = new SimpleObjectProperty<Gamestatus>(Gamestatus.STOPPED);
	private IntegerProperty keystrokes = new SimpleIntegerProperty(0);
	
	
	public Game (DoubleProperty timeProperty){
		this.timeProperty = timeProperty;
	}
	
	public ObjectProperty<Gamestatus> getGameStatusProperty() {
		return gamestatus;
	}
	
	public IntegerProperty keystrokesProperty() {
		return keystrokes;
	}
	
	public void startGame() {
		startTimer();
		keystrokes.set(0);
	}
	
	public void increaseKeystrokes() {
		keystrokes.set(keystrokes.get()+1);
	}
	
	private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeProperty.set(Constants.SECONDS_PER_GAME);
        
        timeline = new Timeline(new KeyFrame(Duration.millis(100),
        		  		new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	timeProperty.set(Math.floor((timeProperty.get()-0.1)*10)/10);
            	if(timeProperty.get() == 0) {
            		timeline.stop();
            		System.out.println("Timeline stopped");
            	}
            }
            
        }));
        timeline.getStatus();
		timeline.statusProperty().addListener(new ChangeListener<Status>() {

			@Override
			public void changed(ObservableValue<? extends Status> observable, Status oldValue, Status newValue) {
				if(newValue.equals(Status.PAUSED) || newValue.equals(Status.STOPPED)) {
					gamestatus.set(Gamestatus.STOPPED);
				} else {
					gamestatus.set(Gamestatus.RUNNING);
				}
			}
		});
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
	}

}
