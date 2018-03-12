
import java.util.ArrayList;
/**
 * Write a description of class Punto6 here.
 * 
 * @author Juan Jose Parra , Luisa Maria Vasquez
 * @version 10/03/2018
 */
public class Punto6
{
    boolean [] visitados;
    int pesoMin = 106;
    int caminoFinal = 0;
    public void recorrer(Digraph g, int origen, int destino){
        recorrer(g, origen, destino, 0, origen);
    }
    
    private void recorrer(Digraph g, int origen, int destino, int peso, int camino){
        if(visitados[origen]){
            return;
        }
        if(origen == destino){
            pesoMin = peso;
            caminoFinal = camino;
        } else {
            ArrayList sucesores = g.getSuccessors(origen);
            if(sucesores == null){
                return;
            }
            for(int i = 0; i < sucesores.size(); i++){
                visitados[origen] = true;
                peso += g.getWeight(origen, (int)sucesores.get(i));
                if(peso <= pesoMin){
                    if((int)sucesores.get(i) >= 100){
                        camino = camino * 1000 + (int)sucesores.get(i);
                    } else if ((int)sucesores.get(i) >= 10){
                        camino = camino * 100 + (int)sucesores.get(i);
                    } else {
                        camino = camino * 10 + (int)sucesores.get(i);
                    }
                    
                    recorrer(g, (int)sucesores.get(i), destino, peso, camino);
                }
                peso -= g.getWeight(origen, (int)sucesores.get(i));
                if((int)sucesores.get(i) >= 100){
                    camino /= 1000;
                } else if ((int)sucesores.get(i) >= 10){
                    camino /= 100;
                }else{
                    camino /= 10;
                }
                visitados[origen] = false;
            }
        }
    }
}
