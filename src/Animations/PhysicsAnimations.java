
package Animations;

import javax.swing.*;
import java.awt.*;

public class PhysicsAnimations {
    
    private MRUAnimation mruAnimation;
    private MRUAAnimation mruaAnimation;
    private NewtonLawsAnimation newtonLawsAnimation;
    
    public PhysicsAnimations() {
        mruAnimation = new MRUAnimation();
        mruaAnimation = new MRUAAnimation();
        newtonLawsAnimation = new NewtonLawsAnimation();
    }
    
    public JPanel getMRUAnimation() {
        return mruAnimation;
    }
    
    public JPanel getMRUAAnimation() {
        return mruaAnimation;
    }
    
    public JPanel getNewtonLawsAnimation() {
        return newtonLawsAnimation;
    }
    
    // Método para mostrar una animación en un panel existente
    public static void showAnimationInPanel(JPanel targetPanel, JPanel animation) {
        targetPanel.removeAll();
        targetPanel.setLayout(new BorderLayout());
        targetPanel.add(animation, BorderLayout.CENTER);
        targetPanel.revalidate();
        targetPanel.repaint();
    }
}