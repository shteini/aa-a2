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
        this.name = chosenName;
        loadSelf(gameData.getPlayerByName(chosenName));

        //attribute counter
        counter = new AttributeCounter(gameFilename, gameData);
        counter.countAttributes(gameData);

    } // end of BinaryGuessPlayer()


    public Guess guess() {

        if(gameData.players.size() == 1)
        {
            return new Guess(Guess.GuessType.Person, "", gameData.players.get(0).name);
        }
        else {
            String[] guess = counter.binaryGuess(gameData.players.size());
            return new Guess(Guess.GuessType.Attribute, guess[0], guess[1]);
        }

        // placeholder, replace

    } // end of guess()

    private void loadSelf(PlayerDefinition temp)
    {
        // For each key in the hashmap we get the key and use it to get
        // the value from the hashmap and save it in this (this object)
        for(String key: temp.attributes.keySet())
        {
            this.attributes.put(key, temp.attributes.get(key));
        }
        // Remove ourselves from the data set
        gameData.removePlayerByName(this.name);
    }


      public boolean answer(Guess currGuess)
      {
        // If the current guestype is Attribute check to see if this player has the key and value
        // if so return true as the guess was correct otherwise method will return false
        if(currGuess.getType() == Guess.GuessType.Attribute)
        {
          String guessKey = currGuess.getAttribute();
          String guessValue = currGuess.getValue();
          if(this.attributes.containsKey(guessKey))
          {
            if(this.attributes.get(guessKey).equals(guessValue))
            {
              return true;
            }
          }
        }
        else
        {
          // if the guess type was person check the name against this instance, if they
          // are equal return true, otherwise method will return false
          String name = currGuess.getValue();
          if(this.name.equals(name))
          {
            return true;
          }
        }
        return false;
      } // end of answer()


    public boolean receiveAnswer(Guess currGuess, boolean answer)
    {
      // If the guess type is person there are two cases
      // 1 the person is correctly guessed in which case we return true
      // 2 the person is incorrect. So we remove them from our Players list and
      // return false
      if(currGuess.getType() == Guess.GuessType.Person)
      {
        if(answer)
        {
          return true;
        }
        else
        {
          gameData.removePlayerByName(currGuess.getValue());
          counter.wipeCounter(gameData);
          counter.countAttributes(gameData);
          return false;
        }
      }
      else
      {
        // If GuessType is Attribute and answer is true, remove all players that dont have the guessed
        // attribute.
        // If answer is false remove all players that do have guessed attribute
        if(answer)
        {
          gameData.removePlayersWithoutAttribute(currGuess.getAttribute(), currGuess.getValue());
          counter.wipeCounter(gameData);
          counter.countAttributes(gameData);
        }
        else
        {
          gameData.removePlayersWithAttribute(currGuess.getAttribute(), currGuess.getValue());
          counter.wipeCounter(gameData);
          counter.countAttributes(gameData);
        }
      }
      return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
