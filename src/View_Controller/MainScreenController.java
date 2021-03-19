/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.partsNameSearch;
import static Model.Inventory.productsNameSearch;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class MainScreenController implements Initializable
{
    Stage stage;
    Parent scene;
    
    Inventory inv;

    
    @FXML
    private TextField mainPartsSearchTxtField;
    @FXML
    private TableView<Part> mainPartsTableView;
    @FXML
    private TableColumn<Part, Integer> mainPartsIdCol;
    @FXML
    private TableColumn<Part, String> mainPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> mainPartsInvCol;
    @FXML
    private TableColumn<Part, Double> mainPartsPriceCol;
    @FXML
    private TextField mainProductsSearchTxtField;
    @FXML
    private TableView<Product> mainProductsTableView;
    @FXML
    private TableColumn<Product, Integer> mainProductsIdCol;
    @FXML
    private TableColumn<Product, String> mainProductsNameCol;
    @FXML
    private TableColumn<Product, Integer> mainProductsInvCol;
    @FXML
    private TableColumn<Product, Double> mainProductsPriceCol;


    private ObservableList<Part> partsSearch = FXCollections.observableArrayList();
    private ObservableList<Part> partListToSearch = FXCollections.observableArrayList();
    private ObservableList<Product> productSearch = FXCollections.observableArrayList();
    private ObservableList<Product> productListToSearch = FXCollections.observableArrayList();

    
    public MainScreenController(Inventory inv)
    {
        this.inv = inv;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {        
        mainPartsTableView.setItems(Inventory.getAllParts());      
        mainPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTableView.setItems(Inventory.getAllProducts());        
        mainProductsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }    

    @FXML
    private void mainScreenPartsSearchBtn(MouseEvent event)
    {
        String searchItem = mainPartsSearchTxtField.getText();        
        
        try
        {
            int partId = Integer.parseInt(searchItem);
            Part part = Inventory.lookupPart(partId);
            mainPartsTableView.getSelectionModel().select(part);
        }
        catch(NumberFormatException e)
        {
            String partName = searchItem;
            Inventory.lookupPart(partName);
            
            for(Part p : partsNameSearch)
            {
                if(p.getName().contains(searchItem))
                {
                    mainPartsTableView.getSelectionModel().select(p);
                }
            }
        }
    }

    @FXML
    private void mainScreenPartsAddBtn(MouseEvent event) throws IOException
    {        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addPart.fxml"));
        AddPartController controller = new AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void mainScreenPartsModifyBtn(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyPart.fxml"));
        ModifyPartController controller = new ModifyPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Part part = mainPartsTableView.getSelectionModel().getSelectedItem();
        controller.setPartToModify(part);
    }

    @FXML
    private void mainScreenPartsDeleteBtn(MouseEvent event)
    {
        Part selectedPart = mainPartsTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart); 
        mainPartsTableView.refresh();
    }

    @FXML
    private void mainScreenProductsSearchBtn(MouseEvent event)
    {
        String searchItem = mainProductsSearchTxtField.getText();        
        
        try
        {
            int productId = Integer.parseInt(searchItem);
            Product product = Inventory.lookupProduct(productId);
            mainProductsTableView.getSelectionModel().select(product);
        }
        catch(NumberFormatException e)
        {
            String productName = searchItem;
            Inventory.lookupProduct(productName);
                        
            for(Product p : productsNameSearch)
            {
                if(p.getName().contains(searchItem))
                {
                    mainProductsTableView.getSelectionModel().select(p);
                }
            }
        }
    }

    @FXML
    private void mainScreenProductsAddBtn(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduct.fxml"));
        AddProductController controller = new AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void mainScreenProductsModifyBtn(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
        ModifyProductController controller = new ModifyProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Product product = mainProductsTableView.getSelectionModel().getSelectedItem();
        controller.setProductToModify(product);
    }

    @FXML
    private void mainScreenProductsDeleteBtn(MouseEvent event)
    {
        Product selectedProduct = mainProductsTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct); 
        mainPartsTableView.refresh();
    }

    @FXML
    private void mainScreenExitBtn(MouseEvent event)
    {
        Platform.exit();
    }
    
}
