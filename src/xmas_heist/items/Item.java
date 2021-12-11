package xmas_heist.items;

import xmas_heist.GameState;

/**
 * Items are what can be found inside of presents. Most are useless, but some can be used.
 */
public interface Item {
    String name();

    /**
     * Items may be used for various effects (or none at all)
     * @param state
     * the game state to operate on
     */
    void use(GameState state);
}
