package xmas_heist.items;

import xmas_heist.GameState;

public class Toy implements Item {
    @Override
    public String name() {
        return "ein Spielzeug";
    }

    @Override
    public void use(GameState state) {
        // not useable; do nothing
    }
}
