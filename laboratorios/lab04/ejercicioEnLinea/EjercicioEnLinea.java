import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Write a description of class EjercicioEnLinea here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EjercicioEnLinea
{
    ArrayList<Bus> buses;
    public void inputManager() throws IOException{
        buses = new ArrayList<>();
        
        String[] datos;
        int[] temprano;
        int[] tarde;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String entrada = br.readLine();
        while(!entrada.equals("0 0 0")){
            datos = entrada.split(" ");
            int r = Integer.parseInt(datos[0]);
            int m = Integer.parseInt(datos[1]);
            int e = Integer.parseInt(datos[2]);
            
            entrada = br.readLine();
            datos = entrada.split(" ");
            temprano = new int[datos.length];
            for(int i = 0; i < r; i++){
                temprano[i] = Integer.parseInt(datos[i]);
            }
            
            entrada = br.readLine();
            datos = entrada.split(" ");
            tarde = new int[datos.length];
            for(int i = 0; i < r; i++){
                tarde[i] = Integer.parseInt(datos[i]);
            }
            
            buses.add(new Bus(r,m,e,temprano,tarde));
            
            entrada = br.readLine();
        }
    }
    
    public void getExtra(){
        for(Bus b: buses){
            System.out.println(b.pagoExtra());
        }
    }
    
    public static void main(String[] args){
        EjercicioEnLinea eel = new EjercicioEnLinea();
        System.out.println();
        try{
            eel.inputManager();
        } catch(IOException e){}
        eel.getExtra();
    }
}
