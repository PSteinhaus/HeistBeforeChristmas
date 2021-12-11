package xmas_heist.scenes;

import xmas_heist.GameState;

public interface Scene {
    /**
     * prints the options currently available to the player onto the screen
     * @param currentState
     * the game state that this next turn is based on
     */
    void showOptions(GameState currentState);

    /**
     * This function waits for the player to supply an input encoding a choice.
     * As soon as an input is given it checks whether the input is valid.
     *
     * If it isn't, then 'false' is returned (and before that, hopefully, some kind of "invalid choice" message is printed)
     * If it is, then the choice is put into action, possibly altering the game state (and most likely the Scene itself)
     * and 'true' is returned.
     *
     * @param currentState
     * the game state that this next turn is based on
     * @return
     * returns whether the given input actually encoded a valid input
     */
    boolean awaitAndHandleChoice(GameState currentState);

    /**
     * Sometimes the game needs to transition into another scene (for example when escaping the warehouse, entering the outside).
     * In these situations this function returns the scene to transition into. It's meant to be called and checked at the end
     * of the game loop.
     * @return
     * returns the scene to transition into, or 'null' if there is no need for a transition
     */
    Scene requestedTransition();
}
