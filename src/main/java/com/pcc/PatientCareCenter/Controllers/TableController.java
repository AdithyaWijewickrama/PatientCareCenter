package com.pcc.PatientCareCenter.Controllers;

import com.pcc.PatientCareCenter.Database.User.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, String> emailColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the columns
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Add data to the table
        ObservableList<Person> data = FXCollections.observableArrayList(
                new Person("John", "Doe", "john.doe@example.com"),
                new Person("Jane", "Doe", "jane.doe@example.com")
        );

        personTable.setItems(data);
    }

}