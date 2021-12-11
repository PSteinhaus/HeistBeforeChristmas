package xmas_heist;

import xmas_heist.scenes.Scene;
import xmas_heist.scenes.Warehouse;

import java.util.Scanner;

public class GameState {
    private SantaClaus santa;
    private boolean gameRunning;
    private Scene currentScene;
    private Scanner scanner;

    public GameState() {
        santa = new SantaClaus();
        gameRunning = true;
        currentScene = new Warehouse();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (gameRunning) {
            currentScene.showOptions(this);
            boolean acceptedChoice;
            do {
                acceptedChoice = currentScene.awaitAndHandleChoice(this);
            } while (!acceptedChoice);

            Scene sceneToSwitchTo = currentScene.requestedTransition();
            if (sceneToSwitchTo != null)
                currentScene = sceneToSwitchTo;
        }
    }

    public SantaClaus getSanta() { return santa; }
    public String getChoice() { return scanner.next(); }

    /**
     * stops the game by setting the game loop flag to false
     */
    public void stopGame() { gameRunning = false; }
}
