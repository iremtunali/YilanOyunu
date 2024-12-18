package yilanoyunu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

class GameBoard extends JPanel {
    private final int BOARD_WIDTH = 400;
    private final int BOARD_HEIGHT = 400;
    private final int UNIT_SIZE = 20; 
    private LinkedList<Point> snake;  
    private LinkedList<Point> aiSnake;
    private Point food;               
    private Direction direction;      
    private Direction aiDirection;    
    private boolean gameOver;         
    private boolean gameStarted;      
    private int score;                
    private int lives;  
    private int aiLives = 3;  // AI yılanı için can ekleyelim
    private Color backgroundColor;    
    private boolean aiGameOver;   
    private String username; // Kullanıcı adı

    public GameBoard() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.BLACK);
        this.snake = new LinkedList<>();
        this.aiSnake = new LinkedList<>();
        this.direction = Direction.RIGHT;
        this.aiDirection = Direction.RIGHT;
        this.gameOver = false;
        this.aiGameOver = false;
        this.gameStarted = false;  
        this.score = 0;
        this.lives = 3;  
        this.backgroundColor = Color.BLACK;

        snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
        aiSnake.add(new Point(BOARD_WIDTH / 4, BOARD_HEIGHT / 4));

        spawnFood();
        
        new Thread(new MusicPlayer("C:\\Users\\Tunali\\Desktop\\ses\\chill-drum-loop-6887.wav")).start();
        new Thread(new DayNightCycle(this)).start();
        
        JButton startButton = new JButton("Başlat");
        startButton.setBounds(BOARD_WIDTH / 2 - 50, BOARD_HEIGHT / 2, 100, 50);
        startButton.setFocusable(false);  
        startButton.setFont(new Font("Arial", Font.BOLD, 20));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                startButton.setVisible(false);
                requestFocus();
            }
        });

        this.setLayout(null);
        this.add(startButton);  
    }

    public void spawnFood() {
        Random rand = new Random();
        int x = rand.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = rand.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        food = new Point(x, y);
    }

    public void updateSnake() {
        if (!gameStarted || gameOver) return;

        Point head = snake.getFirst();
        Point newHead = new Point(head);


        switch (direction) {
            case UP: newHead.y -= UNIT_SIZE; break;
            case DOWN: newHead.y += UNIT_SIZE; break;
            case LEFT: newHead.x -= UNIT_SIZE; break;
            case RIGHT: newHead.x += UNIT_SIZE; break;
        }

        if (newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT || snake.contains(newHead)) {
            lives--; 
            if (lives == 0) gameOver = true;
            return;
        }

        if (newHead.equals(food)) {
            snake.addFirst(newHead);
            spawnFood();
            score += 10;  
            new Thread(new SoundEffectPlayer("C:\\Users\\Tunali\\Desktop\\ses\\jump-up-245782.wav")).start();
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }
    }

    // AI yılanını güncelleyen method
public void updateAiSnake() {
    if (!gameStarted || gameOver || aiGameOver) return;  // AI oyunu bitmişse ya da oyun bitmişse hareket etme

    Point aiHead = aiSnake.getFirst();
    Point newAiHead = new Point(aiHead);

    // AI yılanının yönünü basit bir şekilde yiyeceğe göre belirleyelim
    if (aiHead.x < food.x) {
        newAiHead.x += UNIT_SIZE;
    } else if (aiHead.x > food.x) {
        newAiHead.x -= UNIT_SIZE;
    } else if (aiHead.y < food.y) {
        newAiHead.y += UNIT_SIZE;
    } else if (aiHead.y > food.y) {
        newAiHead.y -= UNIT_SIZE;
    }

    // AI yılanı yiyeceği yediyse büyüsün
    if (newAiHead.equals(food)) {
        aiSnake.addFirst(newAiHead);
        spawnFood();  // Yeni yiyecek üret
    } else {
        // AI yılanını ilerlet
        aiSnake.addFirst(newAiHead);
        aiSnake.removeLast();
    }
}
    public void setDirection(Direction direction) {
        if ((this.direction == Direction.UP && direction == Direction.DOWN) ||
            (this.direction == Direction.DOWN && direction == Direction.UP) ||
            (this.direction == Direction.LEFT && direction == Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && direction == Direction.LEFT)) {
            return;
        }
        this.direction = direction;
    }

    public void startGame() {
        this.gameStarted = true;
        this.gameOver = false;
        this.aiGameOver = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor); 

        if (!gameStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Yılan Oyunu", BOARD_WIDTH / 4, BOARD_HEIGHT / 3);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Başlamak için butona basın", BOARD_WIDTH / 5, BOARD_HEIGHT / 10);
        } else if (!gameOver && !aiGameOver) {
            g.setColor(Color.GREEN);
            for (Point p : snake) g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.BLUE);
            for (Point p : aiSnake) g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.RED);
            g.fillRect(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Lives: " + lives, 10, 40);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", BOARD_WIDTH / 4, BOARD_HEIGHT / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Final Score: " + score, BOARD_WIDTH / 3, BOARD_HEIGHT / 2 + 40);
        }
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    
    // GameBoard sınıfına eklemeniz gereken isGameOver metodu
    public boolean isGameOver() {
        // Hem oyuncunun hem de AI'ın oyun durumunu kontrol edelim
        return gameOver || aiGameOver;
    }
    public void setUsername(String username) {
    this.username = username;
}

public String getUsername() {
    return username;
}
public void sendScoreToPHP() {
    try {
        URL url = new URL("http://localhost/score_submission.php"); // PHP'nin URL'si
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "username=" + username + "&score=" + score;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes());
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Skor başarıyla gönderildi.");
        } else {
            System.out.println("Hata: Skor gönderilemedi. Kod: " + responseCode);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
