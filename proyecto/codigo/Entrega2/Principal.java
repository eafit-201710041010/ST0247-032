package Entrega2;

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
        } else if(s instanceof Cliente){
            getCercana((Cliente)s);
            clientes.add(s);
        } else {
            System.err.println("No se reconoce el tipo de nodo");
        }
        lista.put(total,s);
        total++;
    }

    /**
     * Devuelve la estación más cercana a un nodo
     */
    public Node getCercana(Cliente n){
        int distanciaMin = -1;
        Estacion cercano = null;
        for(int i = 0; i < estaciones.size(); i++){
            int distanciaActual = distancia(n, estaciones.get(i));
            if(distanciaMin == -1 || distanciaMin > distanciaActual){
                distanciaMin = distanciaActual;
                cercano = (Estacion)estaciones.get(i);
            }
        }
        n.setEstacion(cercano);
        cercano.addCliente(n);
        return cercano;
    }
    
    public DigraphAL getRuta(int inicio){        
        DigraphAL finall= new DigraphAL(total);
        ArrayList<Integer> parcial= new ArrayList();
        parcial.add(inicio);
        for(int i =0;i<total;i++){
            if(lista.get(i).getTipo() == 'E'){
                parcial.add(i);
            } 
        }
        for(int i = 0; i < estaciones.size()-1; i++){
            ArrayList<Integer> grupo = new ArrayList();
            grupo.add(parcial.get(i));
            for(int j=0;j<lista.size();j++){
                //Si la distancia entre estaciones es menor a la distancia entre estación y cliente
                if(distancia(lista.get(i),lista.get(i+1)) < distancia(lista.get(i),lista.get(j))){
                    grupo.add(j);

                }
            }
            grupo.add(i+1);
            rutaAux(grupo,finall,0);
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
        Node a = new Estacion ('E',0,0);    //Estación
        Node a1 = new Cliente ('C',10,10);  //Cliente
        Node a2 = new Estacion ('E',40,40);
        Node a3 = new Cliente ('C',20,20);
        p.addNode(a);
        p.addNode(a1);
        p.addNode(a2);
        p.addNode(a3);
        DigraphAL rut = p.getRuta(0);

        rut.toString2();
        for(int i =0;i<p.total;i++){
            System.out.println(i+"  "+p.lista.get(i).tipo);
        }
    }

}
