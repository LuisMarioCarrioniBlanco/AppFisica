
package Animations;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MRUAnimation extends JPanel implements ActionListener {
    
    private Timer timer;
    private int x = 50;            // Posición inicial en x
    private final int y = 150;     // Posición fija en y
    private final int velocity = 5; // Velocidad constante (píxeles por paso)
    private final int objectSize = 30;
    private final int delay = 50;  // Milisegundos entre actualizaciones
    private boolean running = false;
    private int time = 0;
    private int initialPosition = 50;
    
    public MRUAnimation() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 300));
        timer = new Timer(delay, this);
        
        // Panel de control para la animación
        JPanel controls = new JPanel();
        JButton startButton = new JButton("Iniciar");
        JButton resetButton = new JButton("Reiniciar");
        
        startButton.addActionListener(e -> {
            if (!running) {
                timer.start();
                running = true;
                startButton.setText("Pausar");
            } else {
                timer.stop();
                running = false;
                startButton.setText("Continuar");
            }
        });
        
        resetButton.addActionListener(e -> {
            timer.stop();
            running = false;
            x = initialPosition;
            time = 0;
            startButton.setText("Iniciar");
            repaint();
        });
        
        controls.add(startButton);
        controls.add(resetButton);
        
        setLayout(new BorderLayout());
        add(controls, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        x += velocity;
        time += delay;
        
        // Si el objeto sale de la pantalla, reiniciamos
        if (x > getWidth()) {
            x = -objectSize;
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Mejorar la calidad del renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar la línea base (suelo)
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, y + objectSize/2, getWidth(), y + objectSize/2);
        
        // Marcar posiciones cada 50 píxeles
        for (int i = 0; i <= getWidth(); i += 50) {
            g2d.drawLine(i, y + objectSize/2 - 5, i, y + objectSize/2 + 5);
            g2d.drawString(Integer.toString(i), i - 5, y + objectSize/2 + 20);
        }
        
        // Dibujar el objeto
        g2d.setColor(Color.BLUE);
        g2d.fillOval(x, y, objectSize, objectSize);
        
        // Mostrar información del movimiento
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tiempo: " + (time / 1000.0) + " s", 20, 30);
        g2d.drawString("Posición: " + x + " px", 20, 50);
        g2d.drawString("Velocidad: " + velocity + " px/paso", 20, 70);
        
        // Ecuación de MRU: x = x₀ + v·t
        g2d.drawString("x = x₀ + v·t", getWidth() - 150, 30);
        g2d.drawString("x = " + initialPosition + " + " + velocity + "·" + (time / 1000.0), getWidth() - 150, 50);
    }
    
    // Método para usar desde fuera
    public JPanel getAnimationPanel() {
        return this;
    }
}
