package hangman.words;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hangman.utils.Messege;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;

public class WordsController implements Initializable {
	
	//VIEW
	@FXML private HBox view;
	@FXML private ListView<String> wordsListView;
	@FXML private Button addButton;
	@FXML private Button removeButton;

	//MODEL
	WordsModel model = new WordsModel();
	
	//OTHER
	List<String> wordsList = new ArrayList<String>();
	
	//FILES
	public final String PATH_WORDS = "./src/main/resources/files/words.txt";

	public WordsController(List<String> words) throws IOException {
		this.wordsList = words;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WordsView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		removeButton.disableProperty().bind(wordsListView.getSelectionModel().selectedItemProperty().isNull());
		wordsListView.itemsProperty().bind(model.wordsProperty());
		updateListWords();
		model.wordSelectedProperty().bind(wordsListView.getSelectionModel().selectedItemProperty());
	}

	@FXML
	void onAddAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New word");
		dialog.setHeaderText("Add new word at list");
		dialog.setContentText("Word:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			writeDataFile(result.get());
			updateListWords();
		}
	}

	@FXML
	void onRemoveAction(ActionEvent event) {
		removeFile(model.getWordSelected());
		updateListWords();
	}

	private void updateListWords() {
		model.getWords().setAll(this.wordsList);
	}

	private void writeDataFile(String data) {
		try {
			FileWriter fw = new FileWriter(PATH_WORDS, true);
			fw.append(data.toUpperCase());
			fw.append("\n");
			fw.close();
		} catch (IOException ex) {
			Messege.error("Error", "Write Data File", ex.getMessage());
		}
	}

	private void removeFile(String inData) {
		StringBuffer data = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(PATH_WORDS));

			String line;
			while ((line = br.readLine()) != null) {
				if (!line.equals(inData)) {
					data.append(line + "\n");
				}
			}

			FileWriter fw = new FileWriter(new File(PATH_WORDS));
			fw.write(data.toString());
			
			br.close();
			fw.close();

		} catch (IOException ex) {
			Messege.error("Error", "Remove File", ex.getMessage());
		}
	}

	public HBox getView() {
		return view;
	}
}
