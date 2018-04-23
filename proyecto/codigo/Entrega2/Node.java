
/**
 * Clase que representa los clientes y estaciones del mapa.
 * 
 * @author Juan José Parra , Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Node
{
    String tipo;
    int x;
    int y;
    boolean enRuta;

    public Node(String tipo,int x, int y)
    {
        this.tipo=tipo;
        this.x=x;
        this.y=y;
        enRuta=false;
    }


}
