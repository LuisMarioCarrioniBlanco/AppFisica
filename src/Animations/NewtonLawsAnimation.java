package Animations;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.geom.Rectangle2D;

public class NewtonLawsAnimation extends JPanel implements ActionListener {
    
    private Timer timer;
    private double x = 50;
    private final int y = 150;
    private double velocity = 0;
    private double acceleration = 0;
    private double mass = 1.0;
    private double force = 0.0;
    private final int delay = 50;
    private boolean running = false;
    private final int maxWidth = 800;
    private final Color objectColor = new Color(70, 130, 180); // Azul acero
    
    private JSlider massSlider;
    private JSlider forceSlider;
    private JLabel massLabel;
    private JLabel forceLabel;
    private JLabel accelerationLabel;
    
    public NewtonLawsAnimation() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(maxWidth, 300));
        timer = new Timer(delay, this);
        
        // Panel de control para la animación
        JPanel controls = new JPanel(new GridLayout(3, 1));
        JPanel buttonPanel = new JPanel();
        JPanel massPanel = new JPanel();
        JPanel forcePanel = new JPanel();
        
        JButton startButton = new JButton("Iniciar");
        JButton resetButton = new JButton("Reiniciar");
        
        // Slider para la masa (0.5 a 5.0 kg)
        massSlider = new JSlider(JSlider.HORIZONTAL, 5, 50, 10);
        massSlider.setMajorTickSpacing(10);
        massSlider.setPaintTicks(true);
        massSlider.setPaintLabels(true);
        
        // Slider para la fuerza (-10 a 10 N)
        forceSlider = new JSlider(JSlider.HORIZONTAL, -20, 20, 0);
        forceSlider.setMajorTickSpacing(10);
        forceSlider.setPaintTicks(true);
        forceSlider.setPaintLabels(true);
        
        massLabel = new JLabel("Masa: 1.0 kg");
        forceLabel = new JLabel("Fuerza: 0.0 N");
        accelerationLabel = new JLabel("Aceleración: 0.0 m/s²");
        
        massSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mass = massSlider.getValue() / 10.0;
                massLabel.setText("Masa: " + mass + " kg");
                updateAcceleration();
                if (!running) {
                    repaint();
                }
            }
        });
        
        forceSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                force = forceSlider.getValue() / 2.0;
                forceLabel.setText("Fuerza: " + force + " N");
                updateAcceleration();
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
            x = 50;
            velocity = 0;
            startButton.setText("Iniciar");
            repaint();
        });
        
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(accelerationLabel);
        
        massPanel.add(new JLabel("Masa:"));
        massPanel.add(massSlider);
        massPanel.add(massLabel);
        
        forcePanel.add(new JLabel("Fuerza:"));
        forcePanel.add(forceSlider);
        forcePanel.add(forceLabel);
        
        controls.add(buttonPanel);
        controls.add(massPanel);
        controls.add(forcePanel);
        
        setLayout(new BorderLayout());
        add(controls, BorderLayout.SOUTH);
        
        updateAcceleration();
    }
    
    private void updateAcceleration() {
        // Segunda ley de Newton: F = m·a => a = F/m
        acceleration = force / mass;
        accelerationLabel.setText("Aceleración: " + String.format("%.2f", acceleration) + " m/s²");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Convertir delay a segundos para los cálculos
        double deltaT = delay / 1000.0;
        
        // Actualizar velocidad: v = v₀ + a·t
        velocity += acceleration * deltaT;
        
        // Actualizar posición: x = x₀ + v·t
        x += velocity * deltaT * 10; // Factor para hacer visible el movimiento
        
        // Límites de la pantalla con rebote
        int objectWidth = getObjectWidth();
        if (x <= 0) {
            x = 0;
            velocity = -velocity * 0.8; // Rebote con pérdida de energía
        } else if (x >= maxWidth - objectWidth) {
            x = maxWidth - objectWidth;
            velocity = -velocity * 0.8; // Rebote con pérdida de energía
        }
        
        repaint();
    }
    
    private int getObjectWidth() {
        // El ancho es proporcional a la masa
        return (int)(30 + mass * 10);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Mejoras de depuración para identificar el problema
        // Imprimir las dimensiones del panel
        System.out.println("Panel width: " + getWidth() + ", height: " + getHeight());
        // Imprimir posición actual del objeto
        System.out.println("Object position: x=" + x + ", y=" + y);
        
        // Mejorar la calidad del renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Establecer un color de fondo claro para ver mejor los límites del panel
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Dibujar la línea base (suelo)
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, y + 50, getWidth(), y + 50);
        
        // Marcar posiciones cada 50 píxeles
        for (int i = 0; i <= getWidth(); i += 50) {
            g2d.drawLine(i, y + 45, i, y + 55);
            g2d.drawString(Integer.toString(i), i - 10, y + 70);
        }
        
        int objectWidth = getObjectWidth();
        int objectHeight = 50;
        
        // Dibujar el contorno del panel para depuración
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
        
        // Dibujar el objeto (cubo) con un color más brillante para asegurar visibilidad
        g2d.setColor(objectColor);
        g2d.fill3DRect((int)x, y, objectWidth, objectHeight, true);
        
        // Dibujar un borde más visible alrededor del objeto
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)x, y, objectWidth, objectHeight);
        
        // Dibujar la fuerza como una flecha
        if (force != 0) {
            g2d.setColor(force > 0 ? Color.GREEN : Color.RED);
            int arrowLength = (int)(Math.abs(force) * 5);
            int arrowX = (int)x + (force > 0 ? objectWidth : 0);
            int arrowY = y + objectHeight/2;
            int arrowEndX = arrowX + (force > 0 ? arrowLength : -arrowLength);
            
            // Línea de la flecha
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(arrowX, arrowY, arrowEndX, arrowY);
            
            // Punta de la flecha
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];
            
            if (force > 0) {
                xPoints[0] = arrowEndX;
                xPoints[1] = arrowEndX - 10;
                xPoints[2] = arrowEndX - 10;
                yPoints[0] = arrowY;
                yPoints[1] = arrowY - 5;
                yPoints[2] = arrowY + 5;
            } else {
                xPoints[0] = arrowEndX;
                xPoints[1] = arrowEndX + 10;
                xPoints[2] = arrowEndX + 10;
                yPoints[0] = arrowY;
                yPoints[1] = arrowY - 5;
                yPoints[2] = arrowY + 5;
            }
            
            g2d.fillPolygon(xPoints, yPoints, 3);
            g2d.setStroke(new BasicStroke(1));
        }
        
        // Mostrar información
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Segunda Ley de Newton: F = m·a", 20, 30);
        g2d.drawString(String.format("%.1f N = %.1f kg · %.2f m/s²", force, mass, acceleration), 20, 50);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Posición: " + (int)x + " px", 20, 80);
        g2d.drawString("Velocidad: " + String.format("%.2f", velocity) + " m/s", 20, 100);
    }
    
    // Método para usar desde fuera
    public JPanel getAnimationPanel() {
        return this;
    }
}