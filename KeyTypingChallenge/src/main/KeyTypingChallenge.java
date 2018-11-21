package main;
import gui.Gui;
import javafx.application.Application;
import javafx.stage.Stage;

public class KeyTypingChallenge extends Application{

	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Gui gui = new Gui();
		gui.startApplication(primaryStage);
	}
	
}