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
        currentScene = new Warehouse();
        scanner = new Scanner(System.in);
        presentsShared = 0;
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
