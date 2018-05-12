
import java.util.ArrayList;

/**
 * Es como un nodo normal solo que permite diferenciarlo de clientes sin necesidad de "tipo"
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Estacion extends Node
{
    ArrayList<Cliente> clientesCercanos = new ArrayList<Cliente>();
    
    public Estacion(int x, int y, int id){
        super(x,y,id);
    }
    
    public void addCliente(Cliente c){
        clientesCercanos.add(c);
    }
    
    /**
     * Convierte lista de los cercanos de objeto a id
     */
    public ArrayList<Integer> getCercanos(){
        ArrayList<Integer> cercanosConId = new ArrayList<Integer>();
        for(Cliente c : clientesCercanos){
            cercanosConId.add(c.getId());
        }
        return cercanosConId;
    }
}
