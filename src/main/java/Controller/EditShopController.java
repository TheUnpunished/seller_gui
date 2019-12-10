package Controller;

import DAO.ShopDAO;
import DAO.ShopDAOImpl;
import Model.Shop;
import Util.DbWork;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditShopController extends MyController<Shop> {

    private DbWork dbWork = DbWork.getInstance();
    private Stage dialogStage;
    private Boolean forEdit=false;

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    @FXML
    private void handleOk(){
        if(forEdit){
            ShopDAO shopDAO = new ShopDAOImpl(dbWork);
            shopDAO.update(new Shop(nameField.getText(),typeField.getText()));
        }
        else{
            ShopDAO shopDAO = new ShopDAOImpl(dbWork);
            shopDAO.save(new Shop(nameField.getText(),typeField.getText()));
        }
        dialogStage.close();
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void setForEdit(Boolean forEdit) {
        this.forEdit = forEdit;
        nameField.setDisable(forEdit);
    }

    @Override
    public void setItem(Optional<Shop> shop){
        if(shop!=null){
            nameField.setText(shop.get().getName());
            typeField.setText(shop.get().getType());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork=MainframeController.getDbWork();
    }

}
