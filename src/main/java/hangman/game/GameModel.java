package hangman.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class GameModel {

	private IntegerProperty points;
	private IntegerProperty lives;
	private StringProperty wordHide;
	private StringProperty letters;
	private StringProperty newLetter;
	private ObjectProperty<Image> hagmanImage;

	public GameModel() {
		points = new SimpleIntegerProperty();
		lives = new SimpleIntegerProperty();
		wordHide = new SimpleStringProperty();
		letters = new SimpleStringProperty();
		newLetter = new SimpleStringProperty();
		hagmanImage = new SimpleObjectProperty<Image>();
	}

	public final IntegerProperty pointsProperty() {
		return this.points;
	}

	public final int getPoints() {
		return this.pointsProperty().get();
	}

	public final void setPoints(final int points) {
		this.pointsProperty().set(points);
	}

	public final IntegerProperty livesProperty() {
		return this.lives;
	}

	public final int getLives() {
		return this.livesProperty().get();
	}

	public final void setLives(final int lives) {
		this.livesProperty().set(lives);
	}

	public final StringProperty wordHideProperty() {
		return this.wordHide;
	}

	public final String getWordHide() {
		return this.wordHideProperty().get();
	}

	public final void setWordHide(final String wordHide) {
		this.wordHideProperty().set(wordHide);
	}

	public final StringProperty lettersProperty() {
		return this.letters;
	}

	public final String getLetters() {
		return this.lettersProperty().get();
	}

	public final void setLetters(final String letters) {
		this.lettersProperty().set(letters);
	}

	public final StringProperty newLetterProperty() {
		return this.newLetter;
	}

	public final String getNewLetter() {
		return this.newLetterProperty().get();
	}

	public final void setNewLetter(final String newLetter) {
		this.newLetterProperty().set(newLetter);
	}

	public final ObjectProperty<Image> hagmanImageProperty() {
		return this.hagmanImage;
	}

	public final Image getHagmanImage() {
		return this.hagmanImageProperty().get();
	}

	public final void setHagmanImage(final Image hagmanImage) {
		this.hagmanImageProperty().set(hagmanImage);
	}
}
