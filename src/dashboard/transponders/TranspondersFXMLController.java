package dashboard.transponders;

import dashboard.transponders.detail.TransponderDetailsFXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import records.StatusImages;
import records.Transponder;
import records.data.TransponderData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TranspondersFXMLController {

    @FXML
    private StackPane root;

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
        initTableView();
    }

    private void initTableView() {
        tableViewTransponders.getItems().clear();
        tableViewTransponders.getColumns().clear();

        TableColumn<Transponder, String> columnName = new TableColumn("Name");
        TableColumn<Transponder, Boolean> columnStatus = new TableColumn("Status");

        tableViewTransponders.setRowFactory(new Callback<TableView<Transponder>, TableRow<Transponder>>() {
            @Override
            public TableRow<Transponder> call(TableView<Transponder> param) {
                return new TableRow<Transponder>() {
                    @Override
                    protected void updateItem(Transponder item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setOnMouseClicked(event -> openDetailView(getItem()));
                        } else {
                            setOnMouseClicked(null);
                        }
                    }
                };
            }
        });

        columnName.setCellValueFactory(param -> param.getValue().nameProperty());
        columnStatus.setCellValueFactory(param -> param.getValue().statusProperty());
        columnStatus.setCellFactory(new Callback<TableColumn<Transponder, Boolean>, TableCell<Transponder, Boolean>>() {
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
        columnStatus.setSortable(true);

        tableViewTransponders.getColumns().addAll(columnName, columnStatus);

        tableViewTransponders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewTransponders.getItems().addAll(TransponderData.getTransponders());
    }

    private void openDetailView(Transponder transponder) {
        TransponderDetailsFXMLController.setCurrentTransponder(transponder);
        setDetailViewFXMLChild("/dashboard/transponders/detail/transponderDetails.fxml");
    }
}
