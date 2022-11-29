package gitlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.io.File;
import java.util.Date;

/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @HyunjiPark
 */
public class Commit implements Serializable {
    /**
     *
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date timestamp;
    private String parent;
    private String secondParent;
    private String shaId;
    private Blob blob;
    private HashMap<String, Blob> direct;
    private HashMap<String, String> toCommit;
    private HashMap<String, String> toRemove;


    public Commit(String message, Date timestamp, String parent,
                   String secondParent, HashMap<String, Blob> direct) {
        this.message = message;
        if (timestamp != null) {
            this.timestamp = new Date();
        } else {
            timestamp = new Date(1970, 1, 1);
        }
        this.parent = parent;
        this.secondParent = secondParent;
        this.shaId = Utils.sha1(Utils.serialize(this));
        this.direct = direct;
    }

    public void save() {
        File id = Utils.join(Repository.COMMITS, this.shaId);
        try {
            id.createNewFile();
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
        Utils.writeObject(id, this);
    }

    public Commit fromFile(String id) {
        File directory = Utils.join(Repository.COMMITS, id);
        if (directory.exists()) {
            return Utils.readObject(directory, Commit.class);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getParent() {
        return this.parent;
    }

    public String getSecondParent() {
        return this.secondParent;
    }

    public String getId() {
        return this.shaId;
    }

    public HashMap<String, Blob> getDirect() {
        return this.direct;
    }

}
