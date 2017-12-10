/**
 * Character Manager for "Saved by the bell" game.
 * This class is in charge of storing and managing all the characters in the game, including the main character.
 * It stores the secondary characters in the ArrayList characterList and the main character
 * in the mainCharacter variable, for fast access.
 *
 * The main character is initialised by calling the setMainCharacter method, while the rest are initialised and stored
 * using by calling the setCharacterList method.
 *
 * The class also has methods to set the room manager and to retrieve different objects such as:
 * main character, character by name and/or by room and the charactersList.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 * */

import java.util.ArrayList;

public class CharacterManager {
    private MainCharacter mainCharacter;
    private ArrayList<Character> characterList;
    private RoomManager roomManager;

    /**
     * Creates the CharacterManager object and initialises the characterList ArrayList
     * */
    public CharacterManager(){
        characterList = new ArrayList<>();
    }

    /**
     * Sets the roomManager instance
     * @param roomManager RoomManager object
     * */

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
        this.setCharacterList();
    }
    /**
     * Initialises and stores all the NPCs in the game and their actions
     * */

    public void setCharacterList(){

        characterList.add(new Character("Joe","your classmate",roomManager.getRoom("tubeStationB"),new Dialog("Hey! Ready for the test?",true,
                (reply)->{
                    System.out.println("Well... I'm not, so let's hope it is easy!");
                    mainCharacter.changeStress(5);

                })));

        characterList.add(new Character("Jamie","your flatmate",roomManager.getRoom("outside"),new Dialog("Hey! It's getting very cold here, I hope it snows soon!",false,null)));

        characterList.add(new Character("Homeless person","asking for money",roomManager.getRoom("bushHouse"),new Dialog("Hello, do you have spare change to give?\n(Yes,No)",true,
                (reply)->{
                    if (reply.equalsIgnoreCase("yes")) {
                        new Dialog("How much would you like to give me?\n(answer in integer)",true,(reply1)-> {
                            try {
                                int amount = Integer.parseInt(reply1);
                                if (mainCharacter.getMoney()-amount>=0){
                                    mainCharacter.changeMoney(-amount);
                                    System.out.println("Thanks for giving me "+amount+" pounds!");
                                    mainCharacter.changeComfort(15);
                                }
                                else
                                    System.out.println("You do not have "+amount+" pounds!");
                            }catch (NumberFormatException e){
                                System.out.println("That is not a value!");
                            }
                        } ).displayDialog();
                    }
                })));

        characterList.add(new Character("Security guard","greet him to get into the Strand campus",roomManager.getRoom("midWay"),new Dialog("Hey, do you have an ID?\n(Yes/No)",true,
                (reply)->{
                    if (reply.equalsIgnoreCase("Yes")&&mainCharacter.hasItem("Student ID")) {
                        System.out.println("Thanks for showing it to me, you now entered the Strand campus");
                        mainCharacter.subtractTime(2);
                        mainCharacter.goRoom(new Command("go","strandCampus"));

                    }else{
                        System.out.println("You don't have your ID with you, so you had to sign in, which took longer than desired");
                        mainCharacter.subtractTime(7);
                        mainCharacter.goRoom(new Command("go","strandCampus"));
                    }
                })));


        characterList.add(new Character("Prof. Turing","your PPA professor",roomManager.getRoom("examHall"),new Dialog("Hello! Are you ready for the exam?",true,
                (reply)->{
                    if (mainCharacter.getTime()>=0) {
                        if (reply.equalsIgnoreCase("Yes")) {
                            System.out.println("Perfect! Here is your exam.");
                            mainCharacter.takeTest();
                        }
                        else{
                            System.out.println("Well, I'm afraid you will have to take it anyways, here you are.");
                            mainCharacter.takeTest();
                        }

                    }else{
                        System.out.println("I'm afraid you arrived late. Sorry, you will not be able to take the test.");
                        Game.lose();
                    }
                })));

    }



    /**
     * Returns the characterList ArrayList
     * @return characterList
     */

    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    /**
     * Retrieves a character object by the name
     * @param name Name of the character
     * @return character*/

    public  Character getCharacterByName(String name){
        for (Character i: characterList){
            if (i.getName().equalsIgnoreCase(name))
                return i;
        }
        return null;
    }


    /**
     * Sets the main character of the game and stores it
     * @param name Name of the character
     * @param room Initial room
     * @param time Time from which to count down
     */

    public void setMainCharacter(String name, Room room, int time) {
        mainCharacter = new MainCharacter(name,room,roomManager,time);
    }

    /**
     *Returns a list of all the characters present in the specified room
     * @param room Room in which to search for players
     * @return List of characters in the room
     * */
    public ArrayList<Character> getCharactersByRoom(Room room){
        ArrayList<Character>characters = new ArrayList<>();
        for (Character s: characterList){
            if (s.getCurrentRoom() == room){
                characters.add(s);
            }
        }
        return characters;
    }

    /**Returns the main character of the game
     * @return main character
     * */
    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }
}
