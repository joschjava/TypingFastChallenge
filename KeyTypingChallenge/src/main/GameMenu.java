package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import main.GameMenu.State;

public class GameMenu {

	public enum State { TYPE_NAME, CHOOSE_LANGUAGE, READY_FOR_GAME, RUNNING, FINISHED,  };
	private ObjectProperty<State> stateProperty= new SimpleObjectProperty<State>();
	StringBuilder cachedInput = new StringBuilder();
	String output = "";
	String username = "";
	
	public GameMenu(){
		stateProperty.addListener((observable, oldValue, newValue) -> {
			System.out.println("New status:" + newValue.toString());
			switch(newValue) {
			case TYPE_NAME:
				onNameState();
				break;
				
			case READY_FOR_GAME:
				onReadyForGameState();
				break;
			
			case CHOOSE_LANGUAGE:
				onChooseLanguage();
				break;
			}
		});
		
		stateProperty.set(State.TYPE_NAME);
	}

	private void onNameState() {
		output = Constants.TEXT_TYPE_NAME;
	}
	
	private void onReadyForGameState() {
		output = "Connected to game as "+username+"\n\n";
		output += Constants.TEXT_START_ENTER;
	}
	
	private void onChooseLanguage() {
		output = "Choose a programming language using the arrow keys";
	}

	public void setFinished() {
		output = "Game over!";
		stateProperty.set(State.FINISHED);
	}
	
	public String getPlayerName() {
		return username;
	}
	
	public boolean evalInput(String input) {
		Pattern p = Pattern.compile("^ssh (\\w+)@game");
		 Matcher m = p.matcher(input);
		 if(m.find()) {
			 username = m.group(1);
			 stateProperty.set(State.CHOOSE_LANGUAGE);
			 return true;
		 } else {
			 output = "Invalid input, try again!";
			 return false;
		 }
	}
	
	public String getOutput() {
		return output;
	}


	public void setRunning() {
		stateProperty.set(State.RUNNING);
	}
	
	public ObjectProperty<State> stateProperty(){
		return stateProperty;	
	}
}
