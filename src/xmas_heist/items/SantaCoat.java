package xmas_heist.items;

import xmas_heist.GameState;

public class SantaCoat extends SantaOutfit {

    @Override
    public String name() {
        return "ein roter Mantel";
    }

    @Override
    public void use(GameState state) {
        state.getSanta().putOn(this);
    }
}
