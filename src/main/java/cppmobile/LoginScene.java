package cppmobile;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Simple Login placeholder scene for navigation demonstration.
 * Shows basic login form with username/password fields and login button.
 */
public class LoginScene {
    
    private Runnable onLoginAction;
    
    public Scene createScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F5F5F5;");
        
        // Title
        Label titleLabel = new Label("CPP Mobile Login");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #333333;");
        
        // Username field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("System", 14));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(250);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        
        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("System", 14));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(250);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        
        // Login button
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(250);
        loginButton.setPrefHeight(45);
        loginButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 8; " +
            "-fx-border-radius: 8;"
        );
        
        // Set up login action
        loginButton.setOnAction(e -> {
            if (onLoginAction != null) {
                onLoginAction.run();
            }
        });
        
        // Add all components
        root.getChildren().addAll(
            titleLabel,
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginButton
        );
        
        return new Scene(root, 380, 780);
    }
    
    public void setOnLoginAction(Runnable action) {
        this.onLoginAction = action;
    }
}
