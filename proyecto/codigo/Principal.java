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
    /**
     * 
     * Este método está bien
     * 
     */
    private void rutas(Leer mem){
        // Faltan o no clientes
        boolean faltan = true;
        // Distancia ruta mas larga
        int rutaMasLarga = 0;

        //Vamos a mirar una ruta por cada estación
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

    
    /**
     * Este está en progreso
     */
    private Set<Integer> rutaParcial(Leer mem, double Tmax, double cargaTotal, String[] estacion){
        Set<Integer> ruta = new HashSet<>();            //Creo que es mejor cambiar esto por un Arraylist porque los sets no guardan el orden de visita
        int idEstacion = Integer.parseInt(estacion[0]);
        
        // Agrego el deposito y la estación
        ruta.add(0);
        ruta.add(idEstacion+324);
        //Valores iniciales
        double tiempo = Tmax-mem.tiempoABodega(idEstacion);
        double bateria = cargaTotal-mem.cargaABodega(idEstacion);
        
        int clienteCercano = getCercanoAEstacion(mem, idEstacion);
        if(clienteCercano == -1){//no encontró clientes cercanos no visitados a la estacion
            System.out.println("Esta estación no tiene más clientes cercanos por visitar");
            return null;
        }
        
        
        int origen = idEstacion;
        int destino = clienteCercano;
        if(puedoVisitarDeEstacion(mem, bateria, tiempo, origen, destino)){
            ruta.add(clienteCercano);
            mem.visitar(destino);
            tiempo -= mem.tiempoAEstacion(clienteCercano);
            bateria -= mem.cargaAEstacion(clienteCercano);
            origen = destino;
            destino = getCercanoACliente(mem, idEstacion, origen);
            while(destino != -1 && puedoVisitar(mem, bateria, tiempo, origen, destino, idEstacion)){
                ruta.add(clienteCercano);
                mem.visitar(destino);
                tiempo -= mem.tiempoAEstacion(clienteCercano);
                bateria -= mem.cargaAEstacion(clienteCercano);
                origen = destino;
                destino = getCercanoACliente(mem, idEstacion, origen);
            }
        } else {
            System.out.println("Hay algo mal");
        }
        
        /*
         * El siguiente método no lo alcancé a crear pero debería decidir si va a recargar la 
         * batería o va a volver al depósito
         * 
         * Toca hacer que compare el tiempo restante con el tiempo de carga y de visita a los nodos que faltan de la estación actual
         * (si es que faltan)
         * 
         * Si decide volver, entonces se crea una nueva ruta que vaya del depósito a la estación acutal y de ahí, 
         * recorra los clientes que quedan por mirar
         */
        recargarOVolver();
        return null; 
    }
    
    private void recargarOVolver(){
        if(true){
            //volver
        } else {
            //recargar
        }
    }
    
    /**
     * Me dice si puedo visitar a un cliente partiendo desde la estación más cercana a este
     * Revisa si le alcanza para ir al destino, volver a la estación y volver al depósito
     * 
     * Este está bien
     */
    private boolean puedoVisitarDeEstacion(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino){
        double tiempo;
        double carga;

        tiempo = mem.tiempoAEstacion(destino) + mem.tiempoAEstacion(destino) + mem.tiempoABodega(origen);
        carga = mem.cargaAEstacion(destino) + mem.cargaAEstacion(destino) + mem.cargaABodega(origen);

        tiempoRestante -= tiempo;
        cargaRestante -= carga;
        if(tiempoRestante > 0 && cargaRestante > 0){ //Si alcanzo a visitar
            return true;
        } 
        return false;
    }
    
    /**
     * Me dice si puedo visitar a un cliente partiendo desde el cliente más cercano a este
     * Revisa si le alcanza para ir al destino, volver a la estación y volver al depósito
     * 
     * Este está bien
     */
    private boolean puedoVisitar(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino, int estacion){
        double tiempo;
        double carga;

        tiempo = mem.tiempoEntreClientes(origen, destino) + mem.tiempoAEstacion(destino) + mem.tiempoABodega(estacion);
        carga = mem.cargaEntreClientes(origen, destino) + mem.cargaAEstacion(destino) + mem.cargaABodega(estacion);

        tiempoRestante -= tiempo;
        cargaRestante -= carga;
        if(tiempoRestante > 0 && cargaRestante > 0){ //Si alcanzo a visitar
            return true;
        } 
        return false;
    }

    /**
     * En este falta algo importante
     */
    private int getCercanoAEstacion(Leer mem, int estacion){
        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for(int i = 1; i < mem.clientesDeEstacion(estacion).size(); i++){ 
            if(mem.tiempoAEstacion(i) < tiempoMin && !mem.visitados[i]){
                /*
                 * ESTE IF ESTÁ MALO.... NO SÉ COMO LO TIENES GUARDADO EN LA CLASE "LEER"
                 * HAZ QUE COMPARE LOS CLIENTES CERCANOS A LA ESTACIÓN ESPECÍFICA DE LA ENTRADA "estacion"
                 * La variable i, va de 1 a la cantidad de clientes, pero sin importar la estación, va a mirar los mismo siempre
                 */
                clienteCercano = i;
                tiempoMin = mem.tiempoAEstacion(i);
            }
        }
        return clienteCercano;
    }
    
    /**
     * En este falta algo importante
     */
    private int getCercanoACliente(Leer mem, int estacion, int cliente){
        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for(int i = 1; i < mem.clientesDeEstacion(estacion).size(); i++){ //For que recorre los clientes cercanos a una estación específica
            if(mem.tiempoEntreClientes(i,cliente) < tiempoMin && !mem.visitados[i]){//La variable 'i' no es lo que debería ser OJO!!!, AYUDA
                /*
                 * ESTE IF ESTÁ MALO.... NO SÉ COMO LO TIENES GUARDADO EN LA CLASE "LEER"
                 * HAZ QUE RECORRA LOS CLIENTES CERCANOS A LA ESTACIÓN ESPECÍFICA DE LA ENTRADA "estacion"
                 * La variable i, va de 1 a la cantidad de clientes, pero sin importar la estación, va a mirar los mismo siempre
                 */
                clienteCercano = i;
                tiempoMin = mem.tiempoAEstacion(i);
            }
        }
        return clienteCercano;
    }
}