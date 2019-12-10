package Controller;

import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;
import Model.Registration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditRegistrationController extends MyController<Registration> {



    @Override
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    @Override
    public void setForEdit(Boolean forEdit){
        this.forEdit = forEdit;
        nameField.setDisable(forEdit);
    }
    @Override
    public void setItem(Optional<Registration> registration){
        nameField.setText(registration.get().getName());
        registrationField.setText(registration.get().getDate().toString());
        countField.setText(Integer.toString(registration.get().getCount()));
    }

    @FXML
    private void handleOK(){
        RegistrationDAO registrationDAO = new RegistrationDAOImpl(dbWork);
        String[] params = new String[3];
        params[0] = nameField.getText();
        params[1] = registrationField.getText();
        params[2] = countField.getText();
        Registration registration = new Registration(params);
        if(forEdit){
            registrationDAO.update(registration);
        }
        else{
            registrationDAO.save(registration);
        }
        this.dialogStage.close();
    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private TextField nameField;
    @FXML
    private TextField registrationField;
    @FXML
    private TextField countField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
    }
}
