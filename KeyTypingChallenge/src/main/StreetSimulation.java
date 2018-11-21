package main;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;

import gui.Gui;
import javafx.application.Application;
import javafx.stage.Stage;

public class StreetSimulation extends Application{

	
	
	/**
	 * @param args
	 * @throws AWTException 
	 * @throws HeadlessException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
//	public static void main(String[] args) throws HeadlessException, AWTException, IOException, InterruptedException {
//	    Timer timer = new Timer();
//	    timer.schedule( new ScreenshotTask(), 0, 10000 );
//	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Gui gui = new Gui();
		gui.startApplication(primaryStage);
	}
	
}