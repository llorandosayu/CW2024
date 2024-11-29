package com.example.retro.plane;

import com.example.retro.ActiveActorDestructible;

/**
 * Health heart added by me, will add a health count if the user catches it
 */
public class HealthHeart extends FighterPlane {

	private static final String IMAGE_NAME = "heart.png";
	private static final int IMAGE_HEIGHT = 70;
	private static final int HORIZONTAL_VELOCITY = -4;
	private static final int INITIAL_HEALTH = 1;

	public HealthHeart(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
