package dashboard.persons;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import records.Person;
import records.Role;
import records.data.PersonData;

public class PersonsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Person> tableViewPersons;


    @FXML
    private void initialize() {
        tableViewPersons.getColumns().clear();

        TableColumn<Person, String> columnLastName = new TableColumn("Nachname");
        TableColumn<Person, String> columnPreName = new TableColumn("Vorname");
        TableColumn<Person, Number> columnGMID = new TableColumn("GMID");
        TableColumn<Person, Role> columnRole = new TableColumn("Rolle");

        columnLastName.setCellValueFactory(param -> param.getValue().lastNameProperty());
        columnPreName.setCellValueFactory(param -> param.getValue().preNameProperty());
        columnGMID.setCellValueFactory(param -> param.getValue().gmidProperty());
        columnRole.setCellValueFactory(param -> param.getValue().roleProperty());

        columnLastName.setSortable(true);
        columnPreName.setSortable(true);
        columnGMID.setSortable(true);
        columnRole.setSortable(true);

        tableViewPersons.getColumns().addAll(columnLastName, columnPreName, columnGMID, columnRole);

        tableViewPersons.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewPersons.getItems().addAll(PersonData.getPersons());
    }
}
