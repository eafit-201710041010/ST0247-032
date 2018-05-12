
import java.io.*;
import java.util.*;
/**
 * Solución parcial al proyecto de Estructuras de datos y algoritmos 2 
 * 
 * @author Juan Jose Parra, Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Principal
{
    Hashtable<Integer,Node> lista = new Hashtable<Integer,Node>();
    ArrayList<Node> estaciones = new ArrayList<Node>();
    ArrayList<Node> clientes = new ArrayList<Node>();
    int total;
    public Principal(){
        total = 0;
    }
    
    /**
     * Guarda los nodos que va leyendo, 
     * si es una estación, la guarda en una lista
     * si es un cliente, lo guarda junto a la estación más cercana a este
     * 
     * 
     */
    public void addNode(Node s){
        if(s instanceof Estacion){
            estaciones.add(s);
                                                        //System.out.println("Añadiendo e");
        } else if(s instanceof Cliente){
            clientes.add(s);
                                                        //System.out.println("Añadiendo c");
        } else {
            System.err.println("No se reconoce el tipo de nodo");
        }
        lista.put(total,s);
        total++;
    }
    
    void getCercanas(){
        for(Node c: clientes){
            getCercana((Cliente)c);
        }
    }
    
    /**
     * Devuelve la estación más cercana a un nodo
     */
    public Node getCercana(Cliente n){
        int distanciaMin = -1;
        Estacion cercano = null;
        for(Node i: estaciones){
            int distanciaActual = distancia(n, i);
            if(distanciaMin == -1 || distanciaMin > distanciaActual){
                distanciaMin = distanciaActual;
                cercano = (Estacion)i;
            }
        }
        n.setEstacion(cercano);
        cercano.addCliente(n);
                                        //System.out.println(n.id + " cliente -- estacion " + cercano.id);
        return cercano;
    }
    
    public DigraphAL getRuta(int inicio){        
        DigraphAL finall= new DigraphAL(total);
        
        for(Node n : estaciones){
            Estacion e = (Estacion)n;
            if(e.getCercanos().size() == 0) continue;
            rutaAux(e.getCercanos(), finall, 0);
        }
        

        return finall;
    }

    public void rutaAux(ArrayList<Integer> grupo, DigraphAL finall,int sig){
        int a =grupo.get(sig);

        int min=a;
        int minDi=Integer.MAX_VALUE;
        for(int i:grupo){
            if(i != a && distancia(lista.get(i),lista.get(a)) < minDi){
                min=i;
                minDi=distancia(lista.get(i),lista.get(a));
            }
        }
        finall.addArc(a,min,minDi);
        grupo.remove(grupo.indexOf(a));
        if(!grupo.isEmpty()){
            rutaAux(grupo,finall,grupo.indexOf(min));
        }
    }

    /**
     * Devuelve la distancia entre dos nodos
     */
    public int distancia(Node a , Node b){
        return (int)Math.hypot(a.x-b.x, a.y-b.y);
    }
    
    
    
    public static void main(String[] args){
        Principal p = new Principal();
        Node a = new Estacion (0,0,0);    //Estación
        Node a1 = new Cliente (10,10,1);  //Cliente
        Node a2 = new Estacion (40,40,2); //Tipo, x, y, id
        Node a3 = new Cliente (50,50,3);
        p.addNode(a);
        p.addNode(a1);
        p.addNode(a2);
        p.addNode(a3);
        
        p.getCercanas();
        

        DigraphAL rut = p.getRuta(0);

        rut.toString2();
        for(int i =0;i<p.total;i++){
            System.out.println(i+"  "+p.lista.get(i).getClass().getName());
        }
    }

}
