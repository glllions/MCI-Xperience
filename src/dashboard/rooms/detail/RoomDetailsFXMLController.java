package dashboard.rooms.detail;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import records.*;

import java.util.Date;

public class RoomDetailsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Label labelRoom;

    @FXML
    private Label labelBuilding;

    @FXML
    private ListView<Person> listViewRoomManagers;

    @FXML
    private TableView<TransponderRoomLinking> tableViewTransponders;

    @FXML
    private TableView<Lending> tableViewLastActivities;

    private static Room currentRoom;

    public static void setCurrentRoom(Room currentRoom) {
        RoomDetailsFXMLController.currentRoom = currentRoom;
    }

    @FXML
    private void initialize() {
        initLabels();
        initListViewRoomManagers();
        initTableViewTransponders();
        initTableViewLastActivities();
    }

    private void initListViewRoomManagers() {
        listViewRoomManagers.getItems().clear();

        listViewRoomManagers.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {
            @Override
            public ListCell<Person> call(ListView<Person> param) {
                return new ListCell<Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        listViewRoomManagers.getItems().addAll(currentRoom.roomManagersProperty());
    }

    private void initTableViewTransponders() {
        tableViewTransponders.getItems().clear();
        tableViewTransponders.getColumns().clear();

        TableColumn<TransponderRoomLinking, String> columnName = new TableColumn("Name");
        TableColumn<TransponderRoomLinking, Boolean> columnStatus = new TableColumn("Status");

        columnName.setCellValueFactory(param -> param.getValue().transponderProperty().getValue().nameProperty());
        columnStatus.setCellValueFactory(param -> param.getValue().transponderProperty().getValue().statusProperty());
        columnStatus.setCellFactory(new Callback<TableColumn<TransponderRoomLinking, Boolean>, TableCell<TransponderRoomLinking, Boolean>>() {
            @Override
            public TableCell<TransponderRoomLinking, Boolean> call(TableColumn<TransponderRoomLinking, Boolean> param) {
                return new TableCell<TransponderRoomLinking, Boolean>() {
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

        tableViewTransponders.getItems().addAll(currentRoom.linkingsProperty());

        tableViewTransponders.getSortOrder().addAll(columnStatus, columnName);
        tableViewTransponders.sort();
    }

    private void initTableViewLastActivities() {
        tableViewLastActivities.getColumns().clear();
        tableViewLastActivities.getItems().clear();

        TableColumn<Lending, Person> columnPerson = new TableColumn("Ausleihender");
        TableColumn<Lending, Transponder> columnTransponder = new TableColumn("Transponder");
        TableColumn<Lending, Date> columnBegin = new TableColumn("Ausleihe");
        TableColumn<Lending, Date> columnEnd = new TableColumn("Rückgabe");

        columnPerson.setCellValueFactory(param -> param.getValue().personProperty());
        columnTransponder.setCellValueFactory(param -> param.getValue().transponderProperty());
        columnBegin.setCellValueFactory(param -> param.getValue().beginDateProperty());
        columnEnd.setCellValueFactory(param -> param.getValue().endDateProperty());

        columnPerson.setSortable(true);
        columnTransponder.setSortable(true);
        columnBegin.setSortable(true);
        columnEnd.setSortable(true);

        tableViewLastActivities.getColumns().addAll(columnPerson, columnTransponder, columnBegin, columnEnd);

        tableViewLastActivities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewLastActivities.getItems().addAll(currentRoom.lastActivitiesProperty());

        tableViewLastActivities.getSortOrder().add(columnBegin);
        tableViewLastActivities.sort();
    }

    private void initLabels() {
        labelRoom.setText(String.valueOf(currentRoom.numberProperty().getValue()));
        labelBuilding.setText(currentRoom.buildingProperty().getValue().toString());
    }
}
