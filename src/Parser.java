import java.util.Scanner;

/**
 * This class belongs to "Saved by the Bell", an intense, time-dependent, text-based game.
 *
 * Every time the player enters a new room, a new Parser object is created with the commands available
 * for that specific room, increasing the uniqueness of each stage.
 *
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two or three word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 *
 * @author  Álvaro Rausell
 * @version 08.12.2017
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input
    private CharacterManager characterManager; //Character manager
    /**
     * Create a parser to read from the terminal window, obtaining the available commands by calling room.getActions().
     */
    public Parser(Room room,CharacterManager characterManager)
    {
        this.characterManager = characterManager;
        commands = room.getActions();
        reader = new Scanner(System.in);
    }

    /**
     * Reads the command line input by the user and returns either a two or a three word Command object
     * @return The next command from the user.
     */

    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3;
        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            } if (tokenizer.hasNext()){
                word3 = tokenizer.next();
                return new Command(word1,word2,word3);
            }
        }

        return new Command(word1, word2);

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */

    public boolean processCommand(Command command)
    {
        boolean wantToQuit = false;
        String parameter;                 //Variable passed in as the parameters of a command
        if (command.hasThirdWord())
            parameter = command.getSecondWord()+" "+command.getThirdWord();       //If the parameter is composed of two words, it adds the third word to the parameters variable
        else
            parameter = command.getSecondWord();
        String commandWord = command.getCommandWord();


        if (commandWord == null){
            System.out.println("Please, enter a command");
        }
        else if (commands.isCommand(command.getCommandWord())){
            if (commandWord.equals("help")) {
                printHelp();
            }
            else if (commandWord.equals("go")) {
                if (characterManager.getMainCharacter().getCurrentRoom().getExit(parameter)!=null)
                    characterManager.getMainCharacter().goRoom(command);
                else
                    System.out.println("Enter a valid exit");
            }
            else if (commandWord.equalsIgnoreCase("withdraw"))
                if (command.getSecondWord().equalsIgnoreCase("money"))
                    characterManager.getMainCharacter().withdrawMoney();
                else
                    System.out.println("Withdraw what?");
            else if (commandWord.equals("greet")){
                if (parameter == null||!characterManager.getCharacterList().contains(characterManager.getCharacterByName(parameter)))
                    System.out.println("Greet who?");
                else
                    characterManager.getMainCharacter().talkTo(parameter);
            }
            else if (commandWord.equalsIgnoreCase("sleep")){
                if (command.getSecondWord() == null)
                    characterManager.getMainCharacter().sleep();
                else
                    System.out.println("I don't understand");
            }
            else if (commandWord.equalsIgnoreCase("show")){
                if (parameter.equalsIgnoreCase("stats"))
                    characterManager.getMainCharacter().showStats();
                else
                    System.out.println("Show what?");
            }
            else if (commandWord.equalsIgnoreCase("back"))
                characterManager.getMainCharacter().goRoom(new Command("go","back"));
            else if(commandWord.equalsIgnoreCase("take")){
                if (command.getSecondWord().equalsIgnoreCase("all"))
                    characterManager.getMainCharacter().takeAll();
                else
                    characterManager.getMainCharacter().take(parameter);
            }
            else if(commandWord.equalsIgnoreCase("get")){
                if (parameter == null||!parameter.equalsIgnoreCase("up"))
                    System.out.println("Get what?");
                else
                    characterManager.getMainCharacter().getUp();
            }
            else if (commandWord.equalsIgnoreCase("look")){
                if (!command.getSecondWord().equalsIgnoreCase("around"))
                    System.out.println("Look what?");
                else
                    characterManager.getMainCharacter().lookAround();
            }
            else if (commandWord.equals("quit")) {
                wantToQuit = Game.quit(command);
            }
            else if (commandWord.equalsIgnoreCase("use")){
                if (command.getSecondWord() == null)
                    System.out.println("Use what?");
                else
                    characterManager.getMainCharacter().useItem(parameter);
            }
            else if (commandWord.equalsIgnoreCase("buy")){
                if (!command.hasSecondWord())
                    System.out.println("Buy what?");
                else
                    characterManager.getMainCharacter().buy(parameter);
            }
            else if(commandWord.equalsIgnoreCase("check")){
                if (command.getSecondWord().equalsIgnoreCase("items"))
                    characterManager.getMainCharacter().checkBackpack();
                else
                    System.out.println("Check what?");

            }else if(commandWord.equalsIgnoreCase("drop")){
                if (characterManager.getMainCharacter().hasItem(parameter))
                    characterManager.getMainCharacter().dropItem(parameter);
                else
                    System.out.println("Drop what?");

            }else if (commandWord.equalsIgnoreCase("change")){

                if (command.getSecondWord().equalsIgnoreCase("clothes"))
                    characterManager.getMainCharacter().changeClothes();
                else
                    System.out.println("Change what?");
            }
            else
                System.out.println("I don't know what you mean...");
        }
        else{
            System.out.println("I don't know what you mean...");
        }

        // else command not recognised.
        return wantToQuit;
    }


    /**
     * Prints help info, which includes the overall objective and the information concerning the room.
     * Information such as name and description of the room as well as commands allowed and the presence of items/characters.
     */
    private void printHelp()
    {
        System.out.println("You have to get to the examination hall in Strand campus as quickly as possible!");
        System.out.println("For more info use the \"look around\" command");
        System.out.println(characterManager.getMainCharacter().currentRoom.getLongDescription());


    }

}
