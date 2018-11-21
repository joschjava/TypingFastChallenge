package gui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Constants;
import main.KeyTypingChallenge;

public class Gui {

	public void startApplication(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL res = KeyTypingChallenge.class.getResource("/mainwindow.fxml");
        loader.setLocation(res);

        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller controller = loader.getController();
        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyPressed(ke -> controller.handleKeypress(ke));
        primaryStage.setScene(scene);
        primaryStage.setTitle(Constants.WINDOW_TITLE);
        primaryStage.show();
	}

}
