package gitlet;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {

    File fileName;
    String shaId;
    byte[] content;

    public Blob(File fileName) {

        content = Utils.readContents(fileName);
        shaId = Utils.sha1(content);
    }

    public void save() {
        File id = Utils.join(Repository.BLOBS, shaId);
        try {
            id.createNewFile();
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
        Utils.writeObject(id, content);
    }

    public Blob fromFile(String id) {
        File direct = Utils.join(Repository.BLOBS, id);
        if (direct.exists()) {
            return Utils.readObject(direct, Blob.class);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public File getFileName() {
        return fileName;
    }

    public String getId() {
        return shaId;
    }

    public byte[] getContent() {
        return content;
    }
}
