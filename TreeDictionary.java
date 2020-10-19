
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * TreeDictionary is a class that takes in a file and iterates through all the words in that file
 * and creating a node for each child which thereafter creates multiple children depending on the how
 * long the numeric signature is and how many words it contains.
 *
 * @author Ibrahim Alzilitni
 * @version 2020-02-14
 */
public class TreeDictionary implements Dictionary {
    private TrieNode root = new TrieNode();

    /**
     * Constructor for the TreeDictionary class which takes a path to the dictionary.
     * populates the trie with nodes and the words that correspond to each nodes child via
     * the createNode method.
     *
     * @param path path to the list of words that are to be added to the dictionary
     */
    public TreeDictionary(String path) {
        root.addSubNode();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String word = "";
            while ((word = reader.readLine()) != null) {
                if (AuxMethods.wordChecker(word, AuxMethods.wordToSignature(word)))
                    createNode(word.toLowerCase(), 0, root);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate file " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * signatureToWords is a method that is used to return possible words that share the same signature.
     * For example if a user was to enter a numerical signature such as 4663 then all the words with that signature
     * will be returned + all the possible words with that signature.
     *
     * @param signature the numerical signature to be inputted by the user.
     * @return a set of all the words that share the signature inputted by the user.
     * @throws IOException
     */
    @Override
    public Set<String> signatureToWords(String signature) {
        for (Character c : signature.toCharArray()) {
            if (!AuxMethods.validRange(c.toString())) {
                throw new IllegalArgumentException("Not in range of keypad digits");
            }
        }

        ArrayList<String> listToOutput = new ArrayList<>();
        HashSet<String> result = new HashSet<>();
        StringBuffer signatureToProcess = new StringBuffer();
        int signatureLengthj = signature.length();
        int signatureLengthi = signature.length();
        for (int i = 0; i <= signature.length(); i++) {
            for (int j = 0; j < signatureLengthj; j++) {
                signatureToProcess.append(signature.charAt(j));
            }
            signature = signatureToProcess.toString();
            signatureToProcess = new StringBuffer();
            listToOutput.addAll(TrieSearcher(this.root, IndexOfSignature(signature), 0));
        }
        listToOutput = (ArrayList<String>) AuxMethods.decreaseListSize(listToOutput, signatureLengthi);
        Collections.sort(listToOutput);
        for (String s : listToOutput) {
            result.add(s);
        }
        return result;
    }

    /**
     * createNode is a Method to add a node to a tree and populate with the appropriate words
     * that corresponds to the particular numeric node.
     *
     * @param word  word to add to the node
     * @param index position to look at within the word to be added
     * @param node  the tree to edit
     */
    public void createNode(String word, int index, TrieNode node) {
        if (word.length() == index) {
            node.addWord(word);
            return;
        } else {
            String signature = AuxMethods.wordToSignature(word);
            if (node.getSubNodes()[Character.getNumericValue(signature.charAt(index)) - 2] == null) {
                node.addWord(word);
                TrieNode nodeToBeAdded = new TrieNode();
                nodeToBeAdded.addSubNode();
                node.getSubNodes()[Character.getNumericValue(signature.charAt(index)) - 2] = nodeToBeAdded;
                createNode(word, index + 1, node.getSubNodes()[Character.getNumericValue(signature.charAt(index)) - 2]);

            } else {
                node.addWord(word);
                createNode(word, index + 1, node.getSubNodes()[Character.getNumericValue(signature.charAt(index)) - 2]);

            }
        }
    }

    /**
     * TrieSearcher is a method that finds the particular signature in the trie
     * and all its possible words for that signature.
     *
     * @param tree      the tree to look for the node in.
     * @param signature the signature to look for in the trie.
     * @param position  the position in the trie to look at in the word.
     * @return all the matches dependent on the current signature.
     */
    public static Set<String> TrieSearcher(TrieNode tree, String signature, int position) {
        if (position == signature.length()) {
            return tree.getWords();
        }
        int index = Character.getNumericValue(signature.charAt(position));
        return tree.getSubNodes()[index] != null ? TrieSearcher(tree.getSubNodes()[index], signature, position + 1) : tree.getWords();
    }

    /**
     * IndexOfSignature is a method that converts a signature string to a
     * string of indexes that can be used to find a particular word in the trie.
     *
     * @param s signature to convert to index
     * @return the converted signature as an index
     */
    public static String IndexOfSignature(String s) {
        String toReturn = "";
        for (int i = 0; i < s.toCharArray().length; i++) {
            char toConvert = s.charAt(i);
            if (Character.getNumericValue(toConvert) >= 2) {
                int toAdd = Character.getNumericValue(toConvert) - 2;
                toReturn += toAdd;
            }
        }
        return toReturn;

    }

    /**
     * getTrie is a method that gets the full tree.
     *
     * @return the trie tree.
     */
    public TrieNode getTrie() {
        return root;
    }

    /**
     * AuxMethods is a new class that contains all the helper methods to be used in the TreeDictionary class.
     *
     * @author Ibrahim Alzilitni
     * @version 2019-02-14
     */
    static class AuxMethods {


        /**
         * wordChecker is a helper method that checks if a word is valid and the signature for the word is correct.
         *
         * @param wordToCheck word to be checked
         * @param signature   signature to compare with the word
         * @return true if the word is valid in comparision to the signature and matches and false if otherwise
         */
        public static boolean wordChecker(String wordToCheck, String signature) {

            String wordSignature = PredictivePrototype.wordToSignature(wordToCheck);
            return wordSignature.equals(signature) && validWord(wordToCheck);

        }

        /**
         * decreaseListSize is a helper method used to decrease the size of the list and match the output
         * with the user's input.
         *
         * @param n               the list of characters to be added.
         * @param sizeOfSignature the size of the signature.
         * @return a list with the new word in its decreased sized form.
         */
        public static List<String> decreaseListSize(ArrayList<String> n, int sizeOfSignature) {
            ArrayList<String> size = new ArrayList<>();
            for (int i = 0; i < n.size(); i++) {
                if (n.get(i).length() > sizeOfSignature) {
                    size.add(n.get(i).substring(0, sizeOfSignature));
                } else
                    size.add(n.get(i));
            }
            return size;
        }

        /**
         * validWord is a helper method that checks if a word is valid and without strings
         *
         * @param string the word to check
         * @return true if valid, false if otherwise
         */
        public static boolean validWord(String string) {
            return Pattern.compile("^[a-zA-Z]+$").matcher(string).find();
        }

        /**
         * validRange is a helper method used to determine whether the users input fits into the keypads range
         * of numbers
         *
         * @param string user's input.
         * @return true or false depending on whether input is keypad range 2-9.
         */
        public static boolean validRange(String string) {
            return Pattern.compile("[2-9]").matcher(string).find();
        }


        /**
         * charToInt is a method that takes a character and returns the appropriate numerical
         * signature for that particular character.
         *
         * @param c char to convert into  a keypad number
         * @return the number on the keypad associated with that particular char
         */
        public static char charToInt(char c) {

            if (c == 'a' || c == ('b') || c == ('c')) {
                return '2';
            } else if (c == ('d') || c == ('e') || c == ('f')) {
                return '3';

            } else if (c == ('g') || c == ('h') || c == ('c')) {
                return '4';

            } else if (c == ('j') || c == ('k') || c == ('l')) {
                return '5';

            } else if (c == ('m') || c == ('n') || c == ('o')) {
                return '6';

            } else if (c == ('p') || c == ('q') || c == ('r') || c == ('s')) {
                return '7';

            } else if (c == ('t') || c == ('u') || c == ('v')) {
                return '8';

            } else if (c == ('w') || c == ('x') || c == ('y') || c == ('z')) {
                return '9';

            }
            return ' ';
        }

        /**
         * wordToSignature is a method that takes a string and converts it to the keypad entry
         * that would have been typed on a traditional mobile phone.
         *
         * @param word the word to convert into its numerical signature.
         * @return the numerical signature of the word
         */
        public static String wordToSignature(String word) {
            String[] splitted = word.toLowerCase().split("");

            StringBuffer filler = new StringBuffer();

            for (String i : splitted) {
                if (i.equals("a") || i.equals("b") || i.equals("c")) {
                    filler.append("2");
                } else if (i.equals("d") || i.equals("e") || i.equals("f")) {
                    filler.append("3");
                } else if (i.equals("g") || i.equals("h") || i.equals("i")) {
                    filler.append("4");
                } else if (i.equals("j") || i.equals("k") || i.equals("l")) {
                    filler.append("5");
                } else if (i.equals("m") || i.equals("n") || i.equals("o")) {
                    filler.append("6");
                } else if (i.equals("p") || i.equals("q") || i.equals("r") || i.equals("s")) {
                    filler.append("7");
                } else if (i.equals("t") || i.equals("u") || i.equals("v")) {
                    filler.append("8");
                } else if (i.equals("w") || i.equals("x") || i.equals("y") || i.equals("z")) {
                    filler.append("9");
                } else if (i.equals("")) {
                    filler.append("");
                } else {
                    filler.append(" ");
                }
            }

            return filler.toString();
        }


    }

    /**
     * TreeNode class to create a tree. Each node of the tree has children in the form of an array of other tree nodes.
     * Each node has a list of words associated with it.
     *
     * @author Ibrahim Alzilitni
     * @version 2020-02-14
     */
    static class TrieNode {
        private TrieNode[] subNodes;
        private HashSet<String> words = new HashSet<>();

        /**
         * Constructor for the TrieNode class
         * Assigns 8 subnodes for each time the class is called.
         */
        public TrieNode() {
            this.subNodes = new TrieNode[8];
        }

        /**
         * Getter for the words stored in a particular node.
         *
         * @return the list of words that are stored in a particular node.
         */
        public HashSet<String> getWords() {
            return words;
        }

        /**
         * addSubNode is a method of type void that adds sub nodes to the node in place.
         */
        public void addSubNode() {
            for (int i = 0; i < subNodes.length; i++) {
                subNodes[i] = new TrieNode();
            }
        }

        /**
         * Getter for the subNodes of a particular node.
         *
         * @return the subNodes.
         */
        public TrieNode[] getSubNodes() {
            return subNodes;
        }

        /**
         * addWord is a method that adds a word to the particular node's list of words.
         *
         * @param word is the word to be added.
         */
        public void addWord(String word) {
            words.add(word);
        }


    }
}