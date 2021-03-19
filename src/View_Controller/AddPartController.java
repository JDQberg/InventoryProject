/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TheQcrew
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Inventory inv;
    
    boolean inhouse;

    @FXML
    private RadioButton addPartInhouseRBtn;
    @FXML
    private ToggleGroup inhouseOutsourced;
    @FXML
    private RadioButton addPartOutsourcedRBtn;
    @FXML
    private TextField addPartId;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartMin;
    @FXML
    private Label partSourceLabel;
    @FXML
    private TextField partSourceTextField;
    
    public AddPartController(Inventory inv)
    {
        this.inv = inv;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void addPartSave(MouseEvent event) throws IOException
    {
        try
        {
            int id = Integer.parseInt(addPartId.getText());
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInv.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int machineId;
            String companyName;
            
            if(min > max)
                {
                    throw new ArithmeticException();
                }

            //based on radio button selection create inhouse or outsourced part
            //and changes partSource to proper type
            if(inhouse)
            {
                machineId = Integer.parseInt(partSourceTextField.getText());
                Inventory.addPart(new Inhouse(id, stock, min, max, name, price, machineId));
            }
            else
            {
                companyName = partSourceTextField.getText();
                Inventory.addPart(new Outsourced(id, stock, min, max, name, price, companyName));
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
            Alert alert = new Alert(AlertType.ERROR);
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
    private void addPartCancel(MouseEvent event) throws IOException {
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

    @FXML
    private void selectInhouse(MouseEvent event) {
        if(addPartInhouseRBtn.isSelected()){
            partSourceLabel.setText("Machine ID");
            inhouse = true;
        }

    }

    @FXML
    private void selectOutsourced(MouseEvent event) {
        if(addPartOutsourcedRBtn.isSelected()){
            partSourceLabel.setText("Company Name");
            inhouse = false;
        }

    }
    
}
