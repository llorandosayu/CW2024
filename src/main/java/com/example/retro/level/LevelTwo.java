package com.example.retro.level;

import com.example.retro.ActiveActorDestructible;
import com.example.retro.plane.BigGun;
import com.example.retro.plane.Boss;
import com.example.retro.plane.HealthHeart;

/**
 * The implementation class for level two, will show ui components for level one, and handle specific logics of level two
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/retro/images/background2.jpg";
	private static final double BIG_GUN_SPAWN_PROBABILITY = .002;
	private static final double HEALTH_HEART_SPAWN_PROBABILITY = 0.03;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected void spawnBigGuns() {
		if (getCurrentNumberOfBigGuns() == 0) {
			if (Math.random() < BIG_GUN_SPAWN_PROBABILITY) {
				double newBigGunInitialYPosition = Math.random() * getBigGunMaximumYPosition();
				ActiveActorDestructible bigGun = new BigGun(getScreenWidth(), newBigGunInitialYPosition);
				addBigGun(bigGun);
			}
		}
	}

	@Override
	protected void spawnHealthHearts() {
		if (getCurrentNumberOfHealthHearts() == 0) {
			if (Math.random() < HEALTH_HEART_SPAWN_PROBABILITY) {
				double newHealthHeartInitialYPosition = Math.random() * getHealthHeartMaximumYPosition();
				ActiveActorDestructible healthHeart = new HealthHeart(getScreenWidth(), newHealthHeartInitialYPosition);
				addHealthHeart(healthHeart);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

}
