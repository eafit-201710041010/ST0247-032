import java.util.*;
import java.io.*;
/**
 * Solucion al punto 1 del Laboratoio 5 de Estructuras de datos y algoritmos 2 
 * 
 * @author Juan José Parra , Luisa Maria Vásquez
 * 
 */
public class Punto1{

    // Punto 1.1
    /**
     * Método que haya la distancia levenshtein entre dos cadenas
     * @param a Cadena 1
     * @param b Cadena 2
     * @return Distancia levenshtein
     */
    public static int levenshtein(String a, String b) {      

        if (a.equals(b)) {
            return 0;
        }else if (a.length() == 0 ) {
            return b.length();
        }else if (b.length() == 0) {
            return a.length();
        }

        int[] arr1 = new int[b.length() + 1];
        int[] arr2 = new int[b.length() + 1];
        int[] temp;

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
        }

        for (int i = 0; i < a.length(); i++) {
            arr2[0] = i + 1;
            for (int j = 0; j < b.length(); j++) {
                int cost = 1;
                if (a.charAt(i) == b.charAt(j)) {
                    cost = 0;
                }
                arr2[j + 1] = Math.min(arr2[j] + 1, Math.min(arr1[j + 1] + 1,arr1[j] + cost)); 
            }
            temp = arr1;
            arr1 = arr2;
            arr2 = temp;
        }
        return arr1[b.length()];
    }

    // Punto 1.2
    
    /**
     * Método que a partir de un grafo, realiza el algoritmo de Held Karp , con el cual
     * se haya el costo minimo de recorrer todos los vertices del grafo terminando en el 
     * nodo inicial (Travelling Salesman Problem)
     * @param g Grafo 
     * @return Costo minimo 
     */
    public static int heldKarp(Graph g){
        Map<Index,Integer> minCostDP = new HashMap();
        Map<Index,Integer> parent = new HashMap();

        Set<Integer> sets = new HashSet<Integer>();
        for(int i =0;i<g.size()-1;i++){
            sets.add(i+1);
        }

        Set<Set<Integer>> set2 =  particiones(sets);

        for(Set<Integer> set: set2){
            for(int v=1;v<g.size();v++){
                if(set.contains(v)){
                    continue;
                }
                Index index =Index.createIndex(v,set);
                int minCost = Integer.MAX_VALUE;
                int minPrevVertex=0;

                Set<Integer> copia = new HashSet<>(set);
                for(int prev :set){
                    int costo= g.getWeight(prev,v)+ getCost(copia,prev,minCostDP);
                    if(costo<minCost){
                        minCost=costo;
                        minPrevVertex= prev;
                    }

                }
                if(set.size() ==0){
                    minCost=g.getWeight(0,v);
                }
                minCostDP.put(index,minCost);
                parent.put(index,minPrevVertex);
            }
        }
        int min =Integer.MAX_VALUE;
        int prev=-1;
        Set<Integer> copySet= new HashSet<>(sets);
        for(int k : sets){
            int costo= g.getWeight(k,0) + getCost(copySet,k, minCostDP);
            if(costo<min){
                min=costo;
                prev = k;
            }
        }
        parent.put(Index.createIndex(0, sets), prev);
        return min;
    }

    private static  Set<Set<Integer>> particiones(Set<Integer> originalSet) {
        Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<Integer>());
            return sets;
        }
        List<Integer> list = new ArrayList<Integer>(originalSet);
        int head = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size())); 
        for (Set<Integer> set : particiones(rest)) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }       
        return sets;
    }  

    private static int getCost(Set<Integer> set, int prevVertex, Map<Index, Integer> minCostDP) {
        set.remove(prevVertex);
        Index index = Index.createIndex(prevVertex, set);
        int cost = minCostDP.get(index);
        set.add(prevVertex);
        return cost;
    }

    // Punto 1.3
    
    /**
     * Método que haya la longitud de la subcadena comun mas larga entre dos cadenas de caracteres
     * @param x Cadena 1
     * @param y Cadena 2
     * @return Longitud de la subcadena común mas larga entre "x" y "y"
     */
    public static int lcsdyn(String x, String y){
        int[][] mat = new int[x.length()+1][y.length()+1];

        for(int i=0; i<=x.length(); i++){
            for(int j=0; j<=y.length(); j++){
                if(i==0 || j==0){
                    mat[i][j]=0;
                }else if(x.charAt(i-1)==y.charAt(j-1)){
                    mat[i][j] = 1 + mat[i-1][j-1];
                }else{
                    mat[i][j] = Math.max(mat[i-1][j], mat[i][j-1]);
                }
            }
        }

        return mat[x.length()][y.length()];
    }

    // Punto 1.4
    
    /**
     * Método que haya la subcadena comun mas larga entre dos cadenas de caracteres
     * @param x Cadena 1
     * @param y Cadena 2
     * @return Subcadena común mas larga entre "x" y "y"
     */
    public static String lcsdyn2(String x, String y){
        int[][] mat = new int[x.length()+1][y.length()+1];
        String mas="";
        for(int i=0; i<=x.length(); i++){
            for(int j=0; j<=y.length(); j++){
                if(i==0 || j==0){
                    mat[i][j]=0;
                }else if(x.charAt(i-1)==y.charAt(j-1)){
                    mas+=x.charAt(i-1);
                    mat[i][j] = 1 + mat[i-1][j-1];
                }else{
                    mat[i][j] = Math.max(mat[i-1][j], mat[i][j-1]);
                }
            }
        }

        return mas;
    }

    public static void main(String [] args){
        DigraphAM g =new DigraphAM(4);
        g.addArc(0,1,7);
        g.addArc(0,2,15);
        g.addArc(0,3,6);
        g.addArc(1,0,2);
        g.addArc(1,2,7);
        g.addArc(1,3,3);
        g.addArc(2,1,6);
        g.addArc(2,0,9);
        g.addArc(2,3,12);
        g.addArc(3,0,10);
        g.addArc(3,1,4);
        g.addArc(3,2,8);

        System.out.println(heldKarp(g));

    
    }
}