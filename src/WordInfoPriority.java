public class WordInfoPriority extends WordInfo implements Comparable<WordInfoPriority>{
    private int priority;

    public WordInfoPriority(String word, int moves, int estimatedCost) {
        super(word, moves);
        this.priority = estimatedCost;
    }

    public WordInfoPriority(String word, int moves, int estimatedCost, String history) {
        super(word, moves, history);
        this.priority = estimatedCost;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(WordInfoPriority wordInfoPriority) {
        return Integer.compare(this.getPriority(), wordInfoPriority.getPriority());
    }
}
