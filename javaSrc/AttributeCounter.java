import java.io.*;
import java.util.*;

public class AttributeCounter {

  /* This Map holds a String Key and a Hashmap as the value for that key
   * The inner HashMap holds the value for the outer key and also a counter
   * which represents how many times this key value pair exists in the current
   * gameData set
   */
  private Map<String, HashMap<String, Integer>> counter;

  //loop through players and for each player loop through their attributes
  //for each attribute first check if that key and val exists in counter
    //if it does exists incr counter for this (K,V)
    //if not exists add (K,V) to counter and set value as 1
  //when in for each - 1. Add attribute 'key' 2. add attribute 'value' 3. increment counter

  /*
   * @param gameData The current Data set held by the calling instance
   * The constructor Loops through all attributes in gameData and for each one stores the key
   * and value and initialise the counter to 0
   */
  public AttributeCounter(Data gameData)
  {
    counter = new HashMap<String, HashMap<String, Integer>>();

    /* Loop through all attributes in gameData and for each one store the key
     * and value and initialise the counter to 0
     * add all this information to the counter
     */
    for(Map.Entry<String, List<String>> e: gameData.attributes.entrySet())
    {
      /* currentAttributeValue temp variable is
       * used to store all values of a key (e.getKey()) and each
       * values relevant counter
       */
      HashMap<String, Integer> currentAttributeValue = new HashMap<String, Integer>();

      // Get all the values for this key
      List<String> values = e.getValue();

      // loop through each value for current key
      for(String value : values)
      {
        //add the value to currentAttributeValue with counter zero
        currentAttributeValue.put(value, 0);
      }
      // Add this key and all values/counters to the counter instance variable
      this.counter.put(e.getKey(), currentAttributeValue);
    }
  }

  /*
   *  @param gameData The current Data set held by the calling instance
   *
   *  This method loops through all attribute key value pairs for each player in the current gameData set
   *  and increments the same key value pair in the counter Map
   */
  public void countAttributes(Data gameData)
  {
    for(PlayerDefinition player : gameData.players)
    {
      for(Map.Entry<String, String> entry : player.attributes.entrySet())
      {
        String currentAttribute = entry.getKey();
        String attributeValue = entry.getValue();

        //increment the counter for each value
        int count = this.counter.get(currentAttribute).get(attributeValue);
        this.counter.get(currentAttribute).replace(attributeValue, count+1);
      }
    }
  }

  /*
   * @param gameData The current Data set held by the calling instance
   *
   * This method re-initializes the counter instance variable essentially wiping the
   * incremented attributes and re-populating.
   *
   * NB: - We do recognise that this is quite resource heavy and know that there is a way to do the same
   * thing by decrementing only the attributes of players that have been deleted. However we decided this
   * was the solution for us based on how much time we had left to finish the assignment
   */
  public void wipeCounter(Data gameData)
  {
    counter = new HashMap<String, HashMap<String, Integer>>();

    /* Loop through all attributes in gameData and for each one store the key
     * and value and initialise the counter to 0
     * add all this information to the counter
     */
    for(Map.Entry<String, List<String>> e: gameData.attributes.entrySet())
    {
      /* currentAttributeValue temp variable is
       * used to store all values of a key (e.getKey()) and each
       * values relevant counter
       */
      HashMap<String, Integer> currentAttributeValue = new HashMap<String, Integer>();

      // Get all values for current key
      List<String> values = e.getValue();

      // loop through each value for current key
      for(String value : values)
      {
        //add the value to currentAttributeValue with counter zero
        currentAttributeValue.put(value, 0);
      }
      // Add this key and all values/counters to the counter instance variable
      this.counter.put(e.getKey(), currentAttributeValue);
    }
  }

  /*
   * @param playerCount which represents the current amount of players left in the
   * callers Data.players
   *
   * This method loops through th counter and finds the first attribute key value pair
   * with a count == to half the playerCount. In the case that there is an odd number of players left
   * this method will return a key value pair that is as close to half rounded down. i.e
   * 9 players left, half 9 is 4.5, this gets rounded down to 4 and the method returns an attribute
   * key value pair that 4 players have. We are assuming for the scope of this assignment that there will
   * always be an attribute key value pair that half the players will have or in the case of an odd number,
   * half rounded down
   */
  public String[] binaryGuess(int playerCount)
  {
    String[] guessAttribute = new String[2];
    // Half playerCount rounded down
    int halfPlayers = Math.floorDiv(playerCount,2);

    for(Map.Entry<String, HashMap<String,Integer>> attribute: this.counter.entrySet())
    {
      // Loop through all the values (String, integer) for the current key
      for(Map.Entry<String, Integer> value: attribute.getValue().entrySet())
      {
        // if the count of this key value pair is equal to halfPlayers return this
        // key value pair
        if(value.getValue() == halfPlayers)
        {
          guessAttribute[0] = attribute.getKey();
          guessAttribute[1] = value.getKey();
          return guessAttribute;
        }
      }
    }
    return null;
  }

}