package dashboard.rooms;

import dashboard.rooms.detail.RoomDetailsFXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import records.Building;
import records.Room;
import records.data.RoomData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoomsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Room> tableViewRooms;

    private static StackPane staticStackPaneContent;

    private static List<Node> listChildNodes = new ArrayList();

    static void setDetailViewFXMLChild(String path) {
        try {
            listChildNodes.clear();
            staticStackPaneContent.getChildren().forEach(node -> listChildNodes.add(node));
            staticStackPaneContent.getChildren().clear();
            staticStackPaneContent.getChildren().add(FXMLLoader.load(RoomsFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(RoomsFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
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
        tableViewRooms.getColumns().clear();

        TableColumn<Room, Number> columnNumber = new TableColumn("Nummer");
        TableColumn<Room, Building> columnBuilding = new TableColumn("Gebäude");

        columnNumber.setCellValueFactory(param -> param.getValue().numberProperty());
        columnBuilding.setCellValueFactory(param -> param.getValue().buildingProperty());

        tableViewRooms.setRowFactory(new Callback<TableView<Room>, TableRow<Room>>() {
            @Override
            public TableRow<Room> call(TableView<Room> param) {
                return new TableRow<Room>() {
                    @Override
                    protected void updateItem(Room item, boolean empty) {
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

        columnNumber.setSortable(true);
        columnBuilding.setSortable(true);

        tableViewRooms.getColumns().addAll(columnNumber, columnBuilding);

        tableViewRooms.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewRooms.getItems().addAll(RoomData.getRooms());
    }

    private void openDetailView(Room room) {
        RoomDetailsFXMLController.setCurrentRoom(room);
        setDetailViewFXMLChild("/dashboard/rooms/detail/roomDetails.fxml");
    }
}
