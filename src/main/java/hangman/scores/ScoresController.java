package hangman.scores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hangman.scores.model.Score;
import hangman.utils.Messege;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ScoresController implements Initializable {

	//VIEW
	@FXML private HBox view;
	@FXML private ListView<Score> scoresList;

	//MODEL
	private ScoresModel model = new ScoresModel();
	
	//FILES
	public final String PATH_SCORE = "./src/main/resources/files/score.txt";

	public ScoresController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ScoresView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scoresList.itemsProperty().bind(model.scoreListProperty());
		updateScoreList();
	}

	private List<Score> readScoreFile() {
		List<Score> scores = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(PATH_SCORE));

			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",", -1);
				scores.add(new Score(data[0], Integer.parseInt(data[1])));
			}
			br.close();
		} catch (IOException ex) {
			Messege.error("Error", "Read score file error", ex.getMessage());
		}
		return scores;
	}

	public void updateScoreList() {
		List<Score> lista = readScoreFile();
		lista = orderList(lista);
		model.getScoreList().setAll(lista);
	}

	public void writeScore(Score data) {
		try {
			FileWriter fw = new FileWriter(PATH_SCORE, true);
			fw.append(data.getName() + "," + data.getPoints());
			fw.append("\n");
			fw.close();
		} catch (IOException ex) {
			Messege.error("Error", "Write score error", ex.getMessage());
		}
	}

	private List<Score> orderList(List<Score> data) {
		Score aux;
		for (int i = 1; i < data.size(); i++) {
			for (int j = 0; j < data.size() - i; j++) {
				if (data.get(j).getPoints() < data.get(j + 1).getPoints()) {
					aux = data.get(j);
					data.set(j, data.get(j + 1));
					data.set(j + 1, aux);
				}
			}
		}
		return data;
	}

	public HBox getView() {
		return view;
	}
}
