package xmas_heist.scenes;

import xmas_heist.Bag;
import xmas_heist.GameState;
import xmas_heist.encounters.Encounter;

/**
 * The Bmazon warehouse, showplace of most of the game.
 */
public class Warehouse implements Scene {
    /**
     * how deep inside the warehouse the player currently is
     */
    private int level = 0;
    private Encounter encounter = null;
    private SceneState ownState = SceneState.ALONE;

    @Override
    public void showOptions(GameState currentState) {
        switch (ownState) {
            case ALONE -> {
                System.out.println("Du scheinst allein zu sein - Was willst du tun?");
                System.out.println("1) Geschenke einpacken");
                System.out.println("2) tiefer hinein gehen");
                System.out.println("3) zurück gehen");
            }
            case NOT_ALONE -> {
                System.out.println("Da ist " + encounter.name() + ". Was tust du?");
                System.out.println("1) Geschenke einpacken");
                System.out.println("2) tiefer hinein gehen");
                System.out.println("3) zurück gehen");
                System.out.println("4) verstecken");
                System.out.println("5) zurück rennen");
            }
            case CHASED -> {
                System.out.println("Du wirst verfolgt. Was tust du?");
                System.out.println("1) Geschenke einpacken");
                System.out.println("2) tiefer hinein gehen");
                System.out.println("3) verstecken");
                System.out.println("4) zurück rennen");
            }
        }
    }

    @Override
    public boolean awaitAndHandleChoice(GameState currentState) {
        boolean successful = true;  // start with the bias that the operation will succeed and change it if something goes wrong
        String choice = currentState.getChoice();
        try {
            int choiceNum = Integer.parseInt(choice);
            switch (ownState) {
                case ALONE -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // der Spieler packt Geschenke ein
                            System.out.println("Du nimmst dir eines der Geschenke und lässt es in deinen Beutel wandern.");
                            collectPresents(currentState.getSanta().getBag());
                        }
                        case 2 -> {
                            // der Spieler geht tiefer hinein
                            System.out.println("Du gehst leise tiefer hinein.");
                            goDeeper();
                        }
                        case 3 -> {
                            // der Spieler geht zurück
                            System.out.println("Du gehst leise zurück.");
                            goBack();
                        }
                        default -> successful = false;
                    }
                    if (successful) {
                        // since you're alone roll for a new encounter
                        rollForEncounter();
                    }
                }
                case NOT_ALONE -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // packe Geschenke ein
                            System.out.println("Du ignorierst die Gesellschaft und lässt weiter Geschenke in deinen Beutel wandern.");
                            collectPresents(currentState.getSanta().getBag());
                        }
                        case 2 -> {
                            // gehe tiefer hinein
                            System.out.println("Du tust so als gehörst du dazu und gehst einfach weiter.");
                            goDeeper();
                        }
                        case 3 -> {
                            // gehe zurück
                            System.out.println("Du gehst betont unauffällig zurück.");
                            goBack();
                        }
                        case 4 -> {
                            // versteck dich
                            System.out.println("Du versuchst dich zu verstecken.");
                            hide();
                        }
                        case 5 -> {
                            // renne zurück
                            System.out.println("Du wendest dich dem Ausgang zu und sprintest los.");
                            runBack();
                        }
                        default -> successful = false;
                    }
                }
                case CHASED -> {
                    switch (choiceNum) {
                        case 1 -> {
                            // packe Geschenke ein
                            System.out.println("Du bleibst stehen und machst dich hektisch über die Geschenke her.");
                            collectPresents(currentState.getSanta().getBag());
                        }
                        case 2 -> {
                            // gehe tiefer hinein
                            System.out.println("Du lässt dich nicht beeindrucken und gehst noch tiefer hinein.");
                            goDeeper();
                        }
                        case 3 -> {
                            // versteck dich
                            System.out.println("Du biegst um eine Kurve und springst in das erstbeste Versteck.");
                            hide();
                        }
                        case 4 -> {
                            // renne zurück
                            System.out.println("Du rennst weiter Richtung Ausgang.");
                            runBack();
                        }
                        default -> successful = false;
                    }
                }
            }
        } catch (NumberFormatException ignored) {}
        if (!successful) // invalid input was given
            System.out.println("Bitte gib eine der Nummern ein.");
        return successful;
    }

    @Override
    public Scene requestedTransition() {
        return null;
    }

    private void collectPresents(Bag bag) {
        bag.add_present();
    }

    private void goDeeper() {
        ++level;
    }

    private void goBack() {
        --level;
    }

    private void runBack() {
        // TODO
    }

    private void hide() {
        // TODO
    }

    private void rollForEncounter() {

    }
}

enum SceneState {
    ALONE,
    NOT_ALONE,
    CHASED,
}