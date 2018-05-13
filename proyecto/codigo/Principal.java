import java.util.*;
/**
 * Solución parcial al proyecto de Estructuras de datos y algoritmos 2 
 * 
 * @author Juan Jose Parra, Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Principal
{
   
   
    
    /**
     * Guarda los nodos que va leyendo, 
     * si es una estación, la guarda en una lista
     * si es un cliente, lo guarda junto a la estación más cercana a este
     * 
     * 
     */
   
    //Como mem es el objeto que tiene toda la info hay que pasarlo pa toda parte
    static void getCercanas(Leer mem){
        //Otra vez, los clientes van desde 1
        for(int i =1;i< mem.clientes.length;i++){            
            getCercana(i,mem);
        }
    }
    
    /**
     * Devuelve la estación más cercana a un nodo
     */
    public static  void getCercana(int cliente,Leer mem){
        double distanciaMin = -1;
        int Estcercano = 0;
        for(int i =0;i< mem.estaciones.length;i++){
            double distanciaActual = distancia(cliente, i,mem);
            if(distanciaMin == -1 || distanciaMin > distanciaActual){
                distanciaMin = distanciaActual;
                Estcercano = i;
            }
        }
        
        //System.out.println("Le asigne a "+cliente + "la estacion"+ Estcercano);
        // En la posicion del cliente , pongo la estacion mas cercana
        mem.cercana[cliente]=Estcercano;
        // A la estacion le agrego un cliente cercano
        mem.clixesta.get(Estcercano).add(cliente);
        
        
        
    }
    // Lo que ya tenias .. algo mas largo porque en las matrices todo esta en string
    public static  double distancia(int cliente , int estacion,Leer mem){
        double op1 =Double.parseDouble(mem.clientes[cliente][2]);
        double op2 =Double.parseDouble(mem.estaciones[estacion][2]);
        double op3 =Double.parseDouble(mem.clientes[cliente][3]);
        double op4 =Double.parseDouble(mem.estaciones[estacion][3]);
        return Math.hypot(op1-op2,op3-op4 );
    }
    // AQUI EMPIEZA LO HARDCORE
    
    public static void rutas(Leer mem){
        // Faltan o no clientes
        boolean faltan =true;
        // Distancia ruta mas larga
        int rutaMasLarga=0;
       
        while(faltan){
            //Obtengo ruta parcial
            Set<Integer> ruta1= rutaParcial(mem,Double.parseDouble(mem.info[6][1]));
            if(ruta1.size() > rutaMasLarga){
            rutaMasLarga= ruta1.size();
            }
            //La agrego a nuestra coleccion de rutas
           mem.rutas.add(ruta1);
           //Miro si aun faltan verices
           faltan=false;           
           for(boolean a: mem.visitados){
               if(!a){
                   faltan=true;
               }
           }
        }
    }
    
    //Y aqui intente ... pero me toste 
    public static Set<Integer> rutaParcial(Leer mem, double Tmax){
        Set<Integer> ruta = new HashSet<>();
        // Agrego el deposito
        ruta.add(0);
        //Valores iniciales
           double tiempo=0.0;
           double bateria=16000;
           // Busco cliente cercano
           int clienteCercano=0;
           double minDis=Integer.MAX_VALUE;
           for(int i =1;i<mem.clientes.length;i++){
              if(mem.disDep[i] < minDis && !mem.visitados[i]){
               clienteCercano=i;
               minDis= mem.disDep[i];
              }
           }
          //Resto lo que me gasto yendo hasta alla
           tiempo= tiempo- 0.5 -mem.tyb[clienteCercano][0];
           bateria= bateria - mem.tyb[clienteCercano][1];
           // Y hasta aqui llegue
           //Porfa trata de usar las matrices como estan que si nos ponemos
           // a cambiar entre lo que ya hay y objetos no terminamos nunca
           // Pnesaba buscar vecino mas cercano en el mapa de clixesta en lugar de todos
           // para que la complejidad no nos de tan jodida
           //Yo miro a ver que logro adelantar mañana
           
          return null; 
           
    }
    
//    public DigraphAL getRuta(int inicio){        
//        DigraphAL finall= new DigraphAL(total);
//        
//        for(Node n : estaciones){
//            Estacion e = (Estacion)n;
//            if(e.getCercanos().size() == 0) continue;
//            rutaAux(e.getCercanos(), finall, 0);
//        }
//        
//
//        return finall;
//    }
//
//    public void rutaAux(ArrayList<Integer> grupo, DigraphAL finall,int sig){
//        int a =grupo.get(sig);
//
//        int min=a;
//        int minDi=Integer.MAX_VALUE;
//        for(int i:grupo){
//            if(i != a && distancia(lista.get(i),lista.get(a)) < minDi){
//                min=i;
//                minDi=distancia(lista.get(i),lista.get(a));
//            }
//        }
//        finall.addArc(a,min,minDi);
//        grupo.remove(grupo.indexOf(a));
//        if(!grupo.isEmpty()){
//            rutaAux(grupo,finall,grupo.indexOf(min));
//        }
//    }

    /**
     * Devuelve la distancia entre dos nodos
     */
    
    
    
    
//    public static void main2(){
//        Principal p = new Principal();
//        Node a = new Estacion (0,0,0);    //Estación
//        Node a1 = new Cliente (10,10,1);  //Cliente
//        Node a2 = new Estacion (40,40,2); //Tipo, x, y, id
//        Node a3 = new Cliente (50,50,3);
//        p.addNode(a);
//        p.addNode(a1);
//        p.addNode(a2);
//        p.addNode(a3);
//        
//        p.getCercanas();
//        
//
//        DigraphAL rut = p.getRuta(0);
//
//        rut.toString2();
//        for(int i =0;i<p.total;i++){
//            System.out.println(i+"  "+p.lista.get(i).getClass().getName());
//        }
//    }

}