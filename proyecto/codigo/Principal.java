
import java.util.*;

/**
 * Implementacion del algoritmo para el ruteo de vehiculos electricos
 *
 * @author Juan Jose Parra, Luisa Maria Vásquez
 * @version 17/05/2018
 */
public class Principal {

    /**
     * Itera por cada cliente para buscar la estacion más cercana a este
     *
     * @param mem Inforación extraída del archivo
     */
    public void getCercanas(Leer mem) {
        for (int i = 1; i < mem.clientes.length; i++) {
            getCercana(i, mem);
        }
    }

    /**
     * Metodo que asigna a un cliente específico una estación cercana
     *
     * @param cliente Cliente del que se quiere saber la estación más cercana
     * @param mem Infomación extraída del archivo
     */
    private void getCercana(int cliente, Leer mem) {
        double distanciaMin = -1;
        int Estcercano = 0;
        for (int i = 0; i < mem.estaciones.length; i++) {
            double distanciaActual = distancia(cliente, i, mem);
            if (distanciaMin == -1 || distanciaMin > distanciaActual) {
                distanciaMin = distanciaActual;
                Estcercano = i;
            }
        }
        mem.cercana[cliente] = Integer.parseInt(mem.estaciones[Estcercano][0]);
        mem.clixesta.get(Integer.parseInt(mem.estaciones[Estcercano][0])).add(cliente);
    }

    /**
     * Método que haya la distancia entre una estación y un cliente en
     * específico
     *
     * @param cliente Cliente
     * @param estacion Estación
     * @param mem Infomación extraída del archivo
     * @return Distancia entre el cliente y la estación
     */
    private static double distancia(int cliente, int estacion, Leer mem) {
        double op1 = Double.parseDouble(mem.clientes[cliente][2]);
        double op2 = Double.parseDouble(mem.estaciones[estacion][2]);
        double op3 = Double.parseDouble(mem.clientes[cliente][3]);
        double op4 = Double.parseDouble(mem.estaciones[estacion][3]);
        return Math.hypot(op1 - op2, op3 - op4);
    }

    /**
     * Método que haya las rutas por cada estación de carga a sus clientes
     * cercanos
     *
     * @param mem Infomación extraída del archivo
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
     * Método que haya la ruta más adecuada entre los clientes cercanos a
     * determinada estación
     *
     * @param mem Infomación extraída del archivo
     * @param Tmax Tiempo máximo de la ruta
     * @param cargaTotal Carga con la que se inicia la ruta
     * @param estacion Estación sobre la que se costruirá la ruta
     * @return Ruta que recorre una parte o todos los clientes cercanos a una
     * estación
     */
    private static ArrayList<Integer> rutaParcial(Leer mem, double Tmax, double cargaTotal, int estacion) {
        ArrayList<Integer> ruta = new ArrayList<>();
        int idEstacion = estacion;
        ruta.add(0);
        ruta.add(idEstacion);

        double tiempo = Tmax - mem.tiempoABodega(idEstacion) - mem.timePerEsta[(int) (idEstacion - 1 - Double.parseDouble(mem.info[1][1]))];
        double aux = cargaTotal - mem.cargaABodega(estacion) + mem.maxCarga;
        double bateria = aux > Double.parseDouble(mem.info[9][1]) ? Double.parseDouble(mem.info[9][1]) : aux;

        int clienteCercano = getCercanoAEstacion(mem, idEstacion);
        if (clienteCercano == -1) {
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

                ruta.add(destino);
                mem.visitar(destino);
                tiempo = tiempo - mem.tiempoAEstacion(destino) - Double.parseDouble(mem.info[8][1]);
                bateria = bateria - mem.cargaAEstacion(destino);
                origen = destino;
                destino = getCercanoACliente(mem, idEstacion, origen);
            }
            if (bateria > mem.cargaABodega(idEstacion)) {
                ruta.add(0);
            } else {
                ruta.add(idEstacion);
            }

        } else {

            if (mem.estaciones[(int) (idEstacion - 1 - Double.parseDouble(mem.info[1][1]))][5].equals("2")) {
                tiempo = Tmax - mem.tiempoABodega(destino) - mem.tiempoABodega(origen) - mem.tiempoAEstacion(destino) - 1.26;
                double aux2 = (bateria + mem.maxCarga) > Double.parseDouble(mem.info[9][1]) ? Double.parseDouble(mem.info[9][1]) : bateria + mem.maxCarga;
                bateria = aux2 - 2 * mem.cargaAEstacion(destino);
                if (tiempo > 0 && bateria > 0) {
                    ruta.add(destino);
                    ruta.add(0);
                    mem.visitar(destino);
                }
            }

        }

        return ruta;
    }

    /**
     * Método que determina si es posible visitar un cliente desde su estacion
     * más cercana y volver al depósito con los valores de tiempo y energía que
     * se tienen
     *
     * @param mem Infomación extraída del archivo
     * @param cargaRestante Carga actual de camión
     * @param tiempoRestante Tiempo que queda para el recorrido
     * @param origen Estación de origen
     * @param destino Cliente destino
     * @return Si es posible o no hacer este recorrido
     */
    private static boolean puedoVisitarDeEstacion(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino) {
        double tiempo;
        double carga;
        double atencion = Double.parseDouble(mem.info[8][1]);
        tiempo = 2 * mem.tiempoAEstacion(destino) + atencion + mem.tiempoABodega(origen) + mem.timePerEsta[origen - Integer.parseInt(mem.info[1][1]) - 1];
        carga = 2 * mem.cargaAEstacion(destino) + mem.cargaABodega(origen);

        tiempoRestante -= tiempo;
        cargaRestante = cargaRestante - carga + mem.maxCarga;

        if (tiempoRestante > 0 && cargaRestante > 0) {
            return true;
        }
        return false;
    }

    /**
     * Método que determina si es posible visitar un cliente desde un cliente cercano
     * y volver al depósito con los valores de tiempo y energía que se tienen
     *
     * @param mem Infomación extraída del archivo
     * @param cargaRestante Carga actual de camión
     * @param tiempoRestante Tiempo que queda para el recorrido
     * @param origen Cliente de origen
     * @param destino Cliente destino
     * @param estacion Estación cercana a ambos clientes
     * @return Si es posible o no hacer este recorrido
     */
    private static boolean puedoVisitar(Leer mem, double cargaRestante, double tiempoRestante, int origen, int destino, int estacion) {
        double tiempo;
        double carga;
        double atencion = Double.parseDouble(mem.info[8][1]);
        tiempo = atencion + mem.tiempoEntreClientes(origen, destino) + mem.tiempoAEstacion(destino) + mem.tiempoABodega(estacion) + mem.timePerEsta[estacion - Integer.parseInt(mem.info[1][1]) - 1];
        carga = mem.cargaEntreClientes(origen, destino) + mem.cargaAEstacion(destino) + mem.cargaABodega(estacion);

        tiempoRestante -= tiempo;
        cargaRestante = cargaRestante - carga + mem.maxCarga;
        if (tiempoRestante > 0 && cargaRestante > 0) { //Si alcanzo a visitar
            return true;
        }
        return false;
    }

    /**
     * Método que halla el cliente más cercano a una estación
     * @param mem Infomación extraída del archivo
     * @param estacion Estación
     * @return Cliente más cercano a la estación
     */
    private static int getCercanoAEstacion(Leer mem, int estacion) {

        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for (int i : mem.clientesDeEstacion(estacion)) {
            if (mem.tiempoAEstacion(i) < tiempoMin && !mem.visitados[i]) {              
                clienteCercano = i;
                tiempoMin = mem.tiempoAEstacion(i);
            }
        }
        return clienteCercano;
    }

    /**
     * Método que obtiene el cliente más cercano a otro cliente
     * @param mem Infomación extraída del archivo
     * @param estacion Estación cercana al cliente
     * @param cliente Cliente del que se busca su vecino más cercano
     * @return Cliente más cercano al cliente original
     */
    private static int getCercanoACliente(Leer mem, int estacion, int cliente) {
        int clienteCercano = -1;
        double tiempoMin = Integer.MAX_VALUE;
        for (int i : mem.clientesDeEstacion(estacion)) { 
            if (mem.tiempoEntreClientes(i, cliente) < tiempoMin && !mem.visitados[i]) {
                clienteCercano = i;
                tiempoMin = mem.tiempoAEstacion(i);
            }
        }
        return clienteCercano;
    }
}
