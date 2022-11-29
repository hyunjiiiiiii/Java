package capers;

import java.io.File;
<<<<<<< HEAD
=======
import java.io.IOException;
>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85
import java.util.Arrays;

/** Canine Capers: A Gitlet Prelude.
 * @author Sean Dooher
*/
public class Main {
    /** Current Working Directory. */
    static final File CWD = new File(".");

    /** Main metadata folder. */
<<<<<<< HEAD
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers"); // FIXME
=======
    static final File CAPERS_FOLDER = new File(".capers"); // FIXME

>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85

    /**
     * Runs one of three commands:
     * story [text] -- Appends "text" + a newline to a story file in the
     *                 .capers directory. Additionally, prints out the
     *                 current story.
     *
     * dog [name] [breed] [age] -- Persistently creates a dog with
     *                             the specified parameters; should also print
     *                             the dog's toString(). Assume dog names are
     *                             unique.
     *
     * birthday [name] -- Advances a dog's age persistently
     *                    and prints out a celebratory message.
     *
     * All persistent data should be stored in a ".capers"
     * directory in the current working directory.
     *
     * Recommended structure (you do not have to follow):
     *
     * *YOU SHOULD NOT CREATE THESE MANUALLY,
     *  YOUR PROGRAM SHOULD CREATE THESE FOLDERS/FILES*
     *
     * .capers/ -- top level folder for all persistent data in your lab08 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     *
     * @param args arguments from the command line
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            exitWithError("Must have at least one argument");
        }
//        System.out.println("args: " + Arrays.toString(args));
        setupPersistence();
        switch (args[0]) {
            case "story":
                writeStory(args);
                break;
            // FIXME
            case "dog":
                makeDog(args);
                break;
            case "birthday":
                celebrateBirthday(args);
                break;
            default:
                exitWithError(String.format("Unknown command: %s", args[0]));
        }
        return;
    }

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     *
     */
    public static void setupPersistence() throws IOException {
        // FIXME
<<<<<<< HEAD
        File story = Utils.join(CAPERS_FOLDER, "story");
        CAPERS_FOLDER.mkdir();
        Dog.DOG_FOLDER.mkdir();
=======
        if (!CAPERS_FOLDER.exists()) {
            CAPERS_FOLDER.mkdir();
        }
        if (!Dog.DOG_FOLDER.exists()) {
            Dog.DOG_FOLDER.mkdir();
        }
        File STORY_FILE  = Utils.join(CAPERS_FOLDER, "story");

>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param args Array in format: {'story', text}
     */
    public static void writeStory(String[] args) {
        validateNumArgs("story", args, 2);
        // FIXME
<<<<<<< HEAD
        File story = Utils.join(CAPERS_FOLDER, "story");
        if (story.exists()) {
            Utils.writeContents(story, Utils.readContentsAsString(story) + args[1] + "\n");
        } else {
            Utils.writeContents(story, args[1] + "\n");
        }
        System.out.println(Utils.readContentsAsString(story));
=======
        File STORY_FILE = Utils.join(CAPERS_FOLDER, "story");
        if (STORY_FILE.exists()) {
            Utils.writeContents(STORY_FILE, Utils.readContentsAsString(STORY_FILE) + args[1] + "\n");
        } else {
            Utils.writeContents(STORY_FILE, args[1] + "\n");
        }
        System.out.println(Utils.readContentsAsString(STORY_FILE));
>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     * If the user inputs an invalid age, call exitWithError()
     * @param args Array in format: {'story', name, breed, age}
     */
    public static void makeDog(String[] args) {
        validateNumArgs("dog", args, 4);
        // FIXME
        String name = args[1];
        String breed = args[2];
        int age = Integer.parseInt(args[3]);

        if (age < 0) {
            exitWithError("Invalid age, age cannot bbe negative");
        }

        Dog new_dog = new Dog(name, breed, age);
        new_dog.saveDog();

        String info = new_dog.toString();
        System.out.println(info);
    }


    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * If the user's input is invalid, call exitWithError()
     * @param args Array in format: {'birthday', name}
     */
    public static void celebrateBirthday(String[] args) {
        validateNumArgs("birthday", args, 2);
        // FIXME
        String name = args[1];
        String file = Dog.DOG_FOLDER + "/" + name;

        Dog dog = Dog.fromFile(file);
        dog.haveBirthday();
        dog.saveDog();
<<<<<<< HEAD

=======
>>>>>>> fe4b582aa9086682fc5a2e1f96ab37d7c8d3ab85
    }

    /**
     * Prints out MESSAGE and exits with error code -1.
     * Note:
     *     The functionality for erroring/exit codes is different within Gitlet
     *     so DO NOT use this as a reference.
     *     Refer to the spec for more information.
     * @param message message to print
     */
    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(-1);
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param cmd Name of command you are validating
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                String.format("Invalid number of arguments for: %s.", cmd));
        }
    }
}
