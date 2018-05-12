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
    int total;

    public void addNode(Node s){
        lista.put(total,s);
        total++;
    }

    public DigraphAL getRuta(int inicio){
        ArrayList<Integer> parcial= new ArrayList();
        DigraphAL finall= new DigraphAL(total);
        parcial.add(inicio);
        for(int i =0;i<total;i++){
            if(lista.get(i).tipo.equals("Estacion")){
                parcial.add(i);
            } 
        }

        for(int i =0;i<parcial.size()-1;i++){
            ArrayList<Integer> grupo= new ArrayList();
            grupo.add(parcial.get(i));
            for(int j=0;j<lista.size();j++){

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

    public int distancia(Node a , Node b){
        return (int)Math.hypot(a.x-b.x, a.y-b.y);
    }
    
    public static void main(String[] args){
        Principal p = new Principal();
        Node a = new Node ("Estacion",0,0);
        Node a1 = new Node ("Cliente",10,10);
        Node a2 = new Node ("Estacion",40,40);
        Node a3 = new Node ("Cliente",20,20);
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
