
package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    Inventory inv;
    Part part;
    
    boolean inhouse;

    
    private ObservableList<Part> partListToSearch = FXCollections.observableArrayList();
    private ObservableList<Part> selectedPart = FXCollections.observableArrayList();

    @FXML
    private RadioButton modifyPartInhouseRBtn;
    @FXML
    private ToggleGroup modifyPartInhouseOutsourced;
    @FXML
    private RadioButton modifyPartOutsourcedRBtn;
    @FXML
    private TextField modifyPartId;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInv;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMax;
    @FXML
    private TextField modifyPartMin;
    @FXML
    private Label partSourceLabel;
    @FXML
    private TextField partSourceTextField;
    
    public ModifyPartController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setPartToModify(Part part)
    {
        this.part = part;
        
        if(part instanceof Inhouse)
        {
            Inhouse part1 = (Inhouse) part;
            modifyPartInhouseRBtn.setSelected(true);
            modifyPartId.setText(Integer.toString(part.getId()));
            modifyPartName.setText(part.getName());
            modifyPartInv.setText(Integer.toString(part.getStock()));
            modifyPartPrice.setText(Double.toString(part.getPrice()));
            modifyPartMax.setText(Integer.toString(part.getMax()));
            modifyPartMin.setText(Integer.toString(part.getMin()));
            partSourceTextField.setText(Integer.toString(part1.getMachineId()));
            partSourceLabel.setText("Machine ID");
        }
        if(part instanceof Outsourced)
        {
            Outsourced part2 = (Outsourced) part;
            modifyPartOutsourcedRBtn.setSelected(true);
            modifyPartId.setText(Integer.toString(part.getId()));
            modifyPartName.setText(part.getName());
            modifyPartInv.setText(Integer.toString(part.getStock()));
            modifyPartPrice.setText(Double.toString(part.getPrice()));
            modifyPartMax.setText(Integer.toString(part.getMax()));
            modifyPartMin.setText(Integer.toString(part.getMin()));
            partSourceTextField.setText(part2.getCompanyName());
            partSourceLabel.setText("Company Name");
        }
        
    }

    @FXML
    private void modifyPartSave(MouseEvent event) throws IOException
    {
        try
        {
            int id = Integer.parseInt(modifyPartId.getText());
            String name = modifyPartName.getText();
            int stock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int machineId;
            String companyName;
            
            if(min > max)
                {
                    throw new ArithmeticException();
                }

            partListToSearch = inv.getAllParts();        

            //based on radio button selection create inhouse or outsourced part
            //and changes partSource to proper type
            if(modifyPartInhouseRBtn.isSelected())
            {
                machineId = Integer.parseInt(partSourceTextField.getText());


                Part selectedPart = new Inhouse(id, stock, min, max, name, price, machineId);

                int index = -1;
                for(Part sp : partListToSearch){
                    index++;
                    if(sp.getId() == id)
                    {
                        Inventory.updatePart(index, selectedPart);
                    }
                }
            }
            else
            {  
                companyName = partSourceTextField.getText();
                Part selectedPart = new Outsourced(id, stock, min, max, name, price, companyName);
                int index = -1;
                for(Part sp : partListToSearch)
                {
                    index++;
                    if(sp.getId() == id)
                    {
                        Inventory.updatePart(index, selectedPart);
                    }
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
    private void modifyPartCancel(MouseEvent event) throws IOException
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
    
    @FXML
    private void selectInhouse(MouseEvent event) {
        if(modifyPartInhouseRBtn.isSelected()){
            partSourceLabel.setText("Machine ID");
            inhouse = true;
            
        }

    }

    @FXML
    private void selectOutsourced(MouseEvent event) {
        if(modifyPartOutsourcedRBtn.isSelected()){
            partSourceLabel.setText("Company Name");
            inhouse = false;
            
        }
    }    
}
