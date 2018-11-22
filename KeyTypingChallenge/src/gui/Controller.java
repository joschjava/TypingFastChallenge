package gui;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domain.HighscoreObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import main.Constants;
import main.Game;
import main.GameMenu;
import main.GameMenu.State;

public class Controller implements Initializable{

	@FXML
	VBox root;
	
	@FXML
	TextArea tfConsole;

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
	
	@FXML
	Label lbHighscoreList;
	
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
//	, "progress3", "progress4", "progress5", "progress6"
	private final String[] barStyles = {"progress1", "progress2"};
	
	Game game;
	GameMenu gameMenu = new GameMenu();
	List<HighscoreObject> highscoreList = new ArrayList<HighscoreObject>();

	private Sound sound;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sound = new Sound();
		tfConsole.setText("Loading...");
		onGameStatusChanged(State.TYPE_NAME);
		tfConsole.setWrapText(true);
		tfConsole.positionCaret(tfConsole.getText().length());
		DoubleProperty timeProperty = new SimpleDoubleProperty(Constants.SECONDS_PER_GAME);
		lbTime.textProperty().bind(timeProperty.asString());
		game = new Game(timeProperty, gameMenu);
		lbKeystrokesTotal.textProperty().bind(game.keystrokesProperty().asString());
		lbKeystrokesPerSecond.textProperty().bind(game.keysPerSecProperty().asString());
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
						//TODO: E
					pbKeystrokesTotal.getStyleClass().add(barStyles[newValue.intValue()/100]);
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

//		gameMenu.stateProperty().set(GameMenu.State.READY_FOR_GAME);
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
				gameMenu.evalInput(tfConsole.getText().substring(lastLine + 1));
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
	}
		
	private void addConsoleOutput(String output) {
		addConsoleOutput(output, true);
	}
	
	public void onGameStatusChanged(State newState) {
		switch(newState) {
		case TYPE_NAME:
			addConsoleOutput(gameMenu.getOutput());
			tfConsole.setEditable(true);
			break;
			
		case READY_FOR_GAME:
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
//			sound.stopSound();
			addConsoleOutput("\n"+gameMenu.getOutput(), false);
			highscoreList.add(new HighscoreObject(gameMenu.getPlayerName(), game.getScore()));
			addConsoleOutput(highscoreList.get(0).toString(), false);
			updateHighscore();
			break;

		

			
		default:
			tfConsole.setEditable(true);
			break;
		}
	}
	
	//"nano " FÜR programmiersprache
	

	private void updateHighscore() {
		highscoreList.add(new HighscoreObject("Tester1", 123));
		highscoreList.add(new HighscoreObject("Tester2", 23));
		highscoreList.add(new HighscoreObject("Tester3", 444));
		highscoreList.sort((p1, p2) -> ((Integer)p2.getScore()).compareTo(p1.getScore()));
		StringBuilder highscoreString = new StringBuilder();
		for(int i=0;i<highscoreList.size();i++) {
			highscoreString.append((i +1)+ ". ");
			highscoreString.append(highscoreList.get(i).toString());
			highscoreString.append("\n");
		}
		lbHighscoreList.setText(highscoreString.toString());
	}
	
	
	public void typeNextLetter() {
		String curText = tfConsole.getText();
		tfConsole.setText(curText+exampleCode.charAt(counter++%exampleCode.length()));
	}
	
}
