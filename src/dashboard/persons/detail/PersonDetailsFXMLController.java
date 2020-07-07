package dashboard.persons.detail;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import records.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonDetailsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Label labelName;

    @FXML
    private Label labelGMID;

    @FXML
    private Label labelMatNr;

    @FXML
    private Label labelRole;

    @FXML
    private ListView<Authorization> listViewAuthorizations;

    @FXML
    private TableView<Lending> tableViewLastLendings;

    private static Person currentPerson;

    public static void setCurrentPerson(Person currentPerson) {
        PersonDetailsFXMLController.currentPerson = currentPerson;
    }

    @FXML
    private void initialize() {
        initLabels();
        initListViewAuthorizations();
        initTableViewLastLendings();
    }

    private void initListViewAuthorizations() {
        listViewAuthorizations.getItems().clear();

        listViewAuthorizations.setCellFactory(new Callback<ListView<Authorization>, ListCell<Authorization>>() {
            @Override
            public ListCell<Authorization> call(ListView<Authorization> param) {
                return new ListCell<Authorization>() {
                    @Override
                    protected void updateItem(Authorization item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Raum: " + item.roomProperty().getValue().numberProperty().get());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        listViewAuthorizations.getItems().addAll(currentPerson.authorizationsProperty());
    }

    private void initTableViewLastLendings() {
        tableViewLastLendings.getColumns().clear();
        tableViewLastLendings.getItems().clear();

        TableColumn<Lending, Transponder> columnTransponder = new TableColumn("Transponder");
        TableColumn<Lending, Date> columnBegin = new TableColumn("Ausleihe");
        TableColumn<Lending, Date> columnEnd = new TableColumn("Rückgabe");

        columnTransponder.setCellValueFactory(param -> param.getValue().transponderProperty());
        columnBegin.setCellValueFactory(param -> param.getValue().beginDateProperty());
        columnEnd.setCellValueFactory(param -> param.getValue().endDateProperty());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");

        Callback<TableColumn<Lending, Date>, TableCell<Lending, Date>> callbackDateCell = new Callback<TableColumn<Lending, Date>, TableCell<Lending, Date>>() {
            @Override
            public TableCell<Lending, Date> call(TableColumn<Lending, Date> param) {
                return new TableCell<Lending, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(dateFormat.format(item));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        };

        columnBegin.setCellFactory(callbackDateCell);
        columnEnd.setCellFactory(callbackDateCell);

        columnTransponder.setSortable(true);
        columnBegin.setSortable(true);
        columnEnd.setSortable(true);

        tableViewLastLendings.getColumns().addAll(columnTransponder, columnBegin, columnEnd);

        tableViewLastLendings.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewLastLendings.getItems().addAll(currentPerson.lastLendingsProperty());

        tableViewLastLendings.getSortOrder().add(columnBegin);
        tableViewLastLendings.sort();
    }

    private void initLabels() {
        labelName.setText(currentPerson.toString());
        labelGMID.setText(String.valueOf(currentPerson.gmidProperty().get()));
        labelMatNr.setText(String.valueOf(currentPerson.matNrProperty().get()));
        labelRole.setText(String.valueOf(currentPerson.roleProperty().get()));
    }
}
