package xmas_heist.scenes;

import xmas_heist.Bag;
import xmas_heist.GameState;
import xmas_heist.SantaClaus;
import xmas_heist.encounters.Aide;
import xmas_heist.encounters.Encounter;
import xmas_heist.encounters.Security;

import java.util.ArrayList;

/**
 * The Bmazon warehouse, showplace of most of the game.
 */
public class Warehouse implements Scene {
    /**
     * how deep inside the warehouse the player currently is
     */
    private int level = 0;
    private final ArrayList<Integer> presentsFound = new ArrayList<>();
    private final ArrayList<Integer> presentsAvailable = new ArrayList<>();
    private Encounter encounter = null;
    private SceneState ownState = SceneState.ALONE;
    private boolean running = false;
    private boolean firstRound = true;

    public boolean getRunning() { return running; }

    public Warehouse() {
        presentsFound.add(0);   // 0 presents found in the first level at the start
        presentsAvailable.add(0);    // also, there will never be any presents in the first level
    }

    @Override
    public void showOptions(GameState currentState) {
        if (firstRound) {
            System.out.println("Du fliegst zum Bmazon-Warenzentrum, parkst den Schlitten hinter einem Eisberg, nimmst dir eine Zuckerstange und brichst damit eine Tür auf.");
            firstRound = false;
        }

        switch (ownState) {
            case ALONE -> {
                System.out.println("Du scheinst allein zu sein - Was willst du tun?");
                System.out.println("1) Geschenke suchen");
                System.out.println("2) tiefer hinein gehen");
                System.out.println("3) zurück gehen");
                if (canCollectPresents())
                    System.out.println("4) Geschenke einsammeln");
            }
            case NOT_ALONE -> {
                System.out.println("Da ist " + encounter.name() + ". Was tust du?");
                System.out.println("1) Geschenke suchen");
                System.out.println("2) tiefer hinein gehen");
                System.out.println("3) zurück rennen");
                if (canCollectPresents())
                    System.out.println("4) Geschenke einsammeln");
            }
            case CHASED -> {
                System.out.println("Du wirst verfolgt. Was tust du?");
                System.out.println("1) Geschenke suchen");
                System.out.println("2) tiefer hinein laufen");
                System.out.println("3) zurück rennen");
                if (canCollectPresents())
                    System.out.println("4) Geschenke einsammeln");
            }
        }
    }

    private boolean canCollectPresents() { return presentsFound.get(level) > 0; }

    @Override
    public boolean awaitAndHandleChoice(GameState currentState) {
        boolean validInput = true;  // start with the bias that the operation will succeed and change it if something goes wrong
        String choice = currentState.getChoice();
        running = false;
        try {
            int choiceNum = Integer.parseInt(choice);
            switch (ownState) {
                case ALONE -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // suche Geschenke
                            System.out.println("Du suchst den Raum nach Geschenken ab.");
                            searchForPresents();
                            // while you're here someone could come in, so roll for an encounter
                            rollForEncounter();
                        }
                        case 2 -> {
                            // gehe tiefer hinein
                            System.out.println("Du gehst leise tiefer hinein.");
                            goDeeper();
                        }
                        case 3 -> {
                            // der Spieler geht zurück
                            System.out.println("Du gehst leise zurück.");
                            goBack();
                        }
                        case 4 -> {
                            if (canCollectPresents()) {
                                // sammle Geschenke
                                System.out.println("Du bedienst dich bei den Geschenken.");
                                collectPresents(currentState.getSanta().getBag());
                                // while you're here someone could come in, so roll for an encounter
                                rollForEncounter();
                            } else {
                                validInput = false;
                            }
                        }
                        default -> validInput = false;
                    }
                }
                case NOT_ALONE -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // suche Geschenke
                            System.out.println("Du schaust dich, betont unauffällig, nach weiteren Geschenken um.");
                            searchForPresents();
                            rollForBeginOfChase();
                        }
                        case 2 -> {
                            // gehe tiefer hinein
                            System.out.println("Du tust so als gehörst du dazu und gehst einfach weiter.");
                            goDeeper();
                        }
                        case 3 -> {
                            // renne zurück
                            System.out.println("Du wendest dich dem Ausgang zu und sprintest los.");
                            goBack();
                            running = true;
                        }
                        case 4 -> {
                            if (canCollectPresents()) {
                                // sammle Geschenke
                                System.out.println("Du ignorierst die Gesellschaft und lässt munter Geschenke in deinen Beutel wandern.");
                                collectPresents(currentState.getSanta().getBag());
                                rollForBeginOfChase();
                            } else {
                                validInput = false;
                            }
                        }
                        default -> validInput = false;
                    }
                }
                case CHASED -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // suche Geschenke
                            System.out.println("Du lässt dich nicht stören und suchst nach weiteren Geschenken.");
                            searchForPresents();
                        }
                        case 2 -> {
                            // renne tiefer hinein
                            System.out.println("Du läufst einfach weiter in den nächsten Raum.");
                            goDeeper();
                            running = true;
                        }
                        case 3 -> {
                            // renne zurück
                            System.out.println("Du rennst Richtung Ausgang.");
                            goBack();
                            running = true;
                        }
                        case 4 -> {
                            if (canCollectPresents()) {
                                // sammle Geschenke
                                System.out.println("Du bleibst stehen und machst dich hektisch über die Geschenke her.");
                                collectPresents(currentState.getSanta().getBag());
                            } else {
                                validInput = false;
                            }
                        }
                        default -> validInput = false;
                    }
                    if (validInput && ownState == SceneState.CHASED) {
                        // roll the dice to see if you are caught
                        rollForBeingCaught(currentState.getSanta());
                    }
                }
            }
        } catch (NumberFormatException ignored) {}

        if (!validInput) // invalid input was given
            System.out.println("Bitte gib eine der Nummern ein.");

        return validInput;
    }

    @Override
    public Scene requestedTransition() {
        switch (ownState) {
            case CAUGHT -> {
                return new GameOver();
            }
            case ESCAPED -> {
                System.out.println("Du hast es raus geschafft!");
                System.out.println("Dein Fluchtschlitten wartet auf dich.");
                System.out.println("Du springst auf und die Rentiere erheben sich in den Himmel");
                return new Outside();
            }
            default -> {
                return null;
            }
        }
    }

    private void searchForPresents() {
        // find a random percentage of the available, but not found yet, presents in this level
        int foundPresents = presentsFound.get(level);
        int findablePresents = presentsAvailable.get(level) - foundPresents;
        double foundPercentage = Math.random() * 0.6; // find at most 60% of the presents at once
        int newlyFoundPresents = (int)Math.round(foundPercentage * findablePresents);
        // update the amount of presentsFound
        presentsFound.set(level, foundPresents + newlyFoundPresents);
        System.out.println("Du findest "+ newlyFoundPresents +" weitere Geschenke.");
    }

    private void collectPresents(Bag bag) {
        int foundPresents = presentsFound.get(level);
        if (foundPresents > 0) {
            int presentsGrabbed;
            do {
                presentsGrabbed = (int)(1.0 + Math.random() * 3.0);
            } while (presentsGrabbed > foundPresents);

            bag.addPresents(presentsGrabbed);
            presentsFound.set(level, foundPresents - presentsGrabbed);
            int availableBefore = presentsAvailable.get(level);
            presentsAvailable.set(level, availableBefore - presentsGrabbed);
            System.out.println("Du packst " + presentsGrabbed + " Geschenke in deinen Sack.");
        }
    }

    private void goDeeper() {
        if (++level >= presentsFound.size()) {
            presentsFound.add(0); // make space for another level where 0 presents have been found yet

            // generate a random number of presents that can be found in this level
            // let the mean number of available presents rise polynomially with the following exponent:
            final double PRESENT_GROWTH_RATE = 1.3;
            int availablePresents = (int)(Math.random() * Math.pow(level * 2.0, PRESENT_GROWTH_RATE));
            presentsAvailable.add(availablePresents);
        }
        if (ownState == SceneState.NOT_ALONE) {
            // check if that person notices you leaving
            rollForBeginOfChase();
        }
        if (ownState != SceneState.CHASED)
            rollForEncounter(); // you switched levels so roll for a new encounter
    }

    private void goBack() {
        --level;
        if (ownState == SceneState.NOT_ALONE) {
            // check if that person notices you leaving
            rollForBeginOfChase();
        }
        if (ownState != SceneState.CHASED)
            rollForEncounter(); // you switched levels so roll for a new encounter
        if (level < 0) {
            // you reached the outside
            ownState = SceneState.ESCAPED;
        }
    }

    /**
     * Roll the dice to see if someone is on your level.
     * Has to be called after a turn spent alone in a level, as well as when entering another level.
     */
    private void rollForEncounter() {
        final double ENCOUNTER_CHANCE = 0.25;
        if (Math.random() < ENCOUNTER_CHANCE) {
            // decide which kind of encounter
            double encounterRoll = Math.random();
            if (encounterRoll < 0.25) {
                encounter = new Security();
            } else {
                encounter = new Aide();
            }
            ownState = SceneState.NOT_ALONE;
        } else {
            encounter = null;
            ownState = SceneState.ALONE;
        }
    }

    /**
     * Roll the dice to see if the person starts chasing you.
     * Has to be called if there is someone in your level after your turn, or before you leave the level.
     */
    private void rollForBeginOfChase() {
        if (encounter.startsChase(this)) {
            ownState = SceneState.CHASED;
            System.out.println("Die Person wird auf dich aufmerksam und kommt auf dich zu.");
        }
    }

    /**
     * Roll the dice to see if you're caught.
     * This has to be called whenever you're being chased and haven't escaped yet after your turn.
     * @param santa
     * Santa Claus, the player entity
     */
    private void rollForBeingCaught(SantaClaus santa) {
        System.out.println("Dein Verfolger versucht dich zu schnappen.");
        if (encounter.catchesYou(this)) {
            // if you're caught you can still escape if you can lose another part of your costume
            var lost = santa.loseOutfit();
            if (lost != null) {
                // you lost part of your outfit but you haven't been catched
                System.out.println("Du wirst gepackt, kannst dich aber losreißen.");
                System.out.println("Doch dabei zerreißt " + lost.name() + ".");
            } else {
                // you lost your whole costume and have been catched now
                System.out.println("Du wirst gepackt und fällst.");
                ownState = SceneState.CAUGHT;
            }
        } else {
            System.out.println("Du weichst ihm knapp aus.");
        }
    }
}

/**
 * a small enumeration to differentiate between the different states that this scene can be in
 */
enum SceneState {
    ALONE,
    NOT_ALONE,
    CHASED,
    CAUGHT,
    ESCAPED
}