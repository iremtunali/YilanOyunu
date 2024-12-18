/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

/**
 *
 * @author Tunali
 */
// Yılanın hareketi ve oyunun döngüsünü yöneten thread sınıfı
class SnakeGameThread implements Runnable {
    private GameBoard gameBoard;

    public SnakeGameThread(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
    @Override
    public void run() {
    while (!gameBoard.isGameOver()) {
        gameBoard.updateSnake();
        gameBoard.updateAiSnake();  // AI yılanını da güncelle
        gameBoard.repaint();
        try {
            Thread.sleep(150); // Her hareket arasındaki süre
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Oyun bittiğinde skor gönder
    gameBoard.sendScoreToPHP();
}
}