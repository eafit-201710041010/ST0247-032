package Entrega2;


/**
 * Clase que representa los clientes y estaciones del mapa.
 * 
 * @author Juan José Parra , Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Node
{
    char tipo;
    int x;
    int y;
    int id;
    boolean enRuta;

    public Node(char tipo,int x, int y, int id)
    {
        this.tipo=tipo;
        this.x=x;
        this.y=y;
        this.id=id;
        enRuta=false;
    }
    
    public char getTipo(){
        return tipo;
    }
}
