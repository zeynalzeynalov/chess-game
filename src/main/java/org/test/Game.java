package org.test;

/**
 * Main Game class where Players can be created and game started.
 */
public class Game {

    public static int TURN_COUNT_MAX = 10;

    public static void main( String[] args )
    {
        Object lock = new Object();

        Player player1 = new Player("Initiator", lock);
        Player player2 = new Player("Second", lock);

        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

        System.out.println("Chess game started:");

        player1.setCanPlay(true);

        Thread thread1 = new Thread(player1);
        thread1.start();

        Thread thread2 = new Thread(player2);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Chess game finished!");
    }
}
