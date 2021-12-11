package xmas_heist.items;

import xmas_heist.GameState;

public class SantaCoat implements Item {

    @Override
    public String name() {
        return "ein roter Mantel";
    }

    @Override
    public void use(GameState state) {
        state.getSanta().putOn(this);
    }
}
