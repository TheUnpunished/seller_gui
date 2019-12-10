package Controller;

import DAO.SupplierDAO;
import DAO.SupplierDAOImpl;
import Model.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSupplierController extends MyController<Supplier> {

    @FXML
    private TextField typeField;
    @FXML
    private TextField nameField;

    @Override
    public void setForEdit(Boolean forEdit){
        this.forEdit = forEdit;
        typeField.setDisable(forEdit);
    }

    @Override
    public void setItem(Optional<Supplier> supplier){
        typeField.setText(supplier.get().getType());
        nameField.setText(supplier.get().getName());
    }

    @FXML
    private void handleOK(){
        String[] params = new String[2];
        params[0] = typeField.getText();
        params[1] = typeField.getText();
        Supplier supplier = new Supplier(params);
        SupplierDAO supplierDAO = new SupplierDAOImpl(dbWork);
        if(forEdit){
            supplierDAO.update(supplier);
        }
        else{
            supplierDAO.save(supplier);
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        this.dialogStage.close();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
    }
}
