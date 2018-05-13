    import java.io.*;
    import java.util.ArrayList;

    /**
     * Clase que lee los datos necesarios para el cÃ¡lculo de la ruta optima
     * @author Juan Jose Parra, Luisa Maria Vasquez
     * @version 17/05/2018
     */
    public class Leer {
        String[][] info ; // Info del principio
        String[][] clientes ; 
        String[][] estaciones ;
        String[] deposito; // Info del deposito
        double[] disDep; // Arreglo con la distancia de cada uno al deposito
        double[][] tyb; // Matriz que contiene tiempo y bateria que e gasta del deposito a cada punto

        // Va a haber un objeto que contenga toda la informacion , aqui reservo el espacio
        public Leer(String[][] info,int n, int s, String[] depot,int tot){
            this.info=info;
            clientes = new String[n][6];
            estaciones = new String [s][6];
            deposito=depot;
            disDep=new double[tot];
            disDep[0]=0;
            tyb= new double[tot][2];
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
            int n = Integer.parseInt(info[1][1]);
            int i=0;
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
                
                tyb[Integer.parseInt(clientes[i][0])][0] = disDep[Integer.parseInt(clientes[i][0])]/ Double.parseDouble(info[4][1]) ;
                
                tyb[Integer.parseInt(clientes[i][0])][1] = tyb[Integer.parseInt(clientes[i][0])][0] * Double.parseDouble(info[9][1]);
                
                //System.out.println(linea+ "  Dustancia al deposito  "+ disDep[Integer.parseInt(clientes[i][0])] + "En ps: "+Integer.parseInt(clientes[i][0]));
                linea = lector.readLine();
                i++;            
            }

            // Ya acabe con clientes , ahora sigue estaciones
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
                disDep[Integer.parseInt(estaciones[j][0])]= Math.sqrt(Math.pow(xd-x1,2) + Math.pow(yd-y1,2));              
                tyb[Integer.parseInt(estaciones[j][0])][0] = disDep[Integer.parseInt(estaciones[j][0])]/ Double.parseDouble(info[4][1]) ;
                tyb[Integer.parseInt(estaciones[j][0])][1] = tyb[Integer.parseInt(estaciones[j][0])][0] * Double.parseDouble(info[9][1]);
                System.out.println(tyb[Integer.parseInt(estaciones[j][0])][1]);
                linea = lector.readLine();
                j++;            
            }
        }

        

        //EMPIEZA AQUI
        public static void main(String[] args) throws FileNotFoundException, IOException{      
            //Informacion del principio
            String[][] iniciales=new String [10][2];
            BufferedReader lector = new BufferedReader(new FileReader("C:\\Users\\luisa\\Desktop\\set1.txt"));
           //Leo las primeras lineas
            for (int i = 0; i < 10; i++) {
                String linea= lector.readLine();
                //System.out.println("Acabo de leer la linea : "+linea);            
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
            String linea = lector.readLine();
            String[] depos = linea.split(" "); 

            // Llamo al constructor para que cree la matriz del tamaño de los clientes y la matrzi del tamaño de las estaciones 
            Leer a = new Leer(iniciales,clientes,Integer.parseInt(iniciales[2][1]),depos,Integer.parseInt(iniciales[0][1]));
                  
            // Ya tengo el espaico .. solo queda llenarlo
            a.llenarInfo("set1.txt");
            
            
        }
    }

