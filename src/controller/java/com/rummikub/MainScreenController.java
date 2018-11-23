package com.rummikub;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainScreenController implements Initializable {

	@FXML
	private Label lbl_Player1;
	@FXML
	private Label lbl_Player2;
	@FXML
	private Label lbl_Player3;
	@FXML
	private Label lbl_Player4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startGame();
	}

	public void startGame() {
		Game game = new Game(Rummy.players);

		new Thread() {
			public void run() {
				try {
					game.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

}
