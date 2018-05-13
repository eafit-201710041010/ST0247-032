/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;
import javafx.util.*;
import java.lang.*;



import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author luisa
 */
public class Grupos {
    Map<Triplet<Integer,Integer,Double>,Set<Integer>> mapa ; // limites con set de nodos a recorrer
    Set<Triplet<Integer,Integer,Double>> grupos; // Cada pareja tiene los limites de distancia al deposito
    
    public Grupos(Leer leer){
        
        
        for(int i =0;i<leer.disDep.length;i++){
            boolean agregada =false;
           for(Triplet<Integer,Integer,Double> group : grupos){                              
               if(leer.disDep[i] > group.getFirst() && leer.disDep[i] < group.getSecond() ){                   
                   mapa.get(group).add(i);
               
               }
           }
        }
    
    }
    
}
