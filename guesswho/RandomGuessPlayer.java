import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player {
   // Create an initial data set for the place values to go into.
    DataPool attPool = new DataPool();
    Person chosenPerson;
    ArrayList<Person> people = new ArrayList<Person>();


    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName   Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *                     Note you can handle IOException within the constructor and remove
     *                     the "throws IOException" method specification, but make sure your
     *                     implementation exits gracefully if an IOException is thrown.
     */

    public RandomGuessPlayer(String gameFilename, String chosenName)
            throws IOException {
        FileReader fr = new FileReader(gameFilename);
        BufferedReader br = new BufferedReader(fr);
        String fileLine, firstWord;

        ArrayList<String> attrPool;
        boolean readPerson = false;
/*  Read in Starting section to do nothing
 *  and add no values except for the Key.
 *  ignore key values.
 */
while ((fileLine = br.readLine()) != null) {
            if (readPerson == false) {
                if (fileLine.isEmpty()) {
                    readPerson = true;
                    continue;
                }

                String attrName = fileLine.substring(0, fileLine.indexOf(" "));

                //add new attribute to HashMap
                attrPool = new ArrayList<String>();
                attPool.addKey(attrName);
                for (String subAttr : fileLine.split(" ")) {
                    if (!attrName.matches(subAttr)) {
                    //  attPool.addValue(attrName, subAttr);
                    }
                }
            } else {
                if (fileLine.isEmpty()) {
                    continue;
                }
                // Check to see if the Person equals the chosen name in the configuration.
                if (fileLine.matches(chosenName)) {
                    chosenPerson = new Person(chosenName);
            /*
             * Read through the given players, and check if they equal the chosen player for the player.
             */
                    while ((fileLine = br.readLine()) != null && !fileLine.isEmpty()) {
                        String[] subStrings = fileLine.split(" ");
                        //Add the chosenPerson
                        chosenPerson.addAttribute(subStrings[0], subStrings[1]);
                        for(Person p: people){
                            // Check for the Attributes Value, remove them if they exist.
                            if(p.hasAttribute(subStrings[0], subStrings[1])){
                                attPool.removeValue(subStrings[0], subStrings[1]);
                            }
                        }
                        // Add the Value to the Key.
                        attPool.addValue(subStrings[0], subStrings[1]);
                    }
                    people.add(chosenPerson);
                }
                // Adding the Person to the guessing pool
                else {
                    String pName = fileLine;
                    Person newPerson = new Person(pName);

                    while ((fileLine = br.readLine()) != null && !fileLine.isEmpty()) {
                        String[] subStrings = fileLine.split(" ");
                        // Add the person to a new guess.
                        newPerson.addAttribute(subStrings[0], subStrings[1]);
                        for(Person p: people){
                            // Check for the Attributes Value, remove them if they exist
                            if(p.hasAttribute(subStrings[0], subStrings[1])){
                                attPool.removeValue(subStrings[0], subStrings[1]);
                            }
                        }
                        // Add the value
                        attPool.addValue(subStrings[0], subStrings[1]);
                    }
                    // Add the person to the Guessing pool of people.
                    people.add(newPerson);
                }
            }
        }
//        for(Person p: people){
//            p.printName();
//        }
        // chosenPerson.printName();
    }


    // end of BinaryGuessPlayer()

    //            if(fileLine.matches(chosenName)){
//
//}
    public Guess guess() {
        // Create initial values to use.
        Guess personGuess, attributeGuess;
        int keySize, valueSize;
        Boolean isLooking = true;
        String key, value;
        Random rand = new Random();
        /* Check the pool size to see if there is one person left
           If there is one person left, that person is the correct guess for the player to win the game of Guess Who.
         */
        if(people.size() == 1){
            personGuess = new Guess(Guess.GuessType.Person, "", people.get(0).getName());
            return personGuess;
        }
        // If the pool <= 0,  return default person because somewhere in the code it's wrong.
        if(people.size() <= 0) {
            return new Guess(Guess.GuessType.Person, "You win", "I screwed up");
        }
        // Get a Random Key to select a Random Value.
        else{
            keySize = attPool.getKeyTotalNumber();
            key = attPool.getKeyString(rand.nextInt(keySize));
            valueSize = attPool.getValueTotalNumer(key);

            // look for a new attribute if it already equals false
            while(isLooking){
                if (valueSize >= 0) {
                    // Found a new key to use
                    isLooking = false;
                }
                else{
                    // Find a new key to use.
                    key = attPool.getKeyString(rand.nextInt(keySize));
                }
            }
            // Select a random Value
            valueSize = attPool.getValueTotalNumer(key);
            value = attPool.getValueString(key, rand.nextInt(valueSize));
           // Generate guess with the assigned Key and Value, as an Attribute Type.
            attributeGuess = new Guess(Guess.GuessType.Attribute, key, value);
            return attributeGuess;

        }
    } // end of guess()


    public boolean answer(Guess currGuess) {
        //Check if the type of a guess is Person OR Attribute
        // PERSON, automatically means the guess is correct
        if (currGuess.getType() == Guess.GuessType.Person){
            /* If and only if the getType() returns Person,
               there is one person left in the players guessing array
            */
            return true;
        }
        // Check to see if the player's guess Has the Selected attributes or not
        else if (chosenPerson.hasAttribute(currGuess.getAttribute(), currGuess.getValue())) {
            // the player has these attributes.
            return true;
        }
        // The player doesn't have these attributes
        return false;
    } // end of answer()


    public boolean receiveAnswer(Guess currGuess, boolean answer) {
        // Check if the Guess is a Player or Attribute.
        //PLAYER
       if (currGuess.getType() == Guess.GuessType.Person ){
           if(answer == true)
           {// If the the type is a Person and the Answer  == true, the player Wins
            return true;
           }
           else
           {// If the the type is a Person and the Answer  == False, the player Loses and needs to guess again.
             return false;
           }
       }
//ATTRIBUTE
        else if (currGuess.getType() == Guess.GuessType.Attribute) {
           // Guess is an Attribute Type.
            if (answer == true){
                // Answer is correct
                for (int i = 0; i <= people.size(); i++) {
                    // Loop through the size of people
                    for (Person p : people) {
                        // Loop through the people in people, so we can just grab the person in the structure of people.
                        if (!p.hasAttribute(currGuess.getAttribute(), currGuess.getValue())) {
                            // If the player guesses correctly, Remove all Persons that don't have the values as an attribute.
                            people.remove(p);
                            break;
                        }
                    }
                }
                // Remove the guess from the Guessing pool of Attributes
                for(int k = 0; k<= attPool.getValues(currGuess.getAttribute()).size(); k++){
                    // If the pool still has the current guesses Values, Remove it if the value isn't the last one.
                    if(attPool.hasAttribute(currGuess.getAttribute(), currGuess.getValue())
                            && attPool.getValueTotalNumer(currGuess.getAttribute())!= 1){
                        //Remove the value.
                      attPool.removeValue(currGuess.getAttribute(), currGuess.getValue());
                    }
                }

                return false;
            }
            else{
                // If the guess is incorrect, remove the Persons that have the value guessed.
                // Look at the above loops to see how they work bellow.
                for (int i = 0; i <= people.size(); i++) {
                    for (Person p : people) {
                        if (p.hasAttribute(currGuess.getAttribute(), currGuess.getValue())) {
                            people.remove(p);
                            break;
                        }
                    }
                }
                // Remove the value of the guess.
                attPool.removeValue(currGuess.getAttribute(), currGuess.getValue());
                return false;
            }

        }
        // placeholder, replace
        return true;
    } // end of receiveAnswer()



} // end of class RandomGuessPlayer
