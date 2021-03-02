import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class LadderGame {
    public ArrayList<ArrayList<String>> dictionary = new ArrayList<>();
    public ArrayList<ArrayList<String>> dictionaryClone = new ArrayList<>();

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public abstract void play(String start, String end);

    // Finds all the words that are one letter away from the word passed in
    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> words = new ArrayList<>();

        while (!withRemoval) {
            for (String wordTwo : this.dictionary.get(word.length())) {
                int wordDifference = diff(word, wordTwo);

                if (wordDifference == word.length() - 1) {
                    words.add(wordTwo);
                }
            }
            withRemoval = true;
        }

        return words;
    }

    public int diff(String start, String end) {
        int wordDifference = 0;
        for (int j = 0; j < start.length(); j++) {
            char charOne = start.charAt(j);
            char charTwo = end.charAt(j);

            if (charOne == charTwo) {
                wordDifference++;
            }
        }
        return wordDifference;
    }

    public void listWords(int length, int howMany) {
        for (int i = 0; i < howMany; i++) {
            System.out.println(this.dictionary.get(length).get(i));
        }
    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            // Organizing the dictionary
            for(int i = 0; this.dictionary.size() <= longestWord; i++) {
                this.dictionary.add(new ArrayList<String>());
            }
            for (String word : allWords) {
                this.dictionary.get(word.length()).add(word);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}