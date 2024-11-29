package com.example.retro.level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory class to create level objects. Use "factory method" design pattern
 */
public class LevelFactory {

    /**
     * Creation method for level objects
     * @param levelClassName
     * @param height
     * @param width
     * @return LevelParent
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static LevelParent createLevel(String levelClassName, double height, double width) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> myClass = Class.forName(levelClassName);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        LevelParent myLevel = (LevelParent) constructor.newInstance(height, width);

        return myLevel;
    }
}
