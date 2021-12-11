package xmas_heist;

import xmas_heist.scenes.Scene;
import xmas_heist.scenes.Warehouse;

import java.util.Scanner;

public class GameState {
    private SantaClaus santa;
    private boolean gameRunning;
    private Scene currentScene;
    private Scanner scanner;
    private int presentsShared;

    public GameState() {
        santa = new SantaClaus();
        gameRunning = true;
        /**
         * the scene that the player makes decisions in;
         * can currently be 'Warehouse', 'Outside' or 'GameOver'
         */
        currentScene = new Warehouse();
        scanner = new Scanner(System.in);
        /**
         * the highscore of the game
         */
        presentsShared = 0;
    }

    public void run() {
        // the game loop
        while (gameRunning) {
            // start by showing the player all his options
            currentScene.showOptions(this);

            // prompt him until he gives a valid input
            boolean acceptedChoice;
            do {
                acceptedChoice = currentScene.awaitAndHandleChoice(this);
            } while (!acceptedChoice);

            // and finally check if you have to change the scene (for example when santa escapes the warehouse)
            Scene sceneToSwitchTo = currentScene.requestedTransition();
            if (sceneToSwitchTo != null)
                currentScene = sceneToSwitchTo;

            System.out.println(); // print an empty line to separate turns
        }
    }

    public SantaClaus getSanta() { return santa; }
    public String getChoice() { return scanner.next(); }

    /**
     * stops the game by setting the game loop flag to false
     */
    public void stopGame() { gameRunning = false; }

    public void addPresentsShared(int presentCount) {
        presentsShared += presentCount;
    }

    public int getPresentsShared() { return presentsShared; }
}
