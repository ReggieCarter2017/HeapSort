import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        final Tree tree = new Tree();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    int data = Integer.parseInt(reader.readLine());
                    tree.add(data);
                    System.out.println("123");
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Tree {
    private Node root;

        private boolean insertNode(Node node, int data)
        {
            if (node.data == data) { return false; }
            else {
                if (node.data > data) {
                    if (node.left != null) {
                        boolean result = insertNode(node.left, data);
                        node.left = rebalance(node.left);
                        return result;
                    }
                    else {
                        node.left = new Node();
                        node.left.color = Color.RED;
                        node.left.data = data;
                        return true;
                    }
                }
                else {
                    if (node.right != null) {
                        boolean result = insertNode(node.right, data);
                        node.right = rebalance(node.right);
                        return result;
                    }
                    else {
                        node.right = new Node();
                        node.right.color = Color.RED;
                        node.right.data = data;
                        return true;
                    }
                }
            }
        }
        public boolean add(int data)
        {
            if (root != null)
            {
                boolean result = insertNode(root, data);
                root = rebalance(root);
                root.color = Color.BLACK;
                return  result;
            } else {
                root = new Node();
                root.color = Color.BLACK;
                root.data = data;
                return true;
            }
        }

        private void colorSwap(Node node)
        {
            node.right.color = Color.BLACK;
            node.left.color = Color.BLACK;
            node.color = Color.RED;
        }

        private Node leftSwap(Node node)
        {
            Node leftC = node.left;
            Node tempC = leftC.right;
            leftC.right = node;
            node.left = tempC;
            leftC.color = node.color;
            node.color = Color.RED;
            return leftC;
        }

        private Node rightSwap(Node node)
        {
            Node rightC = node.right;
            Node tempC = rightC.left;
            rightC.left = node;
            node.right = tempC;
            rightC.color = node.color;
            node.color = Color.RED;
            return rightC;
        }

        private Node rebalance(Node node)
        {
            Node result = node;
            boolean needRebalance;
            do {
                needRebalance = false;
                if (result.right != null && result.right.color == Color.RED &&
                        (result.left == null || result.left.color == Color.BLACK)){
                    needRebalance = true;
                    result = rightSwap(result);
                }
                if (result.left != null && result.left.color == Color.RED &&
                        (result.left.left != null || result.left.left.color == Color.RED)){
                    needRebalance = true;
                    result = leftSwap(result);
                }
                if (result.left != null && result.left.color == Color.RED &&
                        (result.right != null || result.right.color == Color.RED)){
                    needRebalance = true;
                    colorSwap(result);
                }
            }
            while (needRebalance);
            return result;
        }
    class Node {
        private int data;
        private Color color;
        private Node left;
        private Node right;
    }

    enum Color {
        RED, BLACK;
    }
}

