package Entrega2;

/**
 * Write a description of class Estacion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cliente extends Node
{
    Estacion estacionCercana;
    
    public Cliente(char tipo,int x, int y, int id){
        super(tipo,x,y,id);
        
    }
    
    public void setEstacion(Estacion e){
        estacionCercana = e;
    }
}

