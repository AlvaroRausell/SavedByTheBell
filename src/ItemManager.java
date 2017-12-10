/**
 * This is the ItemManager class for "Saved by the bell".
 *
 * It is in charge of setting up and storing all the items in the game
 * with their corresponding fields, which include name, status, if it can be taken,
 * weight and also the action that takes place whenever they are used.
 *
 * The class requires instances of the RoomManager and CharacterManager classes in order to set the items in relation
 * to the different rooms and characters in the game.
 *
 *The last field mentioned utilises the self-made Action interface, which holds the method run(), that will
 * execute whenever an item is used.
 *
 *@author Álvaro Rausell
 * @version 08.12.2017
 * */

import java.util.ArrayList;
public class ItemManager {
    private ArrayList<Item> items;
    private RoomManager roomManager;
    private MainCharacter mainCharacter;

    /**
     * Creates an ItemManager object while initialising the items ArrayList and creating instances for mainCharacter and
     * roomManager.
     * It also calls the method setItems, which will create every item in the game and store it in the items ArrayList
     * @param roomManager Room manager
     * @param characterManager Character manager*/
    public ItemManager(RoomManager roomManager,CharacterManager characterManager){
        items = new ArrayList<>();
        this.roomManager = roomManager;
        mainCharacter = characterManager.getMainCharacter();
        this.setItems();
    }
    /**
     * Initialises all the items in the game and adds them to the items ArrayList.
     * */

    private void setItems(){
        items.add(new Item("Phone", "Use this to check the time", true, 3,
                ()-> {
                    if (mainCharacter.getTime()>0)
                        System.out.println("You have "+mainCharacter.getTime()+" minutes left before the exam starts");
                    else if (mainCharacter.getTime() == 0)
                        System.out.println("The test is taking place right now");
                    else
                        System.out.println("You are "+mainCharacter.getTime()+" minutes late for the test");
                    mainCharacter.subtractTime(1);
                    mainCharacter.changeStress(5);
                }));

        items.add(new Item("Oyster Card","This is a public transport card, you need it to use the public transport", true,1,
                ()-> {
                    if (roomManager.getStations().contains(mainCharacter.currentRoom)) {
                        System.out.println("Now I can use the public transport");
                        mainCharacter.setHasOyster(true);
                        mainCharacter.subtractTime(1);
                    } else
                        System.out.println("You have to be at a station!");
                }));


        items.add(new Item("Backpack","This is a backpack, use it to have more item capacity",true,0,
                ()->{
                    if (!mainCharacter.hasBackpack()) {
                        mainCharacter.changeWeightLimit(10);
                        mainCharacter.setHasBackpack(true);
                        mainCharacter.changeComfort(-5);
                    }
                    else
                        System.out.println("You are already using it");
                }));


        items.add(new Item("Student ID","This will help you get to uni quicker",true,1,null));
        items.add(new Item("Money","10 GBP",true,1,null));
    }

    /**
     * Searches through the items ArrayList to find an item with name that matches the parameter.
     * @param name Name of the item to find
     * @return Item object*/
    public Item getItem(String name){
        for (Item i: items){
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }
}
