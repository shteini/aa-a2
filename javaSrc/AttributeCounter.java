import java.io.*
import java.util.*

public class AtrributeCounter {

	private Data gameData;
	private Map<String, HashMap<String, Integer>> counter;

	public AttributeCounter(String gameFilename) {

		gameData = new Data();
		counter = new HashMap<String, HashMap<String, Integer>>();
		gameData.loadGameFile(gameFilename);

        /*New map of values & count, eg. (blue hair, number of people w/blue hair)
          Count initialized at 0*/
        for(Map.Entry<String, ArrayList<String>> e: gameData.attributes.entrySet()) {

            HashMap<String, Integer> currentAttributeValue = new HashMap<String, Integer>();
            // loop through each value for current key
            for(ArrayList<String> value : e.getValue()) {

            	//add the value to currentAttributeValue with .put and set 
            	currentAttributeValue.put(value, 0);

            } 
            this.counter.put(e.getKey(), currentAttributeValue);
        }

	}

	//get all attributes from data already saved
	public void countAttributes(Data data){

		for(PlayerDefinition player : gameData.players) {

			for(Map.Entry<String, String> entry : player.attributes.entrySet()) {

				String cuurentAtrrtibute = entry.getKey();
				String attributeType = entry.getValue();

				//increment the counter when a value exists 
				this.counter.get(currentAttributeValue).replace(attributeType, )
			}


		}


		
		//loop through players and for each player loop through their attributes
		//for each attribute first check if that key and val exists in counter 
			//if it does exists incr counter for this (K,V) 
			//if not exists add (K,V) to counter and set value as 1
		//when in for each - 1. Add attribute 'key' 2. add attribute 'value' 3. increment counter


	}

	public void wipe() {

		//loop through counter map and empty it OR create new counter
		counter = new HashMap<String, HashMap<String, Integer>>();
	}
}