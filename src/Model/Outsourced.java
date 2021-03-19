
package Model;

/*
 * Uses polymorphism to extend a generic Part
 * which add an attribute called company name meant
 * to track which company provided the Outsourced part.
 */

public class Outsourced extends Part {
    
    private String companyName;

    public Outsourced(int id, int stock, int min, int max, String name, double price, String companyName) {
        super(id, stock, min, max, name, price);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
