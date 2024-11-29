package com.example.retro.level;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
class LevelFactoryTest {
    @Test
    void testCreateLevel() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        LevelParent levelOne = LevelFactory.createLevel("com.example.retro.level.LevelOne", 100, 100);
        assertInstanceOf(LevelOne.class, levelOne);

        LevelParent levelTwo = LevelFactory.createLevel("com.example.retro.level.LevelTwo", 100, 100);
        assertInstanceOf(LevelTwo.class, levelTwo);
    }
}