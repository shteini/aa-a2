import java.util.*;
import java.io.*;

public class Data
{
  // All possible attribute key value pairs
  public Map<String, List<String>> attributes;
  // All players in the game
  public List<PlayerDefinition> players;

  public Data()
  {
    attributes = new HashMap<String, List<String>>();
    players = new ArrayList<PlayerDefinition>();
  }

  /*
   * returns a random attribute key value pair
   * This method returns a random attribute key value pair
   */
  public Map<String, String> getRandomAttribute()
  {
    // Empty Hashmap for the selected attribute and value
    Map<String, String> attribute = new HashMap<String, String>();
    Random randomGenerator = new Random();
    // get the keyset in an arraylist so we can reference the index with the random int
    // to select a key
    List<String> keys = new ArrayList<String>(attributes.keySet());
    int randOne = randomGenerator.nextInt(attributes.keySet().size());
    String key = keys.get(randOne);
    int randTwo = randomGenerator.nextInt(attributes.get(key).size());
    // Use the selected key to get the corresponding random value
    String value = attributes.get(key).get(randTwo);
    //store the key value pair in attribute
    attribute.put(key, value);
    return attribute;
  }

  /*
   * @param key the key that is to be checked against all players
   * @param the value of the passed in key, to be checked against all players
   * This method removes all players that have the passed in attribute key value pair
   */
  public void removePlayersWithAttribute(String key, String value)
  {
    Iterator itr = players.iterator();
    while(itr.hasNext())
    {
      PlayerDefinition currentPlayer = (PlayerDefinition)itr.next();
      if(currentPlayer.attributes.containsKey(key))
      {
        if(currentPlayer.attributes.get(key).equals(value))
        {
          itr.remove();
        }
      }
    }
  }

  /*
   * @param key the key that is to be checked against all players
   * @param the value of the passed in key, to be checked against all players
   * This method removes all players that do not have the passed in attribute key value pair
   */
  public void removePlayersWithoutAttribute(String key, String value)
  {
    Iterator itr = players.iterator();
    while(itr.hasNext())
    {
      PlayerDefinition currentPlayer = (PlayerDefinition)itr.next();
      if(currentPlayer.attributes.containsKey(key))
      {
        if(!(currentPlayer.attributes.get(key).equals(value)))
        {
          itr.remove();
        }
      }
    }
  }

  /*
   * @param name the name of the player you are searching for
   * This method finds and returns the player with the name matching the passed in name
   */
  public PlayerDefinition getPlayerByName(String name)
  {
    PlayerDefinition temp = new PlayerDefinition();
    for(PlayerDefinition player : players)
    {
      if(player.name.equals(name))
      {
        temp = player;
        break;
      }
    }
    return temp;
  }

  /*
   * @param name the name of the player you are searching for
   * This method finds and removes the player with the name matching the passed in name
   */
  public void removePlayerByName(String name)
  {
    for(int i = 0; i < players.size(); i++)
    {
      if(players.get(i).name.equals(name))
      {
        players.remove(i);
      }
    }
  }

  /*
   * @param filename the name of the file to load
   * This method loads all attributes and then all players from the file that
   * matches the passed in filename
   */
  public void loadGameFile(String filename)
  {
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      loadAttributes(br);
      loadPlayers(br);
    }
    catch(FileNotFoundException ex)
    {
      System.err.println("Missing file " + ex.getMessage() + ".");
      System.exit(0);
    }
    catch(IOException ex)
    {
      System.err.println(ex.getMessage());
      System.exit(0);
    }
  }

  /*
   * @param br the bufferedReader being used to read from the file in the loadGameFile Methd
   * This method loads all attributes from the file opened by the loadGameFile method
   */
  private void loadAttributes(BufferedReader br) throws IOException
  {
    // The current line
    String line;
    // Read line by line until you reach end of file or an empty line
    while((line = br.readLine()) != null && !(line.isEmpty()))
    {
      List<String> values = new ArrayList<String>();
      String[] attributes = line.split(" ");
      // The first value will be the key
      String key = attributes[0];
      // add all values for the key
      for(int i = 1; i<attributes.length; i++)
      {
        values.add(attributes[i]);
      }
      // add the values and the key to the attributes instance variable
      this.attributes.put(key, values);
    }
  }

  /*
   * @param br the bufferedReader being used to read from the file in the loadGameFile Methd
   * This method loads all players from the file opened by the loadGameFile method
   */
  private void loadPlayers(BufferedReader br) throws IOException
  {
    String line;
    // Read in each line until we reach end of file
    while((line = br.readLine()) != null)
    {
      //if the line is empty, skip it, as we are now about to reach a new player
      if(line.isEmpty())
      {
        continue;
      }
      // Empty player object to fill
      PlayerDefinition newPlayer = new PlayerDefinition();
      // current line is the players name
      newPlayer.name = line;
      // Read in each line until we reach an empty line or end of file
      while ((line = br.readLine()) != null && !(line.isEmpty()))
      {
        String[] values = line.split(" ");
        // add the attribute key value pair to the current newPlayer.attributes
        newPlayer.attributes.put(values[0], values[1]);
      }
      // Add the new player to the players list
      players.add(newPlayer);
    }
  }

}
