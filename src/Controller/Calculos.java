/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author LENOVO
 */
public class Calculos {
    public Calculos(){
        
    }
    public double dist(double velocidad,double tiempo){
    double r = velocidad * tiempo;
    return r;
}
    
    public double vel(double distancia,double tiempo){
    double r = distancia / tiempo;
    return r;
}
    
    public double tiemp(double distancia,double velocidad){
    double r = distancia / velocidad;
    return r;
}
    
    public double dist2(double vInicial,double tiempo,double aceleracion){
    double resultado = vInicial * tiempo + 0.5 * aceleracion * tiempo * tiempo;
    return resultado;
    }
    
    public double velFinal(double viInicial,double aceleracion,double tiempo){
    double r = viInicial + aceleracion * tiempo;
    return r;
    }
    
    public double velInicial(double distancia,double velocidad){
    double r = distancia / velocidad;
    return r;
    }
    
   public double enerPotencial(double masa,double altura){
    double r = masa*9.8*altura;
    return r;
   }
   
   public static double calcularVelocidad(double distancia, double tiempo) throws ArithmeticException {
        if (tiempo == 0) {
            throw new ArithmeticException("El tiempo no puede ser cero");
        }
        return distancia / tiempo;
    }

    public static double calcularDistancia(double velocidad, double tiempo) {
        return velocidad * tiempo;
    }
    

    public static double calcularTiempo(double distancia, double velocidad) throws ArithmeticException {
        if (velocidad == 0) {
            throw new ArithmeticException("La velocidad no puede ser cero");
        }
        return distancia / velocidad;
    }

    public static double calcularAceleracion(double velocidadFinal, double velocidadInicial, double tiempo) 
            throws ArithmeticException {
        if (tiempo == 0) {
            throw new ArithmeticException("El tiempo no puede ser cero");
        }
        return (velocidadFinal - velocidadInicial) / tiempo;
    }
    
    public static double calcularVelocidadFinal(double velocidadInicial, double aceleracion, double tiempo) {
        return velocidadInicial + (aceleracion * tiempo);
    }
    
    public static double calcularVelocidadInicial(double velocidadFinal, double aceleracion, double tiempo) {
        return velocidadFinal - (aceleracion * tiempo);
    }
    
    public static double calcularDistanciaAcelerada(double velocidadInicial, double tiempo, double aceleracion) {
        return (velocidadInicial * tiempo) + (0.5 * aceleracion * Math.pow(tiempo, 2));
    }
    
    public static double calcularTiempoAcelerado(double velocidadFinal, double velocidadInicial, double aceleracion) 
            throws ArithmeticException {
        if (aceleracion == 0) {
            throw new ArithmeticException("La aceleración no puede ser cero");
        }
        return (velocidadFinal - velocidadInicial) / aceleracion;
    }
    
    /**
     * Cálculos para Leyes de Newton
     */
    
    public static double calcularFuerza(double masa, double aceleracion) {
        return masa * aceleracion;
    }
    
    public static double calcularAceleracionDesdeF(double fuerza, double masa) throws ArithmeticException {
        if (masa == 0) {
            throw new ArithmeticException("La masa no puede ser cero");
        }
        return fuerza / masa;
    }
    
    public static double calcularMasa(double fuerza, double aceleracion) throws ArithmeticException {
        if (aceleracion == 0) {
            throw new ArithmeticException("La aceleración no puede ser cero");
        }
        return fuerza / aceleracion;
    }
   
}
