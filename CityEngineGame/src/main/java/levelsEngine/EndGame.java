/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levelsEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class EndGame {

    private static ArrayList<EndGameListener> _listeners;

    public EndGame() {
        _listeners = new ArrayList<>();
    }

    public synchronized void addEndGameListener(EndGameListener listener) {
        _listeners.add(listener);
    }

    public synchronized void removeEndGameListener(EndGameListener listener) {
        _listeners.remove(listener);
    }

    public synchronized void endGame() {
        EndGameEvent event = new EndGameEvent(this);
        Iterator listeners = _listeners.iterator();
        while (listeners.hasNext()) {
            ((EndGameListener) listeners.next()).endGame(event);
        }
    }

}
