package xmas_heist.scenes;

import xmas_heist.GameState;

public class Outside implements Scene {
    @Override
    public void showOptions(GameState currentState) {

    }

    @Override
    public boolean awaitAndHandleChoice(GameState currentState) {
        return false;
    }

    @Override
    public Scene requestedTransition() {
        return null;
    }
}
