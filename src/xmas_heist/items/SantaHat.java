package xmas_heist.items;

import xmas_heist.GameState;

public class SantaHat implements Item {
    @Override
    public String name() {
        return "eine Weihnachtsmannmütze";
    }

    @Override
    public void use(GameState state) {
        state.getSanta().putOn(this);
    }
}
