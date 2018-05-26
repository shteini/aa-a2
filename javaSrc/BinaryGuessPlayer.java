import java.io.*;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer extends PlayerDefinition implements Player
{
    private Data gameData;
    private AttributeCounter counter;
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

    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
        //Load data in from file
        //Assigns the name for this RandomGuessPlayer
        //Using that name we can then find which values to load
        //into this player
        gameData = new Data();
        // Load all the players and attributes in
        gameData.loadGameFile(gameFilename);

        // Copy the data of the player with the chosenName to this instance
        // and remove it from the players list as we won't be guessing ourselves
        loadSelf(gameData.getPlayerByName(chosenName));

        //attribute counter 
        counter = new AttributeCounter();

    } // end of BinaryGuessPlayer()


    public Guess guess() {

        if(gameData.players.size() == 1)
        {
            return new Guess(Guess.GuessType.Person, "", gameData.players.get(0).name);
        }
        else {
            
        }

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

} // end of class BinaryGuessPlayer
