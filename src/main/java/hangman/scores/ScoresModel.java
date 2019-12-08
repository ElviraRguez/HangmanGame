package hangman.scores;

import hangman.scores.model.Score;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScoresModel {
	private ListProperty<Score> scoreList;
	private IntegerProperty scoreSelected;

	public ScoresModel() {
		scoreList = new SimpleListProperty<Score>(FXCollections.observableArrayList());
		scoreSelected = new SimpleIntegerProperty();
	}

	public final ListProperty<Score> scoreListProperty() {
		return this.scoreList;
	}

	public final ObservableList<Score> getScoreList() {
		return this.scoreListProperty().get();
	}

	public final void setScoreList(final ObservableList<Score> scoreList) {
		this.scoreListProperty().set(scoreList);
	}

	public final IntegerProperty scoreSelectedProperty() {
		return this.scoreSelected;
	}

	public final int getScoreSelected() {
		return this.scoreSelectedProperty().get();
	}

	public final void setScoreSelected(final int scoreSelected) {
		this.scoreSelectedProperty().set(scoreSelected);
	}

}
