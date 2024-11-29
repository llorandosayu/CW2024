package com.example.retro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Will show when the player failed the game
 */
public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/retro/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
//		setImage(ImageSetUp.getImageList().get(ImageSetUp.getGameOver()));
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	public void hideGameOverImage() {
		this.setVisible(false);
	}

}
