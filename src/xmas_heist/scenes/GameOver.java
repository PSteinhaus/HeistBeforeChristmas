package xmas_heist.scenes;

import xmas_heist.GameState;

public class GameOver implements Scene {

    @Override
    public void showOptions(GameState currentState) {
        System.out.println("Du wurdest geschnappt.");
        System.out.println("GAME OVER.");
        System.out.println("Geschenke verteilt: " + currentState.getPresentsShared());
    }

    @Override
    public boolean awaitAndHandleChoice(GameState currentState) {
        // just stop the game
        currentState.stopGame();
        return true;
    }

    @Override
    public Scene requestedTransition() {
        return null;
    }
}
