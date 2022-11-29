import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL{
    private Node root;

    private class Node {
        private boolean iskey;
        private HashMap<Character, Node> next;
        Character character;

        private Node(Character character, boolean key) {
            this.character = character;
            this.iskey = key;
            this.next = new HashMap<>();
        }
    }
    public MyTrieSet() {
        clear();
    }

    @Override
    public void clear() {
        root = new Node(null, false);
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.next.containsKey(c)) {
                return false;
            }
            curr = curr.next.get(c);
        }
        return curr.iskey;
    }

    @Override
    public void add(String key) {
        if (key == null) {
            return;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.next.containsKey(c)) {
                curr.next.put(c, new Node(c, false));
            }
            curr = curr.next.get(c);
        }
        curr.iskey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> list = new ArrayList<>();
        if (prefix == null) {
            return list;
        }
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!curr.next.containsKey(c)) {
                return list;
            }
            curr = curr.next.get(c);
        }
        return helper(curr, list, prefix);
    }

    private List<String> helper(Node node, List<String> list, String prefix) {
        if (node.iskey) {
            list.add(prefix);
        }
        for (Character character : node.next.keySet()) {
            helper(node.next.get(character), list, prefix + character);
        }
        return list;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
