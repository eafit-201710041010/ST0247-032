import java.util.ArrayList;
/**
 * Solucion al taller 6
 * 
 * @author Juan Jose Parra , Luisa Maria Vasquez

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
        for(int i =0;i<g.size();i++){
            visitados[i]=false;
        }
        return recorrido(g,visitados,0);
    }

    public static int recorrido(Digraph g, boolean[] visitados,int v) {
        boolean todos=true;
        visitados[v]=true;

        for(int i =0;i<g.size();i++){
            if(visitados[i]==false){
                todos=false;
            }
        }
        if(todos ){
            return g.getWeight(v,0);
        }else{            
            ArrayList<Integer> sucesores= g.getSuccessors(v);
            int minimo=v;
            int costoCamino =10000;
            for(int sucesor: sucesores){
                if(g.getWeight(v,sucesor)<costoCamino && visitados[sucesor]==false){
                    minimo=sucesor;
                    costoCamino= g.getWeight(v,minimo);
                }
            }
            return g.getWeight(v,minimo)+ recorrido(g,visitados,minimo);
        }
    }
}