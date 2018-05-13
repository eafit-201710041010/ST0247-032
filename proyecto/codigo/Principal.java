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
     */

    //Como mem es el objeto que tiene toda la info hay que pasarlo pa toda parte
    void getCercanas(Leer mem){
        //Otra vez, los clientes van desde 1
        for(int i =1;i< mem.clientes.length;i++){            
            getCercana(i,mem);
        }
    }

    /**
     * Devuelve la estación más cercana a un nodo
     */
    public void getCercana(int cliente,Leer mem){
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
    
    /**
     * Devuelve la distancia entre dos puntos
     */
    public double distancia(int cliente , int estacion,Leer mem){
        double op1 =Double.parseDouble(mem.clientes[cliente][2]);
        double op2 =Double.parseDouble(mem.estaciones[estacion][2]);
        double op3 =Double.parseDouble(mem.clientes[cliente][3]);
        double op4 =Double.parseDouble(mem.estaciones[estacion][3]);
        return Math.hypot(op1-op2,op3-op4 );
    }
    
    
    
    
    
    
    // AQUI EMPIEZA LO HARDCORE

    public void rutas(Leer mem){
        // Faltan o no clientes
        boolean faltan = true;
        // Distancia ruta mas larga
        int rutaMasLarga = 0;

        for(String[] s : mem.estaciones ){
            
            Set<Integer> ruta = rutaParcial(mem,Double.parseDouble(mem.info[6][1]),Double.parseDouble(mem.info[9][1]), s);
            
            if(ruta.size() > rutaMasLarga){
                rutaMasLarga = ruta.size();
            }
            //La agrego a nuestra coleccion de rutas
            mem.rutas.add(ruta);
            //Miro si aun faltan verices
            faltan = false;           
            for(boolean a: mem.visitados){
                if(!a){
                    faltan = true;
                }
            }
        }
    }

    //Y aqui intente ... pero me toste
    public Set<Integer> rutaParcial(Leer mem, double Tmax, double cargaTotal, String[] estacion){
        Set<Integer> ruta = new HashSet<>();
        // Agrego el deposito
        ruta.add(0);
        //Valores iniciales
        double tiempo=0.0;
        double bateria=cargaTotal;
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
}