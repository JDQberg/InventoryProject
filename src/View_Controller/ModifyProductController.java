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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;    
    Inventory inv;
    Product product;
    private ObservableList<Product> productListToSearch = FXCollections.observableArrayList();

    @FXML
    private TextField modifyProductSearchTxtFld;
    @FXML
    private TextField modifyProductIdTxtFld;
    @FXML
    private TextField modifyProductNameTxtFld;
    @FXML
    private TextField modifyProductInvTxtFld;
    @FXML
    private TextField modifyProductPriceTxtFld;
    @FXML
    private TextField modifyProductMaxTxtFld;
    @FXML
    private TextField modifyProductMinTxtFld;
    @FXML
    private TableView<Part> modifyProductPartsTableView;
    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTableIdCol;
    @FXML
    private TableColumn<Part, String> modifyProductPartsTableNameCol;
    @FXML
    private TableColumn<Part, Integer> modifyProductPartsTableInvCol;
    @FXML
    private TableColumn<Part, Double> modifyProductPartsTablePriceCol;
    @FXML
    private TableView<Part> modifyProductAsscociatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> modifyProductAsscociatedPartsTableIdCol;
    @FXML
    private TableColumn<Part, String> modifyProductAsscociatedPartsTableNameCol;
    @FXML
    private TableColumn<Part, Integer> modifyProductAsscociatedPartsTableInvCol;
    @FXML
    private TableColumn<Part, Double> modifyProductAsscociatedPartsTablePriceCol;
    
    public ModifyProductController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyProductPartsTableView.setItems(Inventory.getAllParts());        
        modifyProductPartsTableIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modifyProductAsscociatedPartsTableView.setItems(Product.getAllAssociatedParts());        
        modifyProductAsscociatedPartsTableIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAsscociatedPartsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAsscociatedPartsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAsscociatedPartsTablePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    @FXML
    private void modifyProductSearchBtn(MouseEvent event)
    {
        String searchItem = modifyProductSearchTxtFld.getText();
        
        try
        {
            int partId = Integer.parseInt(searchItem);
            Part part = Inventory.lookupPart(partId);
            modifyProductPartsTableView.getSelectionModel().select(part);
        }
        catch(NumberFormatException e)
        {
            String partName = searchItem;
            Inventory.lookupPart(partName);
            
            for(Part p : partsNameSearch)
            {
                if(p.getName().contains(searchItem))
                {
                    modifyProductPartsTableView.getSelectionModel().select(p);
                }
            }
        }
    }

    @FXML
    private void modifyProductAddBtn(MouseEvent event)
    {
        Part part = modifyProductPartsTableView.getSelectionModel().getSelectedItem();
        Product.addAssociatedPart(part);
        modifyProductAsscociatedPartsTableView.refresh();
    }

    @FXML
    private void modifyProductDeleteBtn(MouseEvent event)
    {
        Part associatedPart = modifyProductAsscociatedPartsTableView.getSelectionModel().getSelectedItem();
        Product.deleteAssociatedPart(associatedPart);
        modifyProductAsscociatedPartsTableView.refresh();
    }

    @FXML
    private void modifyProductSaveBtn(MouseEvent event) throws IOException
    {
        try
        {
            int id = Integer.parseInt(modifyProductIdTxtFld.getText());
            String name = modifyProductNameTxtFld.getText();
            int stock = Integer.parseInt(modifyProductInvTxtFld.getText());
            double price = Double.parseDouble(modifyProductPriceTxtFld.getText());
            int max = Integer.parseInt(modifyProductMaxTxtFld.getText());
            int min = Integer.parseInt(modifyProductMinTxtFld.getText());
            if(min > max)
                    {
                        throw new ArithmeticException();
                    }

            productListToSearch = inv.getAllProducts();

            Product selectedProduct = new Product(id, stock, min, max, name, price);

            int index = -1;
            for(Product sp : productListToSearch)
            {
                index++;
                if(sp.getId() == id)
                {
                    Inventory.updateProduct(index, selectedProduct);
                }
            }

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
    private void modifyProductCancelBtn(MouseEvent event) throws IOException
    {
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

    void setProductToModify(Product product)
    {
        this.product = product;
        
        modifyProductIdTxtFld.setText(Integer.toString(product.getId()));
        modifyProductNameTxtFld.setText(product.getName());
        modifyProductInvTxtFld.setText(Integer.toString(product.getStock()));
        modifyProductPriceTxtFld.setText(Double.toString(product.getPrice()));
        modifyProductMaxTxtFld.setText(Integer.toString(product.getMax()));
        modifyProductMinTxtFld.setText(Integer.toString(product.getMin()));
    }
    
}
