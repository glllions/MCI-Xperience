package dashboard.transponders.detail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import records.*;

import java.util.Date;

public class TransponderDetailsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Label labelName;

    @FXML
    private Pane paneImageStatus;

    @FXML
    private ListView<TransponderRoomLinking> listViewLinkedRooms;

    @FXML
    private TableView<Authorization> tableViewAuthorizedPersons;

    @FXML
    private TableView<Lending> tableViewLastActivities;

    private static Transponder currentTransponder;

    public static void setCurrentTransponder(Transponder currentTransponder) {
        TransponderDetailsFXMLController.currentTransponder = currentTransponder;
    }

    @FXML
    private void initialize() {
        initLabels();
        initListViewLinkedRooms();
        initTableViewAuthorizedPersons();
        initTableViewLastActivities();
    }

    private void initListViewLinkedRooms() {
        listViewLinkedRooms.getItems().clear();

        listViewLinkedRooms.setCellFactory(new Callback<ListView<TransponderRoomLinking>, ListCell<TransponderRoomLinking>>() {
            @Override
            public ListCell<TransponderRoomLinking> call(ListView<TransponderRoomLinking> param) {
                return new ListCell<TransponderRoomLinking>() {
                    @Override
                    protected void updateItem(TransponderRoomLinking item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Raum " + item.roomProperty().getValue().numberProperty().getValue());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        listViewLinkedRooms.getItems().addAll(currentTransponder.linkingsProperty());
    }

    private void initTableViewAuthorizedPersons() {
        tableViewAuthorizedPersons.getItems().clear();
        tableViewAuthorizedPersons.getColumns().clear();

        TableColumn<Authorization, Person> columnName = new TableColumn("Name");
        TableColumn<Authorization, Date> columnEndDate = new TableColumn("Berechtigt bis");

        columnName.setCellValueFactory(param -> param.getValue().personProperty());
        columnEndDate.setCellValueFactory(param -> param.getValue().endDateProperty());

        columnName.setSortable(true);
        columnEndDate.setSortable(true);

        tableViewAuthorizedPersons.getColumns().addAll(columnName, columnEndDate);

        tableViewAuthorizedPersons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableSet<Authorization> authorizations = FXCollections.observableSet();
        currentTransponder.linkingsProperty().forEach(transponderRoomLinking
                -> authorizations.addAll(transponderRoomLinking.roomProperty().getValue().authorizationsProperty()));

        tableViewAuthorizedPersons.getItems().addAll(authorizations);
    }

    private void initTableViewLastActivities() {
        tableViewLastActivities.getColumns().clear();
        tableViewLastActivities.getItems().clear();

        TableColumn<Lending, Person> columnPerson = new TableColumn("Ausleihender");
        TableColumn<Lending, Date> columnBegin = new TableColumn("Ausleihe");
        TableColumn<Lending, Date> columnEnd = new TableColumn("Rückgabe");

        columnPerson.setCellValueFactory(param -> param.getValue().personProperty());
        columnBegin.setCellValueFactory(param -> param.getValue().beginDateProperty());
        columnEnd.setCellValueFactory(param -> param.getValue().endDateProperty());

        columnPerson.setSortable(true);
        columnBegin.setSortable(true);
        columnEnd.setSortable(true);

        tableViewLastActivities.getColumns().addAll(columnPerson, columnBegin, columnEnd);

        tableViewLastActivities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewLastActivities.getItems().addAll(currentTransponder.lastActivitiesProperty());
    }

    private void initLabels() {
        labelName.setText(String.valueOf(currentTransponder.nameProperty().getValue()));
        paneImageStatus.getChildren().clear();
        ImageView imageView = currentTransponder.statusProperty().getValue() ? StatusImages.getInstance().getGREEN() : StatusImages.getInstance().getRED();
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        paneImageStatus.getChildren().add(imageView);
    }
}
