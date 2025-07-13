package src.smarthire.structures;

public class Trie {
    private NodeTrie head;

    public Trie() {
        this.head = null;
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        head = insert(head, word, 0);
    }

    private NodeTrie insert(NodeTrie curr, String word, int index) {
        char c = word.charAt(index);

        if (curr == null)
            curr = new NodeTrie(c);

        if (c < curr.key) {
            curr.left = insert(curr.left, word, index);
        } else if (c > curr.key) {
            curr.right = insert(curr.right, word, index);
        } else {
            if (index + 1 < word.length()) {
                curr.center = insert(curr.center, word, index + 1);
            } else {
                curr.stop = true;
            }
        }

        return curr;
    }

    public boolean search(String word) {
        NodeTrie found = searchNode(word);
        return found != null && found.stop;
    }

    private NodeTrie searchNode(String word) {
        return searchNode(head, word, 0);
    }

    private NodeTrie searchNode(NodeTrie curr, String word, int index) {
        if (curr == null || index >= word.length()) return null;
        char c = word.charAt(index);

        if (c < curr.key) {
            return searchNode(curr.left, word, index);
        } else if (c > curr.key) {
            return searchNode(curr.right, word, index);
        } else {
            if (index == word.length() - 1)
                return curr;
            return searchNode(curr.center, word, index + 1);
        }
    }

    public void delete(String word) {
        if (word == null || word.isEmpty()) return;
        head = delete(head, word, 0);
    }

    private NodeTrie delete(NodeTrie curr, String word, int index) {
        if (curr == null) return null;

        char c = word.charAt(index);

        if (c < curr.key) {
            curr.left = delete(curr.left, word, index);
        } else if (c > curr.key) {
            curr.right = delete(curr.right, word, index);
        } else {
            if (index < word.length() - 1) {
                curr.center = delete(curr.center, word, index + 1);
            } else {
                curr.stop = false;
            }
        }
        
        if (curr.stop == false && curr.left == null && curr.center == null && curr.right == null) {
            return null;
        }

        return curr;
    }

    public void printTree() {
        if (head == null) {
            System.out.println("El árbol está vacío.");
        } else {
            printTree(head, 0, "ROOT");
        }
    }

    private void printTree(NodeTrie curr, int level, String branch) {
        if (curr == null) return;

        printTree(curr.right, level + 1, "R");

        for (int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(branch + "─ " + curr.key + (curr.stop ? " (#)" : ""));

        printTree(curr.center, level + 1, "C");
        printTree(curr.left, level + 1, "L");
    }
}
