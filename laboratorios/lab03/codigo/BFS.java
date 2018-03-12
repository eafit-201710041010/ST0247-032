import java.util.*;
/**
 * Solucion al punto 1.3 del Lab 3 
 * 
 * @author Juan Jose Parra y Luisa Maria Vasquez
 * @version 11/03/2018
 */
public class BFS
{
    /**
     * Método que recorre un grafo por BFS
     * @param g Grafo a recorrer
     * @param start Nodo de inicio
     * @return Recorrido realizado
     */
    public static ArrayList<Integer> bfs(Digraph g, int start){
        boolean visitados[] = new boolean[g.size()];
        ArrayList<Integer> camino =new ArrayList<Integer>();
        LinkedList<Integer> v= new LinkedList<>();
        v.add(start);
        camino.add(start);
        while(v.isEmpty()==false){
            int ver = v.poll();
            ArrayList<Integer> sucesores = g.getSuccessors(ver);
            if(sucesores != null){
                for(int sucesor:sucesores){
                    v.add(sucesor);
                    if(!visitados[sucesor]){
                        camino.add(sucesor);
                    }

                    visitados[sucesor] = true;
                }
            }

        }
        return camino.size()>1 ? camino : null;
    }

}
