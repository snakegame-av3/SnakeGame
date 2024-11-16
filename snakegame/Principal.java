import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        int largura = 700;
        int altura = largura;

        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SnakeGame snakeGame = new SnakeGame(largura, altura);
        frame.add(snakeGame);

        frame.pack();
        frame.setVisible(true);
        snakeGame.requestFocus();
    }
}