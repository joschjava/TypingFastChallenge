package gui;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import main.Constants;
import main.Game;
import main.Game.Gamestatus;

public class Controller implements Initializable{

	@FXML
	VBox root;
	
	@FXML
	Label console;

	@FXML
	Label lbTime;
	
	@FXML
	Label lbKeystrokesTotal;
	
	@FXML
	Label lbKeystrokesPerSecond;
	
	
	
	int counter = 0;
	
	String exampleCode = "daslkjgasfldkjasdlkfja \n sadfjlksadjflkajfds";

	Game game;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		console.setText("Test");
		console.setMaxWidth(180);
		console.setWrapText(true);
		DoubleProperty timeProperty = new SimpleDoubleProperty(Constants.SECONDS_PER_GAME);
		lbTime.textProperty().bind(timeProperty.asString());
		game = new Game(timeProperty);
		lbKeystrokesTotal.textProperty().bind(game.keystrokesProperty().asString());
	}

	public void startGame() {
		game.startGame();
	}
	
	public void handleKeypress(KeyEvent ke) {
		if(game.getGameStatusProperty().get() == Gamestatus.RUNNING) {
			typeNextLetter();
			game.increaseKeystrokes();
		} else {
			switch(ke.getCode()) {
			case ENTER:
				startGame();
				break;
			default:
				break;
			
			}
		}
	}
	
	public void typeNextLetter() {
		String curText = console.getText();
		console.setText(curText+exampleCode.charAt(counter++%exampleCode.length()));
	}
	
}
