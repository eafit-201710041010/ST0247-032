import java.io.*;
import java.util.ArrayList;

/**
 * Clase que lee los datos necesarios para el cÃ¡lculo de la ruta optima
 * @author Juan Jose Parra, Luisa Maria Vasquez
 * @version 17/05/2018
 */
public class Leer {
    String[][] info ;
    String[][] clientes ;
    String[][] estaciones ;
    String[] deposito;

    public Leer(String[][] info,int n, int s, String[] depot){
        this.info=info;
        clientes = new String[n][6];
        estaciones = new String [s][6];
        deposito=depot;
    }

    private  void llenarInfo(String filename) throws FileNotFoundException, IOException{
        BufferedReader lector = new BufferedReader(new FileReader(filename));
        for (int i = 0; i < 14; i++) {            
            lector.readLine(); 
        }
        String linea = lector.readLine();
        int n = Integer.parseInt(info[1][1]);
        int i=0;
        while (linea!=null&&i<n) 
        {
            //System.out.println(linea);
            clientes[i]=linea.split(" ");
            String tipo = clientes[i][4].equals("c")? "Cliente":"Estacion";      
            linea = lector.readLine();
            i++;            
        }
        int s = Integer.parseInt(info[2][1]);
        int  j=0;
        while (linea!=null&&j<s) 
        {
            //System.out.println(linea);
            estaciones[j]=linea.split(" ");
            String tipo = estaciones[j][4].equals("c")? "Cliente":"Estacion";
            linea = lector.readLine();
            j++;            
        }

    }

    public static void main(String[] args) throws FileNotFoundException, IOException{        
        String[][] iniciales=new String [10][2];
        BufferedReader lector = new BufferedReader(new FileReader("C:\\Users\\luisa\\Desktop\\set1.txt"));
        for (int i = 0; i < 10; i++) {
            String linea= lector.readLine();
            //System.out.println("Acabo de leer la linea : "+linea);            
            if(linea!=null)
                iniciales[i]=linea.split("= ");
            //System.out.println("Parte interesante:"+iniciales[i][1]);
        }

        for (int i = 0; i < 3; i++) {
            lector.readLine();
        }
        int clientes = Integer.parseInt(iniciales[1][1]);
        String[] depos = lector.readLine().split(" "); 
        Leer a = new Leer(iniciales,clientes,Integer.parseInt(iniciales[2][1]),depos);
        a.llenarInfo("set1.txt");

         System.out.println("DEPOSITO ");
         for(int j =0;j<a.deposito.length;j++){
                 System.out.print(a.deposito[j]+" ");
            }
         
          System.out.println("INFO");
          
        
        for(int i =0;i<a.info.length;i++){
            for(int j =0;j<a.info[0].length;j++){
                 System.out.print(a.info[i][j]+" ");
            }
           System.out.println("");
        }
        
        System.out.println("CLIENTES");
        
        for(int i =0;i<a.clientes.length;i++){
            for(int j =0;j<a.clientes[0].length;j++){
                 System.out.print(a.clientes[i][j] +" ");
            }
           System.out.println("");
        }
        
        System.out.println("ESTACIONES");
        
        for(int i =0;i<a.estaciones.length;i++){
            for(int j =0;j<a.estaciones[0].length;j++){
                 System.out.print(a.estaciones[i][j]+" ");
            }
           System.out.println("");
        }
    }

}
