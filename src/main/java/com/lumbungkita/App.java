package com.lumbungkita;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Halaman pertama yang dibuka saat aplikasi dijalankan (LoginView)
        // Ukuran jendela diatur ke 900x600 pixel
        scene = new Scene(loadFXML("LoginView"), 900, 600);
        
        stage.setScene(scene);
        stage.setTitle("LumbungKita - Sistem Informasi Koperasi");
        stage.show();
    }

    // Method statis untuk mengganti tampilan (pindah halaman)
    // Dipanggil dengan: App.setRoot("NamaFileFXML");
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}