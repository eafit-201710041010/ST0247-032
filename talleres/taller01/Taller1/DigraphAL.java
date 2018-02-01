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
        a.add(destination, weight);
        // complete...
        // recuerde: grafo dirigido!
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        // compl        ete...
        // recuerde: null si no hay!
        return null;
    }

    public int getWeight(int source, int destination) {
        // complete...
        return 0;
    }

}