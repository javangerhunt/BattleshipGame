package ch.muehlemann.schiffversenken;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

	public static BattleshipManager battleshipManager = new BattleshipManager();
	public static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		// the following code is about the fxml, i.e. the window with the visual part of the program that pops up when running the program
		HelloApplication.stage = stage;
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		fxmlLoader.setController(new HelloController());
		Scene scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Battleship game");
		stage.setScene(scene);
		stage.show();
		battleshipManager.generateShips();
	}

	public static void main(String[] args) {
		launch();
	}
}
