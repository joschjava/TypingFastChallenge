package gui;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
	
	@FXML
	ProgressBar pbKeysPerSecond;
	
	@FXML
	ProgressBar pbKeystrokesTotal;
	
	int counter = 0;
	
	String exampleCode = "daslkjgasfldkjasdlkfja \n sadfjlksadjflkajfds";
//	, "progress3", "progress4", "progress5", "progress6"
	private final String[] barStyles = {"progress1", "progress2"};
	
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
		lbKeystrokesPerSecond.textProperty().bind(game.keysPerSecProperty().asString());
		game.keysPerSecProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double value = (game.keysPerSecProperty().get()/game.getMaxKeysPerSecond()+0.75)/1.8;
				System.out.println(value);
				pbKeysPerSecond.setProgress(value); 
			}
		});
		root.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());
		game.keystrokesProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pbKeystrokesTotal.setProgress((newValue.doubleValue()%100)/100 ); 
				if(newValue.intValue() % 100 == 0 && newValue.intValue() <= barStyles.length*100) {
					pbKeystrokesTotal.getStyleClass().add(barStyles[newValue.intValue()/100-1]);
				}
			}
		});
		
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
