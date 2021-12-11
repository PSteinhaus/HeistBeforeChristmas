package xmas_heist.encounters;

import xmas_heist.scenes.Warehouse;

public interface Encounter {
    String name();

    /**
     * called as a check, to see if this encounter starts chasing the player
     * @param sceneState
     * the warehouse is supplied, as the encounter might be influenced by the current state of the scene
     * @return
     * returns whether the encounter starts chasing the player
     */
    boolean startsChase(Warehouse sceneState);

    /**
     * called as a check, to see if this encounter catches the player
     * @param sceneState
     * the warehouse is supplied, as the encounter might be influenced by the current state of the scene
     * @return
     * returns whether the encounter catches the player
     */
    boolean catchesYou(Warehouse sceneState);
}
