import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test del Punto 1
 *
 * @author  Juan Jose Parra, Luisa Maria Vasquez
 * @version 08/04/2018
 */
public class TSPTest
{
    @Test
    /**
     * Metodo que prueba casos posibles si el metodo desarrollado para resolver
     * el problema del vendedor viajero funciona
     */
    public void TSP()
    {
       //Repite ciudades para ir a casa
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
        ArrayList<Integer> res = Punto1.camino(g, 0);
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(0);
        ans.add(3);
        ans.add(2);
        ans.add(1);
        ans.add(4);
        ans.add(1);
        ans.add(0);

        System.out.println(ans);
        assertEquals(ans, res);
        
        //No repite ciudades para ir a casa
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
        ArrayList<Integer> res2 = Punto1.camino(g2, 0);
        ArrayList<Integer> ans2 = new ArrayList<Integer>();
        ans2.add(0);
        ans2.add(3);
        ans2.add(2);
        ans2.add(1);
        ans2.add(4);
        ans2.add(0);

        System.out.println(ans2);
        assertEquals(ans2, res2);
        
        // Taller de voraces
        
        DigraphAL g3 = new DigraphAL(4);
        g3.addArc(0,1,7);
        g3.addArc(0,2,15);
        g3.addArc(0,3,6);
        g3.addArc(1,0,2);
        g3.addArc(1,2,7);
        g3.addArc(1,3,3);
        g3.addArc(2,0,9);
        g3.addArc(2,1,6);
        g3.addArc(2,3,12);
        g3.addArc(3,0,10);
        g3.addArc(3,1,4);
        g3.addArc(3,2,8);
        
        ArrayList<Integer> res3 = Punto1.camino(g3, 0);
        ArrayList<Integer> ans3 = new ArrayList<Integer>();
        ans3.add(0);
        ans3.add(3);
        ans3.add(1);
        ans3.add(2);
        ans3.add(0);

        System.out.println(ans3);
        assertEquals(ans3, res3);
    }
}