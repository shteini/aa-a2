import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 10/7/2016.
 *
 * This class counts the number of players with each attribute
 * it generates a guess based on whichever attr has the  closest to 0, from (playerNumber/2)-attributeCount
 * this returns the attribute that's the closest to halving the player pool
 *
 */
public class AttributeCounter {

    private Map<String, HashMap<String, Integer>> attrCount;

    public AttributeCounter(){
        attrCount = new HashMap<String, HashMap<String, Integer>>();
    }

    //Get the available attribute types from the datapool, like hairlength, colour, etc.
    public void initAttributes(DataPool dp){
        //Get pool and it's contained attributes
        Map<String, ArrayList<String>> pool = dp.getPool();
        /*New map of values & count, eg. (blue hair, number of people w/blue hair)
          Count initialized at 0*/
        for(Map.Entry<String, ArrayList<String>> e: pool.entrySet()){
            HashMap<String, Integer> attr = new HashMap<String, Integer>();
            this.attrCount.put(e.getKey(), attr);
        }
    }

    /*Takes each player's array of people in game, and counts up how many have each attribute
    * and its values
    */
    public void countAttrs(ArrayList<Person> people){
        for(Person p: people){
            //Get attributes of player entry = eg. (Hair, blue)
            for(Map.Entry e: p.getAttributes().entrySet()){
                String type = (String)e.getKey();
                String val = (String)e.getValue();

                //If attribute value doesn't exist add it to the map/list and init count
                if(!hasAttribute(type, val)){
                    this.attrCount.get(type).put(val, 1);
                }
                //if already exists increment the count
                 else if(hasAttribute(type,val)) {
                    int count = this.attrCount.get(type).get(val);
                    this.attrCount.get(type).replace(val,count+1);
                }
            }
        }
    }

    //Checks if attribute:value exists in map/list
    public boolean hasAttribute(String type, String attr){
        if(this.attrCount.get(type).containsKey(attr)){
            return true;
        }
        return false;
    }

    /*Returns the closest guess equal to half of the player count
    * It gets the player count/2, then subtracts the number of players who
    * have the attribute, if the result is 0 (exactly half) then it chooses it,
    * otherwise it continues through the list and returns the number closest to half.
    */
    public String[] bestGuess(int pCount){
        String[] guessAttr = new String[2];
        pCount = Math.floorDiv(pCount,2);//floor of player count/2
        int closest = pCount, temp;
        String type = null, attr = null;

        for(Map.Entry<String, HashMap<String,Integer>> e:
                this.attrCount.entrySet()){

            for(Map.Entry<String, Integer> val: e.getValue().entrySet()){
                temp = Math.abs(pCount - val.getValue());
                /*Absolute value of (playerCount/2) - attributeCount
                * so that negative numbers are set to their positive equivalent
                * Save current count as temp, and if it's lower than the previous lowest
                * save it as the closest value*/
                if(temp < closest) {
                    closest = temp;

                    guessAttr[0] = type = e.getKey();
                    guessAttr[1] = attr = val.getKey();

                    if(temp == 0){
                        this.attrCount.get(type).remove(attr);
                        return guessAttr;
                    }
                }
            }
        }
        //remove guessed value from the pool
        this.attrCount.get(type).remove(attr);
        return guessAttr;
    }
}