package ua.epamcourses.natalia_markova.homework.task08.subtask02_03;

/**
 * Created by natalia_markova on 26.05.2016.
 */
public class AVLTreeSet<E extends Comparable<E>> implements Set<E> {

    private int size;
    private Node<E> root;

    private int counter; // for toString()

    private static class Node<E> {
        private E value;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private int height;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }

        public Node(E value, Node<E> parent, int height) {
            this.value = value;
            this.parent = parent;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", height=" + height +
                    '}';
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
        Node<E> node = add(root, element);
        if (node != null) {
            size++;
            return true;
        }
        return false;
    }

    int getBalance(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node<E> add(Node<E> node, E element) {

        if (node == null) {
            return (new Node(element));
        }

        int res = compare(element, node.value);
        if (res == 0) {
            return null;
        } else if (res < 0) {
            node.left = add(node.left, element);
            node.left.parent = node;
        } else {
            node.right = add(node.right, element);
            node.right.parent = node;
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node); // left - right

        if (Math.abs(balance) > 1) {

            if (balance > 1) {
                // inserted element is in the left
                int resLeft = (node.left == null ? 0 : compare(element, node.left.value));
                if (resLeft < 0) {
                    // inserted element is the left child of the left child of node.left
                    return rotateRight(node);
                } else if (resLeft > 0) {
                    // inserted element is the right child of the left child of node.left
                    rotateRight(node.left);
                    return rotateRight(node);
                }

            } else {

                // inserted element is in the right
                int resRight = (node.right == null ? 0 : compare(element, node.right.value));
                if (resRight < 0) {
                    // inserted element is the left child of the right child of node.right
                    rotateLeft(node.right);
                    return rotateLeft(node);
                } else if (resRight > 0) {
                    // inserted element is the right child of the right child of node.right
                    return rotateLeft(node);
                }
            }
        }

        return node;
    }

    @Override
    public boolean remove(E element) {
        return remove(getNode(element));
    }

    private boolean remove(Node<E> node) {
        if (node == null) {
            return false;
        }
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
        counter++;
        if (node.right != null) {
            toString(node.right, str);
        }
    }

    private int getHeight(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return node.height + 1;
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

    private Node<E> rotateLeft(Node<E> node) {
        Node<E> child = node.right;
        if (child == null) {
            return null;
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

        node.height = Math.max(getHeight(node.left), getHeight(node.right));
        child.height = Math.max(getHeight(child.left), getHeight(child.right));

        return child;
    }

    private Node<E> rotateRight(Node<E> node) {
        Node<E> child = node.left;
        if (child == null) {
            return null;
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

        node.height = Math.max(getHeight(node.left), getHeight(node.right));
        child.height = Math.max(getHeight(child.left), getHeight(child.right));

        return child;
    }
}
