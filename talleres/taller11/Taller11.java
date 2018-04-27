import java.util.*;
import java.io.*;
/**
 * Adaptacion a la clase Graph de la implementacion del algoritmo Held-karp propuesta en:
 * https://www.youtube.com/watch?v=-JjA4BLQyqE
 * 
 * @author Tushar Roy , Juan José Parra , Luisa Maria Vásquez
 * 
 */
public class Taller11{

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

    /**
     * Adaptado de : https://stackoverflow.com/questions/1670862/obtaining-a-
     * powerset-of-a-set-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_
     * campaign=google_rich_qa
     */
    public static  Set<Set<Integer>> particiones(Set<Integer> originalSet) {
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