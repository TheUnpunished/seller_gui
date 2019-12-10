package Controller;

import DAO.SupplierDAO;
import DAO.SupplierDAOImpl;
import Model.Supplier;
import Util.DbWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    private DbWork dbWork = DbWork.getInstance();
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

    @FXML
    private ListView<Supplier> supplierList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
        SupplierDAO supplierDAO = new SupplierDAOImpl(dbWork);
        suppliers.addAll(supplierDAO.findAll());
        supplierList.setItems(suppliers);
        supplierList.getSelectionModel().selectedItemProperty().addListener(
                (Observable, oldValue, newValue) -> setSupplierData(newValue));

    }
    private void setSupplierData(Supplier supplier){
        if(supplier!=null){
            nameLabel.setText(supplier.getName());
            typeLabel.setText(supplier.getType());
        }
        else {
            nameLabel.setText("");
            typeLabel.setText("");
        }
    }
}
