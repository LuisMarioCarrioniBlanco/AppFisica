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
   
   
}
