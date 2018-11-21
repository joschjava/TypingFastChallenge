package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu {

	public enum State { TYPE_NAME, READY_FOR_GAME, RUNNING };
	public State curState = State.TYPE_NAME;
	StringBuilder cachedInput = new StringBuilder();
	String output = "";
	String username = "";
	
	public void openMenu() {
		setNameState();
	}
	
	private void setNameState() {
		curState = State.TYPE_NAME;
		output = Constants.TEXT_TYPE_NAME;
	}
	
	public void evalInput(String input) {
		Pattern p = Pattern.compile("^ssh (\\w+)@game");
		 Matcher m = p.matcher(input);
		 if(m.find()) {
			 username = m.group(1);
			 output = "Connected to game as "+username;
			 curState = State.READY_FOR_GAME;
		 } else {
			 output = "Invalid input, try again!";
		 }
		cachedInput = new StringBuilder();
	}
	
	public String getOutput() {
		return output;
	}
	
	public State getCurState() {
		return curState;
	}

	public void setRunning() {
		curState = State.RUNNING;
	}
}
