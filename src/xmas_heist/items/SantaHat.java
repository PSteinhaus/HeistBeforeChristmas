package xmas_heist.items;

import xmas_heist.GameState;

public class SantaHat extends SantaOutfit {
    @Override
    public String name() {
        return "eine Weihnachtsmannmütze";
    }

    @Override
    public void use(GameState state) {
        state.getSanta().putOn(this);
    }
}
