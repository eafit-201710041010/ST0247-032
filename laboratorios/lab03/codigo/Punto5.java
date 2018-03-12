import java.util.ArrayList;
/**
 * Solucion al punto 5 del Lab 3 
 * 
 * @author Juan Jose Parra y Luisa Maria Vasquez 
 * @version 11/03/2018
 */
public class Punto5
{
    /**
     * Método que determina si el grafo tiene o no ciclos
     * @param g Grafo a revisar
     * @return Si tiene o no ciclos
     */
    public static boolean tieneCiclos(Digraph g){
        boolean [] visitados = new boolean [g.size()];
        for(int i = 0; i < visitados.length; i++){
            visitados[i] = false;
        }
        return tieneCiclos(g, visitados, 1);
    }
    
    /**
     * Método recursivo que busca ciclos en un grafo
     * @param g Grafo en el que se buscan los ciclos
     * @param visitados Arreglo que almacena los nodos ya visitados
     * @param origen Nodo inicio
     * @return Si hay ciclos o no
     */
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
