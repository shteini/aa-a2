import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Attribute;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{
   private DataPool attPool;
   private Person chosenPerson;
   private ArrayList<Person> people;
   private AttributeCounter a;
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

    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
        /*Iterates through each line in the .config file, it then saves
        * each attribute & value to the datapool
        * though, the datapool isn't used in this class
        * Once it hits an empty line it begins reading in for people
        * and saves each player object to the player list (people)
        */
        attPool = new DataPool();
        people = new ArrayList<Person>();
        a = new AttributeCounter();
        FileReader fr = new FileReader(gameFilename);
        BufferedReader br = new BufferedReader(fr);
        String fileLine, firstWord;

        ArrayList<String> attrPool;
        boolean readPerson = false;

        while((fileLine = br.readLine())!= null){
            if(readPerson == false) {
                if(fileLine.isEmpty()){
                    readPerson = true;
                    continue;
                }
                //get first line and set as attribute type
                String attrName = fileLine.substring(0, fileLine.indexOf(" "));

                //add new attribute to HashMap
                attrPool = new ArrayList<String>();
                attPool.addKey(attrName);
                for (String subAttr : fileLine.split(" ")) {
                    if (!attrName.matches(subAttr)) {
                        attPool.addValue(attrName, subAttr);
                    }
                }
            }
            else{
                if(fileLine.isEmpty()){
                    continue;
                }
                //if person name matches chosen person read in attributes and save as chosen person
                if(fileLine.matches(chosenName)){
                    this.a.initAttributes(attPool);
                    chosenPerson = new Person(chosenName);

                    while((fileLine = br.readLine())!= null && !fileLine.isEmpty()){
                        String[] subStrings = fileLine.split(" ");
                        chosenPerson.addAttribute(subStrings[0], subStrings[1]);
                    }
                    people.add(chosenPerson);
                }
                else{
                    String pName = fileLine;
                    Person newPerson = new Person(pName);

                    while((fileLine = br.readLine())!= null && !fileLine.isEmpty()){
                        String[] subStrings = fileLine.split(" ");
                        newPerson.addAttribute(subStrings[0], subStrings[1]);
                    }
                    people.add(newPerson);
                }
            }
        }
        a.countAttrs(people);
    }

    //Binary guess function
    public Guess guess() {
        int count;
        Guess guess;
        //if number of remaining people >1 keep getting best attribute guess
        if(people.size()>1){
            String[] s = a.bestGuess(people.size());
            return new Guess(Guess.GuessType.Attribute, s[0], s[1]);
        }
        //if one person remaining guess them
        return new Guess(Guess.GuessType.Person, "", people.get(0).getName());
    } // end of guess()

	public boolean answer(Guess currGuess) {
        if(currGuess.getType() == Guess.GuessType.Attribute ){
            if(chosenPerson.hasAttribute(currGuess.getAttribute(),
                    currGuess.getValue()))
            {
                return true;
            }
        }
        else if(currGuess.getType() == Guess.GuessType.Person){
            if(chosenPerson.getName().matches(currGuess.getValue())){
                return true;
            }
        }
        return false;
    } // end of answer()

    //Receive answer function
	public boolean receiveAnswer(Guess currGuess, boolean answer) {
        //if guess was an attribute
        if(currGuess.getType() == Guess.GuessType.Attribute){
            //creates an iterator to go through the player list
            Iterator<Person> it = people.iterator();

            //If player guesses correctly remove all players who do not
            // have the guessed attribute value
            if(answer == true){
                while(it.hasNext()) {
                    Person p = it.next();
                    if(!p.hasAttribute(currGuess.getAttribute(),currGuess.getValue())){
                        it.remove();
                    }
                }
            }
            //If guess was incorrect remove all people with guessed
            //attribute value
            else{
                while(it.hasNext()) {
                    Person p = it.next();
                    if(p.hasAttribute(currGuess.getAttribute(),currGuess.getValue())){
                        it.remove();
                    }
                }
            }
        }
        //if guess was a person and correct return true, and player wins
        else if(currGuess.getType() == Guess.GuessType.Person){
            if(answer) {
                return true;
            }
        }
        return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
