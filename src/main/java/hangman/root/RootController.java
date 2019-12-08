package hangman.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hangman.game.GameController;
import hangman.scores.ScoresController;
import hangman.utils.Messege;
import hangman.words.WordsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class RootController implements Initializable {

	//FILES
	private final String PATH_WORDS = "./src/main/resources/files/words.txt";
	
	//VIEW
	@FXML private TabPane rootTabPane;
	@FXML private Tab gameTab;
	@FXML private Tab wordsTab;
	@FXML private Tab scoreTab;

	//CONTROLLERS
	private WordsController wordsController;
	private ScoresController scoresController;
	private GameController gameController;

	public RootController() throws IOException {
		gameController = new GameController(readWordOfFile());
		wordsController = new WordsController(readWordOfFile());
		scoresController = new ScoresController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RootView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rootTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		gameTab.setContent(gameController.getView());
		wordsTab.setContent(wordsController.getView());
		scoreTab.setContent(scoresController.getView());

		rootTabPane.getSelectionModel().select(gameTab);

		gameController.pointsList.addListener((o, ov, nv) -> {
			scoresController.writeScore(nv);
			scoresController.updateScoreList();
			rootTabPane.getSelectionModel().select(scoreTab);
		});
	}

	private List<String> readWordOfFile() {
		List<String> data = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(PATH_WORDS));
			String line;
			while ((line = br.readLine()) != null) {
				String[] dataLine = line.split(",", -1);
				for (int i = 0; i < dataLine.length; i++) {
					data.add(dataLine[i]);
				}
			}
			br.close();
		} catch (IOException ex) {
			Messege.error("Error", "Read Words of File Error", ex.getMessage());
		}
		return data;
	}

	public TabPane getView() {
		return rootTabPane;
	}
}
