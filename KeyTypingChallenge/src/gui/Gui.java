package gui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Constants;
import main.KeyTypingChallenge;

public class Gui {

	public void startApplication(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL res = KeyTypingChallenge.class.getResource("/mainwindow.fxml");
        loader.setLocation(res);

        StackPane rootLayout = null;
		try {
			rootLayout = (StackPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller controller = loader.getController();
        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.WINDOW_TITLE);
//        primaryStage.setFullScreen(true);
        
        
        
        controller.setupAfterInit();
        primaryStage.show();
	}

}
