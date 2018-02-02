import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando listas de adyacencia
 *
 * @author Mauricio Toro, Mateo Agudelo, <su nombre>
 */
public class DigraphAL extends Digraph {
    public ArrayList<ArrayList<Pair<Integer, Integer>>> lista;

    public DigraphAL(int size) {
        super(size);
        lista = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            lista.set(i, new ArrayList<>());
        }
    }

    public void addArc(int source, int destination, int weight) {
        ArrayList a = lista.get(source);
        a.add(new Pair(destination, weight));
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList a = lista.get(vertex);
        if(a.isEmpty()){
            return null;
        }
        return a;
    }

    public int getWeight(int source, int destination) {
        ArrayList a = lista.get(source);
        if(a.isEmpty() || destination > a.size()){
            return 0;
        }
        
        for(int i = 0; i < a.size(); i++){
            Pair par = (Pair)a.get(i);
            if ((int)(par.getFirst()) == destination){
                return (int)par.getSecond();
            }
        }
        return 0;
    }

}