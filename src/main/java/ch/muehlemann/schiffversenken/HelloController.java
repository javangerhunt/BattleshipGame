package ch.muehlemann.schiffversenken;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.List;

import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class HelloController {
	@FXML
	private GridPane grid;

	@FXML
	private void initialize() {
		BattleshipManager manager = HelloApplication.battleshipManager;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				// generation of the buttons, so that the player can click on a field and it gets "activated"
				Button button = new Button(" ");
				// button width is set to the same size as the visible fields
				button.setPrefWidth(30);
				button.setPrefHeight(30);
				Position pos = new Position(x, y);
				// as soon as the player clicks on a button it sets it on action and the following occurs:
				button.setOnAction(actionEvent -> {
					// the coordinate of the pressed button gets printed into the console
					System.out.println(pos.getX() + " " + pos.getY());
					// manager checks, whether a ship was hit it sunk or one missed
					AttackResult attackResult = manager.attack(pos);
					// prints out the attack result, i.e. hit, miss, sunk into the console
					System.out.println(attackResult);
					switch (attackResult.getResult()) {
						// in case of a hit, the field shows a red X
						case HIT:
							button.setStyle("-fx-text-fill: red");
							button.setText("X");
							break;
						// in case one missed, the field gets colored grey
						case MISS:
							button.setStyle("-fx-background-color: #474747");
							break;
						/*
						in case it sunk, the field shows a red X and all buttons around the ship
						get disabled & get colored grey
						*/
						case SUNK:
							button.setStyle("-fx-text-fill: red");
							button.setText("X");
							disableButtonsAround(attackResult.getCoordinates());
							break;
					}
					/*
					if all ships have sunk, the game is over and an information window pops up
					 */
					if (manager.isGameOver()) {
						Alert alert = new Alert(INFORMATION);
						alert.setTitle("CONGRATULATIONS");
						alert.setHeaderText("YOU WON");
						alert.setContentText("CONGRATULATIONS YOU WON WITH " + manager.getTries() + " TRIES.");
						alert.showAndWait();
						System.exit(0);
					}
					button.setDisable(true);
				});
				grid.add(button, x, y);
			}
		}
	}

	// the buttons that were pressed and missed, get disabled
	private void disableButtonsAround(List<Position> positions) {
		for (Position pos : positions) {
			disableButtonsAround(pos, positions);
		}
	}

	// disables the buttons around the ship by getting all the coordinates around it
	private void disableButtonsAround(Position pos, List<Position> positions) {
		missButtonAt(pos.getX() - 1, pos.getY(), positions);
		missButtonAt(pos.getX() - 1, pos.getY() - 1, positions);
		missButtonAt(pos.getX() - 1, pos.getY() + 1, positions);
		missButtonAt(pos.getX(), pos.getY() - 1, positions);
		missButtonAt(pos.getX(), pos.getY() + 1, positions);
		missButtonAt(pos.getX() + 1, pos.getY(), positions);
		missButtonAt(pos.getX() + 1, pos.getY() - 1, positions);
		missButtonAt(pos.getX() + 1, pos.getY() + 1, positions);
	}

	/*
	if the button is missed, the program returns and the coordinates get printed into the
	console. It also makes disabled buttons turn grey
	 */
	private void missButtonAt(int x, int y, List<Position> positions) {
		if (x > 9 || y > 9 || x < 0 || y < 0) {
			return;
		}
		System.out.println(x + " " + y);
		Button button = (Button) grid.getChildren().get(x * 10 + (y + 1));
		button.setDisable(true);
		if (!positions.contains(new Position(x, y)))
			button.setStyle("-fx-background-color: #474747");
	}
}
