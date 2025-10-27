package cppmobile;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * CPP Mobile Application - Students Screen
 * 
 * A JavaFX application demonstrating mobile-like UI design principles:
 * - Reduced short-term memory load through simplified sections
 * - Consistency and clear signifiers with uniform tile styling
 * - Reversal of actions with internal locus of control
 * - Visibility of system status with informative feedback
 */
public class CPPMobileApp extends Application {
    
    private Stage primaryStage;
    private Scene loginScene;
    private Scene studentsScene;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Create scenes
        LoginScene loginSceneManager = new LoginScene();
        StudentsScene studentsSceneManager = new StudentsScene();
        
        this.loginScene = loginSceneManager.createScene();
        this.studentsScene = studentsSceneManager.createScene();
        
        // Set up navigation
        loginSceneManager.setOnLoginAction(() -> showStudentsScene());
        studentsSceneManager.setOnBackAction(() -> showLoginScene());
        
        // Start with Students scene as requested
        showStudentsScene();
        
        // Configure stage
        primaryStage.setTitle("CPP Mobile");
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    private void showLoginScene() {
        primaryStage.setScene(loginScene);
        primaryStage.setWidth(380);
        primaryStage.setHeight(780);
        
        // Add fade transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), loginScene.getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
    
    private void showStudentsScene() {
        primaryStage.setScene(studentsScene);
        primaryStage.setWidth(380);
        primaryStage.setHeight(780);
        
        // Add fade transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), studentsScene.getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
