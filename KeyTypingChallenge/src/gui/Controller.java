package gui;
import java.net.URL;
import java.util.ResourceBundle;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfConsole.setText(Constants.CONSOLE_PREFIX);
		tfConsole.setWrapText(true);

		
		tfConsole.positionCaret(tfConsole.getText().length());
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
		root.getStylesheets().add(getClass().getResource("textfield.css").toExternalForm());
		game.keystrokesProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pbKeystrokesTotal.setProgress((newValue.doubleValue()%100)/100 ); 
				if(newValue.intValue() % 100 == 0 && newValue.intValue() <= barStyles.length*100) {
					pbKeystrokesTotal.getStyleClass().add(barStyles[newValue.intValue()/100-1]);
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

		gameMenu.curState = GameMenu.State.READY_FOR_GAME;
	}

	public void startGame() {
		gameMenu.setRunning();
		game.startGame();
		tfConsole.setText("");
		tfConsole.setEditable(false);
		ScrollBar scrollBarv = (ScrollBar)tfConsole.lookup(".scroll-bar:vertical");
		scrollBarv.setDisable(true);
//		tfConsole.textProperty().addListener(
//				new ChangeListener<String>() {
//
//					@Override
//					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//						
//					}
//				}
//				
//				);
	}
	
	public void handleKeypressed(KeyEvent ke) {
 		State state = gameMenu.getCurState();
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
				if (tfConsole.getText().endsWith(">")) {
					ke.consume();
				}
				break;

			case ENTER:

				int lastLine = tfConsole.getText().lastIndexOf(Constants.CONSOLE_PREFIX);
				gameMenu.evalInput(tfConsole.getText().substring(lastLine + 1));
				tfConsole.setText(tfConsole.getText() + "\n" + gameMenu.getOutput() + "\n" + Constants.CONSOLE_PREFIX);
				tfConsole.positionCaret(tfConsole.getText().length());
				ke.consume();
				break;

			default:
				if (ke.getText().equals("<")) {
					ke.consume();
				}
				System.out.println();
				break;
			}
			break;
			
		case READY_FOR_GAME:
			switch (ke.getCode()) {
			case ENTER:
				startGame();
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
		}
		
	}
	
//		if(game.getGameStatusProperty().get() == Gamestatus.RUNNING) {
//			typeNextLetter();
//			game.increaseKeystrokes();
//		} else {
//			switch(ke.getCode()) {
//			case ENTER:
//				
//				startGame();
//				break;
//			default:
//				break;
//			
//			}
//		}

	
	
	
	public void typeNextLetter() {
		String curText = tfConsole.getText();
		tfConsole.setText(curText+exampleCode.charAt(counter++%exampleCode.length()));
	}
	
}
