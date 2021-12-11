package xmas_heist.items;

import xmas_heist.GameState;

public interface Item {
    String name();

    void use(GameState state);
}
