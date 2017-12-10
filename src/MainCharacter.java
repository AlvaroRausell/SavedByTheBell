import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
/**
 * Main Character class for "Saved by the bell" game.
 *
 * This is a child of the Character class, which stores all the information
 * and actions that the player can take throughout the course of the game.
 *
 * This class differs from its parent class in the fact that it includes
 * more fields that are important to progress throughout the game. This includes
 * clothesOn, which is initialised as false (thus not letting the player move to other places
 * apart from the bed, room and bathroom) or hasOyster (necessary to use the transport services).
 * Additionally, it also stores an instance of the game's Room Manager, in order to navigate across the map.
 *
 * Lastly, if has variables such as stress and comfort that will affect the outcome of the game and vary
 * depending on the choices the player makes.
 *
 * It also includes methods to interact with objects and people, as well as buying items.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 * */
public class MainCharacter extends Character{
    private int time;
    private RoomManager roomManager;
    private ArrayList<Room> history;
    private int itemWeights;
    private boolean hasOyster;
    private int comfort,stress;
    private boolean clothesOn, hasBackpack;
    private int money;

    /**
     * Initialises the main character.
     * @param name Name of the player
     * @param room Initial room
     * @param roomManager Room manager
     * @param time Time to count down from
     * */
    public MainCharacter(String name, Room room, RoomManager roomManager, int time){
        super(name, "You are the protagonist, choose wisely what you want to do",room,null);
        hasOyster = false;
        this.time = time;
        this.roomManager = roomManager;
        history = new ArrayList<>();
        itemWeights = 0;
        comfort = 0;
        stress = 0;
        clothesOn = false;
        hasBackpack = false;
        money = 0;
    }

    /**
     * @return true if the user has a backpack
     * */
    public boolean hasBackpack() {
        return hasBackpack;
    }

    /**
     * Interacts with the character given as the parameter
     * @param character Character to interact with
     * */
    public void talkTo(String character){
        currentRoom.getCharacterByName(character).talk();

    }

    /**
     * Sets if the user has a backpack
     * @param hasBackpack true if the object has a backpack
     * */
    public void setHasBackpack(boolean hasBackpack) {
        this.hasBackpack = hasBackpack;
    }

    /**
     * Sets if the user has used the Oyster Card
     * @param hasOyster true if the Oyster Card item has been used*/
    public void setHasOyster(Boolean hasOyster){
        this.hasOyster = hasOyster;
    }

    /**
     * Gets up from bed. If the player is already up, it will display a message saying so*/
    public void getUp(){
        if (currentRoom != roomManager.getRoom("bed")){
            System.out.println("You are already up!");
        }
        else {
            this.goRoom(new Command("go","room"));
            time-=3;
        }
    }

    /**
     * Changes the player's clothes in order to go out.
     * If he has already changed them, it will display a message saying so.
     * */
    public void changeClothes(){
        if (!clothesOn){
            clothesOn = true;
            time-=5;
            System.out.println("You changed your clothes, you can now go out");}
        else
            System.out.println("You are already dressed up!");
    }

    /**
     * Buys the product input by the user. If the name of the product coincides
     * with the place it can be bought at, it adds the item to the inventory
     * and deducts the price from the money the player has.
     *
     * In case he is not in the appropriate place, or
     * if the user has no money, the system will display a message.
     * @param product Name of the product to buy
     * */
    public void buy(String product){
        //Buy coffee at coffee shop
        if (product.equalsIgnoreCase("coffee")&&currentRoom == roomManager.getRoom("coffeeShop")){
            if (!(money-2<0)){
                changeMoney(-2);
                items.add(new Item("Coffee","Take it in order to feel better",true,2,()->{changeComfort(5); items.remove(getItemByName("Coffee"));
                    System.out.println("You took coffee, you feel more energised!");}));
                System.out.println("You added coffee to your inventory, if you want to take it, type \"use coffee\"");
            }
            else
                System.out.println("You do not have enough money!");
        }
        //Buy food at the cafeteria
        else if (product.equalsIgnoreCase("food")&&currentRoom == roomManager.getRoom("cafeteria")){
            if (!(money-5<0)){
                changeMoney(-5);
                items.add(new Item("Food","Take it in order to feel better",true,4,()->{changeComfort(10); items.remove(getItemByName("Food"));
                    System.out.println("You took a bite, you feel much better!");}));
                System.out.println("You added food to your inventory, if you want to take it, type \"use food\"");
            }
            else
                System.out.println("You do not have enough money!");
        }
        //Buy pint at Waterfront bar
        else if (product.equalsIgnoreCase("pint")&&currentRoom == roomManager.getRoom("waterfront")){
            if (!(money-4<0)){
                changeMoney(-4);
                items.add(new Item("Pint","Take it in order to feel more relaxed",true,3,()->{changeStress(-5); items.remove(getItemByName("Pint"));
                    System.out.println("You took a pint, now you feel much more relaxed!");}));
                System.out.println("You added pint to your inventory, if you want to take it, type \"use pint\"");

            }
            else
                System.out.println("You don't have enough money!");
        }else
            System.out.println("You can't buy "+product+" here");
    }

    /**
     * Changes the weight limit, which occurs when the backpack is taken/dropped
     * @param change Amount by which the weight limit changes
     * */
    public void changeWeightLimit(int change){
        weightLimit+=change;
    }

    /**
     * Prints the stats of the player
     * */
    public void showStats(){
        System.out.println("------STATS------");
        System.out.println("Max. item capacity: "+weightLimit);
        System.out.println("Item weight: "+itemWeights);
        System.out.println("Comfort: "+ comfort);
        System.out.println("Stress level: "+stress);
        System.out.println("Money: "+getMoney()+" GBP");
    }

    /**
     * Changes the stress levels by a certain amount
     * @param change amount by which to change the stress level
     * */
    public void changeStress(int change){stress += change;}

    /**
     * @return the money the player has
     * */
    public int getMoney() {
        return money;
    }

    /**
     * Changes the amount of money the player has
     * @param change Amount by which to change the quantity of money
     * */
    public void changeMoney(int change){money+=change;}

    /**
     * Withdraws money from the ATM machine
     * */
    public void withdrawMoney(){
        System.out.println("How much money do you want to withdraw?");
        System.out.println("(Hint: the more you withdraw, the longer the process will take)");
        System.out.println("1)10 GBP      2)20GBP      3)50GBP      4)100GBP");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        switch (option){
            case 1:
                System.out.println("Withdrawing 10 pounds...");
                subtractTime(3);
                changeMoney(10);
                break;
            case 2:
                System.out.println("Withdrawing 20 pounds...");
                subtractTime(6);
                changeMoney(20);
                break;
            case 3:
                System.out.println("Withdrawing 50 pounds...");
                subtractTime(15);
                changeMoney(50);
                break;
            case 4:
                System.out.println("Withdrawing 100 pounds...");
                subtractTime(30);
                changeMoney(100);
                break;
            default:
                System.out.println("Choose a valid option");
                break;
        }
        getItemByName("money").setStatus(""+money+" GBP");
        getItemByName("money").setWeight(money/10);
    }

    /**
     * Changes the comfort levels by a certain amount
     * @param change amount by which to change the comfort level
     * */
    public void changeComfort(int change){comfort += change;}

    /**
     * Takes the test when the player is at the exam hall.
     * It takes into account the stress and comfort levels to set the grade of the test.
     * In the end it displays a goodbye message and exits the program.*/
    public void takeTest(){
        double grade;
        grade =70-stress*1.50+comfort*1.25;
        System.out.println("You took the exam");
        System.out.println("------PRESS ENTER TO CONTINUE------");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println("(*Four weeks later*)");
        System.out.println("Keats\t"+name);
        System.out.println("TEST RESULTS");
        System.out.println("Programming Practice and Applications --- Prof. Alan Turing");
        System.out.println("Semester exam");
        if (grade >100)
            grade = 100;
        System.out.println("Grade: "+grade+"/100");
        if (grade >39)
            System.out.println("You passed the exam, congrats!");
        else
            System.out.println("You failed the exam, try better next time");
        System.out.println("Thank you for playing!");
        System.exit(0);

    }
    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    public void goRoom(Command command)
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (direction.equalsIgnoreCase("back")){

            if (roomManager.getStations().contains(currentRoom)&&roomManager.getStations().contains(history.get(history.size()-1))){
                if (hasOyster){
                    roomManager.setCurrentRoom(history.get(history.size()-1));
                    if (roomManager.getBusStations().contains(currentRoom))
                        time -= 35;
                    else
                        time-=20;
                    roomManager.moveCharactersAround();
                    currentRoom = roomManager.getCurrentRoom();
                    System.out.println(currentRoom.getLongDescription());
                    history.remove(history.get(history.size()-1));
                    setHasOyster(false);

                }else{
                    System.out.println("You need to use the Oyster Card!");
                }
            }
            else{
                roomManager.moveCharactersAround();
                roomManager.setCurrentRoom(history.get(history.size()-1));
                currentRoom = roomManager.getCurrentRoom();
                System.out.println(currentRoom.getLongDescription());
                history.remove(history.get(history.size()-1));
                time -= 3;
                setHasOyster(false);
            }
        }
        else if (nextRoom == null) {
            System.out.println("You can't "+command.getCommandWord()+" "+command.getSecondWord());
        }
        else if(clothesOn||currentRoom==roomManager.getRoom("bed")||nextRoom == roomManager.getRoom("bathroom")||nextRoom == roomManager.getRoom("room")){
            roomManager.moveCharactersAround();
            history.add(currentRoom);
            roomManager.setCurrentRoom(nextRoom);
            currentRoom = roomManager.getCurrentRoom();
            System.out.println(currentRoom.getLongDescription());
            time-=3;
        }else
            System.out.println("You need to change your clothes first!");


    }
    /**
     * Subtracts the time available by the value set in the parameter and increase the stress by the half
     * of the parameter
     * @param sub Value to subtract from the time variable
     * */
    public void subtractTime(int sub){
        time-=sub;
        changeStress(sub/2);
    }

    /**
     * Checks if the user has the item. If so, it calls its interact method.
     * Else, it displays a message according to whether there is no parameter
     * or if teh user doesn't have that item.
     * @param item name of the item to use
     * */

    public void useItem(String item){
        item = item.trim();
        if (this.hasItem(item)) {
            getItemByName(item).interact();
        }
        else if(item == null)
            System.out.println("Use what?");
        else
            System.out.println("You don't have any "+item);
    }

    /**
     * @return the amount of time remaining
     * */
    public int getTime(){return time; }

    /**
     * Displays what the player sees in the current room (items and people)
     * */
    public void lookAround(){
        System.out.println("You look around and see: ");
        System.out.println("Items: "+currentRoom.listItems());
        System.out.println("People: "+currentRoom.listPeople());
    }

    /**
     * Sleeps when the player is in the bed,
     * which increases his comfort and decreases his stress levels,
     * but subtracts time
     * */
    public void sleep(){
        comfort+=10;
        stress-=10;
        subtractTime(30);
        System.out.println("You slept for 30 minutes, you feel very relaxed");
    }

    /**
     * Takes all the items in the room.
     * If it exceeds the weight limit, it will display a message
     * and will not take the remaining items.
     * */
    public void takeAll(){
        if (currentRoom.getItems().isEmpty())
            System.out.println("There are no items here!");
        else {
            for (Iterator<Item> iterator = currentRoom.getItems().iterator(); iterator.hasNext(); ) {
                Item i = iterator.next();
                if (i.canTake()) {
                    if (i.getWeight() + itemWeights <= weightLimit) {
                        items.add(i);
                        iterator.remove();
                        time -= 1;
                        itemWeights += i.getWeight();
                        System.out.println("You took: " + i.getName());
                        if (i.getName().equalsIgnoreCase("money"))
                            money+=10;
                        if (i.getName().equalsIgnoreCase("backpack"))
                            useItem("backpack");
                    } else
                        System.out.println("You do not have more space, did you use the backpack?");
                } else
                    System.out.println("Sorry, you can't take " + i.getName());
            }
        }
    }

    /**
     * This method responds to the take command, thus having several possible tasks.
     * If it is an item, the player will take it as far as it is in the room and
     * it does not exceed the weight limit.
     * If it is something else, such as shower, tube, or bus, the player will do
     * an action as far as it is available in the current room.
     * @param item "Item" to take
     * */
    public void take(String item){
        if (item.equalsIgnoreCase("shower")){
            if (currentRoom == roomManager.getRoom("bathroom")){
                System.out.println("You took a shower, you feel refreshed!");
                time-=15;
                comfort+= 20;
            }
            else
                System.out.println("Where do you intend to take a shower?");
        }
        else if (item.equalsIgnoreCase("bus")){
            if (currentRoom == roomManager.getRoom("busStopA")){
                if (hasOyster){
                    time -=35;
                    comfort+=25;
                    stress +=15;
                    System.out.println("You took the bus");
                    goRoom(new Command("go","busStopB"));
                    setHasOyster(false);
                }
                else
                    System.out.println("You need to use the Oyster Card!");
            }else
                System.out.println("This is not a bus stop!");
        }
        else if (item.equalsIgnoreCase("tube")){
            if (currentRoom == roomManager.getRoom("tubeStationA")){
                if (hasOyster){
                    time -=20;
                    comfort -=10;
                    stress += 25;
                    System.out.println("You took the tube");
                    goRoom(new Command("go","tubeStationB"));
                    setHasOyster(false);
                }
                else
                    System.out.println("You need to use the Oyster Card!");
            }else
                System.out.println("This is not a tube station!");
        }
        else if (roomManager.getCurrentRoom().hasItem(item)){
            Item i= roomManager.getCurrentRoom().getItem(item);
            if (i.canTake()){
                if (i.getWeight()+itemWeights<=weightLimit){
                    items.add(i);
                    if (i.getName().equalsIgnoreCase("money"))
                        changeMoney(10);
                    if (i.getName().equalsIgnoreCase("backpack"))
                        useItem("backpack");
                    currentRoom.removeItem(i);
                    time-=1;
                    itemWeights+=i.getWeight();
                    System.out.println("You took: "+i.getName());
                }
                else
                    System.out.println("You do not have more space, did you use the backpack?");
            }
            else
                System.out.println("Sorry, you can't take "+i.getName());
        }
        else
            System.out.println("There is no "+item+" in here");
    }

    /**
     * Drops an item by name if the player has it. Else, it will say that he does not
     * have such item.
     * If the item in question is the backpack, it will drop it iff the new weight limit is not
     * exceeded by the weight of the items the  player has. Otherwise, the system will ask the player
     * to drop other items before.
     * @param item Item to drop*/
    public void dropItem(String item){
        if (hasItem(item)){

            Item i= getItemByName(item);
            items.remove(i);
            roomManager.getCurrentRoom().addItem(i);
            time-=1;
            itemWeights-=i.getWeight();
            if (item.equalsIgnoreCase("backpack")){
                if (itemWeights>weightLimit-10)
                    System.out.println("You cannot drop the backpack, you will have to drop other items first!");
                else{
                    weightLimit-=10;
                    setHasBackpack(false);
                    System.out.println("You dropped: "+i.getName());
                }
            }else
                System.out.println("You dropped: "+i.getName());

        }
        else
            System.out.println("You don't have a "+item+"!");
    }

}
