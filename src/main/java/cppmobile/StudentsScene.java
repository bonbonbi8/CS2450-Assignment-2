package cppmobile;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.concurrent.CompletableFuture;

/**
 * Students scene implementing the CPP Mobile Students screen.
 * Features three sections (Favorites, Academics, Campus Life) with 2x2 tile grids.
 * Demonstrates mobile UI design principles with consistent styling and feedback.
 */
public class StudentsScene {
    
    private Runnable onBackAction;
    private Label statusLabel;
    private StackPane overlayPane;
    private VBox contentPane;
    
    public Scene createScene() {
        // Main container
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #F5F5F5;");
        
        // Header
        HBox header = createHeader();
        
        // Content area with scroll
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        contentPane = new VBox(20);
        contentPane.setPadding(new Insets(20));
        contentPane.setStyle("-fx-background-color: #F5F5F5;");
        
        // Create sections
        VBox favoritesSection = buildSection("Favorites", 
            "My Classes", "Map", "Dining", "Email");
        VBox academicsSection = buildSection("Academics", 
            "Grades", "Schedule", "Library", "Advising");
        VBox campusLifeSection = buildSection("Campus Life", 
            "Events", "Parking", "Health", "Shuttle");
        
        contentPane.getChildren().addAll(favoritesSection, academicsSection, campusLifeSection);
        scrollPane.setContent(contentPane);
        
        // Status footer
        HBox footer = createFooter();
        
        // Overlay for loading
        overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0,0,0,0.3);");
        overlayPane.setVisible(false);
        
        // Main content with overlay
        StackPane mainContent = new StackPane();
        mainContent.getChildren().addAll(scrollPane, overlayPane);
        
        root.getChildren().addAll(header, mainContent, footer);
        
        return new Scene(root, 380, 780);
    }
    
    private HBox createHeader() {
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;");
        
        // Back button
        Button backButton = new Button("< Back");
        backButton.setStyle(
            "-fx-background-color: #F0F0F0; " +
            "-fx-text-fill: #333333; " +
            "-fx-border-color: #D0D0D0; " +
            "-fx-border-width: 1; " +
            "-fx-background-radius: 6; " +
            "-fx-border-radius: 6; " +
            "-fx-padding: 8 16; " +
            "-fx-font-size: 14; " +
            "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> {
            if (onBackAction != null) {
                onBackAction.run();
            }
        });
        
        // Title - centered in remaining space
        Label titleLabel = new Label("Students");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #333333;");
        
        // Spacer to push title to center
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(backButton, spacer, titleLabel);
        return header;
    }
    
    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 20, 15, 20));
        footer.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; -fx-border-width: 1 0 0 0;");
        
        statusLabel = new Label("Ready");
        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setStyle("-fx-text-fill: #666666;");
        
        footer.getChildren().add(statusLabel);
        return footer;
    }
    
    private VBox buildSection(String title, String tile1, String tile2, String tile3, String tile4) {
        VBox section = new VBox(15);
        section.setPadding(new Insets(20));
        section.setStyle(
            "-fx-background-color: #EAEAEA; " +
            "-fx-background-radius: 12; " +
            "-fx-border-radius: 12;"
        );
        
        // Section title
        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        sectionTitle.setStyle("-fx-text-fill: #333333;");
        
        // 2x2 grid of tiles
        GridPane tileGrid = new GridPane();
        tileGrid.setHgap(15);
        tileGrid.setVgap(15);
        
        // Create tiles
        Button tile1Btn = createTile(tile1, "[B]");
        Button tile2Btn = createTile(tile2, "[M]");
        Button tile3Btn = createTile(tile3, "[D]");
        Button tile4Btn = createTile(tile4, "[E]");
        
        // Add tiles to grid
        tileGrid.add(tile1Btn, 0, 0);
        tileGrid.add(tile2Btn, 1, 0);
        tileGrid.add(tile3Btn, 0, 1);
        tileGrid.add(tile4Btn, 1, 1);
        
        section.getChildren().addAll(sectionTitle, tileGrid);
        return section;
    }
    
    private Button createTile(String label, String emoji) {
        Button tile = new Button();
        tile.setPrefHeight(64);
        tile.setMaxWidth(Double.MAX_VALUE);
        tile.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #D0D0D0; " +
            "-fx-border-width: 1; " +
            "-fx-background-radius: 12; " +
            "-fx-border-radius: 12; " +
            "-fx-padding: 12 16; " +
            "-fx-alignment: center-left; " +
            "-fx-cursor: hand;"
        );
        
        // Tile content
        HBox tileContent = new HBox(12);
        tileContent.setAlignment(Pos.CENTER_LEFT);
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        Label textLabel = new Label(label);
        textLabel.setFont(Font.font("System", 14));
        textLabel.setStyle("-fx-text-fill: #333333;");
        
        tileContent.getChildren().addAll(emojiLabel, textLabel);
        tile.setGraphic(tileContent);
        
        // Hover effect
        tile.setOnMouseEntered(e -> {
            tile.setStyle(
                "-fx-background-color: #F8F8F8; " +
                "-fx-border-color: #B0B0B0; " +
                "-fx-border-width: 1; " +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12; " +
                "-fx-padding: 12 16; " +
                "-fx-alignment: center-left; " +
                "-fx-cursor: hand;"
            );
        });
        
        tile.setOnMouseExited(e -> {
            tile.setStyle(
                "-fx-background-color: white; " +
                "-fx-border-color: #D0D0D0; " +
                "-fx-border-width: 1; " +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12; " +
                "-fx-padding: 12 16; " +
                "-fx-alignment: center-left; " +
                "-fx-cursor: hand;"
            );
        });
        
        // Click handler
        tile.setOnAction(e -> handleTileClick(label));
        
        return tile;
    }
    
    private void handleTileClick(String tileName) {
        showLoadingOverlay("Opening " + tileName + "...");
        
        // Simulate loading with async operation
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // 1 second loading
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            Platform.runLater(() -> {
                hideLoadingOverlay();
                updateStatus("Opening " + tileName + "... done.");
                
                // Reset status after 2 seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> updateStatus("Ready"));
                pause.play();
            });
        });
    }
    
    private void showLoadingOverlay(String message) {
        updateStatus(message);
        
        // Create loading indicator
        VBox loadingBox = new VBox(15);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-padding: 30;"
        );
        
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(40, 40);
        
        Label loadingLabel = new Label("Loading...");
        loadingLabel.setFont(Font.font("System", 14));
        loadingLabel.setStyle("-fx-text-fill: #333333;");
        
        loadingBox.getChildren().addAll(spinner, loadingLabel);
        
        overlayPane.getChildren().clear();
        overlayPane.getChildren().add(loadingBox);
        overlayPane.setVisible(true);
        
        // Disable all tiles
        disableAllTiles(true);
    }
    
    private void hideLoadingOverlay() {
        overlayPane.setVisible(false);
        disableAllTiles(false);
    }
    
    private void disableAllTiles(boolean disable) {
        contentPane.getChildren().forEach(node -> {
            if (node instanceof VBox) {
                VBox section = (VBox) node;
                section.getChildren().forEach(sectionChild -> {
                    if (sectionChild instanceof GridPane) {
                        GridPane grid = (GridPane) sectionChild;
                        grid.getChildren().forEach(tile -> {
                            if (tile instanceof Button) {
                                ((Button) tile).setDisable(disable);
                            }
                        });
                    }
                });
            }
        });
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    public void setOnBackAction(Runnable action) {
        this.onBackAction = action;
    }
}
