package com.example.retro.level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.retro.ActiveActorDestructible;
import com.example.retro.plane.FighterPlane;
import com.example.retro.plane.UserPlane;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

/**
 * Parent class for level related classes, it defines common methods.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private final double bigGunMaximumYPosition;
	private final double healthHeartMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> bigGuns;
	private final List<ActiveActorDestructible> healthHearts;
	
	private int currentNumberOfEnemies;
	private LevelView levelView;
	private boolean isUserPlaneBigGun = false;

	/**
	 * Constructor method, will initialize many objects
	 * @param backgroundImageName
	 * @param screenHeight
	 * @param screenWidth
	 * @param playerInitialHealth
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.bigGuns = new ArrayList<>();
		this.healthHearts = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.bigGunMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.healthHeartMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	protected abstract void spawnBigGuns();

	protected abstract void spawnHealthHearts();

	/**
	 * Restart the game of current level, but it doesn't work correctly.
	 * need to fix bugs.
	 */
	private  void restartGame() {
		levelView.getGameOverImage().hideGameOverImage();
		root.getChildren().clear();
		initializeScene();
		startGame();
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();

		levelView.getGameOverImage().setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.ENTER) restartGame();
			}
		});

		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Key method, will be called frequently, and handle displaying ui components and logics
	 */
	private void updateScene() {
		spawnEnemyUnits();
		spawnBigGuns();
		spawnHealthHearts();;
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleBigGunPenetration();
		handleHealthHeartPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleBigGunCollisions();
		handleHealthHeartCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		root.getChildren().add(background);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);

		if (isUserPlaneBigGun) {
			ActiveActorDestructible anotherProjectile = user.fireProjectile();
			root.getChildren().add(anotherProjectile);
			userProjectiles.add(anotherProjectile);
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		bigGuns.forEach(bigGun -> bigGun.updateActor());
		healthHearts.forEach(healthHeart -> healthHeart.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(bigGuns);
		removeDestroyedActors(healthHearts);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleBigGunCollisions() {
		for (ActiveActorDestructible actor : bigGuns) {
			for (ActiveActorDestructible otherActor : friendlyUnits) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					//TODO: add functions to enhance guns of player plane
					isUserPlaneBigGun = true;
					actor.takeDamage();
				}
			}
		}
	}

	private void handleHealthHeartCollisions() {
		for (ActiveActorDestructible actor : healthHearts) {
			for (ActiveActorDestructible otherActor : friendlyUnits) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					((FighterPlane)otherActor).addHealth();
					actor.takeDamage();
				}
			}
		}
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void handleBigGunPenetration() {
		for (ActiveActorDestructible bigGun : bigGuns) {
			if (bigGunHasPenetratedDefenses(bigGun)) {
				bigGun.destroy();
			}
		}
	}

	private void handleHealthHeartPenetration() {
		for (ActiveActorDestructible healthHeart : healthHearts) {
			if (healthHeartHasPenetratedDefenses(healthHeart)) {
				healthHeart.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.updateHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	private boolean bigGunHasPenetratedDefenses(ActiveActorDestructible bigGun) {
		return Math.abs(bigGun.getTranslateX()) > screenWidth;
	}

	private boolean healthHeartHasPenetratedDefenses(ActiveActorDestructible healthHeart) {
		return Math.abs(healthHeart.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		levelView.getGameOverImage().requestFocus();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected int getCurrentNumberOfBigGuns() {
		return bigGuns.size();
	}

	protected int getCurrentNumberOfHealthHearts() {
		return healthHearts.size();
	}

	protected void addBigGun(ActiveActorDestructible bigGun) {
		bigGuns.add(bigGun);
		root.getChildren().add(bigGun);
	}

	protected void addHealthHeart(ActiveActorDestructible healthHeart) {
		healthHearts.add(healthHeart);
		root.getChildren().add(healthHeart);
	}

	protected double getBigGunMaximumYPosition() {
		return bigGunMaximumYPosition;
	}

	protected double getHealthHeartMaximumYPosition() {
		return bigGunMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
