/**
 * Item class for "Saved by the bell" game.
 *
 * This class stores all the information regarding a specific item,
 * such as name, weight, status, if it can be taken, as well as the action
 * triggered when it is used.
 *
 * The last field mentioned utilises the self-made Action interface, which holds the method run(), that will
 * execute whenever an item is used.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 * */
public class Item {

    private String name, status;
    private boolean canTake;
    private int weight;
    private Action action;

    /**Initialises an Item object, assigning values to all the field variables
     * @param name Name of the item
     * @param status Status of the object, or a brief description
     * @param canTake True when the user can take the object
     * @param weight Amount of space required to carry the object
     * @param action Action triggered when the item is used*/

    public Item(String name,String status,boolean canTake, int weight,Action action){
        this.name = name;
        this.canTake = canTake;
        this.weight = weight;
        this.action = action;
        this.status = status;
    }

    /**
     * Calls the action.run() method, which represents the item being used.
     * If there is no action assigned to the item, it displays an message saying that
     * the user cannot use that item.
     * */
    public void interact(){
        if (action == null)
            System.out.println("You cannot do anything with "+name);
        else
            this.getAction().run();
    }

    /**
     * @return The action object assigned to the item
     * */
    public Action getAction() {
        return action;
    }

    /**
     * @return The name of the item
     * */
    public String getName() {
        return name;
    }


    /**
     * @return The weight of the item
     * */
    public int getWeight() {
        return weight;
    }


    /**
     *@return true if the user can take the item
     * */
    public boolean canTake() {
        return canTake;
    }

    /**
     * @return the information of the item, including name, status and weight
     * */
    public String getFullInfo(){
        return "Item Name: "+this.name
                +"\nDescription: "+this.status
                +"\nWeight: "+this.weight;
    }

    /**
     * Updates the status (or description) of the item
     * @param status Status to set*/
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Updates the weight of the item
     * @param weight New weight
     * */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
