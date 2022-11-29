package capers;

import java.io.*;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
<<<<<<< HEAD
 */
=======
*/
>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85
public class Dog implements Serializable { // FIXME

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = new File(".capers/dogs"); // FIXME
    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        _age = age;
        _breed = breed;
        _name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // FIXME
        Dog dog;

        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(name));
            dog = (Dog) inp.readObject();
            inp.close();

        } catch (IOException | ClassCastException
                 | ClassNotFoundException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }

        return dog;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        _age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        //FIXME
        File file = Utils.join(DOG_FOLDER, _name);
        Dog dog = new Dog(_name, _breed, _age);
        Utils.writeObject(file, dog);

    }

    @Override
    public String toString() {
        return String.format(
                "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
                _name, _breed, _age);
    }

    /** Age of dog. */
    private int _age;
    /** Breed of dog. */
    private String _breed;
    /** Name of dog. */
    private String _name;
}