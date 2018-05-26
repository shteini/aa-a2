import java.io.*;
import java.util.*;

public class AttributeCounter {

  private Map<String, HashMap<String, Integer>> counter;

  public AttributeCounter(String gameFilename, Data gameData)
  {

    counter = new HashMap<String, HashMap<String, Integer>>();

        //loop through players and for each player loop through their attributes
        //for each attribute first check if that key and val exists in counter
          //if it does exists incr counter for this (K,V)
          //if not exists add (K,V) to counter and set value as 1
        //when in for each - 1. Add attribute 'key' 2. add attribute 'value' 3. increment counter
        for(Map.Entry<String, List<String>> e: gameData.attributes.entrySet())
        {

            HashMap<String, Integer> currentAttributeValue = new HashMap<String, Integer>();

            List<String> values = e.getValue();

            // loop through each value for current key
            for(String value : values)
            {

              //add the value to currentAttributeValue with .put and set
              currentAttributeValue.put(value, 0);

            }
            this.counter.put(e.getKey(), currentAttributeValue);
        }

  }

  //get all attributes from data already saved
  public void countAttributes(Data gameData)
  {
    //loop through players from Data 
    for(PlayerDefinition player : gameData.players)
    {
      //loop through players attributes
      for(Map.Entry<String, String> entry : player.attributes.entrySet())
      {
        //assign key and value as per current
        String currentAttribute = entry.getKey();
        String attributeType = entry.getValue();

        //increment the counter when a value exists
        int count = this.counter.get(currentAttribute).get(attributeType);
        this.counter.get(currentAttribute).replace(attributeType, count+1);
      }
    }
  }

  //required to wipe the counter of all data for next guess
  public void wipeCounter(Data gameData)
  {
    counter = new HashMap<String, HashMap<String, Integer>>();
     for(Map.Entry<String, List<String>> e: gameData.attributes.entrySet())
       {

              HashMap<String, Integer> currentAttributeValue = new HashMap<String, Integer>();

              List<String> values = e.getValue();

              // loop through each value for current key
              for(String value : values)
              {

                //add the value to currentAttributeValue with .put and set
                currentAttributeValue.put(value, 0);

              }
              this.counter.put(e.getKey(), currentAttributeValue);
       }

  }

  //method to return an array of atrributes to use for guessing and splitting player list in half
  public String[] binaryGuess(int playerCount)
  {

    String[] guessAttribute = new String[2];
    int halfPlayers = Math.floorDiv(playerCount,2);

    for(Map.Entry<String, HashMap<String,Integer>> attribute: this.counter.entrySet()) {

        for(Map.Entry<String, Integer> value: attribute.getValue().entrySet())
        {
            if(value.getValue() == halfPlayers)
            {
              guessAttribute[0] = attribute.getKey();
              guessAttribute[1] = value.getKey();
            }

        }
    }
    return guessAttribute;
  }

}
