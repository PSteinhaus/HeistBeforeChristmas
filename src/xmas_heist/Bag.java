package xmas_heist;

/**
 * Holds the presents picked up by Santa.
 */
public class Bag {

    private int presentCount;

    public void addPresents(int count) {
        presentCount += count;
    }

    public boolean isEmpty() { return presentCount == 0; }

    public void removePresents(int count) {
        presentCount -= count;
        if (presentCount < 0)
            presentCount = 0;
    }

    public int getPresentCount() { return presentCount; }
}
