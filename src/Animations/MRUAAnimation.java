
package Animations;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MRUAAnimation extends JPanel implements ActionListener {
    
    private Timer timer;
    private int x = 50;                  // Posición inicial en x
    private final int y = 150;           // Posición fija en y
    private double velocity = 0;         // Velocidad inicial (píxeles por segundo)
    private double acceleration = 2;     // Aceleración (píxeles por segundo²)
    private final int objectSize = 30;
    private final int delay = 50;        // Milisegundos entre actualizaciones
    private boolean running = false;
    private int time = 0;
    private int initialPosition = 50;
    private JSlider accelerationSlider;
    private JLabel accelerationLabel;
    
    public MRUAAnimation() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800, 300));
        timer = new Timer(delay, this);
        
        // Panel de control para la animación
        JPanel controls = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel();
        JPanel sliderPanel = new JPanel();
        
        JButton startButton = new JButton("Iniciar");
        JButton resetButton = new JButton("Reiniciar");
        
        accelerationSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);
        accelerationSlider.setMajorTickSpacing(2);
        accelerationSlider.setMinorTickSpacing(1);
        accelerationSlider.setPaintTicks(true);
        accelerationSlider.setPaintLabels(true);
        
        accelerationLabel = new JLabel("Aceleración: 2.0 px/s²");
        
        accelerationSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                acceleration = accelerationSlider.getValue();
                accelerationLabel.setText("Aceleración: " + acceleration + " px/s²");
                if (!running) {
                    repaint();
                }
            }
        });
        
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
            velocity = 0;
            time = 0;
            startButton.setText("Iniciar");
            repaint();
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        
        sliderPanel.add(new JLabel("Aceleración:"));
        sliderPanel.add(accelerationSlider);
        sliderPanel.add(accelerationLabel);
        
        controls.add(buttonPanel);
        controls.add(sliderPanel);
        
        setLayout(new BorderLayout());
        add(controls, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Convertir delay a segundos para los cálculos
        double deltaT = delay / 1000.0;
        
        // Actualizar velocidad: v = v₀ + a·t
        velocity += acceleration * deltaT;
        
        // Actualizar posición: x = x₀ + v₀·t + ½·a·t²
        x += velocity * deltaT;
        
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
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, objectSize, objectSize);
        
        // Dibujar una flecha indicando la aceleración
        if (acceleration > 0) {
            g2d.setColor(Color.GREEN);
            int arrowLength = (int)(acceleration * 5);
            g2d.drawLine(x + objectSize/2, y + objectSize/2, 
                         x + objectSize/2 + arrowLength, y + objectSize/2);
            g2d.drawLine(x + objectSize/2 + arrowLength, y + objectSize/2,
                         x + objectSize/2 + arrowLength - 5, y + objectSize/2 - 5);
            g2d.drawLine(x + objectSize/2 + arrowLength, y + objectSize/2,
                         x + objectSize/2 + arrowLength - 5, y + objectSize/2 + 5);
        }
        
        // Mostrar información del movimiento
        double timeInSeconds = time / 1000.0;
        g2d.setColor(Color.BLACK);
        g2d.drawString("Tiempo: " + String.format("%.2f", timeInSeconds) + " s", 20, 30);
        g2d.drawString("Posición: " + x + " px", 20, 50);
        g2d.drawString("Velocidad: " + String.format("%.2f", velocity) + " px/s", 20, 70);
        g2d.drawString("Aceleración: " + acceleration + " px/s²", 20, 90);
        
        // Ecuación de MRUA: x = x₀ + v₀·t + ½·a·t²
        g2d.drawString("x = x₀ + v₀·t + ½·a·t²", getWidth() - 200, 30);
        g2d.drawString("x = " + initialPosition + " + 0·" + String.format("%.2f", timeInSeconds) + 
                       " + ½·" + acceleration + "·" + String.format("%.2f", timeInSeconds) + "²", 
                       getWidth() - 200, 50);
    }
    
    // Método para usar desde fuera
    public JPanel getAnimationPanel() {
        return this;
    }
}
