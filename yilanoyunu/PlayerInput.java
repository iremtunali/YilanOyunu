/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

import javax.swing.JOptionPane;

/**
 *
 * @author Tunali
 */
public class PlayerInput {
    public static String getPlayerName() {
        return JOptionPane.showInputDialog("Lütfen oyuncu adınızı giriniz:");
    }
}
