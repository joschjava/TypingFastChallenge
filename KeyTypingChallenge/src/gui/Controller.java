package gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.math3.util.Precision;

import domain.HighscoreObject;
import domain.Language;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import main.Constants;
import main.Game;
import main.GameMenu;
import main.GameMenu.State;

public class Controller implements Initializable{

	@FXML
	BorderPane root;
	
	@FXML
	TextArea tfConsole;

	@FXML
	Label lbTime;
	
	@FXML
	Label lbKeystrokesTotal;
	
	@FXML
	ProgressBar pbKeysPerSecond;
	
	@FXML
	ProgressBar pbKeystrokesTotal;
	
	@FXML
	Label lbHighscoreList;
	
	@FXML 
	Label lbHighscore;
	
	@FXML
	ProgressBar pbTime;
	
	@FXML
	Label lbPower;
	
	@FXML
	Label lbLevel;
	
	int counter = 0;
	
	
	String exampleCode = "@Override\r\n" + 
			"public void displayExportFile() {\r\n" + 
			"	JTextField displayVal = new JTextField(filename);\r\n" + 
			"	displayVal.setEditable(false);\r\n" + 
			"	\r\n" + 
			"	JPanel displayPanel = new JPanel();\r\n" + 
			"	displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS)); //vertically align\r\n" + 
			"	displayPanel.add(new JLabel(\"Export Filename\"));\r\n" + 
			"	displayPanel.add(displayVal);\r\n" + 
			"	displayPanel.setPreferredSize(new Dimension(400,40)); //resize appropriately\r\n" + 
			"	\r\n" + 
			"	final int displayDialog = JOptionPane.showConfirmDialog(null, displayPanel, \r\n" + 
			"			friendlyName() + \" Display\", JOptionPane.OK_CANCEL_OPTION,\r\n" + 
			"			JOptionPane.PLAIN_MESSAGE);\r\n" + 
			"	if (displayDialog == JOptionPane.OK_OPTION) {\r\n" + 
			"		//do nothing\r\n" + 
			"	}\r\n" + 
			"}\r\n" + 
			" ";

	private final String[] barStyles = {"progress1", "progress2", "progress3", "progress4", "progress5", "progress6"};
	

	
	Game game;
	GameMenu gameMenu = new GameMenu();
	ArrayList<HighscoreObject> highscoreList = loadHighscore();

	LanguageChooser languageChooser = new LanguageChooser();
	Language curLanguage = null;
	private Sound sound;
	private final static String SAVE_LOCATION = "highscore.ser";

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sound = new Sound();
		tfConsole.setText("Loading...");
		onGameStatusChanged(State.TYPE_NAME);
		tfConsole.setWrapText(true);
		tfConsole.positionCaret(tfConsole.getText().length());
		DoubleProperty timeProperty = new SimpleDoubleProperty(Constants.SECONDS_PER_GAME);
		lbTime.textProperty().bind(timeProperty.asString());
		timeProperty.addListener((observable, oldValue, newValue) -> { pbTime.setProgress(newValue.doubleValue()/Constants.SECONDS_PER_GAME); });
		game = new Game(timeProperty, gameMenu);
		lbKeystrokesTotal.textProperty().bind(game.keystrokesProperty().asString());
		game.keysPerSecProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double value = (game.keysPerSecProperty().get()/game.getMaxKeysPerSecond()+0.75)/1.8;
				pbKeysPerSecond.setProgress(value); 
			}
		});
		root.getStylesheets().add(getClass().getResource("progress.css").toExternalForm());
		root.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
		game.keystrokesProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pbKeystrokesTotal.setProgress((newValue.doubleValue()%100)/100 ); 
				if(newValue.intValue() % 100 == 0 && newValue.intValue() <= (barStyles.length-1)*100) {
					try {

						int pbNr = newValue.intValue()/100;
//						if(pbNr != 0) {
//							pbKeystrokesTotal.getStyleClass().removeIf(style -> style.equals(barStyles[pbNr-1]));
//						}
						pbKeystrokesTotal.getStyleClass().add(barStyles[pbNr]);
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		});
		tfConsole.getStyleClass().add("textfield-as-label");
		tfConsole.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				handleKeypressed(keyEvent);
			}
		});

		
		
		
		
		
		
		
		
		gameMenu.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				onGameStatusChanged(newValue);
			}
		});

		updateHighscore();
		
//		playEndGameAnimation();
//		gameMenu.stateProperty().set(GameMenu.State.READY_FOR_GAME);
		
	}

	public void setupAfterInit() {
		Node[] gameNodes = getGameNodes();
		for (int j = 0; j < gameNodes.length; j++) {
			gameNodes[j].setOpacity(0.0);
		}
	}
	
	public void startGame() {
		game.startGame(sound);

		ScrollBar scrollBarv = (ScrollBar)tfConsole.lookup(".scroll-bar:vertical");
		scrollBarv.setDisable(true);
	}
	
	public void handleKeypressed(KeyEvent ke) {
 		State state = gameMenu.stateProperty().get();
		switch (state) {
		case TYPE_NAME:

			switch (ke.getCode()) {
			// block cursor control keys.
			case LEFT:
			case RIGHT:
			case UP:
			case DOWN:
			case PAGE_UP:
			case PAGE_DOWN:
			case HOME:
			case END:
			case TAB:
				ke.consume();
				break;

			case DELETE:
			case BACK_SPACE:
				if (tfConsole.getText().endsWith("\n"+Constants.CONSOLE_PREFIX) || tfConsole.getText().length() == 1) {
					ke.consume();
				}
				break;

			case ENTER:

				int lastLine = tfConsole.getText().lastIndexOf(Constants.CONSOLE_PREFIX);
				boolean valid = gameMenu.evalInput(tfConsole.getText().substring(lastLine + 1));
				if(!valid) {
					addConsoleOutput(gameMenu.getOutput());
				}
				ke.consume();
				break;

			default:
				if (ke.getText().equals("<")) {
					ke.consume();
				}
				break;
			}
			break;
			
		case READY_FOR_GAME:
			switch (ke.getCode()) {
			case ENTER:
				gameMenu.stateProperty().set(State.RUNNING);
			default:
				ke.consume();
				break;
			}
		break;
		
		case CHOOSE_LANGUAGE:
			
			switch (ke.getCode()) {
			case ENTER:
				gameMenu.stateProperty().set(State.READY_FOR_GAME);
				break;
				
			case UP:
				curLanguage = languageChooser.getPrevious();
				replaceLastConsoleLine(LanguageChooser.prepareLanguage(curLanguage));
				break;
				
			case DOWN:
				curLanguage = languageChooser.getNext();
				replaceLastConsoleLine(LanguageChooser.prepareLanguage(curLanguage));
				break;
				
			default:
				ke.consume();
				break;
			}
		break;
		
		case RUNNING:
			ke.consume();
			typeNextLetter();
			tfConsole.positionCaret(tfConsole.getText().length());
			game.increaseKeystrokes();
			break;
		
		case FINISHED:
			switch (ke.getCode()) {
			// block cursor control keys.
			case LEFT:
			case RIGHT:
			case UP:
			case DOWN:
			case PAGE_UP:
			case PAGE_DOWN:
			case HOME:
			case END:
			case TAB:
				ke.consume();
				break;

			case DELETE:
			case BACK_SPACE:
				if (tfConsole.getText().endsWith("\n"+Constants.CONSOLE_PREFIX)) {
					ke.consume();
				}
				break;
				
				default:
					break;
			}
			break;
		}
	}

	private void addConsoleOutput(String output, boolean showConsolePrefix) {
		String consolePrefix = "";
		if(showConsolePrefix) {
		 consolePrefix = "\n" + Constants.CONSOLE_PREFIX;
		}
		tfConsole.setText(tfConsole.getText() + "\n" + output + consolePrefix);
		tfConsole.positionCaret(tfConsole.getText().length());
//		tfConsole.setScrollTop(Double.MAX_VALUE);
	}
		
	private void addConsoleOutput(String output) {
		addConsoleOutput(output, true);
	}
	
	private void replaceLastConsoleLine(String output) {
		String prev = tfConsole.getText();
		int lastLineStart = prev.lastIndexOf("\n"+Constants.CONSOLE_PREFIX);
		prev = prev.substring(0, lastLineStart);
		tfConsole.setText(prev + "\n"+Constants.CONSOLE_PREFIX + output);
	}
	
	public void onGameStatusChanged(State newState) {
		switch(newState) {
		case TYPE_NAME:
			tfConsole.setText("");
			addConsoleOutput(gameMenu.getOutput());
			tfConsole.setEditable(true);
			lbHighscore.setVisible(false);
			break;
			
		case CHOOSE_LANGUAGE:
			
			addConsoleOutput(gameMenu.getOutput(), false);
			curLanguage = languageChooser.getFirstLanguage();
			addConsoleOutput(Constants.CONSOLE_PREFIX + LanguageChooser.prepareLanguage(curLanguage), false);
			tfConsole.setEditable(false);
			break;
			
		case READY_FOR_GAME:
			game.reset();
			fadeGameUI(true);
			counter = 0;
			sound.prepareSound();
			addConsoleOutput(gameMenu.getOutput());
			tfConsole.setEditable(false);
			break;
			
		
			
		case RUNNING:
			sound.playSound();
			tfConsole.setText("");
			tfConsole.setEditable(false);
			startGame();
			break;
			
		case FINISHED:
			fadeGameUI(false);
			addConsoleOutput("\n"+gameMenu.getOutput(), false);
			HighscoreObject highscoreUser = new HighscoreObject(gameMenu.getPlayerName(), game.getScore(), curLanguage.getName());
			highscoreList.add(highscoreUser);
			addConsoleOutput(highscoreUser.toString(), false);
			updateHighscore();
			saveHighscore(highscoreList);
			ArrayList<String> endgameText = new ArrayList<String>();
			lbHighscore.setVisible(false);
			endgameText.add("Game Over");
			int place = highscoreList.indexOf(highscoreUser) +1;
			String placeString = String.valueOf(place);
			if(place == 1) {
				placeString+="st";
			} else if (place == 2) {
				placeString+="nd";
			} else if (place == 3) {
				placeString+="rd";
			} else {
				placeString+="th";
			}
			endgameText.add(placeString+" place");
			endgameText.add("Score: "+game.getScore());
			endgameText.add("Keys/s: "+Precision.round(game.keysPerSecProperty().get(), 2));
			playEndGameAnimation(endgameText);
			break;

			
		default:
			tfConsole.setEditable(true);
			break;
		}
	}
	
	//"nano " F�R programmiersprache
	
	public void playEndGameAnimation(ArrayList<String> messages) {
		Glow glow = ((Glow)((DropShadow) lbHighscore.getEffect()).getInput());
	      Timeline glowAnimation = new Timeline(new KeyFrame(Duration.millis(0),
	    		  new KeyValue(glow.levelProperty(), 1.0)),
	    		  new KeyFrame(Duration.millis(200),
			    		  new KeyValue(glow.levelProperty(), 0.0))) ;
	      glowAnimation.setCycleCount(Timeline.INDEFINITE);
	      glowAnimation.setAutoReverse(true);
	      glowAnimation.play();
	      Timeline textChanger = new Timeline();
	   
    	  textChanger.getKeyFrames().add(new KeyFrame(Duration.millis(0), (ActionEvent ae) -> {
        	  lbHighscore.setVisible(true);
    	  }));
	      
    	  for(int i=0; i< messages.size();i++) {
    		  int duration = i*3000;
    		  final String message = messages.get(i);
    		  textChanger.getKeyFrames().add(new KeyFrame(Duration.millis(duration),
    				  new KeyValue(lbHighscore.textProperty(), message)));
    	  }
    	  
    	  textChanger.getKeyFrames().add(new KeyFrame(Duration.millis(messages.size()*3000), (ActionEvent ae) -> {
    		  glowAnimation.stop();
    		  lbHighscore.setVisible(false);
    		  gameMenu.stateProperty().set(State.TYPE_NAME);
    	  }));
    	  
	      textChanger.play();
	     
	}
	
	private void fadeGameUI(boolean fadeIn) {
		double startValue = 0.0;
		double endValue = 1.0;
		if(!fadeIn) {
			startValue = 1.0;
			endValue = 0.0;
		}
		Timeline fadeGameUiTimeline = new Timeline();
		Node[] gameNodes = getGameNodes();
		int fadeInterval = 300;
		for(int i=0;i< gameNodes.length;i++) {
			fadeGameUiTimeline.getKeyFrames().add(generateKeyFrame(gameNodes[i], 0+i*fadeInterval, startValue));
			fadeGameUiTimeline.getKeyFrames().add(generateKeyFrame(gameNodes[i], 1000+i*fadeInterval, endValue));
		}
		fadeGameUiTimeline.play();
	}
	
	private Node[] getGameNodes() {
		Node[] gameNodes = {lbPower, pbKeysPerSecond, lbLevel, pbKeystrokesTotal, lbKeystrokesTotal, lbTime, pbTime};
		return gameNodes;
	}
	
	private KeyFrame generateKeyFrame(Node node, double offset, double value) {
		return new KeyFrame(Duration.millis(offset),
				  new KeyValue(node.opacityProperty(), value));
	}
	
	private void updateHighscore() {
		highscoreList.sort((p1, p2) -> ((Integer)p2.getScore()).compareTo(p1.getScore()));
		StringBuilder highscoreString = new StringBuilder();
		for(int i=0;i<highscoreList.size();i++) {
			highscoreString.append((i +1)+ ". ");
			highscoreString.append(highscoreList.get(i).toString());
			highscoreString.append("\n");
		}
		lbHighscoreList.setText(highscoreString.toString());
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<HighscoreObject> loadHighscore(){
	      ArrayList<HighscoreObject> previous = null;
	      try {
	    	 File file = new File(SAVE_LOCATION);
	    	 if(file.exists()) {
		         FileInputStream fileIn = new FileInputStream(file);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         previous = (ArrayList<HighscoreObject>) in.readObject();
		         in.close();
		         fileIn.close();
	    	 }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } 
	      if(previous == null) {
	    	  previous = new ArrayList<HighscoreObject>();
	      }
	      return previous;
	}
	
	private static void saveHighscore(ArrayList<HighscoreObject> previous) {
		try {
			FileOutputStream fileOut = new FileOutputStream(SAVE_LOCATION);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(previous);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public void typeNextLetter() {
		String curText = tfConsole.getText();
		String code = curLanguage.getSourceCode();
		tfConsole.setText(curText+code.charAt(counter++%code.length()));
	}
	
}
