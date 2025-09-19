package ui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import model.StudentService;

import java.util.List;
import java.util.Map;

public class MainView extends Application {

    private StudentService service;
    private VBox semesterViewerBox;
    private TableView<String[]> conflictTable;

    public void start(Stage stage) {
        // Init service (point DataLoader to your CSV files)
        DataLoader loader = new DataLoader();
        service = new StudentService(loader);

        // Search bar
        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Enter student name...");
        Button searchButton = new Button("Search");
        searchBox.getChildren().addAll(new Label("Student:"), searchField, searchButton);
        searchBox.setPadding(new Insets(10));

        // TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        semesterViewerBox = new VBox(10);
        semesterViewerBox.setPadding(new Insets(15));
        Tab semesterTab = new Tab("Semester Viewer", new ScrollPane(semesterViewerBox));

        conflictTable = new TableView<>();
        conflictTable.setPlaceholder(new Label("Select a semester to view conflicts"));
        Tab conflictTab = new Tab("Conflict Chart", conflictTable);

        tabPane.getTabs().addAll(semesterTab, conflictTab);

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(tabPane);

        // Search button action
        searchButton.setOnAction(e -> {
            String name = searchField.getText().trim();
            service.findStudentByName(name).ifPresentOrElse(student -> {
                Map<String, List<String>> schedule = service.getStudentSchedule(student.getID());
                updateSemesterViewer(schedule);
            }, () -> {
                semesterViewerBox.getChildren().clear();
                semesterViewerBox.getChildren().add(new Label("Student not found."));
            });
        });

        // Scene
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Advisor Planning Tool");
        stage.setScene(scene);
        stage.show();
    }

    private void updateSemesterViewer(Map<String, List<String>> schedule) {
        semesterViewerBox.getChildren().clear();
        schedule.forEach((semester, courses) -> {
            ListView<String> courseList = new ListView<>();
            courseList.getItems().addAll(courses);
            TitledPane pane = new TitledPane(semester, courseList);
            semesterViewerBox.getChildren().add(pane);
        });
    }
}
