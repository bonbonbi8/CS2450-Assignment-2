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
        
        // Back button with Unicode arrow
        Button backButton = new Button("\u2190 Back");
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
            "-fx-border-radius: 12; " +
            "-fx-border-color: #43A047; " +
            "-fx-border-width: 0 0 0 4;"
        );
        
        // Section title with green accent
        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        sectionTitle.setStyle("-fx-text-fill: #43A047;");
        
        // 2x2 grid of tiles
        GridPane tileGrid = new GridPane();
        tileGrid.setHgap(15);
        tileGrid.setVgap(15);
        
        // Create tiles with contextually appropriate emojis
        Button tile1Btn = createTile(tile1, getEmojiForTile(tile1));
        Button tile2Btn = createTile(tile2, getEmojiForTile(tile2));
        Button tile3Btn = createTile(tile3, getEmojiForTile(tile3));
        Button tile4Btn = createTile(tile4, getEmojiForTile(tile4));
        
        // Add tiles to grid
        tileGrid.add(tile1Btn, 0, 0);
        tileGrid.add(tile2Btn, 1, 0);
        tileGrid.add(tile3Btn, 0, 1);
        tileGrid.add(tile4Btn, 1, 1);
        
        section.getChildren().addAll(sectionTitle, tileGrid);
        return section;
    }
    
    private String getEmojiForTile(String tileName) {
        // Return unique, contextually appropriate emojis for each tile
        switch (tileName.toLowerCase()) {
            case "my classes":
                return "\uD83D\uDCDA"; // 📚 Books
            case "map":
                return "\uD83D\uDDFA"; // 🗺️ Map
            case "dining":
                return "\uD83C\uDF7D"; // 🍽️ Dining
            case "email":
                return "\u2709"; // ✉️ Email
            case "grades":
                return "\uD83D\uDCC8"; // 📈 Chart/Grades
            case "schedule":
                return "\uD83D\uDDD3"; // 🕓 Clock/Schedule
            case "library":
                return "\uD83D\uDCD6"; // 📖 Open Book
            case "advising":
                return "\uD83E\uDDD1\u200D\uD83C\uDF93"; // 👨‍🎓 Graduation Cap
            case "events":
                return "\uD83C\uDF89"; // 🎉 Party/Events
            case "parking":
                return "\uD83D\uDE97"; // 🚗 Car/Parking
            case "health":
                return "\uD83D\uDC96"; // 💖 Heart/Health
            case "shuttle":
                return "\uD83D\uDE8C"; // 🚌 Bus/Shuttle
            default:
                return "\uD83D\uDCDA"; // 📚 Default to books
        }
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
        // Try multiple fonts for better emoji support
        try {
            emojiLabel.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, 18));
        } catch (Exception e) {
            try {
                emojiLabel.setFont(Font.font("Arial Unicode MS", FontWeight.BOLD, 18));
            } catch (Exception e2) {
                emojiLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            }
        }
        
        Label textLabel = new Label(label);
        textLabel.setFont(Font.font("System", 14));
        textLabel.setStyle("-fx-text-fill: #333333;");
        
        tileContent.getChildren().addAll(emojiLabel, textLabel);
        tile.setGraphic(tileContent);
        
        // Enhanced hover effect with subtle green tint
        tile.setOnMouseEntered(e -> {
            tile.setStyle(
                "-fx-background-color: #F0F8F0; " +
                "-fx-border-color: #43A047; " +
                "-fx-border-width: 2; " +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12; " +
                "-fx-padding: 12 16; " +
                "-fx-alignment: center-left; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(67,160,71,0.3), 8, 0, 0, 2);"
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
                "-fx-cursor: hand; " +
                "-fx-effect: null;"
            );
        });
        
        // Click handler
        tile.setOnAction(e -> handleTileClick(label));
        
        return tile;
    }
    
    private void handleTileClick(String tileName) {
        showLoadingOverlay("Loading " + tileName + "...");
        
        // Simulate loading with async operation
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // 1 second loading
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            Platform.runLater(() -> {
                hideLoadingOverlay();
                updateStatus(tileName + " opened successfully.");
                
                // Reset status after 2 seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> updateStatus("Ready"));
                pause.play();
            });
        });
    }
    
    private void showLoadingOverlay(String message) {
        updateStatus(message);
        
        // Create enhanced loading indicator
        VBox loadingBox = new VBox(15);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12; " +
            "-fx-padding: 30; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);"
        );
        
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(40, 40);
        spinner.setStyle("-fx-progress-color: #43A047;");
        
        Label loadingLabel = new Label("Loading...");
        loadingLabel.setFont(Font.font("System", 14));
        loadingLabel.setStyle("-fx-text-fill: #43A047;");
        
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
