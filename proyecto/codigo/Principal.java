
import java.util.*;

/**
 * Solución parcial al proyecto de Estructuras de datos y algoritmos 2
 *
 * @author Juan Jose Parra, Luisa Maria Vásquez
 * @version 22/04/2018
 */
public class Principal {

    /**
     * Guarda los nodos que va leyendo, si es una estación, la guarda en una
     * lista si es un cliente, lo guarda junto a la estación más cercana a este
     */
    //Como mem es el objeto que tiene toda la info hay que pasarlo pa toda parte
    void getCercanas(Leer mem) {
        //Otra vez, los clientes van desde 1
        for (int i = 1; i < mem.clientes.length; i++) {
            getCercana(i, mem);
        }
    }

    /**
     * Devuelve la estación más cercana a un nodo
     */
    public void getCercana(int cliente, Leer mem) {
        double distanciaMin = -1;
        int Estcercano = 0;
        for (int i = 0; i < mem.estaciones.length; i++) {
            double distanciaActual = distancia(cliente, i, mem);
            if (distanciaMin == -1 || distanciaMin > distanciaActual) {
                distanciaMin = distanciaActual;
                Estcercano = i;
            }
        }

        //System.out.println("Le asigne a "+cliente + "la estacion"+ mem.estaciones[Estcercano][0]);
        // En la posicion del cliente , pongo la estacion mas cercana
        mem.cercana[cliente] = Integer.parseInt(mem.estaciones[Estcercano][0]);
        // A la estacion le agrego un cliente cercano
        mem.clixesta.get(Integer.parseInt(mem.estaciones[Estcercano][0])).add(cliente);
    }

    /**
     * Devuelve la distancia entre dos puntos
     */
    public static double distancia(int cliente, int estacion, Leer mem) {
        double op1 = Double.parseDouble(mem.clientes[cliente][2]);
        double op2 = Double.parseDouble(mem.estaciones[estacion][2]);
        double op3 = Double.parseDouble(mem.clientes[cliente][3]);
        double op4 = Double.parseDouble(mem.estaciones[estacion][3]);
        return Math.hypot(op1 - op2, op3 - op4);
    }

    // AQUI EMPIEZA LO HARDCORE
    /**
     *
     * Este método está bien
     *
     */
    public static void rutas(Leer mem) {

        double rutaMasDemorada = 0;
        for (Map.Entry<Integer, Set<Integer>> entry : mem.clixesta.entrySet()) {
            boolean faltan = true;
            
            while (faltan) {
                ArrayList<Integer> ruta = rutaParcial(mem, Double.parseDouble(mem.info[6][1]), Double.parseDouble(mem.info[9][1]), entry.getKey());
                mem.rutas.add(ruta);
                faltan = false;
                for (int a : entry.getValue()) {
                    if (!mem.visitados[a]) {
                        faltan = true;
                    }
                }

            }
        }
        
       
    }

    /**
     * Este está en progreso
     */
    private static ArrayList<Integer> rutaParcial(Leer mem, double Tmax, double cargaTotal, int estacion) {
        ArrayList<Integer> ruta = new ArrayList<>();            //Creo que es mejor cambiar esto por un Arraylist porque los sets no guardan el orden de visita
        int idEstacion = estacion; // Esta id no es el de la tabla sino el general , es decir desde 324
       // System.out.println("Estacion:" + idEstacion);
        // Agrego el deposito y la estación
        ruta.add(0);
        ruta.add(idEstacion);
        //Valores iniciales
        double tiempo = Tmax - mem.tiempoABodega(idEstacion) - mem.timePerEsta[(int)(idEstacion-1-Double.parseDouble(mem.info[1][1]))] ;
        double bateria = cargaTotal ; // Porque en la estacion lleno bateria
        //System.out.println("Tiempo desde est: "+tiempo);
        //System.out.println("Carga desde est: "+bateria);
        int clienteCercano = getCercanoAEstacion(mem, idEstacion); // Id estacion 324+
        //System.out.println("Cliente Cercano: " + clienteCercano);
        if (clienteCercano == -1) {//no encontró clientes cercanos no visitados a la estacion
            System.out.println("Esta estación no tiene más clientes cercanos por visitar");
            return null;
        }

        int origen = idEstacion;
        int destino = clienteCercano;
        
        if (puedoVisitarDeEstacion(mem, bateria, tiempo, origen, destino)) {
            
            ruta.add(clienteCercano);
            mem.visitar(destino);
            tiempo = tiempo - mem.tiempoAEstacion(clienteCercano) - Double.parseDouble(mem.info[8][1]);
            bateria -= mem.cargaAEstacion(clienteCercano);
            origen = destino;
            destino = getCercanoACliente(mem, idEstacion, origen);
            while (destino != -1 && puedoVisitar(mem, bateria, tiempo, origen, destino, idEstacion)) {
               // System.out.println("Voy a agregar alguien ams a la ruta");
                ruta.add(destino);
                mem.visitar(destino);
                tiempo =tiempo - 2*mem.tiempoAEstacion(destino)- Double.parseDouble(mem.info[8][1]) ;
                bateria= bateria - 2*mem.cargaAEstacion(destino);
                origen = destino;
                destino = getCercanoACliente(mem, idEstacion, origen);
            }
            ruta.add(idEstacion);
            ruta.add(0);
        } else {
            
            
            if(mem.estaciones[(int)(idEstacion-1-Double.parseDouble(mem.info[1][1]))][5].equals("2")){
            tiempo = Tmax - mem.tiempoABodega(destino) - mem.tiempoABodega(origen)- mem.tiempoAEstacion(destino)-1.54;   
            bateria = 15200 - 2*mem.cargaAEstacion(destino); 
            if(tiempo>0 && bateria>0){                
                ruta.add(destino);
                ruta.add(0);
                mem.visitar(destino);
            }
            }// Porque en la estacion lleno bateria
            //System.out.println("En el else");
            
            
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
        
        return ruta;
    }
    
    public static boolean clienteVolver(Leer mem,int cliente, int estacion,double tiempo, double bateria){
       
        double carga= bateria -mem.tyb[cliente][1]-mem.tiempoAEstacion(cliente);
        double tiempoT= tiempo - mem.tyb[cliente][0] - mem.cargaAEstacion(cliente) ;
        return (carga>0)&& (tiempoT>0) ? true:false;
    }

    /**
     * Funciona para saber cuanto se demora una ruta agrega el tiempo de
     * translado entre inicial y lo que se demore en el destino
     *
     */
    private static double demoreRuta(ArrayList<Integer> ruta, Leer mem) {
        double tot = 0;
        int inicial = 0;
        for (int a : ruta) {
            if (a != 0 && a <= Double.parseDouble(mem.info[1][1])) {
                //Si voy de cliente o estacion a cliente , agrego recorrido + atencion
                tot += +Double.parseDouble(mem.info[8][1]);
                if (inicial <= Double.parseDouble(mem.info[1][1])) {
                    tot = tot + mem.tiempoEntreClientes(inicial, a);
                } else {
                    tot = tot + mem.tiempoAEstacion(a);
                }
                inicial = a;

            } else if (a != 0 && a > Double.parseDouble(mem.info[1][1])) {
                //Sivoy de cliente a estacion , agrego recorrio y carga
                double a2 = a - Double.parseDouble(mem.info[1][1]) - 1.0;
                tot = tot + mem.tiempoAEstacion(a) + mem.timePerEsta[(int) a2];

            } else if (inicial == 0 && a > Double.parseDouble(mem.info[1][1]) || a == 0 && inicial > Double.parseDouble(mem.info[1][1])) {
                //Si voy desde deposito a estacion o al reves agrego recorrido y carga
                tot = tot + mem.tyb[a][0];
                double a1 = a - Double.parseDouble(mem.info[1][1]) - 1.0;
                tot += mem.timePerEsta[(int) a1];
                inicial = a;
            }

        }
        return tot;
    }

    private static void recargarOVolver() {
        if (true) {
            //volver
        } else {
            //recargar
        }
    }

    /**
     * Me dice si puedo visitar a un cliente partiendo desde la estación más
     * cercana a este Revisa si le alcanza para ir al destino, volver a la
     * estación y volver al depósito
     *
     * Este está bien Falta agregar tiempo de atencion a cliente y de carga
     */
    public static boolean puedoVisitarDeEstacion(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino) {
        double tiempo;
        double carga;
        double atencion = Double.parseDouble(mem.info[8][1]);
        tiempo = 2 * mem.tiempoAEstacion(destino) +atencion+ mem.tiempoABodega(origen) + mem.timePerEsta[origen - Integer.parseInt(mem.info[1][1]) - 1] ;
        carga = 2 * mem.cargaAEstacion(destino);

        tiempoRestante -= tiempo;
        cargaRestante -= carga;
       // System.out.println("Cuando visito primer: " + tiempoRestante);
        //System.out.println("Cuando visito primer: " + cargaRestante);
        if (tiempoRestante > 0 && cargaRestante > 0) { //Si alcanzo a visitar
            return true;
        }
        return false;
    }

    /**
     * Me dice si puedo visitar a un cliente partiendo desde el cliente más
     * cercano a este Revisa si le alcanza para ir al destino, volver a la
     * estación y volver al depósito
     *
     * Este está bien
     */
    private static boolean puedoVisitar(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino, int estacion) {
        double tiempo;
        double carga;
        double atencion = Double.parseDouble(mem.info[8][1]);
        tiempo = atencion+mem.tiempoEntreClientes(origen, destino) + mem.tiempoAEstacion(destino) ;
        carga = mem.cargaEntreClientes(origen, destino) + mem.cargaAEstacion(destino) ;

        tiempoRestante -= tiempo;
        cargaRestante -= carga;
        if (tiempoRestante > 0 && cargaRestante > 0) { //Si alcanzo a visitar
            return true;
        }
        return false;
    }

    /**
     * En este falta algo importante
     */
    private static int getCercanoAEstacion(Leer mem, int estacion) {

        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for (int i : mem.clientesDeEstacion(estacion)) {
            if (mem.tiempoAEstacion(i) < tiempoMin && !mem.visitados[i]) {
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
    private static int getCercanoACliente(Leer mem, int estacion, int cliente) {
        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for (int i : mem.clientesDeEstacion(estacion)) { //For que recorre los clientes cercanos a una estación específica
            if (mem.tiempoEntreClientes(i, cliente) < tiempoMin && !mem.visitados[i]) {//La variable 'i' no es lo que debería ser OJO!!!, AYUDA
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
