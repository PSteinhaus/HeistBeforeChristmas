package xmas_heist;

import xmas_heist.items.*;

/**
 * Santa Claus, the main character of the game, controlled by the player.
 */
public class SantaClaus {
    private Bag bag = new Bag();
    private SantaCoat coat = new SantaCoat();
    private SantaHat hat = new SantaHat();
    private SantaTrousers trousers = new SantaTrousers();

    public Bag getBag() { return bag; }

    public boolean isStripped() {
        return coat == null && hat == null && trousers == null;
    }

    /**
     * lose a piece of your outfit
     * @return
     * returns the lost piece
     */
    public Item loseOutfit() {
        // first check if you're already stripped
        // if you are then nothing more can be lost, so return 'null'
        if (isStripped())
            return null;

        Item lostOutfit = null;
        do {
            double roll = Math.random();
            if (roll < 0.33) {
                if (coat != null) {
                    lostOutfit = coat;
                    coat = null;
                }
            } else if (roll < 0.66) {
                if (hat != null) {
                    lostOutfit = hat;
                    hat = null;
                }
            } else {
                if (trousers != null) {
                    lostOutfit = trousers;
                    trousers = null;
                }
            }
        } while (lostOutfit == null);

        return lostOutfit;
    }

    public void putOn(SantaHat santaHat) {
        if (hat != null) {
            System.out.println("Du hast bereits eine Mütze.");
        } else {
            hat = santaHat;
            System.out.println("Du setzt die Mütze auf.");
        }
    }

    public void putOn(SantaTrousers santaTrousers) {
        if (trousers != null) {
            System.out.println("Du hast bereits Hosen an.");
        } else {
            trousers = santaTrousers;
            System.out.println("Du ziehst die Hosen an.");
            System.out.println("Du fühlst dich direkt besser.");
        }
    }

    public void putOn(SantaCoat santaCoat) {
        if (coat != null) {
            System.out.println("Du hast bereits einen Mantel.");
        } else {
            coat = santaCoat;
            System.out.println("Du schlüpfst in den Mantel.");
        }
    }
}
