package hangman.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import hangman.scores.model.Score;
import hangman.utils.Messege;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class GameController implements Initializable {
	
	//VIEW
	@FXML private BorderPane view;
	@FXML private TextField newLetterText;
	@FXML private Button letterButton;
	@FXML private Button resolveButton;
	@FXML private Label wordHideLabel;
	@FXML private Label lettersLabel;
	@FXML private ImageView hagmanImage;
	@FXML private Label pointsLabel;
	@FXML private Label livesLabel;

	//MODEL
	private GameModel model = new GameModel();

	//OTHER
	public ObjectProperty<Score> pointsList = new SimpleObjectProperty<>();
	
	private List<String> listWords = new ArrayList<>();
	private String wordGame = "";
	private String wordGameWithSpaces = "";
	private String wordHide = "";
	private String lettersUserTry = "";
	private int numTry = 1;
	private int lives = 8;
	private  int points = 0;
	
	//FILES
	public final URL PATH_IMAGES = getClass().getResource("/images");
	
	public GameController(List<String> words) throws IOException {
		this.listWords = words;
		selectRandomWord();
		guessWord();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hagmanImage.imageProperty().bind(model.hagmanImageProperty());
		model.setHagmanImage(new Image(new File(PATH_IMAGES + "/" + numTry + ".png").toURI().toString()));
		
		wordHideLabel.textProperty().bind(model.wordHideProperty());
		lettersLabel.textProperty().bind(model.lettersProperty());
		model.newLetterProperty().bind(newLetterText.textProperty());
		livesLabel.textProperty().bindBidirectional(model.livesProperty(), new NumberStringConverter());
		pointsLabel.textProperty().bindBidirectional(model.pointsProperty(), new NumberStringConverter());
		model.setLives(lives);

		model.livesProperty().addListener((o, ov, nv) -> {
			if(nv.intValue() == 0) {
				Messege.information("You lose", "", "The lives are end.");
			}
		});
		
		model.wordHideProperty().addListener((o,ov,nv) ->{
			if(!nv.contains("_")) {
				Optional<String> result = Messege.inputDialog("You win", "", "Please, enter the player name.");
				String playerName = "";
				if (result.isPresent()){
					playerName = result.get();
				}
				if(playerName.isEmpty()) {
					playerName = "Anonymous";
				}
				pointsList.setValue(new Score(playerName, model.getPoints()));
			}
		});

		letterButton.disableProperty().bind(model.newLetterProperty().isEmpty().or(model.livesProperty().isEqualTo(0)));
		resolveButton.disableProperty().bind(model.newLetterProperty().isEmpty().or(model.livesProperty().isEqualTo(0)));
	}

	@FXML
	void onLettersAction(ActionEvent event) {
		String data = model.getNewLetter().toUpperCase();
		if (!data.isBlank() && data.length() == 1 && Pattern.matches("^[a-zA-Z]*$", data)) {
			boolean isLetter = false;
			StringBuilder guessWord = new StringBuilder(this.wordHide);
			for (int i = 0; i < wordGameWithSpaces.length(); i++) {
				if (wordGameWithSpaces.charAt(i) == data.charAt(0)) {
					guessWord.setCharAt(i, data.charAt(0));
					model.setPoints(++points);
					isLetter = true;
				}
			}

			if (!isLetter) {
				numTry++;
				model.setHagmanImage(new Image(new File(PATH_IMAGES + "/" + numTry + ".png").toURI().toString()));
				model.setLives(--lives);
			}
			this.lettersUserTry += data + " ";
			this.wordHide = guessWord.toString();
			model.setWordHide(this.wordHide);
			model.setLetters(lettersUserTry);
		} else {
			Messege.error("Error", "", "The data introduce is invalid. Only are valid numeric characters.");
		}

		newLetterText.setText("");
	}

	@FXML
	void onResolveAction(ActionEvent event) {
		String data = model.getNewLetter().toUpperCase();
		if (!data.isBlank() && data.length() == wordGame.length() && Pattern.matches("^[a-zA-Z]*$", data)) {
			boolean isEqual = true;
			int i = 0;
			while(isEqual && i != wordGame.length()) {
				if(wordGame.charAt(i) != data.charAt(i)) {
					isEqual = false;
				}
				i++;
			}
			
			if(isEqual) {
				points = wordGame.length();
				model.setPoints(points);
				model.setWordHide(this.wordGameWithSpaces);
			} else {
				numTry++;
				model.setHagmanImage(new Image(new File(PATH_IMAGES + "/" + numTry + ".png").toURI().toString()));
				model.setLives(--lives);
				
				this.lettersUserTry += data + " ";
				model.setLetters(lettersUserTry);
			}
		}
	}

	private void guessWord() {
		for (int i = 0; i < wordGameWithSpaces.length(); i++) {
			if (wordGameWithSpaces.charAt(i) != ' ') {
				wordHide += "_ ";
			}
		}
		model.setWordHide(wordHide.toString());
	}
	

	private void selectRandomWord() {
		int pos = (int) (Math.random() * listWords.size());
		wordGame = listWords.get(pos);
		String result = "";
		for (int i = 0; i < wordGame.length(); i++) {
			result += wordGame.charAt(i) + " ";
		}
		wordGameWithSpaces = result;
	}
	

	public BorderPane getView() {
		return view;
	}
}
