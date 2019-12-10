package Controller;

import DAO.SellerDAO;
import DAO.SellerDAOImpl;
import Model.Seller;
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

public class SellerController implements Initializable {

    private final ObservableList<Seller> sellers = FXCollections.observableArrayList();
    private DbWork dbWork = DbWork.getInstance();

    @FXML
    private ListView<Seller> sellerList;
    @FXML
    private Label nameLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label countLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbWork = MainframeController.getDbWork();
        SellerDAO sellerDAO = new SellerDAOImpl(dbWork);
        sellers.addAll(sellerDAO.findAll());
        sellerList.setItems(sellers);
        sellerList.getSelectionModel().selectedItemProperty().addListener(
                (Observable, oldValue, newValue) -> setSeller(newValue));
    }
    private void setSeller(Seller seller){
        if(seller!=null){
            nameLabel.setText(seller.getName());
            cityLabel.setText(seller.getCity());
            productNameLabel.setText(seller.getProduct_name());
            priceLabel.setText(Integer.toString(seller.getPrice()));
            countLabel.setText(Integer.toString(seller.getCount()));
        }
        else{
            nameLabel.setText("");
            cityLabel.setText("");
            productNameLabel.setText("");
            priceLabel.setText("");
            countLabel.setText("");
        }
    }
}
