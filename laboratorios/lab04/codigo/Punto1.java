import java.util.*;
/**
 * Solucion al punto 1 del Laboratorio 4, en el cual se soluciona el problema del vendedor viajero 
 * con un algoritmo voraz
 * 
 * @author Juan Jose Parra , Luisa Maria Vasquez
 * @version 08/04/2018
 */
public class Punto1
{
    /**
     * Metodo principal que llama  a los auxiliares con los datos necesarios para solucionar el 
     * problema del vendedor viajero
     * @param g Grafo a recorrer 
     * @param origen Ciudad de inicio a la cual se debe volver
     * @return camino encontrado
     */
    public static ArrayList<Integer> camino(Graph g, int origen){
        boolean[] visitados = new boolean[g.size()];
         boolean[] visitados2 = new boolean[g.size()];
        ArrayList<Integer> camino = new ArrayList<Integer>();
        ArrayList<Integer> caminoV = new ArrayList<Integer>();
        caminoAux(g,origen,visitados,camino);
        caminoVueltaAux(g,camino.get(camino.size()-1),origen,visitados2,caminoV);
        if(caminoV.size()==2){
            System.out.println("Logramos llegar a casa sin repetir ciudad");        
        }else{
             System.out.println("Para llegar a casa hubo que repetir ciudad, no habia otra forma"); 
        }
        for(int i =1;i<caminoV.size();i++){
            camino.add(caminoV.get(i));
        }
        return camino;
    }

    /**
     * Método que recorre todas las ciudades escogiendo ir a la que este siempre a menos distancia
     * @param g Grafo a recorrer
     * @param o Ciudad de origen
     * @param vis Ciudades visitadas
     * @param camino Camino que se va armando de a poco
     * 
     */
    private static void caminoAux(Graph g,int o,boolean[] vis ,ArrayList<Integer> camino){
         
        vis[o]=true;
        camino.add(o);
        int minP =Integer.MAX_VALUE;
        int minV =-1;
        ArrayList<Integer> suc = g.getSuccessors(o);
        for(int i : suc){
            if(g.getWeight(o,i) != 0 && !vis[i]){
                if(g.getWeight(o,i) < minP){
                    minP=g.getWeight(o,i);
                    minV=i;
                }
            }
        }

        if(minV>=0){
            caminoAux(g,minV,vis,camino);
        }        
    }

    /**
     * Método que busca el camino mas corto de una ciudad a otra, es usado para encontrar el camino de vuelta 
     * desde la ultima ciudad visitada
     * @param g Grafo a recorrer
     * @param o Ciudad de origen
     * @param f Ciudad destino
     * @param vis Ciudades visitadas
     * @param camino Camino que se va armando de a poco
     * 
     */
    private static void caminoVueltaAux(Graph g,int o,int f,boolean[] vis ,ArrayList<Integer> camino){
        
        if(o!=f){
            vis[o]=true;
            int minP =Integer.MAX_VALUE;
            int minV =-1;
            ArrayList<Integer> suc = g.getSuccessors(o);
            for(int i : suc){
                if(g.getWeight(o,i) != 0 && !vis[i] && i!=f){
                    if(g.getWeight(o,i) < minP){
                        minP=g.getWeight(o,i);
                        minV=i;
                    }
                }else if (i==f){
                    minV=f;   
                    break;
                }
            }

            if(minV>=0  ){
                camino.add(o); 
                caminoVueltaAux(g,minV,f,vis,camino);
            }   
        }else{
            camino.add(f); 
        }
    }
    

    /**
     * Metodo para probar progreso
     */
    public static void main(String [] args){
      DigraphAL g = new DigraphAL(5);
        g.addArc(0, 1, 2);
        g.addArc(0, 3, 1);
        g.addArc(3, 4, 7);
        g.addArc(4, 3, 8);
        g.addArc(2, 4, 6);
        g.addArc(3, 2, 6);
        g.addArc(2, 1, 5);
        g.addArc(1, 4, 4);
        g.addArc(4, 1, 1);
        g.addArc(1, 0, 1);

        for(int i: camino(g,0)){
            System.out.println(i+" ");
        }
       
        DigraphAL g2 = new DigraphAL(5);
        g2.addArc(0, 1, 2);
        g2.addArc(0, 3, 1);
        g2.addArc(3, 4, 7);
        g2.addArc(4, 3, 8);
        g2.addArc(2, 4, 6);
        g2.addArc(3, 2, 6);
        g2.addArc(2, 1, 5);
        g2.addArc(1, 4, 4);
        g2.addArc(4, 0, 1);
        for(int i: camino(g2,0)){
            System.out.println(i+" ");
        }
    }

}
