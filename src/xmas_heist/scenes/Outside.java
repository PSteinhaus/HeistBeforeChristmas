package xmas_heist.scenes;

import xmas_heist.Bag;
import xmas_heist.GameState;
import xmas_heist.items.*;

public class Outside implements Scene {

    private boolean done = false;

    @Override
    public void showOptions(GameState currentState) {
        System.out.println("Ihr fliegt hoch über den Wolken durch die Winternacht. Über euch leuchten die Sterne.");
        if (currentState.getSanta().getBag().isEmpty()) {
            System.out.println("Dein Sack ist leer. Du hast kein einziges Geschenk für die Kinder...");
            System.out.println("Gib eine beliebige Eingabe ein, um einen neuen Versuch zu starten.");
        } else {
            System.out.println("In deinem Sack sind noch Geschenke. Was willst du tun?");
            System.out.println("1) eines der Geschenke auspacken");
            System.out.println("2) die Geschenke an die Kinder verteilen");
        }
    }

    @Override
    public boolean awaitAndHandleChoice(GameState currentState) {
        var santa = currentState.getSanta();
        boolean validInput = true;
        if (santa.getBag().isEmpty()) {
            String choice = currentState.getChoice();
            done = true;
        } else {
            String choice = currentState.getChoice();
            try {
                int choiceNum = Integer.parseInt(choice);
                switch (choiceNum) {
                    case 1 -> {
                        System.out.println("Du reißt eines der Geschenke auf.");
                        Item item = openPresent(santa.getBag());
                        System.out.println("Es ist "+ item.name() +".");
                        // use the item (if there is any use to it...)
                        item.use(currentState);
                    }
                    case 2 -> {
                        int presentCount = santa.getBag().getPresentCount();
                        System.out.println("Ihr fliegt weiter bis ihr auf eine kleine Stadt trefft.");
                        System.out.println("Dort verteilt ihr alle " + presentCount + " Geschenke an die Kinder.");
                        santa.getBag().removePresents(presentCount);
                        // increase the highscore
                        currentState.addPresentsShared(presentCount);
                        done = true;
                    }
                    default -> {
                        validInput = false;
                    }
                }
            } catch (NumberFormatException e) {
                validInput = false;
            }
            if (!validInput)
                System.out.println("Bitte gib eine der Nummern ein.");
        }
        return validInput;
    }

    @Override
    public Scene requestedTransition() {
        if (done) {
            System.out.println("Du brichst erneut zum Warenzentrum auf.");
            return new Warehouse();
        }
        return null;
    }

    private Item openPresent(Bag bag) {
        bag.removePresents(1);
        // there is a 20% chance that the present contains a fresh piece of santas outfit
        final double OUTFIT_CHANCE = 0.2;
        if (Math.random() < OUTFIT_CHANCE) {
            int outfitChoice = (int)(Math.random() * 3.0);
            switch (outfitChoice) {
                case 0 -> {
                    return new SantaHat();
                }
                case 1 -> {
                    return new SantaCoat();
                }
                case 2 -> {
                    return new SantaTrousers();
                }
                default -> {
                    // should never be reached
                    return null;
                }
            }
        } else {
            return new Toy();
        }
    }
}
