package org.test;

/**
 * Player class is simulating chess player which has a unique name,
 * and internally store fields to control game.
 * It also stores corresponding player object inside otherPlayer.
 */
public class Player implements Runnable {

    /**
     * Unique player name
     */
    private String name;

    /**
     * Corresponding player object
     */
    private Player otherPlayer;

    /**
     * Flag indicates if this player can make move
     */
    private volatile boolean canPlay = false;

    /**
     * Total count of moves made
     */
    private int turnCount = 1;

    /**
     * Multithreaded lock object to establish synchronisation
     */
    private Object lock;

    public Player(String name, Object lock) {
        this.name = name;
        this.lock = lock;
    }

    @Override
    public void run() {

        while(turnCount <= Game.TURN_COUNT_MAX ) {
            synchronized (lock) {
                try {
                    while (!canPlay)
                        lock.wait();

                    System.out.println(String.format("Player: %s: Message: %d", name, turnCount));

                    this.canPlay = false;
                    otherPlayer.canPlay = true;
                    lock.notifyAll();

                    turnCount++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }
}
