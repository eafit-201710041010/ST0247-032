

/**
 * Clase que representa los clientes y estaciones del mapa.
 * 
 * @author Juan José Parra , Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Node
{
    int x;
    int y;
    int id;
    boolean enRuta;

    public Node(int x, int y, int id)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        enRuta=false;
    }
    
    public int getId(){
        return id;
    }
}
