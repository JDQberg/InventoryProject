/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.partsNameSearch;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class AddProductController implements Initializable {
    
    Inventory inv;
    Stage stage;
    Parent scene;
    Product product;

    @FXML
    private TextField addProductSearchTextField;
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TableView<Part> addProductAllPartsTableView;
    @FXML
    private TableColumn<Part, Integer> addProductAllPartsIdCol;
    @FXML
    private TableColumn<Part, String> addProductAllPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductAllPartsInvCol;
    @FXML
    private TableColumn<Part, Double> addProductAllPartsPriceCol;
    @FXML
    private Button addProductAddBtn;
    @FXML
    private TableView<Part> addProductAssociatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedPartsIdCol;
    @FXML
    private TableColumn<Part, String> addProductAssociatedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductAssociatedPartsInvCol;
    @FXML
    private TableColumn<Part, Double> addProductAssociatedPartsPriceCol;
    @FXML
    private Button addProductDeleteBtn;
    @FXML
    private Button addProductSaveBtn;
    @FXML
    private Button addProductCancelBtn;
    
    public AddProductController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        addProductAllPartsTableView.setItems(Inventory.getAllParts());        
        addProductAllPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAllPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAllPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAllPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        addProductAssociatedPartsTableView.setItems(Product.getAllAssociatedParts()); 
        addProductAssociatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssociatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssociatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssociatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void addProductSearchBtn(MouseEvent event)
    {
        String searchItem = addProductSearchTextField.getText();
        
        try
        {
            int partId = Integer.parseInt(searchItem);
            Part part = Inventory.lookupPart(partId);
            addProductAllPartsTableView.getSelectionModel().select(part);
        }
        catch(NumberFormatException e)
        {
            String partName = searchItem;
            Inventory.lookupPart(partName);
            
            for(Part p : partsNameSearch)
            {
                if(p.getName().contains(searchItem))
                {
                    addProductAllPartsTableView.getSelectionModel().select(p);
                }
            }
        }
    }

    @FXML
    private void addProductAddBtn(MouseEvent event)
    {
        Part part = addProductAllPartsTableView.getSelectionModel().getSelectedItem();
        Product.addAssociatedPart(part);
        addProductAssociatedPartsTableView.refresh();
    }

    @FXML
    private void addProductDeleteBtn(MouseEvent event)
    {
        Part part = addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        Product.deleteAssociatedPart(part);
        addProductAssociatedPartsTableView.refresh();
    }

    @FXML
    private void addProductSaveBtn(MouseEvent event) throws IOException
    {
        try
        {
            int id = Integer.parseInt(addProductId.getText());
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());
            if(min > max)
                {
                    throw new ArithmeticException();
                }
            Inventory.addProduct(new Product(id, stock, min, max, name, price));        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please provide valid entries for each item.");
            alert.showAndWait();
        }
        catch(ArithmeticException e)
        {            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Minimum inventory level can not be higher than Maximum");
            alert.showAndWait();
        }
    }

    @FXML
    private void addProductCancelBtn(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }    
}
