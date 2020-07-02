package dashboard.transponders;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import records.Building;
import records.Room;
import records.Transponder;
import records.data.RoomData;
import records.data.TransponderData;

public class TranspondersFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Transponder> tableViewTransponders;


    @FXML
    private void initialize() {
        tableViewTransponders.getColumns().clear();

        TableColumn<Transponder, String> columnName = new TableColumn("Name");
        TableColumn<Transponder, Boolean> columnStatus = new TableColumn("Status");

        columnName.setCellValueFactory(param -> param.getValue().nameProperty());
        columnStatus.setCellValueFactory(param -> param.getValue().statusProperty());

        columnName.setSortable(true);
        columnStatus.setSortable(true);

        tableViewTransponders.getColumns().addAll(columnName, columnStatus);

        tableViewTransponders.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewTransponders.getItems().addAll(TransponderData.getTransponders());
    }
}
