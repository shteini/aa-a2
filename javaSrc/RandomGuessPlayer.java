import java.io.*;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player extends PlayerDefinition
{
    // Each player needs to know who they are from the players list
    // which can be found through this.name as we extend PlayerDefinition
    //
    // They also need to hold a list of all other players in the game so
    // that they can eliminate players as the guesses happen(minus themselves)
    private ArrayList<PlayerDefinition> players;

    // We need a list of all possible attributes and their values for this player to guess from, this could be the data/utility class with an accessible arraylist
    // of all attributes and values

    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public RandomGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
        //Load data in from file
        //Assigns the name for this RandomGuessPlayer
        //Using that name we can then find which values to load
        //into this player
        this.name = chosenName;


    } // end of RandomGuessPlayer()


    public Guess guess() {

        // placeholder, replace
        return new Guess(Guess.GuessType.Person, "", "Placeholder");
    } // end of guess()


    public boolean answer(Guess currGuess) {

        // placeholder, replace
        return false;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {

        // placeholder, replace
        return true;
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
