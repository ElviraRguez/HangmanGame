package hangman.words;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WordsModel {

	private ListProperty<String> words;
	private StringProperty wordSelected;

	public WordsModel() {
		words = new SimpleListProperty<String>(FXCollections.observableArrayList());
		wordSelected = new SimpleStringProperty();
	}

	public final ListProperty<String> wordsProperty() {
		return this.words;
	}

	public final ObservableList<String> getWords() {
		return this.wordsProperty().get();
	}

	public final void setWords(final ObservableList<String> words) {
		this.wordsProperty().set(words);
	}

	public final StringProperty wordSelectedProperty() {
		return this.wordSelected;
	}

	public final String getWordSelected() {
		return this.wordSelectedProperty().get();
	}

	public final void setWordSelected(final String wordSelected) {
		this.wordSelectedProperty().set(wordSelected);
	}
}
