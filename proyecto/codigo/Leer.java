
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Clase principal que lee el archivo de clientes y basado en sus posiciones
 * almacena infomación útil para el cálculo de rutas
 *
 * @author Juan Jose Parra, Luisa Maria Vasquez
 * @version 17/05/2018
 */
public class Leer {

    /**
     * Información básica del set de datos, tal como total de nodos, total de
     * clientes, etc.
     */
    String[][] info;
    /**
     * Matriz con toda la información de los clientes, tal como Id, posición en
     * x y en y
     */
    String[][] clientes;
    /**
     * Matriz con toda la información de las estaciones, tal como Id, posición
     * en x y y, tipo de estacion, etc.
     */
    String[][] estaciones;
    /**
     * Información básica del deposito (posición)
     */
    String[] deposito;
    /**
     * Distancia de cada nodo al depósito
     */
    double[] disDep;
    /**
     * Matriz que guarda el tiempo y la batería que se gasta un camión yendo
     * desde cada nodo al deposito
     */
    double[][] tyb;
    /**
     * Arreglo que guarda la estación más cercana a cada cliente
     */
    int[] cercana;
    /**
     * Mapa que contiene un set de clientes cercanos por cada estacion de carga
     */
    Map<Integer, Set<Integer>> clixesta;
    /**
     * Arreglo que contiene si cada cliente ya ha sido visitado o no
     */
    boolean[] visitados;
    /**
     * Set que guarda todas las rutas generadas por el algoritmo
     */
    Set<ArrayList<Integer>> rutas;
    /**
     * Arreglo de los posibles tiempos de carga que puede tener una estación
     */
    double[] timeCharge;
    /**
     * Arreglo que guarda el tiempo de carga completa de cada estación
     */
    double[] timePerEsta;
    
    /**
     * Máximo de carga adquirida en estación
     */
    double maxCarga;

    /**
     * Constructor del objeto que tendrá toda la información del set de datos
     *
     * @param info Información básica del set de datos
     * @param n Número total de clientes
     * @param s Número total de estaciones
     * @param depot Información del depósito
     * @param tot Total de nodos
     */
    public Leer(String[][] info, int n, int s, String[] depot, int tot) {
        clixesta = new HashMap<>();  
        this.info = info; 
        clientes = new String[n][6]; 
        estaciones = new String[s][6]; 
        deposito = depot; 
        disDep = new double[tot]; 
        disDep[0] = 0; 
        tyb = new double[tot][2]; 
        cercana = new int[n]; 
        visitados = new boolean[n]; 
        rutas = new HashSet<>(); 
        timeCharge = new double[3]; 
        timePerEsta = new double[s]; 

    }

    /**
     * Método que itera sobre el archivo de texto y llena la información
     * necesaria para el cáculo de la ruta
     *
     * @param filename Fichero del cual se leerá la información
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void llenarInfo(String filename) throws FileNotFoundException, IOException {
        BufferedReader lector = new BufferedReader(new FileReader(filename));

        for (int i = 0; i < 14; i++) {
            lector.readLine();
        }

        String linea = lector.readLine();
        int n = Integer.parseInt(info[1][1]) + 1;
        int i = 1;

        while (linea != null && i < n) {
            clientes[i] = linea.split(" ");
            //Ignora la linea que sigue , es solo para mirar que leyera bien
            String tipo = clientes[i][4].equals("c") ? "Cliente" : "Estacion";
            // Hallar distancia al deposito y la agrego al arreglo
            double xd = Double.parseDouble(deposito[2]);
            double x1 = Double.parseDouble(clientes[i][2]);
            double yd = Double.parseDouble(deposito[3]);
            double y1 = Double.parseDouble(clientes[i][3]);
            disDep[Integer.parseInt(clientes[i][0])] = Math.sqrt(Math.pow(xd - x1, 2) + Math.pow(yd - y1, 2));
            tyb[Integer.parseInt(clientes[i][0])][0] = disDep[Integer.parseInt(clientes[i][0])] / Double.parseDouble(info[5][1]);
            tyb[Integer.parseInt(clientes[i][0])][1] = disDep[Integer.parseInt(clientes[i][0])] * Double.parseDouble(info[4][1]);

            linea = lector.readLine();
            i++;
        }

      
        int s = Integer.parseInt(info[2][1]);
        int j = 0;
        while (linea != null && j < s) {
            
            estaciones[j] = linea.split(" ");
            
            String tipo = estaciones[j][4].equals("c") ? "Cliente" : "Estacion";
            
            double xd = Double.parseDouble(deposito[2]);
            double x1 = Double.parseDouble(estaciones[j][2]);
            double yd = Double.parseDouble(deposito[3]);
            double y1 = Double.parseDouble(estaciones[j][3]);

          
            disDep[Integer.parseInt(estaciones[j][0])] = Math.sqrt(Math.pow(xd - x1, 2) + Math.pow(yd - y1, 2));
            tyb[Integer.parseInt(estaciones[j][0])][0] = disDep[Integer.parseInt(estaciones[j][0])] / Double.parseDouble(info[5][1]);
            tyb[Integer.parseInt(estaciones[j][0])][1] = disDep[Integer.parseInt(estaciones[j][0])] * Double.parseDouble(info[4][1]);
          
            clixesta.put(Integer.parseInt(estaciones[j][0]), new HashSet());

            
            linea = lector.readLine();
            j++;
        }
       
        for (int t = 0; t < 2; t++) {
            lector.readLine();
        }

        for (int tc = 0; tc < 3; tc++) {

            String linea2 = lector.readLine();

            String[] arr = linea2.split(" ");
            timeCharge[tc] = Double.parseDouble(arr[1]);
        }
        for (int ts = 0; ts < estaciones.length; ts++) {
            if (estaciones[ts][5].equals("0")) {
                timePerEsta[ts] = timeCharge[0];
            } else if (estaciones[ts][5].equals("1")) {
                timePerEsta[ts] = timeCharge[1];
            } else {
                timePerEsta[ts] = timeCharge[2];
            }

        }
        
        for (int t = 0; t < 3; t++) {
            lector.readLine();
        }
        
        String add = lector.readLine();
        
        String arr2[] = add.split(" ");
         maxCarga=Double.parseDouble(arr2[1]);

    }

    /**
     * Método que pone coo visitado un nodo
     * @param cliente Cliente visitado
     */
    public void visitar(int cliente) {
        visitados[cliente] = true;
    }

    /**
     * Método que obtiene los clientes cercanos a una estación
     * @param estacion Estación de la que se desea saber los clientes cercanos
     * @return Set de clientes cercanos a la estación
     */
    public Set<Integer> clientesDeEstacion(int estacion) {
        return clixesta.get(estacion);
    }

    /**
     * Devuelve tiempo a bodega del nodo ingresado
     * @param estacion Nodo del que se quiere saber el tiempo a la bodega
     * @return Tiempo a bodega del nodo
     */
    public double tiempoABodega(int estacion) {
        return tyb[estacion][0];
    }

    /**
     * Devuelve batería a bodega del nodo ingresado
     * @param estacion Nodo del que se quiere saber la carga que se gasta yendo a la bodega
     * @return Carga que se gasta de la bodega a nodo
     */
    public double cargaABodega(int estacion) {
        return tyb[estacion][1];
    }

    /**
     * Devuelve el tiempo que tarda en ir del cliente origen al cliente destino
     * @param origen Cliente 1
     * @param destino Cliente 2
     * @return  Tiempo que se demora de ir de 1 a 2
     */
    public double tiempoEntreClientes(int origen, int destino) {
        double op1 = Double.parseDouble(clientes[origen][2]);
        double op2 = Double.parseDouble(clientes[destino][2]);
        double op3 = Double.parseDouble(clientes[origen][3]);
        double op4 = Double.parseDouble(clientes[destino][3]);
        double dis = Math.hypot(op1 - op2, op3 - op4);
        return dis / Double.parseDouble(info[5][1]);
    }

    /**
     * Devuelve el tiempo que tarda en ir del cliente a estacion mas cercana
     * @param cliente Cliente de que se quiere saber el tiempo a la estación más cercana
     * @return Tiempo a la estación más cercana
     */
    public double tiempoAEstacion(int cliente) {
        int estacion = 0;
        for (Map.Entry<Integer, Set<Integer>> entry : clixesta.entrySet()) {
            if (entry.getValue().contains(cliente)) {
                estacion = entry.getKey();
            }
        }
        double op1 = Double.parseDouble(clientes[cliente][2]);
        double op2 = Double.parseDouble(estaciones[estacion - Integer.parseInt(info[1][1]) - 1][2]);
        double op3 = Double.parseDouble(clientes[cliente][3]);
        double op4 = Double.parseDouble(estaciones[estacion - Integer.parseInt(info[1][1]) - 1][3]);
        double dis = Math.hypot(op1 - op2, op3 - op4);
        return dis / Double.parseDouble(info[5][1]);
    }

    /**
     * Devuelve la batería que consume en ir del cliente origen al cliente destino
     * @param origen Cliente origen
     * @param destino Cliente destino
     * @return Carga gastada yendo de un cliente al otro
     */
    public double cargaEntreClientes(int origen, int destino) {
        double op1 = Double.parseDouble(clientes[origen][2]);
        double op2 = Double.parseDouble(clientes[destino][2]);
        double op3 = Double.parseDouble(clientes[origen][3]);
        double op4 = Double.parseDouble(clientes[destino][3]);
        double dis = Math.hypot(op1 - op2, op3 - op4);
        return dis * Double.parseDouble(info[4][1]);
    }

    /**
     * Devuelve la batería que consume en ir del cliente a estacion mas cercana
     * @param cliente Cliente de que se quiere saber la carga gastada yendo a la estación más cercana
     * @return Carga consumida yendo a la estación más cercana
     */
    public double cargaAEstacion(int cliente) {
        int estacion = 0;
        for (Map.Entry<Integer, Set<Integer>> entry : clixesta.entrySet()) {
            if (entry.getValue().contains(cliente)) {
                estacion = entry.getKey();
            }
        }
        double op1 = Double.parseDouble(clientes[cliente][2]);
        double op2 = Double.parseDouble(estaciones[estacion - Integer.parseInt(info[1][1]) - 1][2]);
        double op3 = Double.parseDouble(clientes[cliente][3]);
        double op4 = Double.parseDouble(estaciones[estacion - Integer.parseInt(info[1][1]) - 1][3]);
        double dis = Math.hypot(op1 - op2, op3 - op4);
        return dis * Double.parseDouble(info[4][1]);
    }

    /**
     * Método que imprime las rutas halladas
     */
    public void imprimirRutas2() {
        DecimalFormat df = new DecimalFormat("#.##");
        int ruta = 1;
        for (ArrayList<Integer> a : rutas) {
            System.out.print("Ruta " + ruta + " : ");

            for (int i = 0; i < a.size(); i++) {

                if (a.size() == 4) {

                    if (a.get(i) == 0 && i + 1 != a.size()) {
                        System.out.print(a.get(i) + "(0 h) ,");
                    } else if (a.get(i) > Integer.parseInt(info[1][1])) {
                        System.out.print(a.get(i) + "(" + df.format(tiempoABodega(a.get(i)) + 1.54) + " h) ");
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i) != 0) {
                        System.out.print(a.get(i) + "(" + df.format(tiempoAEstacion(a.get(i)) + 0.5) + " h) ");
                    } else if (a.get(i) == 0 && i + 1 == a.size()) {
                        System.out.print(a.get(i) + "(" + df.format(tiempoABodega(a.get(i - 1))) + " h) ");
                    }

                }
                if (a.size() > 4) {

                    if (a.get(i) == 0 && i + 1 != a.size()) {
                        System.out.print(a.get(i) + "(0 h) ,");
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i - 1) <= Integer.parseInt(info[1][1]) && a.get(i)!=0) {
                        System.out.print(a.get(i) + "(" + (df.format(0.5 + tiempoEntreClientes(a.get(i), a.get(i - 1)))) + " h) ");
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i - 1) > Integer.parseInt(info[1][1]) && a.get(i) != 0) {
                        System.out.print(a.get(i) + "(" + df.format(0.5 + tiempoAEstacion(a.get(i))) + " h) ");
                    } else if (a.get(i) > Integer.parseInt(info[1][1]) && a.get(i - 1) <= Integer.parseInt(info[1][1]) && a.get(i - 1) != 0) {
                        System.out.print(a.get(i) + "(" + df.format(timePerEsta[a.get(i) - Integer.parseInt(info[1][1]) - 1] + tiempoAEstacion(a.get(i - 1))) + " h) ");
                    } else if (a.get(i) > Integer.parseInt(info[1][1]) && a.get(i - 1) == 0) {
                        System.out.print(a.get(i) + "(" + df.format(timePerEsta[a.get(i) - Integer.parseInt(info[1][1]) - 1] + tiempoABodega(a.get(i))) + " h) ");
                    } else {
                        System.out.print(a.get(i) + "(" + df.format(tiempoABodega(a.get(i - 1))) + " h) ");
                    }
                }

            }
            System.out.println(" ");
            ruta++;
        }

    }
    
    /**
     * Método que calcula el tiempo total de las rutas, la ruta más demorada y la más breve
     */
    public void tiempoTotal() {
        double maxDemore=0;
        double minDemore=Integer.MAX_VALUE;
        double tot=0;
        int rutaM =0;
        int rutaMin=0;
        int ruta =1;
        for (ArrayList<Integer> a : rutas) {
            
             double demoreParcial=0;
            for (int i = 0; i < a.size(); i++) {
               
                if (a.size() == 4) {

                    if (a.get(i) == 0 && i + 1 != a.size()) {
                        demoreParcial+=0;
                    } else if (a.get(i) > Integer.parseInt(info[1][1])) {
                        demoreParcial+=tiempoABodega(a.get(i)) + 1.54;
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i) != 0) {
                        demoreParcial+=tiempoAEstacion(a.get(i)) + 0.5;
                    } else if (a.get(i) == 0 && i + 1 == a.size()) {
                        demoreParcial+=tiempoABodega(a.get(i - 1));
                    }

                }
                if (a.size() > 4) {

                    if (a.get(i) == 0 && i + 1 != a.size()) {
                        demoreParcial+=0;
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i - 1) <= Integer.parseInt(info[1][1]) && a.get(i )!=0) {
                        demoreParcial+=0.5 + tiempoEntreClientes(a.get(i), a.get(i - 1));
                    } else if (a.get(i) <= Integer.parseInt(info[1][1]) && a.get(i - 1) > Integer.parseInt(info[1][1]) && a.get(i) != 0) {
                        demoreParcial+=0.5 + tiempoAEstacion(a.get(i));
                    } else if (a.get(i) > Integer.parseInt(info[1][1]) && a.get(i - 1) <= Integer.parseInt(info[1][1]) && a.get(i - 1) != 0) {
                        demoreParcial+=timePerEsta[a.get(i) - Integer.parseInt(info[1][1]) - 1] + tiempoAEstacion(a.get(i - 1));
                    } else if (a.get(i) > Integer.parseInt(info[1][1]) && a.get(i - 1) == 0) {
                        demoreParcial+= timePerEsta[a.get(i) - Integer.parseInt(info[1][1]) - 1] + tiempoABodega(a.get(i));
                    } else {
                        demoreParcial+= tiempoABodega(a.get(i - 1));
                    }
                }

            }
            if(demoreParcial>maxDemore){
                maxDemore=demoreParcial;
                rutaM=ruta;
            }
            if(demoreParcial<minDemore){
                minDemore=demoreParcial;
                rutaMin=ruta;
            }
            if(demoreParcial >10){
                System.out.println("La ruta "+ruta+" se demora más de 10 h");
            
            }
            tot+=demoreParcial;
            ruta++;
        }

        System.out.println("La ruta más larga ("+rutaM+") es de: "+maxDemore+ " h ");
        System.out.println("La ruta más corta ("+rutaMin+") es de: "+minDemore+ " h ");
        System.out.println("Total de horas: "+tot+ " h ");
    }

    

    /**
     * Método principal que lee la información básica del set de datos y llama los métodos necesarios para el cálculo de la ruta
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[]args) throws FileNotFoundException, IOException {
        long startTime = System.currentTimeMillis();

        String[][] iniciales = new String[10][2];
        BufferedReader lector = new BufferedReader(new FileReader("set10.txt"));
        for (int i = 0; i < 10; i++) {
            String linea = lector.readLine();           
            if (linea != null) {
                iniciales[i] = linea.split("= ");
            }
        }
        for (int i = 0; i < 3; i++) {
            lector.readLine();
        }
        int clientes = Integer.parseInt(iniciales[1][1]);
        String linea = lector.readLine();
        String[] depos = linea.split(" ");
        Leer a = new Leer(iniciales, clientes + 1, Integer.parseInt(iniciales[2][1]), depos, Integer.parseInt(iniciales[0][1]));

        a.llenarInfo("set10.txt");

        Principal p = new Principal();
        p.getCercanas(a);
        Principal.rutas(a);
        a.imprimirRutas2();
        System.out.println("");
        a.tiempoTotal();
        long endTime =  System.currentTimeMillis() - startTime;
        System.out.println("");
        System.out.println("Tiempo empleado:"+endTime + " ms");

    }
}
