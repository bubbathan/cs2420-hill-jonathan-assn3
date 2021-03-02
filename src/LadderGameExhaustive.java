import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame{

    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }

    public void play(String start, String end) {
        if (start.length() != end.length()) {
            System.out.printf("%s and %s are not the same length", start, end);
        } else if (!this.dictionary.get(start.length()).contains(start) || !this.dictionary.get(end.length()).contains(end)) {
            System.out.printf("%s and/or %s are not in the dictionary", start, end);
        }

        for (int i = 0; i < this.dictionary.size(); i++) {
            this.dictionaryClone.add((ArrayList<String>) this.dictionary.get(i).clone());
        }

        int enqueues = 0;
        WordInfoExhaustive partialSolution = new WordInfoExhaustive(start, 0, start);
        Queue<WordInfo> partialSolutionQueue = new Queue<>();
        partialSolutionQueue.enqueue(partialSolution);
        enqueues++;

        boolean ladderComplete = false;
        while (!partialSolutionQueue.isEmpty() && !ladderComplete) {
            WordInfo dequeueSolution = partialSolutionQueue.dequeue();
            ArrayList<String> solutionList = oneAway(dequeueSolution.getWord(), false);
            this.dictionary.get(start.length()).removeAll(solutionList);

            for (String word : solutionList) {
                String stringHistory = dequeueSolution.getHistory() + " " + word;
                int newMoves = dequeueSolution.getMoves() + 1;
                WordInfoExhaustive newPartialSolution = new WordInfoExhaustive(word, newMoves, stringHistory);

                if (newPartialSolution.getWord().compareTo(end) == 0) {
                    System.out.printf("Seeking exhaustive solution from %s => %s\n\t[%s] total enqueues %d\n", start, end, newPartialSolution.getHistory(), enqueues);
                    partialSolutionQueue.clearQueue();
                    ladderComplete = true;
                } else {
                    partialSolutionQueue.enqueue(newPartialSolution);
                    enqueues++;
                }
            }
        }
        this.dictionary.removeAll(dictionary);
        for (ArrayList<String> strings : dictionaryClone) {
            this.dictionary.add((ArrayList<String>) strings.clone());
        }
        if (partialSolutionQueue.isEmpty() && !ladderComplete) {
            System.out.printf("Seeking exhaustive solution from %s => %s\n\tNo ladder found\n", start, end);
        }
    }
}
