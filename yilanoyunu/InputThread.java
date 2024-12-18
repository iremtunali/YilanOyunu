/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Tunali
 */
// Klavye girdilerini dinleyen thread
class InputThread implements Runnable {
    private GameBoard gameBoard;

    public InputThread(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void run() {
        gameBoard.setFocusable(true);
        gameBoard.requestFocusInWindow();
        gameBoard.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        gameBoard.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        gameBoard.setDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        gameBoard.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        gameBoard.setDirection(Direction.RIGHT);
                        break;
                }
            }
        });
    }
}