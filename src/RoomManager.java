/**
 * This is the RoomManager class for "Saved by the bell".
 *
 * It is in charge of setting up and storing all the rooms in the game
 * with their corresponding fields, which include name, description, available actions,
 * exits, and the items and characters that are contained in it.
 *
 * It also has specialised collections for the stations, which will be useful whenever the player has to travel back
 * and forth between them.
 *
 * The class requires instances of the ItemManager and CharacterManager classes in order to set up with their
 * corresponding content.
 *
 * It creates an instance of the CharacterManager class after the constructor in order to set the main character first,
 * avoiding potential Null Pointer Exceptions. This also occurs with the ItemManager class.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 * */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RoomManager {
    private CharacterManager characterManager;
    private HashMap<String,Room> rooms;
    private Room currentRoom;
    private ItemManager itemManager;
    private ArrayList<Room> stations,busStations,tubeStations;

    //Creates the RoomManager object, which initialises all the necessary collections to store all the information.
    public RoomManager(){
        stations = new ArrayList<>();
        rooms = new HashMap<>();
        busStations = new ArrayList<>();
        tubeStations = new ArrayList<>();
    }

    /**
     * Sets the character manager, which will be important in order to place each character in its room
     *@param characterManager The character manager*/
    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }




    /**
     * Sets the item manager, which will be used to sets the items in place
     * @param itemManager The item manager*/
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }



    /**
     * Creates all the rooms and its exits, and stores them in the HashMap rooms.
     * Also, it sets the stations array lists, which will be used in the MainCharacter class
     * */
    public void createRooms()
    {
        // create the rooms
        rooms.put("bed",new Room("in your bed",("get up,sleep".split(","))));
        rooms.put("room",new Room("in your room",("go west(to the bathroom),go out,change clothes").split(",")));
        rooms.put("bathroom", new Room("in the bathroom",("take shower").split(",")));
        rooms.put("outside", new Room("outside the accommodation",("greet Jamie,go east (the tube station),go north (the bus stop)").split(",")));
        rooms.put("busStopA", new Room("at bus stop MK",("take bus (to Trafalgar Square),go south (accommodation)").split(",")));//HERE
        rooms.put("busStopB",new Room("at bus stop L, in Trafalgar Square",("go north (coffee shop),go east (towards Strand)").split(",")));
        rooms.put("tubeStationA",new Room("at Finsbury Park station",("take tube,go west (accommodation)").split(",")));
        rooms.put("tubeStationB",new Room("at Holborn Station",("go south (to Bush House),go east (coffee shop)").split(",")));
        rooms.put("coffeeShop",new Room("at the coffee shop",("buy coffee,go west (Holborn)").split(",")));
        rooms.put("midWay",new Room("at Strand, getting close to the campus",("greet Security Guard,go north (Bush House),go south (restaurant),go west (Bus Stop L)").split(",")));
        rooms.put("bushHouse",new Room("at Bush House","greet homeless person,go south (Strand),go east (ATM Machine),go north (Holborn Station)".split(",")));
        rooms.put("strandCampus",new Room("at the Strand campus","go up (Waterfront Bar),go south (King's building)".split(",")));
        rooms.put("cafeteria",new Room("in a restaurant","buy food,go north (Strand)".split(",")));
        rooms.put("waterfront", new Room("at Waterfront Bar","buy pint, go down (Strand campus)".split(",")));
        rooms.put("kingsBuilding", new Room("at King's building","go west (Examination Hall),go north (Strand Campus)".split(",")));
        rooms.put("examHall",new Room("at the Examination Hall","greet Prof. Turing,go east (King's Building)".split(",")));
        rooms.put("atm",new Room("in an ATM Machine","withdraw money,go west (Bush House)".split(",")));


        // initialise room exits
        setExit("atm","west","bushHouse");
        setExit("bed","room","room");
        setExit("room","west","bathroom");
        setExit("room","out","outside");
        setExit("outside","east","tubeStationA");
        setExit("outside","north","busStopA");
        setExit("busStopA","busStopB","busStopB");
        setExit("busStopA","south","outside");
        setExit("busStopB","north","coffeeShop");
        setExit("busStopB","east","midWay");
        setExit("coffeeShop","west","tubeStationB");
        setExit("midWay","west","busStationB");
        setExit("midWay","north","bushHouse");
        setExit("midWay","south","cafeteria");
        setExit("midWay","strandCampus","strandCampus");
        setExit("cafeteria","north","midWay");
        setExit("tubeStationA","tubeStationB","tubeStationB");
        setExit("tubeStationA","west","outside");
        setExit("tubeStationB","east","coffeeShop");
        setExit("tubeStationB","south","bushHouse");
        setExit("bushHouse","north","tubeStationB");
        setExit("bushHouse","south","midWay");
        setExit("bushHouse","east","atm");
        setExit("strandCampus","up","waterfront");
        setExit("strandCampus","south","kingsBuilding");
        setExit("waterfront","down","strandCampus");
        setExit("kingsBuilding","west","examHall");
        setExit("kingsBuilding","north","strandCampus");
        setExit("examHall","east","kingsBuilding");




        //Sets starting room
        currentRoom = rooms.get("bed");

        //Set the stations ArrayLists
        stations.add(rooms.get("busStopA"));
        stations.add(rooms.get("busStopB"));
        stations.add(rooms.get("tubeStationA"));
        stations.add(rooms.get("tubeStationB"));
        busStations.add(rooms.get("busStopA"));
        busStations.add(rooms.get("busStopB"));
        tubeStations.add(rooms.get("tubeStationA"));
        tubeStations.add(rooms.get("tubeStationB"));



    }


    /**
     * Places the items from itemManager to the corresponding room
     * */
    public void setItems(){
        rooms.get("room").addItem(itemManager.getItem("Phone"));
        rooms.get("room").addItem((itemManager.getItem("Oyster Card")));
        rooms.get("room").addItem(itemManager.getItem("Backpack"));
        rooms.get("room").addItem(itemManager.getItem("Student ID"));
        rooms.get("room").addItem(itemManager.getItem("Money"));
    }




    /**
     * Shortcut method to set an exit for a room.
     * @param roomName The name of the original room
     * @param direction The direction of the destination (north, south, east, west, etc...)
     * @param nextRoom The destination room
     */
    private void setExit(String roomName,String direction, String nextRoom){
        rooms.get(roomName).setExit(direction,rooms.get(nextRoom));
    }



    /**
     * Returns the stations ArrayList
     * @return stations
     */
    public ArrayList<Room> getStations() {
        return stations;
    }


    /**
     * Returns the bus stations in an ArrayList
     * @return busStations
     */
    public ArrayList<Room> getBusStations() {
        return busStations;
    }



    /**
     * Moves some of the characters across the different parts of the map.
     * The rest will remain in their position
     * */
    public void moveCharactersAround(){
        Random random = new Random();
        String [] possibleMoves = {"north","south","east","west"};
        String direction = possibleMoves[random.nextInt(possibleMoves.length)];
        characterManager.getCharacterByName("Jamie").move(direction);
        direction = possibleMoves[random.nextInt(possibleMoves.length)];
        characterManager.getCharacterByName("Joe").move(direction);
        direction = possibleMoves[random.nextInt(possibleMoves.length)];
        characterManager.getCharacterByName("Homeless person").move(direction);
        setCharacters(); //Updates their new position
    }

    /**
     * Places the characters from characterManager into their corresponding room
     * */
    public void setCharacters(){
        for (Map.Entry e: rooms.entrySet()){
            Room room = (Room)(e.getValue());
            room.setCharacters(characterManager.getCharactersByRoom(room));
        }
    }


    /**
     * This method return the Room object requested identified by its name
     * @param name  Name of the room to find*/
    public Room getRoom(String name){
        for (Map.Entry e: rooms.entrySet() ){
            Room temp = (Room)e.getValue();
            if(e.getKey().equals(name))
                return temp;

        }
        return null;
    }



    /**
     * Updates the current room of the game
     * @param currentRoom The new current room
     */
    public void setCurrentRoom(Room currentRoom){
        this.currentRoom = currentRoom;
    }




    /**
     * Returns the current room
     * */
    public Room getCurrentRoom() {
        return currentRoom;
    }

}//end of class


