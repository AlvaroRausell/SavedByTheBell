import java.util.*;

/**
 *
 * Room class in "Saved by the bell" game.
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room. It also stores the possible
 * commands that are specific to it as well as collections of characters and items
 * present in the room.
 *
 * @author  Álvaro Rausell
 * @version 08.12.2017
 */

public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private CommandWords actions;
    private ArrayList<Character> characters;
    private ArrayList<Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard". It also sets the commands that are available
     * for this specific room.
     *
     * @param description The room's description.
     */
    public Room(String description,String[] actions)
    {
        this.description = description;
        exits = new HashMap<>();
        this.actions = new CommandWords(actions);
        this.items = new ArrayList<>();
    }

    /**
     * @return the characters in the room as an ArrayList*/
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * @return A character retrieved by name
     */
    public Character getCharacterByName(String name){
        for (Character c: characters){
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
    /**
     * Sets a list of the characters in a room
     * @param characters ArrayList with the characters*/
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    /**
     * @return The commandWords of that room
     * */
    public CommandWords getActions() {
        return actions;
    }

    /**
     * @return a list with all the characters in the room and their description
     * */
    public String listPeople(){
        String people = "";
        if (characters.isEmpty())
            people += "You are alone";
        else
            for (Character c: characters)
                people+=c.getName()+", "+c.getDescription()+" | ";
        return people;
    }

    /**
     * @return a list with all the characters in the room and their description
     * */
    public String listItems(){
        int index = 1;
        String result = "";
        if (items.isEmpty()){
            result = "There are no items here";
            return result;
        }
        for (Item i: items){
            result+="------Item "+index+"------\n";
            result+= i.getFullInfo()+"\n";
            index++;
        }
        return result;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }


    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getActionString()+"\nItems in the room: "+listBasicItems()+"\nPeople: "+listPeople();
    }

    /**
     * @return a list with all the items in the room
     * */
    public String listBasicItems(){
        String itemNames="";
        for (Item i: items)
            itemNames+=i.getName()+" |  ";
        if (itemNames.equalsIgnoreCase(""))
            itemNames="There are no items here";
        return itemNames;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getActionString()
    {
        String returnString = "Actions: ";
        for(String s: actions.getValidCommands()) {
            returnString += " | "+s;
        }
        return returnString;
    }

    /**
     * Adds an item to the items ArrayList
     * @param item Item to add
     * */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * @return the items ArrayList
     * */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @return the item by the name
     * @param name Name of the item*/
    public Item getItem(String name){
        for (Item i: items){
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }


    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * @return true if the item is in the room
     * @param name Name of the item to look for
     * */
    public boolean hasItem(String name){
        for (Item i: items){
            if (i.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
    /**
     * Removes an item from the items ArrayList
     * @param item item to remove
     * */
    public void removeItem(Item item){
        items.remove(item);
    }

}

