import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Bloco {
        int x, y;

        Bloco(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final int largura, altura;
    private final int tamanho = 25;

    private Bloco cabeçaCobra;
    private ArrayList<Bloco> corpoCobra;
    private Bloco comida;
    private Random geradorAleatorio;

    private Timer temporizadorMovimento;
    private int velocidadeX, velocidadeY;
    private Color corCobra;

    SnakeGame(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.corCobra = Color.GREEN; 
        setPreferredSize(new Dimension(this.largura, this.altura));
        
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        inicializarJogo();
    }

    private void inicializarJogo() {
        cabeçaCobra = new Bloco(5, 5);
        corpoCobra = new ArrayList<>();
        comida = new Bloco(10, 10);
        geradorAleatorio = new Random();
        colocarComida();

        velocidadeX = 0;
        velocidadeY = 0;

        temporizadorMovimento = new Timer(120, this);
        temporizadorMovimento.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharJogo(g);
    }

    private void desenharJogo(Graphics g) {
        g.setColor(Color.RED);
        g.fill3DRect(comida.x * tamanho, comida.y * tamanho, tamanho, tamanho, true);

        g.setColor(corCobra);
        g.fill3DRect(cabeçaCobra.x * tamanho, cabeçaCobra.y * tamanho, tamanho, tamanho, true);

        for (Bloco parte : corpoCobra) {
            g.fill3DRect(parte.x * tamanho, parte.y * tamanho, tamanho, tamanho, true);
        }
    }

    private void colocarComida() {
        comida.x = geradorAleatorio.nextInt(largura / tamanho);
        comida.y = geradorAleatorio.nextInt(altura / tamanho);
    }

    private boolean colisao(Bloco bloco1, Bloco bloco2) {
        return bloco1.x == bloco2.x && bloco1.y == bloco2.y;
    }

    private void movimentarCobra() {
        if (colisao(cabeçaCobra, comida)) {
            corpoCobra.add(new Bloco(comida.x, comida.y));
            colocarComida();
        }

        for (int i = corpoCobra.size() - 1; i >= 0; i--) {
            Bloco parte = corpoCobra.get(i);
            if (i == 0) {
                parte.x = cabeçaCobra.x;
                parte.y = cabeçaCobra.y;
            } else {
                Bloco parteAnterior = corpoCobra.get(i - 1);
                parte.x = parteAnterior.x;
                parte.y = parteAnterior.y;
            }
        }

        cabeçaCobra.x += velocidadeX;
        cabeçaCobra.y += velocidadeY;

        if (cabeçaCobra.x * tamanho < 0 || cabeçaCobra.x * tamanho >= largura ||
            cabeçaCobra.y * tamanho < 0 || cabeçaCobra.y * tamanho >= altura) {
            temporizadorMovimento.stop();
        }

        for (Bloco parte : corpoCobra) {
            if (colisao(cabeçaCobra, parte)) {
                temporizadorMovimento.stop();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movimentarCobra();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (velocidadeY != 1) {
                    velocidadeX = 0;
                    velocidadeY = -1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (velocidadeY != -1) {
                    velocidadeX = 0;
                    velocidadeY = 1;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (velocidadeX != 1) {
                    velocidadeX = -1;
                    velocidadeY = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (velocidadeX != -1) {
                    velocidadeX = 1;
                    velocidadeY = 0;
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}