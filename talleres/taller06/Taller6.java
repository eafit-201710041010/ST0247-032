import java.util.*;
/**
 * Clase en la cual se implementan los metodos del Taller de Clase #6
 * 
 * @author Mauricio Toro, Mateo Agudelo
 */
public class Taller6 {

	public static int[] cambioGreedy(int n, int[] denominaciones) {
		return null;
	}

	public static int recorrido(Digraph g) {
	    boolean[] visitados =new boolean[g.size()];
		return recorrido(g,visitados,0,0);
	}
	
	public static int recorrido(Digraph g, boolean[] visitados,int v,int suma) {
	    visitados[v]=true;
	    ArrayList<Integer> sucesores= g.getSuccessors(v);
	    int minimo=g.size();
	    for(int i =0;i<sucesores.size();i++){
	        if(sucesores.get(i)<minimo && visitados[i]==false){
	            minimo=i;
	           }
	    }
		
	    return suma+ recorrido(g,visitados,minimo,suma);
	}

}
