
/**
 * Write a description of class nreinas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class nreinas
{
    private static int queens(String pos, String pre, int n, int[] queens, int[] cuenta) {
        if(pre.length() == 0){
            for(int i = 0; i < n; i++){
                queens[i] = Integer.parseInt(pos.charAt(i)+"");
            }
            if(esValido(queens)){
                cuenta[0]++;
            }
        } else {
            for (int i = 0; i < pre.length(); i++){
                queens(pos + pre.charAt(i), pre.substring(0,i) + pre.substring(i+1, pre.length()), n, queens, cuenta);
            }
        }
        return cuenta[0];
    }
    public static int queens(int n) {
        int[] cuenta = {0};
        int[] queens = new int[n];
        String s = "";
        for(int i = 0; i < n; i++){
            s += i;
        }
        return queens("",s,n,queens, cuenta);
    }
    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print(" ");
        for (int i = 0; i < n; ++i)
            System.out.print(i + " ");
            System.out.println();
        for (int i = 0; i < n; ++i) {
            System.out.print(i + " ");
            for (int j = 0; j < n; ++j)
                System.out.print((tablero[i] == j ? "Q" : "#") + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static boolean esValido(int[] queens) {
        for(int i = 0; i < queens.length; i++){
            for(int j = i+1; j < queens.length; j++){
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j)){
                    return false;
                }
            }
        }
        imprimirTablero(queens);
        return true;
    }

}
