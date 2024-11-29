package com.example.retro.plane;

import com.example.retro.ActiveActorDestructible;
import com.example.retro.projectile.EnemyProjectile;

/**
 * New weapon added by me, will enhance the user plane's weapon
 */
public class BigGun extends FighterPlane {

	private static final String IMAGE_NAME = "biggun.png";
	private static final int IMAGE_HEIGHT = 110;
	private static final int HORIZONTAL_VELOCITY = -4;
	private static final int INITIAL_HEALTH = 1;

	public BigGun(double initialXPos, double initialYPos) {
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
