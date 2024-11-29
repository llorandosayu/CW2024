package com.example.retro.level;

import com.example.retro.ActiveActorDestructible;
import com.example.retro.plane.BigGun;
import com.example.retro.plane.EnemyPlane;
import com.example.retro.plane.HealthHeart;

/**
 * The implementation class for level one, will show ui components for level one, and handle specific logics of level one
 */
public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/retro/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.retro.level.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final double BIG_GUN_SPAWN_PROBABILITY = .002;
	private static final double HEALTH_HEART_SPAWN_PROBABILITY = 0.03;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget())
			goToNextLevel(NEXT_LEVEL);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
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
}
