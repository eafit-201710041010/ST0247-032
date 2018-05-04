
/**
 * Write a description of class Taller11 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Taller12 {
    static int min;
    static int[] minTablero;
    static int num = 5000;
    
    private static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print(" ");
        for (int i = 0; i < n; ++i)
            System.out.print(i + " ");
            System.out.println();
        for (int i = 0; i < n; ++i) {
            System.out.print(i + " ");
            for (int j = 0; j < n; ++j)
                System.out.print((tablero[j] == i ? "L" : "#") + " ");
            System.out.println();
        }
        System.out.println();
    }

    private static boolean esValido(int[] queens) {
        for(int i = 0; i < queens.length; i++){
            for(int j = i+1; j < queens.length; j++){
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j) || queens[i]==queens[j]){
                    return false;
                }
            }
        }
        imprimirTablero(queens);
        return true;
    }
    
    public static void queens(int n){
        int[] tablero = new int[n];
        min = n*n;
        for(int i = 0; i < n; i++){
            tablero[i] = 0;
        }
        
        int a = 0;
        
        while(!esValido(tablero) && a < num){
            a++;            
            tablero = mirarCambios(tablero);
                                                        imprimirTablero(tablero);
        }
        if(esValido(tablero)) {
            System.out.println("Tablero encontrado");
        } else {
            System.out.println("No se encontró solución, \nesto es lo mejor que pudo hacer");
        }
    }
    
    private static int[] mirarCambios(int[] tablero){
        int n = tablero.length;
        min = n*n;
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                int[] copia = tablero.clone();
                copia[r] = c;
                int numAtaques = ataques(copia);
                
                if(numAtaques < min){
                    min = numAtaques;
                    minTablero = copia;
                }
            }
        }
        return minTablero;
    }
    
    private static int ataques(int[] queens) {
        int cuenta =0;
        for(int i = 0; i < queens.length; i++){
            for(int j = i+1; j < queens.length; j++){
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j) || queens[i]==queens[j]){
                    cuenta++;
                }
            }
        }
        return cuenta;
    }
}
