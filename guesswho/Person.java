import java.util.*;

/**
 * Created by Daniel on 9/30/2016.
 */

public class Person {

    private String name;
    private Map<String, String> attributes;


    public Person(String Name){
        this.name = Name;
        this.attributes = new HashMap<String, String>();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String type, String subType){
        this.attributes.put(type, subType);
    }

    public boolean hasAttribute(String type, String subType){
        if(this.attributes.get(type).matches(subType)){
            return true;
        }
        return false;
    }

    public String getAttribute(String type){
        return this.attributes.get(type);
    }

    public String getName(){
        return this.name;
    }

    public Collection<String> getKeys(){
        return this.attributes.keySet();
    }
    public String getKeyFromIndex(int index){
        List keys = new ArrayList(getKeys());
        for(int i = 0; i <keys.size(); i++) {
            Object obj = keys.get(i);
            if (index == i) {
                return obj.toString();
            }
        }
        return "Error";
    }

    public void printName(){
        System.out.println("hairLength with getAttribute() = "+getAttribute("hairLength"));
        System.out.println("getting hairLength with it's index: "+ getKeyFromIndex(0));
        System.out.println(this.name);
        System.out.println(attributes.entrySet());
    }


}
