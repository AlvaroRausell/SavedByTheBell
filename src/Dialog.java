import java.util.Scanner;

/**
 * Dialog class for "Saved by the bell" game.
 *
 * This class is in charge of storing the possible dialogs
 * that are displayed when the player interacts with a character.
 *
 * Not only this, but it also holds a boolean that is set to true when
 * a conversation requires a reply, as well as a method form the self-made
 * Response interface that will trigger an action according to the reply input by the user and
 * other factors.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 * */
public class Dialog {

    private String dialog;
    private Response answer;
    private boolean requiresReply;

    /**
     * Initialises a Dialog object
     * @param dialog Initial sentence from the character
     * @param requiresReply True if the initial dialog requires a reply by the user
     * @param answer Action that is triggered in consequence of the reply input by the user
     * */

    public Dialog (String dialog, Boolean requiresReply,Response answer){
        this.dialog = dialog;
        this.requiresReply = requiresReply;
        this.answer = answer;
    }


    /**
     * Displays the initial dialog and requests a reply if the conversation requires one
     * */

    public void displayDialog(){
        System.out.println(dialog);
        if (requiresReply)
            requestReply();
    }

    /**
     * Requests a reply by the user, which then triggers answer.respond() set initially in the constructor
     * */

    public void requestReply (){
        Scanner sc = new Scanner(System.in);
        answer.respond(sc.nextLine());
    }

}
