import java.util.*;
import java.util.*;

/**
 * Clase en la cual se implementa la solución al punto 1.1 del Lab 3
 * Realizado por: Juan Jose Parra, Luisa Maria Vasquez
 */
public class nQueens {

    /**
     * Método que determina si es posible o no poner una reina en determinada posición
     * @param r Fila donde se pondrá la reina
     * @param c Columna donde se pondrá la reina
     * @param tablero Tablero a revisar
     * @return Si se puede o no poner una reina
     */
    private static boolean puedoPonerReina(int r, int c, int[] tablero) {
        for (int i=0; i<r;i++) {
            if ( (i-r)==(tablero[i]-c)||(i-r)==(c-tablero[i]) || tablero[i]==c){ 
                return false;
            }
        }
        return true;
    }
 
    /**
     * Metodo que recibe el tamaño del tablero y retorna una solución al problema de las n reinas
     * @param n Tamaño del tablero
     * @return Tablero con reinas puestas
     */
    public static int[] nReinas(int n) {
        int[] tablero=new int[n];
        int[] sol= new int[n];
        ArrayList<Integer> solucion =new ArrayList<Integer>();
        nReinas(0,n,tablero,solucion);
        for(int i =0;i<n;i++){
            sol[i]=solucion.get(i);
        }
        return sol;
    }

    /**
     * Método que usa backtracking para encontrar soluciones a las nReinas
     * @param r Fila en la cual se podria poner una reina
     * @param c Columna en la cual se podria poner una reina
     * @param queens Tablero temporal donde se van probando soluciones
     * @param sol ArrayList que va guardando las soluciones posibles
     * 
     */
    private static void nReinas(int r, int c, int[] queens,ArrayList<Integer> sol){
        for (int i = 0;i<c;i++){
            if(puedoPonerReina(r, i, queens)){
                queens[r] = i;
                if(r ==c-1){

                    for(int ver:queens){
                        sol.add(ver);
                    }
                }else{
                    nReinas(r+1,c,queens,sol);
                } 
            }  
        }

    }
    
    /**
     * Método que imprime un tablero
     * @param tablero Tablero a imprimir
     */public static void imprimirTablero(int[] tablero) {
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


}
