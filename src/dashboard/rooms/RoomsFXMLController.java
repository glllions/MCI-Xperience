package dashboard.rooms;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import records.Building;
import records.Room;
import records.data.RoomData;

public class RoomsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Room> tableViewRooms;


    @FXML
    private void initialize() {
        tableViewRooms.getColumns().clear();

        TableColumn<Room, Number> columnNumber = new TableColumn("Nummer");
        TableColumn<Room, Building> columnBuilding = new TableColumn("Gebäude");

        columnNumber.setCellValueFactory(param -> param.getValue().numberProperty());
        columnBuilding.setCellValueFactory(param -> param.getValue().buildingProperty());

        columnNumber.setSortable(true);
        columnBuilding.setSortable(true);

        tableViewRooms.getColumns().addAll(columnNumber, columnBuilding);

        tableViewRooms.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewRooms.getItems().addAll(RoomData.getRooms());
    }
}
