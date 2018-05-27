import java.io.*;
import java.util.*;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer extends PlayerDefinition implements Player
{
  /* Each player needs to know who they are from the players list
   * which can be found through this.name as we extend PlayerDefinition
   *
   * They also need to hold a list of all other players in the game so
   * that they can eliminate players as the guesses happen
   * I would have assumed that two players cannot be the samer person i.e.
   * game1.chosen = P1 P1. for this reason, each player removes themselves
   * from their own list. As this was not specified in the specs I am assuming that two players
   * cannot be the same
   */
  private Data gameData;

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
      gameData = new Data();

      // Load all the players and attributes in
      gameData.loadGameFile(gameFilename);

      /* Assigns the name for this RandomGuessPlayer
       * Using that name we can then find which values to load
       * into this player
       */
      this.name = chosenName;

      // Copy the data of the player with the chosenName to this instance
      loadSelf(gameData.getPlayerByName(chosenName));
  } // end of RandomGuessPlayer()

  /*
    @param temp temporary PlayerDefinition variable holding the attributes to
    be assigned to this instance of RandomGuessPlayer
    This method loads copies the attribute key value pairs from temp to this instance
   */
  private void loadSelf(PlayerDefinition temp)
  {
    // For each key in the hashmap we get the key and use it to get
    // the value from the hashmap and save it in this (this object)
    for(String key: temp.attributes.keySet())
    {
        this.attributes.put(key, temp.attributes.get(key));
    }
    /* remove this instance from the players list as we are assuming that the other player cannot
    * be the same player as us as stated above.
    * NB: To allow players to choose the same person just comment out this line and the program will
    * run as expected
    */
    gameData.removePlayerByName(this.name);
  }

  public Guess guess()
  {
    // If there is only 1 player left the guess will be that player by name
    // otherwise we get a random attribute/value pair and make a guess
    if(gameData.players.size() == 1)
    {
        return new Guess(Guess.GuessType.Person, "", gameData.players.get(0).name);
    }
    else
    {
     /*Here we call the getRandomAttribute method of Data class and it returns
      * a Map containing a random key value pair we then get the key and value out of the
      * map and return it as part of the guess
      */
      Map<String, String> guessMap = gameData.getRandomAttribute();
      String key = new ArrayList<String>(guessMap.keySet()).get(0);
      String value = guessMap.get(key);
      return new Guess(Guess.GuessType.Attribute, key, value);
    }
  } // end of guess()


  public boolean answer(Guess currGuess)
  {
   /* If the current guestype is Attribute check to see if this player has the key and value
    * If it is present return true as the guess was correct
    * if guessType is Attribute and the key value pair is not present, we return false
    */
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
      // if the guessType was person check the name against this instance, if they
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
      }
      else
      {
        gameData.removePlayersWithAttribute(currGuess.getAttribute(), currGuess.getValue());
      }
    }
    return false;
  } // end of receiveAnswer()

} // end of class RandomGuessPlayer
