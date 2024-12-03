package com.example.retro.level;

import com.example.retro.GameOverImage;
import com.example.retro.HeartDisplay;
import com.example.retro.WinImage;
import javafx.scene.Group;

/**
 * Default class for level view, define common methods
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	public WinImage getWinImage() {
		return winImage;
	}

	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	public GameOverImage getGameOverImage() {
		return gameOverImage;
	}

	/**
	 * Update health heart, can add or remove player's HP
	 * 
	 * @param heartsRemaining
	 */
	public void updateHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();

		if (currentNumberOfHearts > heartsRemaining) {
			for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
				heartDisplay.removeHeart();
			}
		} else if (currentNumberOfHearts < heartsRemaining) {
			heartDisplay.addHeart();
		}
	}

}
