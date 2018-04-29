import java.util.*;

/**
 * Created by Andrew on 1/10/2016.
 */
public class DataPool{
    private Map<String, ArrayList<String>> pool;
    public static int id = 0;

    public DataPool(){
        this.pool = new HashMap<String, ArrayList<String>>();
        this.id = id;
        ++id;
    }
    public void addKey(String key){
        ArrayList<String> values = new ArrayList<String>();
        // Grab the key name and set to the pool
        this.pool.put(key, values);
    }
    public void addValue(String key, String value){
        // Insert the Values into the associated Key.
        this.pool.get(key).add(value);
    }
    public boolean hasAttribute(String key, String value){
        if(this.pool.get(key).contains(value)){
            return true;
        }
        return false;
    }
    public void removeValue(String key, String value){
       // Remove the Value from the associated key
        this.pool.get(key).remove(value);
    }
    public int getKeyTotalNumber() {
        return this.pool.size();
    }
    public int getValueTotalNumer(String key) {
        return this.pool.get(key).size();
    }
    public Set<String> getKeys() {
        return this.pool.keySet();
    }
    public ArrayList<String> getValues(String key){
        return this.pool.get(key);
    }
    public String getKeyString(int index){
        List keys = new ArrayList(getKeys());
        for(int i = 0; i <keys.size(); i++) {
            Object obj = keys.get(i);
            if (index == i) {
                return obj.toString();
            }
        }
        return "Error";
    }
    public String getValueString(String key, int index){
        List values = new ArrayList(getValues(key));
        for (int i = 0; i<values.size(); i++){
            Object obj = values.get(i);
            if(index == i){
                return obj.toString();
            }
        }


        return"Error";
    }

    public Map<String, ArrayList<String>> getPool() {
        return pool;
    }

    public void displaySet(){System.out.println("Dataset: "+pool.entrySet());

    }

}
