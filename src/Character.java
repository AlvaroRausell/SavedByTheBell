/**
 * Character class for "Saved by the Bell" game.
 * This class is the parent class of MainCharacter.
 *
 * It stores information common to all types of characters (main and NPCs).
 * This includes: name, description, current location, weight limit, items, and dialogs.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 */
import java.util.ArrayList;


public class Character {
    //Sets the fields
    protected Room currentRoom;
    protected ArrayList<Item> items;
    protected String name, description;
    protected int weightLimit;
    private Dialog dialog;


    /**
     * Creates a Character object, initialising the items ArrayList and setting up the other fields.
     * @param name Name of the Character
     * @param description Description of the Character
     * @param currentRoom Room where the character is located
     * @param dialog Possible dialogs displayed when the player interacts with the character.
     */
    public Character(String name, String description, Room currentRoom,Dialog dialog){
        items = new ArrayList<>();
        this.currentRoom = currentRoom;
        this.name = name;
        this.description = description;
        weightLimit = 5;
        this.dialog = dialog;
    }

    public void move(String direction){

        if (currentRoom.getExit(direction)!=null){
            currentRoom.getActions().removeCommand("greet "+name);
            this.currentRoom = currentRoom.getExit(direction);
            currentRoom.getActions().addCommand("greet "+name);
        }

    }
    /**
     * Returns the room where the character is located
     * @return currentRoom
     * */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Starts the interaction with the character, which calls the displayDialog method of the Dialog class
     * */
    public void talk(){
        dialog.displayDialog();
    }
    /**
     * Checks if the character has an item specified by its name
     * @param name Name of the item
     * @return hasItem
     * */
    public boolean hasItem(String name){
        for (Item i: items)
            if (i.getName().equalsIgnoreCase(name))
                return true;
        return false;
    }


    /**
     * Returns the name of the character
     * @return name
     * */
    public String getName() {
        return name;
    }
    /**
     * Returns description of the character
     * @return description
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Returns an item carried by the character after searching for it by name in the items ArrayList
     * @param name Name of the item
     * @return item
     */
    public Item getItemByName(String name){
        for (Item i: items){
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }
    /**
     * Lists all the items carried by the character with all their info
     * */
    public void checkBackpack(){
        int index = 1;
        if (items.isEmpty())
            System.out.println("You don't have items");
        else
            for (Item i: items){
                System.out.println("------ Item "+index+"------");
                System.out.println(i.getFullInfo());
                index++;
            }
    }
}
