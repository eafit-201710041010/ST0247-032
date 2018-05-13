    import java.io.*;
    import java.util.*;

    /**
     * Clase que lee los datos necesarios para el cÃ¡lculo de la ruta optima
     * @author Juan Jose Parra, Luisa Maria Vasquez
     * @version 17/05/2018
     */
    public class Leer {
        /**
         * NOTA IMPORTANTEEEE 
         * Los clientes estan numerados desde 1 , ya que la informacion esta metida 
         * segun la primera columna del archivo de texto, las estaciones si estan desde 0 , y en
         * los que tienen clientes y estacioes tambien estan desde 1
         * 
         * ->> Deberiamos como borrar todas esas clases que no estamos utilizando del repositorio
         */
        //AQUI ESTA TODA LA INFORMAICON
        String[][] info ; // Info del principio
        String[][] clientes ; // cada fila es un cliente y cada columna es un dato de este (empieza en 1)
        String[][] estaciones ; // cada fila es una estacion y cada columna es un dato de estea (empieza en 0)
        String[] deposito; // Info del deposito
        double[] disDep; // Arreglo con la distancia de cada cliente o esta  estacion al deposito (empieza en 1 , als estaciones son 324+)
        double[][] tyb; // Matriz que contiene tiempo y bateria que e gasta del deposito a cada punto (empieza en 1 , als estaciones son 324+)
        int[] cercana; // Cada posicion es un cliente que contiene el numero de la estacion mas cercana (empieza en 1)
        Map<Integer,Set<Integer>> clixesta; // Por cada estacion hay un set que contiene los clientes cercanos
        boolean[] visitados; // Clientes visitados(empieza en 1)
        Set<Set<Integer>> rutas ; // Set dodne se almacenan todas rutas a haacer
        double[] timeCharge; // Aqui se alamacenen los ppsibles tiempos de carga
        double[] timePerEsta;// Cada estacion con su  tiempo de carga segun tipo(empiieza en 0)

        
        // Va a haber un objeto que contenga toda la informacion , aqui reservo inicializo todo
        public Leer(String[][] info,int n, int s, String[] depot,int tot){
            
            //Voy a expliar nombres
            clixesta= new HashMap<>();  //Clientes por estacion
            this.info=info; //Informaicon del comienzo
            clientes = new String[n][6]; //A prueba de bobos
            estaciones = new String [s][6]; //A prueba de bobos
            deposito=depot; //A prueba de bobos
            disDep=new double[tot]; //Distancia al deposito
            disDep[0]=0; //Distancia del deposito al deposito es 0 , duh
            tyb= new double[tot][2]; // Tiempo y bateria
            cercana=new int[n]; // Estacion de carga ams cercana
            visitados=new boolean[n]; // A prueba de bobos
            rutas= new HashSet<>(); // Rutas que vamos a generar
            timeCharge= new double[3]; //Posibles tiempos que se demora cargando
            timePerEsta= new double[s]; // Tiempo en carga completa por estacion
        }

        //Lleno la info con lo que lea del archivo
        private  void llenarInfo(String filename) throws FileNotFoundException, IOException{
            BufferedReader lector = new BufferedReader(new FileReader(filename));
            // Como en el main ya lei la informacion del principio me la salto
            for (int i = 0; i < 14; i++) {            
                lector.readLine();
            }

            //Empiezo a leer clientes
            String linea = lector.readLine();
            // Como empiezo desde 1 
            int n = Integer.parseInt(info[1][1])+1;
            int i=1;
            
            while (linea!=null&&i<n) 
            {
                //System.out.println(linea);

                clientes[i]=linea.split(" ");
                //Ignora la linea que sigue , es solo para mirar que leyera bien
                String tipo = clientes[i][4].equals("c")? "Cliente":"Estacion";
                // Hallar distancia al deposito y la agrego al arreglo
                double xd = Double.parseDouble(deposito[2]);
                double x1= Double.parseDouble(clientes[i][2]);
                double yd = Double.parseDouble(deposito[3]);
                double y1= Double.parseDouble(clientes[i][3]);
                disDep[Integer.parseInt(clientes[i][0])]= Math.sqrt(Math.pow(xd-x1,2) + Math.pow(yd-y1,2));
                // Con esta linea te das cuenta que las posocione en el arreglo y la columna del texto si coinciden
                //System.out.println("Del texto "+ Integer.parseInt(clientes[i][0])+ "De lista"+i);
                // Hallo tiempo y bateria y los agrego
                tyb[Integer.parseInt(clientes[i][0])][0] = disDep[Integer.parseInt(clientes[i][0])]/ Double.parseDouble(info[4][1]) ;                
                tyb[Integer.parseInt(clientes[i][0])][1] = tyb[Integer.parseInt(clientes[i][0])][0] * Double.parseDouble(info[9][1]);

                //System.out.println(linea+ "  Dustancia al deposito  "+ disDep[Integer.parseInt(clientes[i][0])] + "En ps: "+Integer.parseInt(clientes[i][0]));
                linea = lector.readLine();
                i++;            
            }

            // Ya acabe con clientes , ahora sigue estaciones, aqui si desde 0
            // Porque maluco solo llenar desde 324 pa arriba 
            int s = Integer.parseInt(info[2][1]);
            int  j=0;
            while (linea!=null&&j<s) 
            {
                //System.out.println(linea);
                estaciones[j]=linea.split(" ");
                //Ignora la linea que sigue , es solo para mirar que leyera bien
                String tipo = estaciones[j][4].equals("c")? "Cliente":"Estacion";
                // Hallar distancia al deposito y la agrego al arreglo
                double xd = Double.parseDouble(deposito[2]);
                double x1= Double.parseDouble(estaciones[j][2]);
                double yd = Double.parseDouble(deposito[3]);
                double y1= Double.parseDouble(estaciones[j][3]);
                
                //Distania desde el deposito
                disDep[Integer.parseInt(estaciones[j][0])]= Math.sqrt(Math.pow(xd-x1,2) + Math.pow(yd-y1,2));              
                tyb[Integer.parseInt(estaciones[j][0])][0] = disDep[Integer.parseInt(estaciones[j][0])]/ Double.parseDouble(info[4][1]) ;
                tyb[Integer.parseInt(estaciones[j][0])][1] = tyb[Integer.parseInt(estaciones[j][0])][0] * Double.parseDouble(info[9][1]);              
                //Inicializo el set dode voy a meter los clientes cercanos a la estacion
                clixesta.put(j,new HashSet());
                
               // System.out.println("Estacion numero "+j);
                linea = lector.readLine();
                j++;            
            }
            //Saltp de 3 lineas
            for (int t = 0; t < 2; t++) {
                lector.readLine();
            }
            
            //Leo tiempos de carga ( Solo cogi los de carga completa)
            for(int tc =0;tc<3;tc++){
                
             String linea2 = lector.readLine();    
             //System.out.println(linea2);
             String[] arr =linea2.split(" ");
             timeCharge[tc]= Double.parseDouble(arr[3]);     
            }
           // Segun el tipo de la estacion le asigno el tiempo que se demora en cargar
            for(int ts =0;ts<estaciones.length;ts++){
                if(estaciones[ts][5].equals("0")){
                 timePerEsta[ts]=timeCharge[0];
                } else if(estaciones[ts][5].equals("1")){
                timePerEsta[ts]=timeCharge[1];
                }else{
                timePerEsta[ts]=timeCharge[2];
                }
            
            }
            
           
        }

        

        //EMPIEZA AQUI
        public static void main(String[] args) throws FileNotFoundException, IOException{ 
            
            
            //Informacion del principio
            String[][] iniciales=new String [10][2];
            BufferedReader lector = new BufferedReader(new FileReader("set10.txt"));
           //Leo las primeras lineas
            for (int i = 0; i < 10; i++) {
                String linea= lector.readLine();
               // System.out.println("Acabo de leer la linea : "+linea);            
                if(linea!=null)
                    iniciales[i]=linea.split("= ");
                //System.out.println("Parte interesante:"+iniciales[i][1]);
            }
            //Lineas entre info del pirncipio y coordenadas
            for (int i = 0; i < 3; i++) {
                lector.readLine();
            }
            //Obtengo numero de clientes totales
            int clientes = Integer.parseInt(iniciales[1][1]);
            //Informacion del deposito
            String linea = lector.readLine();
            String[] depos = linea.split(" "); 

            // Llamo al constructor para que cree la matriz del tamaño de los clientes y la matrzi del tamaño de las estaciones 
            Leer a = new Leer(iniciales,clientes+1,Integer.parseInt(iniciales[2][1]),depos,Integer.parseInt(iniciales[0][1]));
                  
            // Ya tengo el espaico .. solo queda llenarlo
            a.llenarInfo("set10.txt");
            
            //Asigno los clientes con las estaciones cercanas
            Principal.getCercanas(a);
            
            //Miremos si todos los clientes si quedaron asignados
            int cumple=0;
            for (Map.Entry<Integer, Set<Integer>> entry : a.clixesta.entrySet()) {
                System.out.println("Para la estacion: "+ entry.getKey()+" Los clientes son:");
                for(Integer cl: entry.getValue()){
                    System.out.print(cl+" ");
                    cumple++;
                }
                    System.out.println("");
             }
            System.out.println(cumple==320?"Yei":"Sorry"); // Si quedaron asignados todos

        }
    }

