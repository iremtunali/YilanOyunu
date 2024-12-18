/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;  // "yilanoyunu" adlı paketin bir parçası olan sınıf tanımlanıyor.

import java.awt.Color;  // "Color" sınıfı, bileşenlerin arka plan veya metin rengini ayarlamak için kullanılır.

/**
 *
 * @author Tunali
 */


class DayNightCycle implements Runnable {  
    

    private GameBoard gameBoard;  
    private boolean isDay;  

    
    public DayNightCycle(GameBoard gameBoard) {
        this.gameBoard = gameBoard;  
        this.isDay = true;  
    }

    
    @Override
    public void run() {
        
        while (true) {
            if (isDay) {  
                gameBoard.setBackgroundColor(Color.CYAN);  
                isDay = false;  
            } else {  
                gameBoard.setBackgroundColor(Color.DARK_GRAY);  
                isDay = true;  
            }

            
            gameBoard.repaint();  

            try {
                
                Thread.sleep(15000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }
        }
    }
}
