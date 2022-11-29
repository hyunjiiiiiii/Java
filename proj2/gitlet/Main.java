package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @HyunjiPark
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                checkDirect();
                Repository.add(args[1]);
                break;
            case "commit":
                checkDirect();
                Repository.commit(args[1]);
                break;
            case "log":
                checkDirect();
                Repository.log();
                break;
            case "checkout":
                checkDirect();
                if (args.length == 3) {
                    if (!args[1].equals("--")) {
                        System.out.println("Incorrect operands.");
                        return;
                    }
                    Repository.checkoutFileName(args[2]);
                } else if (args.length == 4) {
                    if (!args[2].equals("--")) {
                        System.out.println("Incorrect operands.");
                        return;
                    }
                    Repository.checkoutCommitId(args[1], args[3]);
                } else if (args.length == 2) {
                    Repository.checkoutBranch(args[1]);
                } else {
                    System.out.println("Incorrect operands.");
                }
                break;
            case "rm":
                checkDirect();
                Repository.rm(args[1]);
                break;
            case "global-log":
                checkDirect();
                Repository.globalLog();
                break;
            case "find":
                checkDirect();
                Repository.find(args[1]);
                break;
            case "status":
                checkDirect();
                Repository.status();
                break;
            case "branch":
                checkDirect();
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                checkDirect();
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                checkDirect();
                Repository.reset(args[1]);
                break;
            case "merge":
                checkDirect();
                Repository.merge(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    public static void checkDirect() {
        if (!Repository.GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
