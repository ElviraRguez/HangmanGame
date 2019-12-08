package hangman.app;

import hangman.root.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HagmanApp extends Application {
	RootController rootController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		rootController = new RootController();

		primaryStage.setTitle("Hagman Game");
		primaryStage.setScene(new Scene(rootController.getView(), 600, 500));
		primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
