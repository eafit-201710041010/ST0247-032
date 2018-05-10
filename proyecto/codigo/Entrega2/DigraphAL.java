package Entrega2;

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.LinkedList;

/**
 * Esta clase es una implementación de un digrafo usando listas de adyacencia
 * 
 * @author Mauricio Toro 
 * @version 1
 */
public class DigraphAL extends Graph {

  ArrayList<LinkedList<Pair<Integer, Integer>>> lista;

  public DigraphAL(int size) {
    super(size);
    lista = new ArrayList<LinkedList<Pair<Integer, Integer>>>();
    for (int i = 0; i < size; i++) {
      lista.add(new LinkedList<Pair<Integer, Integer>>());
    }
  }

  public void addArc(int source, int destination, int weight) {
    lista.get(source).add(new Pair(destination, weight));
  }

  public int getWeight(int source, int destination) {
    for (Pair<Integer, Integer> pareja : lista.get(source))
      if (pareja.getKey() == destination) {
        return pareja.getValue();
      }
    return 0;
  }

  public ArrayList<Integer> getSuccessors(int vertice) {
    ArrayList<Integer> listaVecinos = new ArrayList<Integer>();
    for (Pair<Integer, Integer> pareja : lista.get(vertice)) {
      listaVecinos.add(pareja.getKey());
    }
    return listaVecinos;
  }
  
  public  void toString2(){
      for(int i =0;i<lista.size();i++){
          for(Pair a: lista.get(i)){
              System.out.println(i + "->"+a.toString());
            }
        }
    }

}
