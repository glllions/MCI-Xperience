package dashboard.transponders;

import dashboard.transponders.detail.TransponderDetailsFXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import records.Person;
import records.Room;
import records.StatusImages;
import records.Transponder;
import records.data.TransponderData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TranspondersFXMLController {

    public static Person CHOOSE_MODE_PERSON = null;

    @FXML
    private StackPane root;

    @FXML
    private Label labelTitle;

    @FXML
    private TableView<Transponder> tableViewTransponders;

    private static StackPane staticStackPaneContent;

    private static List<Node> listChildNodes = new ArrayList();

    static void setDetailViewFXMLChild(String path) {
        try {
            listChildNodes.clear();
            staticStackPaneContent.getChildren().forEach(node -> listChildNodes.add(node));
            staticStackPaneContent.getChildren().clear();
            staticStackPaneContent.getChildren().add(FXMLLoader.load(TranspondersFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(TranspondersFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
            e.printStackTrace();
        }
    }

    static void setStoredOverview() {
        staticStackPaneContent.getChildren().clear();
        staticStackPaneContent.getChildren().addAll(listChildNodes);
    }


    @FXML
    private void initialize() {
        staticStackPaneContent = root;
        initLabel();
        initTableView();
    }

    private void initLabel() {
        labelTitle.setText(CHOOSE_MODE_PERSON == null ? "Transponder" : "Verfügbare Transponder für " + CHOOSE_MODE_PERSON.toString());
    }

    private void initTableView() {
        tableViewTransponders.getItems().clear();
        tableViewTransponders.getColumns().clear();

        TableColumn<Transponder, String> columnName = new TableColumn("Name");
        TableColumn<Transponder, Boolean> columnState = new TableColumn("Status");

        tableViewTransponders.setRowFactory(new Callback<TableView<Transponder>, TableRow<Transponder>>() {
            @Override
            public TableRow<Transponder> call(TableView<Transponder> param) {
                return new TableRow<Transponder>() {
                    @Override
                    protected void updateItem(Transponder item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            if (CHOOSE_MODE_PERSON != null && !item.availableProperty().getValue()) {
                                setTextFill(Color.LIGHTGRAY);
                                setDisable(true);
                            } else {
                                setTextFill(Color.BLACK);
                                setDisable(false);
                            }
                            setOnMouseClicked(event -> openDetailView(getItem()));
                        } else {
                            setTextFill(Color.BLACK);
                            setOnMouseClicked(null);
                            setDisable(false);
                        }
                    }
                };
            }
        });

        columnName.setCellValueFactory(param -> param.getValue().nameProperty());
        columnName.setCellFactory(new Callback<TableColumn<Transponder, String>, TableCell<Transponder, String>>() {
            @Override
            public TableCell<Transponder, String> call(TableColumn<Transponder, String> param) {
                return new TableCell<Transponder, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            if (getTableRow() != null) {
                                setTextFill(getTableRow().getTextFill());
                            }
                            setText(item);
                        } else {
                            setText(null);
                            setTextFill(Color.BLACK);
                        }
                    }
                };
            }
        });
        columnState.setCellValueFactory(param -> param.getValue().availableProperty());
        columnState.setCellFactory(new Callback<TableColumn<Transponder, Boolean>, TableCell<Transponder, Boolean>>() {
            @Override
            public TableCell<Transponder, Boolean> call(TableColumn<Transponder, Boolean> param) {
                return new TableCell<Transponder, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            ImageView imageViewStatus = item ? StatusImages.getInstance().getGREEN() : StatusImages.getInstance().getRED();
                            imageViewStatus.setFitHeight(25);
                            imageViewStatus.setFitWidth(25);
                            setGraphic(imageViewStatus);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        columnName.setSortable(true);
        columnState.setSortable(true);
        columnState.setSortType(TableColumn.SortType.DESCENDING);

        tableViewTransponders.getColumns().addAll(columnName, columnState);

        tableViewTransponders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewTransponders.getSortOrder().add(columnState);

        List<Transponder> shownTransponders;
        if (CHOOSE_MODE_PERSON == null) {
            shownTransponders = TransponderData.getTransponders();
        } else {
            List<Room> authorizationRooms = CHOOSE_MODE_PERSON.authorizationsProperty().stream()
                    .map(authorization -> authorization.roomProperty().getValue())
                    .collect(Collectors.toList());

            shownTransponders = TransponderData.getTransponders().stream()
                    .filter(transponder -> transponder.linkingsProperty().stream()
                            .anyMatch(linking -> authorizationRooms.contains(linking.roomProperty().getValue())))
                    .collect(Collectors.toList());
        }
        tableViewTransponders.getItems().addAll(shownTransponders);

        tableViewTransponders.sort();
    }

    private void openDetailView(Transponder transponder) {
        TransponderDetailsFXMLController.setCurrentTransponder(transponder);
        setDetailViewFXMLChild("/dashboard/transponders/detail/transponderDetails.fxml");
    }
}
