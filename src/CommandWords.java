import java.util.ArrayList;

/**
 * CommandWords class for "Saved by the bell" game
 *
 * This class holds an enumeration of all command words known available for a specific set of actions.
 * It lists all basic commands that are common to all rooms in the game while it also adds extra commands
 * that are unique to each room in particular.
 *
 * It is used to recognise commands as they are typed in.
 *
 * @author  Álvaro Rausell
 * @version 08.12.2017
 */

public class CommandWords
{
    // a constant array that holds all nasic command words
    private static final String[] basicCommands = {
            "check items","look around","take (item name)","take all","use (item name)","drop (item name)", "show stats","back","quit", "help"
    };
    //List of the final commands valid for a room
    private ArrayList<String> validCommands;

    /**
     * Initialises the command words by collecting the basic commands
     * and the ones specific to each room (obtained as the parameter actions).
     *
     * @param actions Actions specific to a room
     */
    public CommandWords(String[] actions)
    {
        validCommands = new ArrayList<>();
        for (int i = 0; i< actions.length+basicCommands.length;i++){

            if (i< actions.length){
                validCommands.add(actions[i]);
            }
            else{
                validCommands.add(basicCommands[i-actions.length]);
            }
        }
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.size(); i++) {
            if(validCommands.get(i).contains(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    public void addCommand(String command){
        validCommands.add(0,command);
    }
    public void removeCommand(String command){
        validCommands.remove(command);
    }

    /**
     * @return all the valid commands
     * */
    public ArrayList<String> getValidCommands() {
        return validCommands;
    }
}
