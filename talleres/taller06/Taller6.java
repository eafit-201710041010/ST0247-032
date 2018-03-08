import java.util.ArrayList;
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
        if (n != 0) return null;
        return devuelta;
    }

    public static int recorrido(Digraph g) {
        boolean[] visitados =new boolean[g.size()];
        return recorrido(g,visitados,0);
    }

    public static int recorrido(Digraph g, boolean[] visitados,int v) {

        if(v==g.size()){
            return 0;
        }else{
            visitados[v]=true;
            ArrayList<Integer> sucesores= g.getSuccessors(v);
            int minimo=g.size();
            for(int i =0;i<sucesores.size();i++){
                if(sucesores.get(i)<minimo && visitados[i]==false){
                    minimo=i;
                }
            }
            return g.getWeight(v,minimo)+ recorrido(g,visitados,minimo);
        }
    }
}
