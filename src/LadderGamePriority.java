import java.util.ArrayList;

public class LadderGamePriority extends LadderGame{

    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
        //TODO: Write this method
        if (start.length() != end.length()) {
            System.out.printf("%s and %s are not the same length", start, end);
        } else if (!this.dictionary.get(start.length()).contains(start) || !this.dictionary.get(end.length()).contains(end)) {
            System.out.printf("%s and/or %s are not in the dictionary", start, end);
        }

        int enqueues = 0;
        WordInfoPriority partialSolution = new WordInfoPriority(start, 0, 1 + diff(start, end), start);
        AVLTree partialSolutionTree = new AVLTree();
        partialSolutionTree.insert(partialSolution);
        enqueues++;

        boolean ladderComplete = false;
        ArrayList<WordInfoPriority> usedWords = new ArrayList<>();
        usedWords.add(partialSolution);
        while (!partialSolutionTree.isEmpty() && !ladderComplete) {
            WordInfo dequeueSolution = (WordInfo) partialSolutionTree.deleteMin();
            ArrayList<String> solutionList = oneAway(dequeueSolution.getWord(), false);

            for (String word : solutionList) {
                String stringHistory = dequeueSolution.getHistory() + " " + word;
                int newMoves = dequeueSolution.getMoves() + 1;
                WordInfoPriority newPartialSolution = new WordInfoPriority(word, newMoves, newMoves + diff(word, end),stringHistory);

                if (newPartialSolution.getWord().compareTo(end) == 0) {
                    System.out.printf("Seeking A* solution from %s => %s\n\t[%s] total enqueues %d\n", start, end, newPartialSolution.getHistory(), enqueues);
                    partialSolutionTree.makeEmpty();
                    ladderComplete = true;
                } else {
                    boolean value = binarySearch(usedWords, newPartialSolution, 0, usedWords.size());
                    if (value) {
                        partialSolutionTree.insert(newPartialSolution);
                        enqueues++;
                    }
                }
            }
        }

        if (partialSolutionTree.isEmpty() && !ladderComplete) {
            System.out.printf("Seeking A* solution from %s => %s\n\tNo ladder found\n", start, end);
        }
    }

    public boolean binarySearch(ArrayList<WordInfoPriority> words, WordInfoPriority wordInfo, int min, int max) {
        if (min >= max) {
            words.add(min, wordInfo);
            return true;
        } else {
            int value = ((max - min) / 2) + min;

            if (wordInfo.getWord().equals(words.get(value).getWord())) {
                if (wordInfo.compareTo(words.get(value)) >= 0) {
                    return false;
                } else if (wordInfo.compareTo(words.get(value)) < 0) {
                    words.remove(value);
                    words.add(value, wordInfo);
                    return true;
                }
            } else if (wordInfo.getWord().compareTo(words.get(value).getWord()) > 0) {
                return binarySearch(words, wordInfo, value + 1, max);
            } else {
                return binarySearch(words, wordInfo, min, value - 1);
            }
        }
        return false;
    }
}
