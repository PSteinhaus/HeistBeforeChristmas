package xmas_heist.encounters;

import xmas_heist.scenes.Warehouse;

public class Security implements Encounter {
    @Override
    public String name() {
        return "jemand vom Sicherheitspersonal";
    }

    @Override
    public boolean startsChase(Warehouse sceneState) {
        boolean playerIsRunning = sceneState.getRunning();
        // security staff is way more likely to start chasing you
        final double BASE_CHANCE = 0.4;
        // here that chance increases even more when you start running
        double chase_chance = playerIsRunning ? BASE_CHANCE * 2.0 : BASE_CHANCE;
        return Math.random() < chase_chance;
    }

    @Override
    public boolean catchesYou(Warehouse sceneState) {
        final double CATCH_CHANCE = 0.75f;
        double chance = sceneState.getRunning() ? CATCH_CHANCE / 3.0 : CATCH_CHANCE;
        return Math.random() < chance;
    }
}
