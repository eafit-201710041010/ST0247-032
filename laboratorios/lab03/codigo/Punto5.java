import java.util.ArrayList;
/**
 * Write a description of class Punto5 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Punto5
{
    public static boolean tieneCiclos(Digraph g){
        boolean [] visitados = new boolean [g.size()];
        for(int i = 0; i < visitados.length; i++){
            visitados[i] = false;
        }
        return tieneCiclos(g, visitados, 1);
    }
    private static boolean tieneCiclos(Digraph g, boolean[] visitados, int origen){
        ArrayList sucesores = g.getSuccessors(origen);
        if(visitados[origen]){
            return true;
        } else {
            visitados[origen] = true;
        }
        if(sucesores != null){
            for(int i = 0; i < sucesores.size(); i++){
                return tieneCiclos(g, visitados, (int)sucesores.get(i));
            }
        }
        return false;
    }
}
