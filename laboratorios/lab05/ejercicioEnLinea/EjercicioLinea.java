
import java.util.*;
import javafx.util.Pair;
import java.lang.Math;
import java.io.*;

/**
 * Solución al ejercicio en linea del Lab 5
 * 
 * @author Juan Jose Parra , Luisa Maria Vasquez
 * @version 29/04/2018
 */ 

public class EjercicioLinea {

    public static int distance( ArrayList<Pair<Integer,Integer>> recoger,int[][] mapa, int inix , int iniy){
        Pair<Integer,Integer> inicial = new Pair(inix,iniy);
        Pair<Integer,Integer> inicialg = new Pair(inix,iniy);
        double distMin=Integer.MAX_VALUE;
        int indexMasCercano =0;
        int totes=0;
        while(!recoger.isEmpty()){            
            for(Pair<Integer,Integer> a : recoger){                               
                double d =distancia(inicial,a);
                if(d<distMin){
                    distMin=d;
                    indexMasCercano = recoger.indexOf(a);
                }                        
            }
            totes=totes+ Math.abs(inicial.getKey()-recoger.get(indexMasCercano).getKey())+
            Math.abs(inicial.getValue()-recoger.get(indexMasCercano).getValue());
            inicial=recoger.get(indexMasCercano);
            recoger.remove(recoger.get(indexMasCercano));
            distMin=Integer.MAX_VALUE;
            indexMasCercano =0;
        }
        return totes+ Math.abs(inicial.getKey()-inicialg.getKey())+Math.abs(inicial.getValue()-inicialg.getValue());

    }

    private static double distancia(Pair<Integer,Integer> a,Pair<Integer,Integer> b){
        double d = Math.hypot(Math.abs(a.getKey()-b.getKey()), Math.abs(a.getValue()-b.getValue()));
        return d;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        System.out.println (" ");
        String esc = sc.readLine();
        int escenarios = Integer.parseInt(esc);
        for(int i=0;i<escenarios;i++){
            String tamaño = sc.readLine();
            String[] lxa = tamaño.split(" ");
            int[][] mapa= new int [Integer.parseInt(lxa[0])][Integer.parseInt(lxa[1])];
            esc = sc.readLine();
            String[] aux = esc.split(" ");
            esc = sc.readLine(); 
            int desechos = Integer.parseInt(esc);
            ArrayList<Pair<Integer,Integer>> vertices = new ArrayList();
            for(int j=0;j<desechos;j++){
                String posicion = sc.readLine();
                String [] partido = posicion.split(" ");
                Pair<Integer,Integer> a = new Pair(Integer.parseInt(partido[0]),Integer.parseInt(partido[1]));
                mapa[Integer.parseInt(partido[0])][Integer.parseInt(partido[1])]= 1;
                vertices.add(a);
            }
            System.out.println("The shortest path has length: "+distance(vertices,mapa,Integer.parseInt(aux[0]),Integer.parseInt(aux[1])));

        }

    }

}