/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tunali
 */
public class SnakeGameApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Yılan Oyunu");
        GameBoard gameBoard = new GameBoard();
        frame.add(gameBoard);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Kullanıcıdan oyun başlamadan önce isim alın
        String username = JOptionPane.showInputDialog(frame, "Kullanıcı adınızı girin:");
        gameBoard.setUsername(username);
        
        
        // Yılanı hareket ettiren thread
        Thread gameThread = new Thread(new SnakeGameThread(gameBoard), "GameThread");

        // Kullanıcı girdilerini dinleyen thread
        Thread inputThread = new Thread(new InputThread(gameBoard), "InputThread");
        
        // Thread'leri başlat
        gameThread.start();
        inputThread.start();
        
    }
}