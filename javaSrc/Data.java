import java.util.*;
import java.io.*;

public class Data
{
  public Map<String, List<String>> attributes;
  public List<PlayerDefinition> players;

  public Data()
  {
    attributes = new HashMap<String, List<String>>();
    players = new ArrayList<PlayerDefinition>();
  }

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
    // Use the selected key to get the corresponding value
    String value = attributes.get(key).get(randTwo);
    attribute.put(key, value);
    // return the attribute to guess
    return attribute;
  }

  public void removePlayersWithAttribute(String key, String value)
  {
    for(PlayerDefinition player: players)
    {
      if(player.attributes.containsKey(key))
      {
        if(player.attributes.get(key).equals(value))
        {
          players.remove(players.indexOf(player));
        }
      }
    }
  }

  public void removePlayersWithoutAttribute(String key, String value)
  {
    for(PlayerDefinition player: players)
    {
      if(player.attributes.containsKey(key))
      {
        if(!(player.attributes.get(key).equals(value)))
        {
          players.remove(players.indexOf(player));
        }
      }
    }
  }

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

  public void removePlayerByName(String name)
  {
    for(PlayerDefinition player : players)
    {
      if(player.name.equals(name))
      {
        players.remove(players.indexOf(player));
      }
    }
  }

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

  private void loadAttributes(BufferedReader br) throws IOException
  {
    String line;
    List<String> values = new ArrayList<String>();
    while((line = br.readLine()) != null && !(line.isEmpty()))
    {
      String[] attributes = line.split(" ");
      String key = attributes[0];
      for(int i = 1; i<attributes.length; i++)
      {
        values.add(attributes[i]);
      }
      this.attributes.put(key, values);
    }
  }

  private void loadPlayers(BufferedReader br) throws IOException
  {
    String line;
    while((line = br.readLine()) != null)
    {
      if(line.isEmpty())
      {
        continue;
      }
      // Empty player object to fill
      PlayerDefinition newPlayer = new PlayerDefinition();
      while (!(line.isEmpty()))
      {
        // If the line length is 2 it is a player name
        if(line.length() == 2)
        {
          newPlayer.name = line;
        }
        else
        {
          String[] values = line.split(" ");
          newPlayer.attributes.put(values[0], values[1]);
        }
        line = br.readLine();
      }
      players.add(newPlayer);
    }
  }
}




