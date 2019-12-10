package Controller;

import DAO.*;
import Model.*;
import Util.DbWork;
import Util.MyRunnable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MainframeController implements Initializable {

    private static DbWork dbWork=DbWork.getInstance();
    private static final Map<Class, MyRunnable> editHashMap = new HashMap<Class, MyRunnable>();
    private static final Map<Class, MyRunnable> deleteHasMap = new HashMap<Class, MyRunnable>();
    private static final Map<Class, MyRunnable> fileHashMap = new HashMap<Class, MyRunnable>();
    {
        editHashMap.put(Shop.class, new MyRunnable() {
            @Override
            public void run() {
                openDialog((Boolean) getParam().get(), "/FXML/EditShop.fxml");
            }
        });
        editHashMap.put(Supplier.class, new MyRunnable(){
            @Override
            public void run(){
                openDialog((Boolean) getParam().get(), "/FXML/EditSupplier.fxml");
            }
        });
        editHashMap.put(Registration.class, new MyRunnable(){
            @Override
            public void run(){
                openDialog((Boolean) getParam().get(), "/FXML/EditRegistration.fxml");
            }
        });
        editHashMap.put(Seller.class, new MyRunnable(){
            @Override
            public void run(){
                openDialog((Boolean) getParam().get(), "/FXML/EditSeller.fxml");
            }
        });
        deleteHasMap.put(Shop.class, new MyRunnable(){
            @Override
            public void run(){
                ShopDAO shopDAO = new ShopDAOImpl(dbWork);
                shopDAO.delete((Shop) getParam().get());
            }
        });
        deleteHasMap.put(Supplier.class, new MyRunnable(){
            @Override
            public void run(){
                SupplierDAO supplierDAO = new SupplierDAOImpl(dbWork);
                supplierDAO.delete((Supplier) getParam().get());
            }
        });
        deleteHasMap.put(Registration.class, new MyRunnable(){
            @Override
            public void run(){
                RegistrationDAO registrationDAO = new RegistrationDAOImpl(dbWork);
                registrationDAO.delete((Registration) getParam().get());
            }
        });
        deleteHasMap.put(Seller.class, new MyRunnable(){
            @Override
            public void run(){
                SellerDAO sellerDAO = new SellerDAOImpl(dbWork);
                sellerDAO.delete((Seller) getParam().get());
            }
        });
        fileHashMap.put(Shop.class, new MyRunnable(){
            @Override
            public void run(){
                ShopDAO shopDAO = new ShopDAOImpl(dbWork);
                List<Optional> optionals = (List) getParam().get();
                for(Optional optional: optionals){
                    Shop shop = (Shop) optional.get();
                    shopDAO.save(shop);
                }
            }
        });
        fileHashMap.put(Seller.class, new MyRunnable(){
            @Override
            public void run(){
                SellerDAO sellerDAO = new SellerDAOImpl(dbWork);
                List<Optional> optionals = (List) getParam().get();
                for(Optional optional: optionals){
                    Seller seller = (Seller) optional.get();
                    sellerDAO.save(seller);
                }
            }
        });
        fileHashMap.put(Registration.class, new MyRunnable(){
            @Override
            public void run(){
                RegistrationDAO registrationDAO = new RegistrationDAOImpl(dbWork);
                List<Optional> optionals = (List) getParam().get();
                for(Optional optional: optionals){
                    Registration registration = (Registration) optional.get();
                    registrationDAO.save(registration);
                }
            }
        });
        fileHashMap.put(Supplier.class, new MyRunnable(){
            @Override
            public void run(){
                SupplierDAO supplierDAO = new SupplierDAOImpl(dbWork);
                List<Optional> optionals = (List) getParam().get();
                for(Optional optional: optionals){
                    Supplier supplier = (Supplier) optional.get();
                    supplierDAO.save(supplier);
                }
            }
        });
    }

    public static DbWork getDbWork() {
        return dbWork;
    }

    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab sellerTab;
    @FXML
    private Tab registrationTab;
    @FXML
    private Tab supplierTab;
    @FXML
    private Tab shopTab;
    @FXML
    private AnchorPane sellerTabPage;
    @FXML
    private AnchorPane registrationTabPage;
    @FXML
    private AnchorPane supplierTabPage;
    @FXML
    private AnchorPane shopTabPage;

    @FXML
    private void handleAddAction(){
        handleAddEdit(false);
    }

    @FXML
    private void handleEditAction(){
        handleAddEdit(true);
    }


    private void handleAddEdit(Boolean forEdit){
        AnchorPane anchorPane = (AnchorPane) getCurrentTab().getContent();
        ListView listView = getListView(anchorPane);
        int size = listView.getItems().size();
        int index = listView.getSelectionModel().getSelectedIndex();
        Optional optional = Optional.of(listView.getSelectionModel().getSelectedItem());
        MyRunnable runnable = editHashMap.get(optional.get().getClass());
        runnable.setParam(Optional.of(forEdit));
        runnable.run();
        refreshTab(getCurrentTab());
        if(size!=listView.getItems().size()){
            selectLast(getCurrentTab());
        }
        else{
            listView.getSelectionModel().select(index);
        }
    }

    private void handleFile(Boolean toFile){
        File file = new File(("src/main/resources/Dynamic/"+getCurrentTab().getText())+".txt");
        FileDAO fileDAO = new FileDAOImpl(file,"'");
        AnchorPane anchorPane = (AnchorPane) getCurrentTab().getContent();
        ListView listView = getListView(anchorPane);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Работа с файлом");
        alert.setHeaderText("Внимание");
        if(toFile){
            List<Optional> optionals = listView.getItems();
            fileDAO.save(optionals);
            alert.setContentText("Запись в файл завершена");
        }
        else {
            List<Optional> optionals = fileDAO.findAll();
            MyRunnable myRunnable = fileHashMap.get(optionals.get(0).get().getClass());
            myRunnable.setParam(Optional.of(optionals));
            myRunnable.run();
            refreshTab(getCurrentTab());
            selectFirst(getCurrentTab());
            alert.setContentText("Восстановление из файла завершено");
        }
        alert.showAndWait();
    }

    @FXML
    private void handleToFileAction(){
        handleFile(true);
    }
    @FXML
    private void handleFromFileAction(){
        handleFile(false);
    }

    @FXML
    private void handleDeleteAction(){
        Optional optional = getCurrentItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Внимание!");
        alert.setContentText("Вы действительно хотите удалить " + optional.get().toString()+"?");
        Optional<ButtonType>  buttonType = alert.showAndWait();
        if(buttonType.get()==ButtonType.OK){
            MyRunnable myRunnable = deleteHasMap.get(optional.get().getClass());
            myRunnable.setParam(optional);
            myRunnable.run();
            refreshTab(getCurrentTab());
            selectFirst(getCurrentTab());
        }
    }

    private Tab getCurrentTab(){
        return mainTabPane.getSelectionModel().getSelectedItem();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbWork.setConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selectFirstForAll();
    }

    private void selectFirstForAll(){
        List<Tab> tabList= mainTabPane.getTabs();
        for(Tab tab : tabList){
            selectFirst(tab);
        }
    }
    private void selectFirst(Tab tab){
        AnchorPane anchorPane = (AnchorPane)tab.getContent();
        ListView listView = getListView(anchorPane);
        listView.getSelectionModel().select(0);
    }
    private void selectLast(Tab tab){
        AnchorPane anchorPane = (AnchorPane)tab.getContent();
        ListView listView = getListView(anchorPane);
        listView.getSelectionModel().select(listView.getItems().size()-1);
    }

    private ListView getListView(AnchorPane currentPage){
        SplitPane splitPane = (SplitPane) currentPage.getChildren().get(0);
        AnchorPane anchorPane = (AnchorPane) splitPane.getItems().get(0);
        return (ListView) anchorPane.getChildren().get(0);
    }

    private Optional getCurrentItem(){
        AnchorPane anchorPane = (AnchorPane) getCurrentTab().getContent();
        ListView listView = getListView(anchorPane);
        Optional optional = Optional.of(listView.getSelectionModel().getSelectedItem());
        return optional;
    }

    private void openDialog(Boolean forEdit, String link){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(link));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        controller.setForEdit(forEdit);
        if(forEdit)
        controller.setItem(getCurrentItem());
        controller.setDialogStage(stage);
        stage.setScene(scene);
        stage.setTitle("Редактирование");
        stage.showAndWait();
    }

    private void refreshTab(Tab tab){
        AnchorPane anchorPane = (AnchorPane) tab.getContent();
        ListView listView = getListView(anchorPane);
        switch (tab.getText()){
            case "Магазин":{
                ShopDAO shopDAO = new ShopDAOImpl(dbWork);
                listView.getItems().clear();
                listView.getItems().addAll(shopDAO.findAll());
                break;
            }
            case "Регистрация":{
                RegistrationDAO registrationDAO = new RegistrationDAOImpl(dbWork);
                listView.getItems().clear();
                listView.getItems().addAll(registrationDAO.findAll());
                break;
            }
            case "Продавцы": {
                SellerDAO sellerDAO = new SellerDAOImpl(dbWork);
                listView.getItems().clear();
                listView.getItems().addAll(sellerDAO.findAll());
                break;
            }
            case "Поставщики": {
                SupplierDAO supplierDAO = new SupplierDAOImpl(dbWork);
                listView.getItems().clear();
                listView.getItems().addAll(supplierDAO.findAll());
                break;
            }
        }
    }

    @FXML
    private void runQueryTool() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/QueryTool.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Query Tool");
        stage.showAndWait();
        refreshAllTabs();
        selectFirstForAll();
    }

    private void refreshAllTabs(){
        List<Tab> tabs = mainTabPane.getTabs();
        for (Tab tab: tabs){
            refreshTab(tab);
        }
    }
}
