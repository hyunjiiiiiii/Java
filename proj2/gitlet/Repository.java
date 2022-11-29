package gitlet;

import java.io.File;
import static gitlet.Utils.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/** Represents a gitlet repository.
 *
 *  does at a high level.
 *
 *  @HyunjiPark
 */
public class Repository implements Serializable {
    /**
     *
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    static final File BLOBS = Utils.join(GITLET_DIR, "/blobs");
    static final File COMMITS = Utils.join(GITLET_DIR, "/commits");


    private static HashMap<String, String> _branches = new HashMap<>();
    private static HashMap<String, Blob> _staging = new HashMap<>();
    private static HashMap<String, Blob> _removed = new HashMap<>();
    private static HashSet<String> _allCommit = new HashSet<String>();

    private static String _head;
    private static String _currBranch;
    private static Date _time;

    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println(
                    "A Gitlet version-control system already exists in the current directory.");
            return;
        } else {
            GITLET_DIR.mkdir();
            BLOBS.mkdir();
            COMMITS.mkdir();

            Date initDate = new Date(1970, 1, 1);
            Commit initCommit = new Commit("initial commit", initDate, null, null, new HashMap<>());

            initCommit.save();

            _currBranch = "main";
            _head = initCommit.getId();
            _allCommit.add(_head);
            _branches.put(_currBranch, _head);

            File branches = Utils.join(GITLET_DIR, "branches");
            try {
                branches.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeObject(branches, _branches);

            File staging = Utils.join(GITLET_DIR, "staging");
            try {
                staging.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeObject(staging, _staging);

            File removed = Utils.join(GITLET_DIR, "removed");
            try {
                removed.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeObject(removed, _removed);

            File allCommit = Utils.join(GITLET_DIR, "allCommit");
            try {
                allCommit.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeObject(allCommit, _allCommit);

            File head = Utils.join(GITLET_DIR, "head");
            try {
                head.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeContents(head, _head);

            File currBranch = Utils.join(GITLET_DIR, "currBranch");
            try {
                currBranch.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeContents(currBranch, _currBranch);
        }
    }

    public static void add(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        Blob blob = new Blob(file);
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap currStage = Utils.readObject(Utils.join(GITLET_DIR, "staging"), HashMap.class);



        if (currCommit.getDirect().containsKey(fileName)) {
            Blob before = currCommit.getDirect().get(fileName);
            if (blob.shaId.equals(before.shaId)) {
                currStage.remove(fileName, blob);
            } else {
                currStage.put(fileName, blob);
                blob.save();
            }
        } else {
            currStage.put(fileName, blob);
            blob.save();
        }
        HashMap currRemove = Utils.readObject(Utils.join(GITLET_DIR, "removed"), HashMap.class);
        currRemove.remove(fileName);

        Utils.writeObject(Utils.join(GITLET_DIR, "removed"), currRemove);
        Utils.writeObject(Utils.join(GITLET_DIR, "staging"), currStage);
    }

    public static void commit(String message) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit previous = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap commitTree = previous.getDirect();
        HashMap currStage = Utils.readObject(Utils.join(GITLET_DIR, "staging"), HashMap.class);
        HashMap currRemove = Utils.readObject(Utils.join(GITLET_DIR, "removed"), HashMap.class);

        if (currStage.isEmpty() && currRemove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        Iterator iter = commitTree.keySet().iterator();
        while (iter.hasNext()) {
            Object a = iter.next();
            if (currRemove.containsKey(a)) {
                iter.remove();
            }
        }
        for (Object k: currStage.keySet()) {
            if (commitTree.containsKey(k)) {
                commitTree.replace(k, currStage.get(k));
            } else {
                commitTree.put(k, currStage.get(k));
            }
        }
        Commit currCommit = new Commit(
                message, previous.getTimestamp(), previous.getId(), null, commitTree);
        currCommit.save();

        _head = currCommit.getId();
        HashSet allCommit = Utils.readObject(Utils.join(GITLET_DIR, "allCommit"), HashSet.class);
        HashMap branches = Utils.readObject(Utils.join(GITLET_DIR, "branches"), HashMap.class);
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));

        Utils.writeContents(Utils.join(GITLET_DIR, "head"), _head);
        allCommit.add(currCommit.getId());
        Utils.writeObject(Utils.join(GITLET_DIR, "allCommit"), allCommit);
        currStage.clear();
        currRemove.clear();
        Utils.writeObject(Utils.join(GITLET_DIR, "staging"), currStage);
        Utils.writeObject(Utils.join(GITLET_DIR, "removed"), currRemove);
        branches.replace(currBranch, currCommit.getId());
        Utils.writeObject(Utils.join(GITLET_DIR, "branches"), branches);
    }
    public static void log() {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        while (currCommit.getParent() != null) {
            System.out.println("===");
            System.out.println("commit " + currCommit.getId());
            System.out.println(String.format(
                    "Date: %1$ta %1$tb %1$te %1$tT %1$tY %1$tz", currCommit.getTimestamp()));
            System.out.println(currCommit.getMessage());
            System.out.println("");
            currCommit = currCommit.fromFile(currCommit.getParent());
        }
        System.out.println("===");
        System.out.println("commit " + currCommit.getId());
        System.out.println(String.format(
                "Date: %1$ta %1$tb %1$te %1$tT %1$tY %1$tz", currCommit.getTimestamp()));
        System.out.println(currCommit.getMessage());
        System.out.println("");
    }

    public static void checkoutFileName(String fileName) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap<String, Blob> currDirect = currCommit.getDirect();

        if (!currDirect.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }

        Blob blob = currDirect.get(fileName);
        byte[] content = blob.content;
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
        Utils.writeContents(file, content);
    }

    public static void checkoutCommitId(String commitId, String fileName) {
        HashSet<String> allCommit = Utils.readObject(
                Utils.join(GITLET_DIR, "allCommit"), HashSet.class);
        String id = commitId;

        if (commitId.length() < 40) {
            id = shortId(id);
        }

        if (!allCommit.contains(id)) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, id), Commit.class);
        HashMap<String, Blob> currDirect = currCommit.getDirect();

        if (!currDirect.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }

        Blob blob = currDirect.get(fileName);
        byte[] content = blob.content;
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
        Utils.writeContents(file, content);
    }

    public static String shortId(String commitId) {
        HashSet<String> allCommit = Utils.readObject(
                Utils.join(GITLET_DIR, "allCommit"), HashSet.class);
        Iterator<String> iter = allCommit.iterator();
        int count = 1;
        while (iter.hasNext()) {
            String a = iter.next();
            for (int j = 0; j < commitId.length(); j++) {
                if (a.charAt(j) != commitId.charAt(j)) {
                    break;
                } else {
                    count++;
                    if (count == commitId.length()) {
                        String search = a;
                        return search;
                    }
                }
            }
        }
        return commitId;
    }

    public static void checkoutBranch(String branch) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap<String, Blob> currDirect = currCommit.getDirect();
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));
        List<String> files = Utils.plainFilenamesIn(CWD);

        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                if (!currDirect.containsKey(files.get(i))) {
                    System.out.println("There is an untracked file in the way;"
                            + " delete it, or add and commit it first.");
                    return;
                }
            }
        }
        if (!branches.containsKey(branch)) {
            System.out.println("No such branch exists.");
            return;
        }
        if (currBranch.equals(branch)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }

        String head = branches.get(branch);
        Commit commit = Utils.readObject(Utils.join(COMMITS, head), Commit.class);
        HashMap<String, Blob> direct = commit.getDirect();

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                if (!direct.containsKey(files.get(i))) {
                    Utils.restrictedDelete(files.get(i));
                }
            }
        }
        for (String k : direct.keySet()) {
            Blob blob = direct.get(k);
            byte[] content = blob.content;
            File file = new File(k);
            try {
                file.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeContents(file, content);
        }
        _head = commit.getId();
        HashMap<String, Blob> currStage = Utils.readObject(
                Utils.join(GITLET_DIR, "staging"), HashMap.class);
        HashMap<String, Blob> currRemove = Utils.readObject(
                Utils.join(GITLET_DIR, "removed"), HashMap.class);

        Utils.writeContents(Utils.join(GITLET_DIR, "head"), _head);
        Utils.writeContents(Utils.join(GITLET_DIR, "currBranch"), branch);
        currStage.clear();
        currRemove.clear();
        Utils.writeObject(Utils.join(GITLET_DIR, "staging"), currStage);
        Utils.writeObject(Utils.join(GITLET_DIR, "removed"), currRemove);
    }

    public static void rm(String fileName) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap currStage = Utils.readObject(Utils.join(GITLET_DIR, "staging"), HashMap.class);
        HashMap currRemove = Utils.readObject(Utils.join(GITLET_DIR, "removed"), HashMap.class);

        if (!currStage.containsKey(fileName) && !currCommit.getDirect().containsKey(fileName)) {
            System.out.println("No reason to remove the file.");
        }
        if (currStage.containsKey(fileName)) {
            currStage.remove(fileName, currStage.get(fileName));
        }
        if (currCommit.getDirect().containsKey(fileName)) {
            currRemove.put(fileName, currCommit.getDirect().get(fileName));
            Utils.restrictedDelete(fileName);
        }
        Utils.writeObject(Utils.join(GITLET_DIR, "staging"), currStage);
        Utils.writeObject(Utils.join(GITLET_DIR, "removed"), currRemove);
    }

    public static void globalLog() {
        HashSet<String> allCommit = Utils.readObject(
                Utils.join(GITLET_DIR, "allCommit"), HashSet.class);
        for (String id : allCommit) {
            Commit currCommit = Utils.readObject(Utils.join(COMMITS, id), Commit.class);

            System.out.println("===");
            System.out.println("commit " + currCommit.getId());
            System.out.println(String.format(
                    "Date: %1$ta %1$tb %1$te %1$tT %1$tY %1$tz", currCommit.getTimestamp()));
            System.out.println(currCommit.getMessage());
            System.out.println("");
        }
    }

    public static void find(String message) {
        StringBuilder idMessage = new StringBuilder();
        HashSet<String> allCommit = Utils.readObject(
                Utils.join(GITLET_DIR, "allCommit"), HashSet.class);
        for (String id : allCommit) {
            Commit currCommit = Utils.readObject(Utils.join(COMMITS, id), Commit.class);
            if (currCommit.getMessage().equals(message)) {
                idMessage.append(id).append("\n");
            }
        }
        if (idMessage.isEmpty()) {
            System.out.println("Found no commit with that message.");
        } else {
            System.out.println(idMessage);
        }
    }

    public static void status() {
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);
        List<String> branchesStatus = new ArrayList<>();
        System.out.println("=== Branches ===");
        for (String branch : branches.keySet()) {
            branchesStatus.add(branch);
        }
        Collections.sort(branchesStatus);
        for (String branchOrder : branchesStatus) {
            if (branchOrder.equals(currBranch)) {
                System.out.println("*" + branchOrder);
            } else {
                System.out.println(branchOrder);
            }
        }
        System.out.println();

        HashMap<String, Blob> currStage = Utils.readObject(
                Utils.join(GITLET_DIR, "staging"), HashMap.class);
        List<String> stagedStatus = new ArrayList<>();
        System.out.println("=== Staged Files ===");
        for (String stage : currStage.keySet()) {
            stagedStatus.add(stage);
        }
        stagedStatus.sort(String::compareTo);

        for (String stageOrder : stagedStatus) {
            System.out.println(stageOrder);
        }
        System.out.println();

        HashMap<String, Blob> currRemove = Utils.readObject(
                Utils.join(GITLET_DIR, "removed"), HashMap.class);
        System.out.println("=== Removed Files ===");
        for (String remove : currRemove.keySet()) {
            System.out.println(remove);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void branch(String fileName) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);

        if (branches.containsKey(fileName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        branches.put(fileName, currHead);
        Utils.writeObject(Utils.join(GITLET_DIR, "branches"), branches);
    }

    public static void rmBranch(String fileName) {
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));

        if (currBranch.equals(fileName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        if (!branches.containsKey(fileName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        branches.remove(fileName, branches.get(fileName));
        Utils.writeObject(Utils.join(GITLET_DIR, "branches"), branches);
    }

    public static void reset(String id) {
        String currHead = Utils.readContentsAsString(Utils.join(GITLET_DIR, "head"));
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        List<String> files = Utils.plainFilenamesIn(CWD);
        HashMap<String, Blob> currDirect = currCommit.getDirect();

        Commit head = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        HashMap<String, Blob> direct = head.getDirect();

        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                if (!currDirect.containsKey(files.get(i)) && !direct.containsKey(files.get(i))) {
                    System.out.println(
                            "There is an untracked file in the way; "
                                    + "delete it, or add and commit it first.");
                    return;
                }
            }
        }
        HashSet<String> allCommit = Utils.readObject(
                Utils.join(GITLET_DIR, "allCommit"), HashSet.class);

        if (!allCommit.contains(id)) {
            System.out.print("No commit with that id exists.");
            return;
        }
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                if (!direct.containsKey(files.get(i))) {
                    Utils.restrictedDelete(files.get(i));
                }
            }
        }
        for (String key : direct.keySet()) {
            Blob blob = direct.get(key);
            byte[] content = blob.content;
            File file = new File(key);

            try {
                file.createNewFile();
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
            Utils.writeObject(file, content);
        }
        _head = head.getId();
        Utils.writeContents(Utils.join(GITLET_DIR, "head"), _head);
    }

    public static void merge(String branch) {
        String currBranch = Utils.readContentsAsString(Utils.join(GITLET_DIR, "currBranch"));
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);

        if (!branches.containsKey(branch)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        String currHeadId = branches.get(currBranch);
        String headId = branches.get(branch);
        Commit currHead = Utils.readObject(Utils.join(COMMITS, currHeadId), Commit.class);
        Commit head = Utils.readObject(Utils.join(COMMITS, headId), Commit.class);
        Commit split = Utils.readObject(Utils.join(COMMITS, currHeadId), Commit.class);
        String splitId = splitHelper(branch, currBranch);
        List<String> files = Utils.plainFilenamesIn(CWD);

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                if (!currHead.getDirect().containsKey(files.get(i))) {
                    System.out.println("There is an untracked file in the way; "
                            + "delete it, or add and commit it first.");
                    return;
                }
            }
        }
        HashMap<String, Blob> currStage = Utils.readObject(
                Utils.join(GITLET_DIR, "staging"), HashMap.class);
        HashMap<String, Blob> currRemove = Utils.readObject(
                Utils.join(GITLET_DIR, "removed"), HashMap.class);

        if (!currStage.isEmpty() && !currRemove.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
        if (branch.equals(currBranch)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
        if (splitId.equals(branch)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }
        if (splitId.equals(currBranch)) {
            System.out.println("Current branch fast-forwarded.");
            return;
        }
        HashMap<String, Blob> currFiles = currHead.getDirect();
        HashMap<String, Blob> givenFiles = head.getDirect();
        HashMap<String, Blob> splitFiles = split.getDirect();
        boolean conflict = false;

        for (String fileName : givenFiles.keySet()) {
            if (splitFiles.containsKey(fileName)) {
                if (givenFiles.containsKey(fileName) && currFiles.containsKey(fileName)) {
                    Blob curr = currFiles.get(fileName);
                    Blob given = givenFiles.get(fileName);
                    Blob spl = splitFiles.get(fileName);

                    if (!given.shaId.equals(curr.shaId)) {
                        conflict = true;
                        File newFile = new File(fileName);
                        String currFile = Utils.readContentsAsString(Utils.join(BLOBS, curr.shaId));
                        String givenFile = Utils.readContentsAsString(Utils.join(BLOBS, given.shaId));
                        Utils.writeContents(newFile, "<<<<<<< HEAD" + currFile
                                + "=======" + givenFile + ">>>>>>>");
                        Blob blob = new Blob(newFile);
                        currStage.put(fileName, blob);
                    }
                    if (curr.shaId.equals(spl.shaId)) {
                        if (!given.shaId.equals(curr.shaId)) {
                            checkoutCommitId(headId, fileName);
                            currStage.put(fileName, given);
                        }
                    }
                }
                if (!givenFiles.containsKey(fileName) && currFiles.containsKey(fileName)) {
                    Blob curr = currFiles.get(fileName);
                    Blob spl = splitFiles.get(fileName);
                    if (curr.shaId.equals(spl.shaId)) {
                        rm(fileName);
                        Utils.restrictedDelete(fileName);
                    }
                }
            }
            else if (!currFiles.containsKey(fileName)) {
                Blob blob = givenFiles.get(fileName);
                checkoutCommitId(headId, fileName);
                currStage.put(fileName, blob);
            }
        }
        if (conflict == true) {
            System.out.println("Encountered a merge conflict.");
        }
        String msg = "Merged [" + branch + "] into [" + currBranch + "].";
        Commit merge = new Commit(msg, currHead.getTimestamp(), currHeadId, headId, currStage);
    }

    private static String splitHelper(String branch1, String branch2) {
        HashMap<String, String> branches = Utils.readObject(
                Utils.join(GITLET_DIR, "branches"), HashMap.class);
        String currHead = branches.get(branch1);
        Commit currCommit = Utils.readObject(Utils.join(COMMITS, currHead), Commit.class);
        ArrayList<String> b1 = new ArrayList<String>();

        while (currCommit.getParent() != null) {
            b1.add(currCommit.getId());
            currCommit = currCommit.fromFile(currCommit.getParent());
        }

        String head2 = branches.get(branch2);
        Commit b2 = Utils.readObject(Utils.join(COMMITS, head2), Commit.class);

        while (b2.getParent() != null) {
            if (b1.contains(b2.getId())) {
                return b2.getId();
            }
            b2 = b2.fromFile(b2.getParent());
        }
        return b2.getId();
    }
}
