/**
 * Response interface created for "Saved by the bell" game.
 * Its utility is to save a method to be executed when a dialog receives a reply.
 * This method would be executed by implementing the respond() method, and will take the reply
 * as parameter.
 *
 * @author Álvaro Rausell
 * @version 08.12.2017
 */

interface Response {
    /**
     * Method triggered by a reply by the user
     * @param reply Reply input by the user
     * */
    void respond(String reply);
}
