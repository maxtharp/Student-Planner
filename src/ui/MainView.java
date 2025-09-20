package ui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import controller.StudentService;

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
            boolean editable = true; // TODO: Decide if this term is editable
            Node pane = createSemesterPane(semester, courses, editable);
            semesterViewerBox.getChildren().add(pane);
        });
    }
    private Node createSemesterPane(String semester, List<String> courses, boolean editable) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10));

        Label title = new Label(semester);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ListView<String> courseList = new ListView<>();
        courseList.getItems().addAll(courses);

        if (!editable) {
            courseList.setDisable(true); // past semesters locked
        } else {
            // ComboBox to select a new course
            ComboBox<String> coursePicker = new ComboBox<>();
            //coursePicker.getItems().addAll(getAllCourseKeys()); // e.g., "CS 101", "MATH 220"

            final HBox controls = gethBox(coursePicker, courseList);
            box.getChildren().addAll(title, courseList, controls);
        }

        if (!editable) {
            box.getChildren().addAll(title, courseList);
        }

        return box;
    }

    private static HBox gethBox(ComboBox<String> coursePicker, ListView<String> courseList) {
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String selected = coursePicker.getValue();
            if (selected != null && !courseList.getItems().contains(selected)) {
                courseList.getItems().add(selected);

            }
        });

        Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(e -> {
            String selected = courseList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                courseList.getItems().remove(selected);

            }
        });

        return new HBox(5, coursePicker, addButton, removeButton);
    }
}
