package dashboard.persons;

import dashboard.persons.detail.PersonDetailsFXMLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.util.Callback;
import records.Person;
import records.Role;
import records.data.PersonData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PersonsFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Person> tableViewPersons;

    private static StackPane staticStackPaneContent;

    private static List<Node> listChildNodes = new ArrayList();

    static void setDetailViewFXMLChild(String path) {
        try {
            listChildNodes.clear();
            staticStackPaneContent.getChildren().forEach(node -> listChildNodes.add(node));
            staticStackPaneContent.getChildren().clear();
            staticStackPaneContent.getChildren().add(FXMLLoader.load(PersonDetailsFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(PersonsFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
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
        tableViewPersons.getItems().clear();
        tableViewPersons.getColumns().clear();

        TableColumn<Person, String> columnLastName = new TableColumn("Nachname");
        TableColumn<Person, String> columnPreName = new TableColumn("Vorname");
        TableColumn<Person, Number> columnGMID = new TableColumn("GMID");
        TableColumn<Person, Role> columnRole = new TableColumn("Rolle");

        tableViewPersons.setRowFactory(new Callback<TableView<Person>, TableRow<Person>>() {
            @Override
            public TableRow<Person> call(TableView<Person> param) {
                return new TableRow<Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
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

    private void openDetailView(Person person) {
        PersonDetailsFXMLController.setCurrentPerson(person);
        setDetailViewFXMLChild("/dashboard/persons/detail/personDetails.fxml");
    }
}
