package ua.epamcourses.natalia_markova.homework.problem07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natalia_markova on 07.06.2016.
 */
public class TextCompressor {
    private String incomeData;
    private List<String> text = new ArrayList<>();
    private Node root;
    private Map<String,String> codes;

    public TextCompressor(String incomeData) {
        this.incomeData = incomeData;
    }

    private static class Node implements Comparable<Node>{
        private String value;
        private int weight;
        private Node left;
        private Node right;

        public Node(String value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            int res = weight - o.weight;
            if (res == 0) {
                res = value.compareTo(o.value);
            }
            return res;
        }
    }

    public String compress() {
        if (incomeData == null) {
            return null;
        }
        if (codes == null) {
            getCodes();
        }
        StringBuilder str = new StringBuilder();
        for (String word: text) {
            str.append(codes.get(word));
        }
        return str.toString();
    }

    public String decompress(String compressedData) {
        if (codes == null) {
            getCodes();
        }
        if (root == null) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        decompress(compressedData, str);
        return str.toString();
    }

    private void getCodes() {
        codes = new HashMap<>();
        PriorityQueue<Node> tree = getFrequency();
        buildTree(tree);
        getCodes(root, "");
    }

    private void getCodes(Node root, String code) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            codes.put(root.value, code);
            return;
        }
        getCodes(root.left, code + "0");
        getCodes(root.right, code + "1");
    }

    private void buildTree(PriorityQueue<Node> tree) {
        while (tree.size() > 1) {
            Node left = tree.remove();
            Node right = tree.remove();
            Node parent = new Node(left.value + "_" + right.value, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            tree.add(parent);
        }
        root = tree.poll();
    }

    private PriorityQueue<Node> getFrequency() {
        Map<String,Integer> wordFrequencies = new HashMap<>();
        PriorityQueue<Node> frequency = new PriorityQueue<>();

        Pattern pattern = Pattern.compile("\\w+|\\W");
        Matcher matcher = pattern.matcher(incomeData);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();
            Integer freq = wordFrequencies.get(word);
            if (freq == null) {
                freq = 0;
            }
            wordFrequencies.put(word, freq + 1);
            text.add(word);
        }

        for (Map.Entry<String,Integer> freq: wordFrequencies.entrySet()) {
            frequency.add(new Node(freq.getKey(), freq.getValue()));
        }
        return frequency;
    }

    private void decompress(String compressedData, StringBuilder str) {
        Node node = null;
        Node nextNode = root;
        int index = 0;
        while (nextNode!= null) {
            node = nextNode;
            if (index == compressedData.length()) {
                break;
            }
            char ch = compressedData.charAt(index);
            if (ch == '0') {
                nextNode = node.left;
            } else {
                nextNode = node.right;
            }
            index++;
        }
        str.append(node.value);
        if (index < compressedData.length()) {
            decompress(compressedData.substring(index - 1), str);
        }
    }

    public static void main(String[] args) {
        String textString = "";
//        String fileName = "O:\\!Java\\text compressor test.txt";
        String fileName = "O:\\!Java\\compositions.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                textString = textString + line + System.getProperty("line.separator");
            }
        } catch (IOException e) {
            System.out.println("Couldn't read file " + fileName);
            return;
        }
        TextCompressor compressor = new TextCompressor(textString);
        String code = compressor.compress();
        System.out.println(code);
        System.out.println(compressor.decompress(code));
    }
}
