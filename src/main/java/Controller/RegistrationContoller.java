package Controller;

import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;
import Model.Registration;
import Util.DbWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationContoller implements Initializable {

    private DbWork dbWork=DbWork.getInstance();
    private final ObservableList<Registration> registrations = FXCollections.observableArrayList();

    @FXML
    private ListView<Registration> regList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label posLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork=MainframeController.getDbWork();
        RegistrationDAO registrationDAO = new RegistrationDAOImpl(dbWork);
        registrations.addAll(registrationDAO.findAll());
        regList.setItems(registrations);
        regList.getSelectionModel().selectedItemProperty().addListener((
                Observable, oldValue, newValue) -> setRegData(newValue)
                );
    }
    private void setRegData(Registration registration){
        if(registration!=null){
            nameLabel.setText(registration.getName());
            dateLabel.setText(registration.getDate().toString());
            posLabel.setText(Integer.toString(registration.getCount()));
        }
        else{
            nameLabel.setText("");
            dateLabel.setText("");
            posLabel.setText("");
        }
    }
}
