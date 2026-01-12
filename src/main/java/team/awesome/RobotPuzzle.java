package team.awesome;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


public class RobotPuzzle extends Application {
    
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Top: Game Board
        GridPane board = createBoard();
        root.setCenter(board);

        // Bottom: Available Robots
        HBox robotPool = createRobotPool();
        root.setBottom(robotPool);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Robot Puzzle Game");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createBoard() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(15);

        grid.getColumnConstraints().add(new ColumnConstraints(120));
        grid.getColumnConstraints().add(new ColumnConstraints(140));
        grid.getColumnConstraints().add(new ColumnConstraints(140));

        // Header
        grid.add(new Label("Student"), 0, 0);
        grid.add(new Label("Robot 1"), 1, 0);
        grid.add(new Label("Robot 2"), 2, 0);

        String[] students = {"John", "Kate", "Liam", "Mia", "Noah"};
        String[] images   = {"John.png", "Kate.png", "Liam.png", "Mia.png", "Noah.png"};

        for (int i = 0; i < students.length; i++) {
            VBox studentView = createStudentView(students[i], images[i]);
            grid.add(studentView, 0, i + 1);
            grid.add(createDropSlot(), 1, i + 1);
            grid.add(createDropSlot(), 2, i + 1);
        }

        return grid;
    }
    

    private StackPane createDropSlot() {
        StackPane slot = new StackPane();
        slot.setPrefSize(120, 40);
        slot.setStyle("-fx-border-color: black; -fx-background-color: #f0f0f0;");

        // Allow drop
        slot.setOnDragDropped(e -> {
        Dragboard db = e.getDragboard();
        boolean success = false;

        if (db.hasString()) {
        String data = db.getString();
        String[] parts = data.split(",");

        // We only care about the image now
        String imageFile = parts[1];

        Image image = new Image(
            getClass().getResourceAsStream("/images/robots/" + imageFile)
        );

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);

        slot.getChildren().clear();
        slot.getChildren().add(imageView);

        success = true;
        }

        e.setDropCompleted(success);
        e.consume();
        });



        return slot;
    }

    private HBox createRobotPool() {
        HBox box = new HBox(15);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);

        String[] robots = {"Cleaning", "Cooking", "Gardening", "Coding", "Security"};
        String[] robotImages = {"cleaning.png", "cooking.png", "gardening.png", "coding.png", "security.png"};

        for (int i = 0; i < robots.length; i++) {
        box.getChildren().add(createDraggableRobot(robots[i], robotImages[i]));
        }

        return box;
    }

    private Label createDraggableRobot(String name) {
    Label label = new Label(name);
    label.setStyle(
        "-fx-border-color: black;" +
        "-fx-padding: 8;" +
        "-fx-background-color: #d0eaff;"
    );
    label.setMinSize(80, 30);
    label.setAlignment(Pos.CENTER);

    label.setOnDragDetected(e -> {
        Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(name);
        db.setContent(content);
        e.consume();
    });

    return label;
}
//create student view with image
private VBox createStudentView(String name, String imageFile) {
    // Load image from resources
    Image image = new Image(
        getClass().getResourceAsStream("/images/" + imageFile)
    );

    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(60);
    imageView.setFitHeight(60);
    imageView.setPreserveRatio(true);

    // Make it circular
    Circle clip = new Circle(30, 30, 30);
    imageView.setClip(clip);

    Label nameLabel = new Label(name);
    nameLabel.setAlignment(Pos.CENTER);

    VBox box = new VBox(5);
    box.setAlignment(Pos.CENTER);
    box.getChildren().addAll(imageView, nameLabel);

    return box;
}

    //create draggable robot with image
    private VBox createDraggableRobot(String name, String imageFile) {
    Image image = new Image(
        getClass().getResourceAsStream("/images/robots/" + imageFile)
    );

    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.setPreserveRatio(true);

    Label label = new Label(name);

    VBox box = new VBox(5);
    box.setAlignment(Pos.CENTER);
    box.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-background-color: #d0eaff;");
    box.getChildren().addAll(imageView, label);

    // Drag logic
    box.setOnDragDetected(e -> {
        Dragboard db = box.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();

        // Send both name and image path
        content.putString(name + "," + imageFile);

        db.setContent(content);
        e.consume();
    });

    return box;
}



}
