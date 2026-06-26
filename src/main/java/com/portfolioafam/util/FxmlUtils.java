package com.portfolioafam.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FxmlUtils {

    private FxmlUtils() {
    }

    public static Parent loadFxml(String resourcePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(resourcePath));
        return loader.load();
    }

    public static FXMLLoader loadFxmlLoader(String resourcePath) {
        return new FXMLLoader(FxmlUtils.class.getResource(resourcePath));
    }
}
