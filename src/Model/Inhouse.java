
package Model;

/*
 * Uses polymorphism to extend a generic Part
 * which add an attribute called machine Id meant
 * to track which machine the Inhouse part was 
 * produced with.
 */

public class Inhouse extends Part {
    
    private int machineId;

    public Inhouse(int id, int stock, int min, int max, String name, double price, int machineId) {
        super(id, stock, min, max, name, price);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
