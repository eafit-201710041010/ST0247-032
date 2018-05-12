
/**
 * Es como un nodo normal solo que permite diferenciarlo de estaciones sin necesidad de "tipo"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cliente extends Node
{
    Estacion estacionCercana;
    
    public Cliente(int x, int y, int id){
        super(x,y,id);
        
    }
    
    public void setEstacion(Estacion e){
        estacionCercana = e;
    }
}

