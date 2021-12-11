package xmas_heist.items;

import xmas_heist.GameState;

public class SantaTrousers extends SantaOutfit {
    @Override
    public String name() {
        return "eine flauschige rote Hose";
    }

    @Override
    public void use(GameState state) {
        state.getSanta().putOn(this);
    }
}
