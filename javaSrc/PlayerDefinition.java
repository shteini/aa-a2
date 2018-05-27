import java.util.*;
public class PlayerDefinition
{
  // Holds all the players attribute key value pairs
  public Map<String, String> attributes;
  // The players name
  public String name;

  public PlayerDefinition()
  {
    attributes = new HashMap<String, String>();
    name = "";
  }

}
