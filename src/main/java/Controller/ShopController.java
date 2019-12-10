package Controller;

import DAO.ShopDAO;
import DAO.ShopDAOImpl;
import DAO.SupplierDAO;
import Model.Shop;
import Model.Supplier;
import Util.DbWork;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    private DbWork dbWork = DbWork.getInstance();
    private final ObservableList<Shop> shops = FXCollections.observableArrayList();

    @FXML
    private ListView<Shop> shopList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
        ShopDAO shopDAO = new ShopDAOImpl(dbWork);
        shops.addAll(shopDAO.findAll());
        shopList.setItems(shops);
        shopList.getSelectionModel().selectedItemProperty().addListener(
                (Observable, oldValue, newValue) -> setShopData(newValue));
    }
    private void setShopData(Shop shop){
        if(shop!=null){
            nameLabel.setText(shop.getName());
            typeLabel.setText(shop.getType());
        }
        else {
            nameLabel.setText("");
            typeLabel.setText("");
        }
    }
}
