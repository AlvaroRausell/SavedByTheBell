import java.util.Scanner;

/**
 *  This class is the main class of the "Saved by the bell" application.
 * "The BlueJ Odyssey" is an intense, time-dependent, text-based game.
 * Its objective is to walk through the different rooms to go to the examination hall in time for the exam.
 * However, the player should also take into account variables such as comfort and stress, since they will play a role
 * in the outcome of the game.
 *
 * The amount of time available will be dependent on the difficulty chosen by the player, having more time the easier options.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all the managers, which initialise their corresponding fields,
 *  creates the parser and starts the game.
 *
 * @author  Álvaro Rausell
 * @version 08.12.2017
 */

public class Game
{
    private CharacterManager characterManager;
    private RoomManager roomManager;
    private Parser parser;
    /**
     * Create the game and initialise the different managers.
     */
    public Game()
    {
        roomManager = new RoomManager(); //Initialises roomManager, which creates the rooms
        roomManager.createRooms(); //Creates all the rooms in the roomManager class
        characterManager = new CharacterManager(); //Initialises characterManager
        characterManager.setRoomManager(roomManager);
        roomManager.setCharacterManager(characterManager);
        roomManager.setCharacters(); //Adds the characters from characterManager class to the roomManager class
        parser = new Parser(roomManager.getCurrentRoom(),characterManager); //Initialises a parser designed for the starting room
    }


    /**
     * Sets up the rest of elements of the game, such as the main character and the implementation of the itemManager class.
     * It also sets up the difficulty given an input form the user, making the time available greater the easier it it set.
     * */
    private void setUp(){
        //Sets up basic information about the main character
        System.out.println("Hello! What's your name?");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        int dif = 0;
        String difficulty; //Difficulty of the game
        //Sets up the difficulty level
        boolean difficultySet = false;
        while(!difficultySet){
            System.out.println("What difficulty level do you desire?");
            System.out.println("1-Easy      2-Medium      3-Hard");
            try {

                difficulty = sc.nextLine();
                dif = Integer.parseInt(difficulty);
            }
            catch (NumberFormatException e){

            }


            if (dif == 1){
                characterManager.setMainCharacter(name,roomManager.getCurrentRoom(),150);
                difficultySet = true;
            }
            else if (dif == 2){
                characterManager.setMainCharacter(name,roomManager.getCurrentRoom(),120);
                difficultySet = true;
            }
            else if (dif == 3){
                characterManager.setMainCharacter(name,roomManager.getCurrentRoom(),90);
                difficultySet = true;
            }else
                System.out.println("Please, enter a valid option");
        }

        //Implements the itemManager class and connects it to the roomManager class
        roomManager.setItemManager(new ItemManager(roomManager,characterManager));
        roomManager.setItems();

    }
    /**
     *  Main play routine.  Loops until end of play.
     */

    public void play()
    {
        boolean finished;
        setUp();
        printWelcome();

        finished = false;
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        while (! finished) {
            parser = new Parser(roomManager.getCurrentRoom(),characterManager);
            Command command = parser.getCommand();
            finished = parser.processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */

    private void printWelcome()
    {

        System.out.println("Welcome "+characterManager.getMainCharacter().getName()+" \"to Saved by the Bell!\"");
        System.out.println("A game in which not only time will matter, but every decision will affect its outcome");
        System.out.println("Make sure you take and use the phone to keep track of the time until the test starts");
        System.out.println("Also, make sure to use \"show stats\" to check your stress and comfort, these will affect how well you do!");
        System.out.println("You just woke up from a crazy night at Waterfront, you don't remember anything that happened");
        System.out.println("You just realised you have an exam today!");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(roomManager.getCurrentRoom().getLongDescription());
    }




    /**
     * Is called when the player did not get to the exam on time.
     * Displays a message and quits the game.
     * */

    public static void lose(){
        System.out.println("You were not able to arrive in time, you lost");
        System.out.println("Thanks for playing!");
        System.exit(0);
    }


    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    public static boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
