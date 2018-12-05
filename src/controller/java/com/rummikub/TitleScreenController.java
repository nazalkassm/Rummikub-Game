package com.rummikub;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TitleScreenController implements Initializable {
	@FXML
	private AnchorPane root;
	@FXML
	private ComboBox<String> cb_PlayerCount;
	@FXML
	private ComboBox<String> cb_Player1;
	@FXML
	private ComboBox<String> cb_Player2;
	@FXML
	private ComboBox<String> cb_Player3;
	@FXML
	private ComboBox<String> cb_Player4;
	@FXML
	private CheckBox ckBx_GameMode;
	@FXML
	private CheckBox ckBx_RigDraw;
	@FXML
	private VBox vb_PlayerStrategies;
	@FXML
	private Button btn_Play;
	@FXML
	private Button btn_chooseFile;
	@FXML
	private Rectangle background;
	@FXML
	private Rectangle title;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		init_background_images();

		cb_PlayerCount.getItems().addAll("2", "3", "4");
		cb_Player1.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player2.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player3.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");
		cb_Player4.getItems().addAll("Human", "Strategy 1", "Strategy 2", "Strategy 3", "Strategy 4");

		for (int i = 0; i < vb_PlayerStrategies.getChildren().size(); i++) {
			vb_PlayerStrategies.getChildren().get(i).setVisible(false);
		}

	}

	@FXML
	public void handlePlayerCountCB(ActionEvent event) {
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());

		for (int i = 0; i < vb_PlayerStrategies.getChildren().size(); i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> currNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);

			if (i < numPlayers) {
				currNode.setVisible(true);
			} else {
				currNode.setVisible(false);
			}
			currNode.setValue("Select");

		}

		btn_Play.setDisable(true);
		btn_chooseFile.setDisable(true);
	}

	@FXML
	public void handleStrategyCB(ActionEvent event) {
		boolean check = true;
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());
		for (int i = 0; i < numPlayers; i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> currNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);
			if (currNode.getValue() == "Select")
				check = false;
		}

		if (check) {
			btn_Play.setDisable(false);
			btn_chooseFile.setDisable(false);
		} else {
			btn_Play.setDisable(true);
			btn_chooseFile.setDisable(true);
		}
	}

	@FXML
	public void handlePlayBtn(ActionEvent event) throws Exception {
		List<Player> players = getPlayers();

		if (players.size() >= 2) {

			Boolean waitAfterEachTurn = false;
			Boolean rigDraw = false;
			Boolean useGUI = true;
			Boolean testingMode = ckBx_GameMode.isSelected();
			Rummy.game = new Game(players, testingMode, rigDraw, waitAfterEachTurn, useGUI);

			// Get the event's source stage, and set the scene to be the game.
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(Rummy.loadScene("MainScreen.fxml"));
		} else {
			Print.print("Unknown strategy selected.");
		}

	}

	@FXML
	public void handleFileBtn(ActionEvent event) throws Exception {

		List<Player> players = getPlayers();
		if (players.size() >= 2) {

			// Get the event's source stage
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Rigged File");
			fileChooser.setInitialDirectory(new File(Constants.INPUT_FILE_DIRECTORY));
			File file = fileChooser.showOpenDialog(stage);

			if (file != null) {
				Boolean waitAfterEachTurn = false;
				Boolean useGUI = true;
				Boolean testingMode = ckBx_GameMode.isSelected();
				Boolean rigDraw = ckBx_RigDraw.isSelected();
				Rummy.game = new Game(players, testingMode, rigDraw, waitAfterEachTurn, useGUI);

				FileParser.reset();
				FileParser.parse(file);
				Rummy.game.stock = FileParser.stock;
				Rummy.game.table = new Table(Rummy.game.stock);

				if (!FileParser.inputError) {
					// set the scene to be the main screen.
					stage.setScene(Rummy.loadScene("MainScreen.fxml"));
				} else {
					Print.print("Input error of some sort!");
				}
			}
		} else {
			Print.print("Unknown strategy selected.");
		}

	}

	private List<Player> getPlayers() {
		int numPlayers = Integer.parseInt(cb_PlayerCount.getValue());
		List<Player> players = new ArrayList<Player>();

		for (int i = 0; i < numPlayers; i++) {
			@SuppressWarnings("unchecked")
			ComboBox<String> currNode = (ComboBox<String>) vb_PlayerStrategies.getChildren().get(i);

			switch (currNode.getValue()) {
			case "Human":
				players.add(new Player("p" + i, new Strategy0()));
				break;
			case "Strategy 1":
				players.add(new Player("p" + i, new Strategy1()));
				break;
			case "Strategy 2":
				players.add(new Player("p" + i, new Strategy2()));
				break;
			case "Strategy 3":
				players.add(new Player("p" + i, new Strategy3()));
				break;
			case "Strategy 4":
				players.add(new Player("p" + i, new Strategy4()));
				break;
			default:
				return new ArrayList<Player>();
			}
		}

		return players;
	}

	public void init_background_images() {
		background.setFill(new ImagePattern(new Image(Constants.TITLE_BG_IMG)));
		title.setFill(new ImagePattern(new Image(Constants.TITLE_IMG)));
	}

}
