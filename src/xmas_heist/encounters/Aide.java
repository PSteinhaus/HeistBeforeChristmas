package xmas_heist.encounters;

import xmas_heist.scenes.Warehouse;

public class Aide implements Encounter {
    @Override
    public String name() {
        return "eine unterbezahlte Hilfskraft";
    }

    @Override
    public boolean startsChase(Warehouse sceneState) {
        boolean playerIsRunning = sceneState.getRunning();
        // an aide is not very motivated to chase weird old men around
        final double BASE_CHANCE = 0.2;
        // an aide is even less motivated to catch some running weird old men
        double chase_chance = playerIsRunning ? BASE_CHANCE / 2.0 : BASE_CHANCE;
        return Math.random() < chase_chance;
    }

    @Override
    public boolean catchesYou(Warehouse sceneState) {
        final double CATCH_CHANCE = 0.5f;
        double chance = sceneState.getRunning() ? CATCH_CHANCE / 3.0 : CATCH_CHANCE;
        return Math.random() < chance;
    }
}
