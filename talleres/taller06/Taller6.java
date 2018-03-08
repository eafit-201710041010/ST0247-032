
/**
 * Write a description of class s here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Taller6
{
    public static void main (String[] args){
        int[] denominaciones = {500, 300, 200, 50};
        cambioGreedy(1000,denominaciones);
    }
    public static int[] cambioGreedy(int n, int[] denominaciones) {
        int [] devuelta = new int [denominaciones.length];
        for(int i = 0; i < denominaciones.length; i++){
            while(n >= denominaciones[i]){
                n -= denominaciones[i];
                devuelta[i]++;
            }
        }
        return devuelta;
    }
}
