
package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TheQcrew
 */
public class Inventory {
    
    private static ObservableList<Part> allParts=FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts=FXCollections.observableArrayList();
    public static ObservableList<Part> partsNameSearch = FXCollections.observableArrayList();
    public static ObservableList<Product> productsNameSearch=FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    
    public static Part lookupPart(int partId) 
    {       
        for(int i = 0; i < allParts.size(); i++)
            {
                if(allParts.get(i).getId() == partId)
                {
                    return allParts.get(i);
                }
            }
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName)
    {
        for(Part p : allParts)
            {
                if(p.getName().contains(partName))
                {
                    partsNameSearch.add(p);
                    return partsNameSearch;
                }
            }        
        return null;
    }
    
    
    public static Product lookupProduct(int productId)
    {
        for(int i = 0; i < allProducts.size(); i++)
            {
                if(allProducts.get(i).getId() == productId)
                {
                    return allProducts.get(i);
                }
            }
        return null;
    }    
    
    public static ObservableList<Product> lookupProduct(String productName)
    {
        for(Product p : allProducts)
            {
                if(p.getName().contains(productName))
                {
                    productsNameSearch.add(p);
                    return productsNameSearch;
                }
            }
        return null;
    }
    
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }
    
    public static void deletePart(Part selectedPart)
    {
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct(Product selectedProduct)
    {
        allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }    
            
}
