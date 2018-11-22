package gui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Constants;
import main.KeyTypingChallenge;

public class Gui {

	public void startApplication(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL res = KeyTypingChallenge.class.getResource("/mainwindow.fxml");
        loader.setLocation(res);

        BorderPane rootLayout = null;
		try {
			rootLayout = (BorderPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.WINDOW_TITLE);
        primaryStage.show();
	}

}
