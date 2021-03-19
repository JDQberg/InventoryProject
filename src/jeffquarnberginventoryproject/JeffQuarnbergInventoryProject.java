/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeffquarnberginventoryproject;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TheQcrew
 */
public class JeffQuarnbergInventoryProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception
    {        
        Inventory inv = new Inventory();
        addTestData(inv);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/mainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    void addTestData(Inventory inv){
    
    Part a1 = new Inhouse(1, 2, 5, 10, "Pickup", 2.99, 750);
    Part a2 = new Inhouse(2, 10, 5, 10, "Neck", 10.99, 759);    
    inv.addPart(a1);
    inv.addPart(a2);    
    
    //Outsourced(int id, int stock, int min, int max, String name, double price, String companyName)
    Part a3 = new Outsourced(3, 15, 5, 10, "Switch", 3.00, "XYZ Corp");    
    inv.addPart(a3);    
    
    //Product(int id, int stock, int min, int max, String name, double price)
    Product p1 = new Product(1, 3, 3, 10, "Les Paul", 1999.99);
    Product p2 = new Product(2, 4, 2, 5, "Flying V", 1599.99);
    Product p3 = new Product(3, 4, 3, 10, "SG Standard", 1300.00);
    inv.addProduct(p1);
    inv.addProduct(p2);
    inv.addProduct(p3);
    p3.addAssociatedPart(a2);
    p3.addAssociatedPart(a1);    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
