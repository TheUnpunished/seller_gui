package Controller;

import DAO.SellerDAO;
import DAO.SellerDAOImpl;
import Model.Seller;
import Util.DbWork;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSellerController extends MyController<Seller>
{
    @FXML
    private TextField nameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField product_nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField countField;

    @Override
    public void setItem(Optional<Seller> seller){
        nameField.setText(seller.get().getName());
        cityField.setText(seller.get().getCity());
        product_nameField.setText(seller.get().getProduct_name());
        priceField.setText(Integer.toString(seller.get().getPrice()));
        countField.setText(Integer.toString(seller.get().getCount()));
    }
    @Override
    public void setForEdit(Boolean forEdit){
        this.forEdit = forEdit;
        nameField.setDisable(forEdit);
        cityField.setDisable(forEdit);
        product_nameField.setDisable(forEdit);
    }

    @FXML
    private void handleOK(){
        String[] params = new String[5];
        params[0] = nameField.getText();
        params[1] = cityField.getText();
        params[2] = product_nameField.getText();
        params[3] = priceField.getText();
        params[4] = countField.getText();
        Seller seller = new Seller(params);
        SellerDAO sellerDAO = new SellerDAOImpl(dbWork);
        if(forEdit){
            sellerDAO.update(seller);
        }
        else{
            sellerDAO.save(seller);
        }
        this.dialogStage.close();
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
