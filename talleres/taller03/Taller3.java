import java.util.ArrayList;
import java.util.Collections;

/**
 * Clase en la cual se implementan los metodos del Taller de Clase #3
 * 
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller3 {

    private static boolean puedoPonerReina(int r, int c, int[] tablero) {
        for(int i = 0; i < c; i++){
            if(Math.abs(tablero[i] - r) == Math.abs(i - c)){
                imprimirTablero(tablero);
                return false;
            }
        }
        return true;
    }

    public static int nReinas(int n) {
        int cuenta = 0;
        int[] queens = new int[n];
        int res;
        for(int c = 0; c < n; c++){
            for(int r = 0; r <  n; r++){
                if(r == c){
                    continue;
                }
                res = nReinas(c,r,queens);
                if(res == 0){
                    break;
                } else {
                    queens[r] = c;
                }
                if(c == n && puedoPonerReina(r, c, queens)){
                    cuenta++;
                    imprimirTablero(queens);
                }
            }
        }
        return cuenta;
    }

    private static int nReinas(int r, int c, int[] queens) {
        if(!puedoPonerReina(r, c, queens)){
            return 0;
        } else {
            return 1;
        }
    }

    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print("    ");
        for (int i = 0; i < n; ++i)
            System.out.print(i + " ");
        System.out.println("\n");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + "   ");
            for (int j = 0; j < n; ++j)
                System.out.print((tablero[i] == j ? "Q" : "#") + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static ArrayList<Integer> camino(Digraph g, int inicio, int fin) {
        return new ArrayList<>();    
    }

    // recomendacion
    private static boolean dfs(Digraph g, int nodo, int objetivo, boolean[] visitados, ArrayList<Integer> list) {
        return true;
    }

}
