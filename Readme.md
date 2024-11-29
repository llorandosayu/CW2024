ReadMe

## My github repository

https://xxx

## Compilation Instructions

1. Download and install JDK 19.0.2
2. Set environment variable JAVA_HOME to the install path of JDK 19.0.2
3. In cmd console, change directory to the root path of the project, run command: 

mvnw package 

to build the project, and run command:

mvnw javafx:run

to run the application.


## Implemented and Working Properly

1. Rename package name from com.example.demo to com.example.retro
2. Fix Bugs: ShieldImage.java, the image file name is not correct, on line 15.
3. Fix Bugs: Controller.java, in update() method, should check if it has already changed to the new level before changing to, if not, will cause "out of memory" error.
4. Refactor: create a new package com.example.retro.level and move level related classes under the package, including LevelOne, LevelParent, LevelTwo, LevelView, LevelViewLevelTwo.
5. Refactor: create a new package com.example.retro.plane and move plane related classes under the package, including Boss, EnemyPlane, FighterPlane, UserPlane.
6. Appear an enhanced weapon occasionally, and the player can move to get the weapon, which can enhance the attack capability.
7. A red heart will show occasionally, and the player can catch it to add a new health for himself.
8. Add a Factory class LevelFactory to create level objects, use "factory method" design pattern.



## Implemented but Not Working Properly

1. Add restart function when the user failed in a level, have added  pressing the ENTER key to restart, but can't continue to play, only cleared the scene.
2. Add a unit test class for LevelFactory, but can't run it successfully.


## Features Not Implemented



## New Java Classes

1. BigGun.java, which is used for a more powerful weapon and the player can get.
2. HealthHeart.java, which is used for adding a health when the player catch it.
3. LevelFactory.java, LevelFactoryTest.java, a factory class and a unit test class.


## Modified Java Classes

1. All classes under package com.example.demo, because of refactoring the package name.
2. Controller.java, the value of the variable LEVEL_ONE_CLASS_NAME has been changed.
3. LevelOne.java, the value of the variable NEXT_LEVEL, because the original class package has been changed.
4. ShieldImage.java, the image file name is not correct, on line 15.
5. Controller.java, in update() method, has a bug which causes OutOfMemory error.
6. LevelOne.java, LevelParent.java, LevelTwo.java, LevelView.java, LevelViewLevelTwo.java, change the package of these classes.
7. Boss.java, EnemyPlane.java, FighterPlane.java, UserPlane.java, change the package of these classes.
8. LevelParent.java, GameOverImage.java, implement the restart function when failed in a level, didn't implement it completely.
9. LevelParent.java, LevelOne.java, LevelTwo.java, add the weapon enhancement function.
10. LevelParent.java, LevelOne.java, LevelTwo.java, FighterPlane.java, LevelView.java, add health when the player catch a health heart.
11. Controller.java, replace the creation of level objects with the factory class.


## Unexpected Problems

1. When renaming package name from com.example.demo to com.example.retro, there is a string variable to record the game level, and it can't be refactored by IntelliJ, 
   and there is compiling error when I build the project. I checked the compiling error several times and found the problem and fix it.
2. Common test cases can't run successfuly because JavaFX applications is different from common applications, and need specific handling.
   





