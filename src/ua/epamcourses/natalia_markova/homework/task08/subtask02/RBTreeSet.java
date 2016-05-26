package ua.epamcourses.natalia_markova.homework.task08.subtask02;

/**
 * Created by natalia_markova on 26.05.2016.
 */
public class RBTreeSet<E extends  Comparable> implements Set<E> {

    private Node<E> root;
    private int size;

    private enum NodeColor {
        RED, BLACK
    }

    private static class Node<E> {
        private E value;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private NodeColor color;

        public Node(E value, Node<E> parent, NodeColor color) {
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
            root = new Node<>(element, null, NodeColor.BLACK);
            size++;
            return true;
        }
        Node<E> node = root;
        while (node != null) {
            int res = compare(node.value, element);
            if (res == 0) {
                return false;
            } else if (res < 0) {
                if (node.right == null) {
                    Node<E> newNode = new Node<>(element, node, NodeColor.RED);
                    node.right = newNode;
                    break;
                }
                node = node.right;
            } else {
                if (node.left == null) {
                    Node<E> newNode = new Node<>(element, node, NodeColor.RED);
                    node.left = newNode;
                    break;
                }
                node = node.left;
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(E element) {
        Node<E> node = getNode(element);
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

    private void rotateLeft(Node<E> node) {
        Node<E> child = node.right;
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
}
