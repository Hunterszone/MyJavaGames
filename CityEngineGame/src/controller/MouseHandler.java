/*
 * this class is designed to handle user mouse input while focus is on the
 * frame.
 *
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import city.cs.engine.World;
import city.cs.engine.WorldView;
import shapes.Player;

/**
 * class designed to handle mouse input
 */
public class MouseHandler extends MouseAdapter {

    /**
     * instance of player, with which to shoot a shot
     */
    private final Player player;
    /**
     * the view that is currently being used by the game
     */
    private final WorldView view;
    /**
     * the current world that is being run
     */
    private final World world;

    /**
     * initialize player, view and world objects
     *
     * @param view
     * @param world
     * @param player
     */
    public MouseHandler(WorldView view, World world, Player player) {
        this.view = view;
        this.player = player;
        this.world = world;

    }

    /**
     * listener that listens for mouse clicks, and shoots a player shot upon
     * each click
     *
     * @param e event object containing the mouse position
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //all of the projectile related code is worked out in the player class
        if (world.isRunning() && player.getShots() > 0) {
            player.shootProjectile(view.viewToWorld(e.getPoint()));

        }
    }

}
