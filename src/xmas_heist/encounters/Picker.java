package xmas_heist.encounters;

import xmas_heist.scenes.Warehouse;

public class Picker implements Encounter {
    @Override
    public String name() {
        return "ein Picker";
    }

    @Override
    public boolean startsChase(Warehouse sceneState) {
        boolean playerIsRunning = sceneState.getRunning();
        // a picker is not very motivated to chase weird old men around either
        final double BASE_CHANCE = 0.25;
        // a suspicious old man running around his/her domain with a sack full of presents might make him/her suspicious though
        double chase_chance = playerIsRunning ? BASE_CHANCE * 1.5 : BASE_CHANCE;
        return Math.random() < chase_chance;
    }

    @Override
    public boolean catchesYou(Warehouse sceneState) {
        final double CATCH_CHANCE = 0.5f;
        double chance = sceneState.getRunning() ? CATCH_CHANCE / 3.0 : CATCH_CHANCE;
        return Math.random() < chance;
    }
}
