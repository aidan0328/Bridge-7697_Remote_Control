package ControllerManager;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MianPage extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		//�霈��脖�XML���Scene��oot node
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		
		//憿舐內Stage
		primaryStage.setTitle("Linkit Manager");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
