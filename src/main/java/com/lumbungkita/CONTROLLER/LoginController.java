package com.lumbungkita.CONTROLLER;

import com.lumbungkita.App;
import com.lumbungkita.DATABASE.AdminDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {

    // menghubungkan elemen UI dari FXML
    @FXML
    private TextField tfUsername;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pfPassword;

    // membuat instance AdminDAO untuk akses database
    private AdminDAO adminDAO = new AdminDAO();

    // method untuk menangani aksi login
    @FXML
    void handleLogin(ActionEvent event) {
        String user = tfUsername.getText();
        String pass = pfPassword.getText();

        // mengecek input kosong
        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Username dan Password tidak boleh kosong!");
            return;
        }

        // mengecek login melalui AdminDAO
        if (adminDAO.cekLogin(user, pass)) {
            // try-catch untuk menangani IOException
            try {
                App.setRoot("DashboardView"); 
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Gagal memuat halaman Dashboard.");
            }
        } else {
            showAlert("Gagal", "Username atau Password salah!");
        }
    }

    // method untuk menampilkan alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}