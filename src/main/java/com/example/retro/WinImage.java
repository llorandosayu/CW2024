package com.example.retro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Show when the player wins
 */
public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/retro/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;
	
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
	
	public void showWinImage() {
		this.setVisible(true);
	}

}
