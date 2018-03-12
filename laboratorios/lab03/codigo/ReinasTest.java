import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * Pruebas de el método para las n Reinas (punto 1.2 del Lab 3)
 * @author Juan Jose Parra y Luisa Maria Vasquez
 */
public class ReinasTest {

    /**
     * Test para probar si la solucion dada por el metodo nReinas es correcta
     */
    @Test
    public void testReinas() {

        assertTrue(esValido(nQueens.nReinas(4)));
        assertTrue(esValido(nQueens.nReinas(5)));
        assertTrue(esValido(nQueens.nReinas(6)));
        assertTrue(esValido(nQueens.nReinas(7)));
        assertTrue(esValido(nQueens.nReinas(8)));
        assertTrue(esValido(nQueens.nReinas(9)));
        assertTrue(esValido(nQueens.nReinas(10)));

    }

    /**
     * Método que determina si en un tablero dado las reinas no se atacan
     * @param queens Tablero a verificar
     * @return Si el tablero es valido o no
     */
    static boolean esValido(int[] queens) {
        for(int i = 0; i < queens.length; i++){
            for(int j = i+1; j < queens.length; j++){
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j)){
                    return false;
                }
            }
        }

        return true;
    }
}