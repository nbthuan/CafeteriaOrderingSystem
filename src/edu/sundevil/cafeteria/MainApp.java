//package edu.sundevil.cafeteria;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.scene.Parent;
//
//import java.net.URL;
//
//public class MainApp extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        DBHelper.init(); // SQLite setup
//
//        URL fxml = getClass().getResource("/menu_view.fxml");
//        if (fxml == null) throw new IllegalStateException("FXML not found");
//
//        Parent root = FXMLLoader.load(fxml);
//        primaryStage.setTitle("Cafeteria Menu Manager");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

package edu.sundevil.cafeteria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        DBHelper.init(); // Ensure DB is ready
        loadScene("login_view.fxml", "Cafeteria Login");
    }

    public static void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/" + fxmlFile));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
