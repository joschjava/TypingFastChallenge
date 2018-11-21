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
import main.StreetSimulation;

public class Gui {

	public void startApplication(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        URL res = StreetSimulation.class.getResource("/streetgui.fxml");
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
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
