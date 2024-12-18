/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yilanoyunu;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Tunali
 */
public class ScoreSaver {
    public static void saveScore(String playerName, int score) {
        try {
            // PHP Endpoint'inin URL'si
            URL url = new URL("http://localhost/snakegame/save_score.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // POST isteği ayarları
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Gönderilecek veriyi hazırlama
            String data = "player_name=" + playerName + "&score=" + score;

            // Veriyi gönderme
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            // Sunucudan yanıt al
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Skor başarıyla kaydedildi.");
            } else {
                System.out.println("Skor kaydı başarısız oldu. Kod: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
