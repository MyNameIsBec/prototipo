package com.portfolioafam.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SceneManager {

    private static Stage primaryStage;
    private static final Map<String, Scene> scenes = new HashMap<>();
    private static final Map<String, Supplier<Parent>> factories = new HashMap<>();
    private static final int DEFAULT_WIDTH = 1366;
    private static final int DEFAULT_HEIGHT = 768;

    private SceneManager() {
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void registerScene(String name, Parent root, int width, int height) {
        scenes.put(name, new Scene(root, width, height));
    }

    public static void registerScene(String name, Parent root) {
        registerScene(name, root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static void registerSceneFactory(String name, Supplier<Parent> rootFactory) {
        factories.put(name, rootFactory);
    }

    public static void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene == null) {
            Supplier<Parent> factory = factories.get(name);
            if (factory != null) {
                scene = new Scene(factory.get(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
                scenes.put(name, scene);
                factories.remove(name);
            }
        }
        if (scene != null && primaryStage != null) {
            primaryStage.setScene(scene);
        }
    }

    public static Scene getScene(String name) {
        Scene scene = scenes.get(name);
        if (scene == null) {
            Supplier<Parent> factory = factories.get(name);
            if (factory != null) {
                scene = new Scene(factory.get(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
                scenes.put(name, scene);
                factories.remove(name);
            }
        }
        return scene;
    }

    public static void clearScenes() {
        scenes.clear();
        factories.clear();
    }
}
