package ua.epamcourses.natalia_markova.homework.problem05.task08.subtask02_03;

/**
 * Created by natalia_markova on 26.05.2016.
 */
public class RBTreeSet<E extends  Comparable<E>> implements Set<E> {

    private Node<E> root;
    private int size;

    private int counter; // for toString()

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<E> {
        private E value;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private boolean color; // false - black; true - red

        public Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
            this.color = false;
        }

        public Node(E value, Node<E> parent, boolean color) {
            this.value = value;
            this.parent = parent;
            this.color = color;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return getNode(element) != null;
    }

    @Override
    public boolean add(E element) {
        if (size == 0) {
            root = new Node<>(element, null);
            size++;
            return true;
        }
        Node<E> node = root;
        Node<E> newNode = null;
        while (node != null) {
            int res = compare(node.value, element);
            if (res == 0) {
                return false;
            } else if (res < 0) {
                if (node.right == null) {
                    newNode = new Node<>(element, node, RED);
                    node.right = newNode;
                    break;
                }
                node = node.right;
            } else {
                if (node.left == null) {
                    newNode = new Node<>(element, node, RED);
                    node.left = newNode;
                    break;
                }
                node = node.left;
            }
        }
        convertOnInsert(newNode);
        size++;
        return true;
    }

    @Override
    public boolean remove(E element) {
        return remove(getNode(element));
    }

    private boolean remove(Node<E> node) {
        if (node == null) {
            return false;
        }
        if (node.right == null && node.left == null) {
            // no children
            if (node.parent == null) {
                // node == root
                root = null;
            }
            else if (node.parent.right == node) {
                node.parent.right = null;
            }
            else if (node.parent.left == node) {
                node.parent.left = null;
            }

            // if node is red, everything is Ok
            // have to do something only in case node is black and node is not root
            if (node.color == BLACK) {
                convertOnDelete(node);
            }

        } else if (node.right != null && node.left != null) {
            // node has both children
            // search for the vey left node of the right node
            Node<E> veryLeftNode = node.right;
            while (veryLeftNode.left != null) {
                veryLeftNode = veryLeftNode.left;
            }
            // move value
            node.value = veryLeftNode.value;
            remove(veryLeftNode);
            return true;
        } else {
            // has only one child
            // change node to child
            Node<E> child = null;
            if (node.left != null) {
                child = node.left;
            } else {
                child = node.right;
            }
            if (node.parent == null) {
                // node == root
                root = child;
            }
            else if (node.parent.right == node) {
                node.parent.right = child;
            }
            else if (node.parent.left == node) {
                node.parent.left = child;
            }
            child.parent = node.parent;
            // both node and child can't be red
            // both node and child can't be black
            // if node is red, no need to change color on child
            if (node.color == BLACK && child.color == RED) {
                // node is black, child id red
                child.color = BLACK;
            }
        }
        size--;
        return true;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder str = new StringBuilder("[");
        counter = 0;
        toString(root, str);
        str.append("]");
        return str.toString();
    }

    private void toString(Node<E> node, StringBuilder str) {
        if (node.left != null) {
            toString(node.left, str);
        }
        if (counter > 0) {
            str.append(", ");
        }
        str.append(node.value);
        if (node == root) {
            str.append("*");
        }
        if (node.color == RED) {
            str.append("+");
        }
        counter++;
        if (node.right != null) {
            toString(node.right, str);
        }
    }

    private Node<E> getNode(E element) {
        if (size == 0) {
            return null;
        }
        Node<E> node = root;
        while (node != null) {
            int res = compare(node.value, element);
            if (res == 0) {
                return node;
            } else if (res < 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private int compare(E el1, E el2) {
        if (el1 != null) {
            return el1.compareTo(el2);
        } else {
            return (el2 == null ? 0 : -1);
        }
    }

    private void convertOnInsert(Node<E> node) {
        if (node.parent == null && node.color == RED) { // node == root and it's red
            node.color = BLACK;
            return;
        }
        if (node.parent.color == BLACK) { // parent is black, everything is OK, no need to convert
            return;
        }
        Node<E> uncle = getUncle(node);
        Node<E> grandParent = getGrandParent(node);
        if (node.parent.color == RED && uncle != null && uncle.color == RED) { // both parent and uncle are red
            node.parent.color = BLACK;
            uncle.color = BLACK;
            grandParent.color = RED;
            convertOnInsert(grandParent);
            return;
        }
        if (node.parent.color == RED && grandParent.color == BLACK) {
            if (grandParent.left == node.parent && node.parent.right == node) {
                rotateLeft(node.parent);
                node = node.left;
            } else if (grandParent.right == node.parent && node.parent.left == node) {
                rotateRight(node.parent);
                node = node.right;
            }
        }
        node.parent.color = BLACK;
        grandParent.color = RED;
        if (grandParent.right == node.parent && node.parent.left == node ) {
            rotateRight(grandParent);
        } else { // (grandParent.right == node.parent && node.parent.right == node)
            rotateLeft(grandParent);
        }
    }

    private void convertOnDelete(Node<E> node) {
        if (node.parent == null) {
            return;
        }
        Node<E> sibling = getSibling(node);
        if (sibling == null) {
            return;
        }

        if (sibling.color == RED) {
            node.parent.color = RED;
            sibling.color = BLACK;
            if (node.parent.left == node) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
            sibling = getSibling(node);
            if (sibling == null) {
                return;
            }
        }

        if (node.parent.color == BLACK && sibling.color == BLACK
                && (sibling.left == null || sibling.left.color == BLACK) && (sibling.right == null || sibling.right.color == BLACK)) {
                    sibling.color = RED;
                    convertOnDelete(node.parent);
        } else if (node.parent.color  == RED && sibling.color == BLACK
                && (sibling.left == null || sibling.left.color == BLACK) && (sibling.right == null || sibling.right.color == BLACK)) {
                    sibling.color = RED;
                    node.parent.color = BLACK;
        } else {

            if  (sibling.color == BLACK) {
                if (node.parent.left == node && (sibling.right == null || sibling.right.color == BLACK) && sibling.left != null && sibling.left.color == RED) {
                    sibling.color = RED;
                    sibling.left.color = BLACK;
                    rotateRight(sibling);
                } else if (node.parent.right== node && (sibling.left == null || sibling.left.color == BLACK) && sibling.right!= null && sibling.right.color == RED) {
                    sibling.color = RED;
                    sibling.right.color = BLACK;
                    rotateLeft(sibling);
                }
                sibling = getSibling(node);
                if (sibling == null) {
                    return;
                }
            }

            sibling.color = node.parent.color;
            node.parent.color = BLACK;

            if (node.parent.left == node) {
                sibling.right.color = BLACK;
                rotateLeft(node.parent);
            } else {
                sibling.left.color = BLACK;
                rotateRight(node.parent);
            }
        }
    }

    private Node<E> getUncle(Node<E> node) {
        Node<E> grandParent = getGrandParent(node);
        if (grandParent == null) {
            return null;
        }
        if (grandParent.right == node.parent) {
            return grandParent.left;
        } else {
            return grandParent.right;
        }
    }

    private Node<E> getGrandParent(Node<E> node) {
        if (node == null || node.parent == null) {
            return null;
        }
        return node.parent.parent;
    }

    private Node<E> getSibling(Node<E> node) {
        if (node == null || node.parent == null) {
            return null;
        }
        return (node.parent.left == node ? node.parent.right : node.parent.left);
    }

    private void rotateLeft(Node<E> node) {
        Node<E> child = node.right;
        if (child == null) {
            return;
        }
        child.parent = node.parent;
        if (node.parent == null) {
            root = child;
        } else {
            if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }
        node.right = child.left;
        if (child.left != null) {
            child.left.parent = node;
        }
        node.parent = child;
        child.left = node;
    }

    private void rotateRight(Node<E> node) {
        Node<E> child = node.left;
        if (child == null) {
            return;
        }
        child.parent = node.parent;
        if (node.parent == null) {
            root = child;
        } else {
            if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }
        node.left = child.right;
        if (child.right != null) {
            child.right.parent = node;
        }

        node.parent = child;
        child.right = node;
    }

    public static void main(String[] args) {
        //Set<Integer> set = new RBTreeSet<>();
        Set<Integer> set = new AVLTreeSet<>();
        System.out.println(set);
        for (int i = 0; i < 10; i++) {
            if (set.add((int)(Math.random() * 100))) {
                System.out.println(set);
            }
        }
        System.out.println("contains(8) == " + set.contains(8));
        System.out.println("contains(100) == " + set.contains(100));
        System.out.println("contains(-10) == " + set.contains(-10));
        set.remove(100);
        System.out.println(set);
        for (int i = 100; i >= 0; i--) {
            if (set.remove((int)(Math.random() * 100))) {
                System.out.println(set);
            }
        }
        set.clear();
        System.out.println(set);

    }
}
