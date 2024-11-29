package com.example.retro.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.retro.level.LevelFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.retro.level.LevelParent;

/**
 * Major controller class, controls how to display ui components, and controls keyboard inputs.
 */
public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.retro.level.LevelOne";
	private final Stage stage;

	private String currentLevelName = LEVEL_ONE_CLASS_NAME;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * When launching, first go to the one level
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}

	/**
	 * Level switching method, such as from level one to level two
	 * @param className
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			LevelParent myLevel = LevelFactory.createLevel(className, stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}

	/**
	 * Abstract method from the parent class, will be called frequently, to update ui components
	 * @param arg0     the observable object.
	 * @param arg1   an argument passed to the {@code notifyObservers}
	 *                 method.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {

			if (!this.currentLevelName.equals(arg1)) {
				this.currentLevelName = (String) arg1;
				goToLevel((String) arg1);
			}

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
			e.printStackTrace();
		}
	}

}
