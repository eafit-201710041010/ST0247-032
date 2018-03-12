import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
/**
 * Pruebas de el método para BFS (punto 1.4 del Lab 3)
 * @author Juan Jose Parra y Luisa Maria Vasquez
 */
public class BFSTest {

    /**
     * Test para probar si la solucion dada por el metodo BFS es correcta tanto para 
     * DigraphAM como para DigraphAL
     */
    @Test
    public void testBFS() {

        Digraph g1 =new DigraphAM(4);
        g1.addArc(0,1,1);
        g1.addArc(0,2,2);
        g1.addArc(2,3,1);

        ArrayList<Integer> camino1= BFS.bfs(g1,0);
        ArrayList<Integer> correcto1=new ArrayList<Integer>();
        correcto1.add(0);
        correcto1.add(1);
        correcto1.add(2);
        correcto1.add(3);
        assertTrue(esIgual(correcto1,camino1));

        Digraph g2 =new DigraphAL(4);
        g2.addArc(0,1,1);
        g2.addArc(0,2,2);
        g2.addArc(2,3,1);

        ArrayList<Integer> camino2= BFS.bfs(g2,0);
        ArrayList<Integer> correcto2=new ArrayList<Integer>();
        correcto2.add(0);
        correcto2.add(1);
        correcto2.add(2);
        correcto2.add(3);
        assertTrue(esIgual(correcto2,camino2));

        Digraph g3 =new DigraphAM(10);
        g3.addArc(0,1,1);
        g3.addArc(0,9,2);
        g3.addArc(1,2,1);
        g3.addArc(1,8,4);
        g3.addArc(9,3,2);
        g3.addArc(9,7,3);
        g3.addArc(3,6,1);
        g3.addArc(3,4,2);
        g3.addArc(4,5,1);

        ArrayList<Integer> camino3= BFS.bfs(g3,0);
        ArrayList<Integer> correcto3=new ArrayList<Integer>();
        correcto3.add(0);
        correcto3.add(1);
        correcto3.add(9);
        correcto3.add(2);
        correcto3.add(8);
        correcto3.add(3);
        correcto3.add(7);
        correcto3.add(4);
        correcto3.add(6);
        correcto3.add(5);

        assertTrue(esIgual(correcto3,camino3));

        Digraph g4 =new DigraphAL(5);
        g4.addArc(0,1,1);
        g4.addArc(0,2,1);
        g4.addArc(1,3,2);
        g4.addArc(2,4,2);

        ArrayList<Integer> camino4= BFS.bfs(g4,0);
        ArrayList<Integer> correcto4=new ArrayList<Integer>();

        correcto4.add(0);
        correcto4.add(1);
        correcto4.add(2);
        correcto4.add(3);
        correcto4.add(4);

        assertTrue(esIgual(correcto4,camino4));

    }

    /**
     * Método que determina si los dos ArrayList son iguales
     * @param expectativa Recorrido correcto de BFS
     * @param realidad Recorrido que retorna el metodo
     * @return Si son iguales o no
     */
    static boolean esIgual(ArrayList<Integer> expectativa, ArrayList<Integer> realidad ) {
        for(int i = 0; i < realidad.size(); i++){
            if(expectativa.get(i) != realidad.get(i))
                return false;

        }
        return true;
    }
}