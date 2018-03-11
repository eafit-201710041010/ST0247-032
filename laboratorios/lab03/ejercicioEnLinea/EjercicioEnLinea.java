import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Solucion al ejercicio en linea
 * 
 * @author Juan Jose Parra , Luisa Maria Vasquez
 * @version 10/03/2018
 */
public class EjercicioEnLinea
{
    boolean [] visitados;
    int pesoMin = 106;
    int nodos;
    int caminoFinal = 0;
    public DigraphAM inputManager() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String entrada = br.readLine();
        String[] uno = entrada.split(" ");
        nodos = Integer.parseInt(uno[0]);
        int arcos = Integer.parseInt(uno[1]);
        visitados = new boolean[arcos];
        DigraphAM g = new DigraphAM(arcos);
        for (int i = 0; i < arcos; i++){
            entrada = br.readLine();
            String[] dos = entrada.split(" ");
            int origen = Integer.parseInt(dos[0]);
            int destino = Integer.parseInt(dos[1]);
            int peso = Integer.parseInt(dos[2]);

            g.addArc(origen, destino, peso);
        }
        return g;
    }

    public void recorrer(Digraph g, int origen, int destino, int peso, int camino){
        if(visitados[origen]){
            return;
        }
        if(origen == destino){
            pesoMin = peso;
            caminoFinal = camino;
        } else {
            ArrayList sucesores = g.getSuccessors(origen);
            if(sucesores == null){
                return;
            }
            for(int i = 0; i < sucesores.size(); i++){
                visitados[origen] = true;
                peso += g.getWeight(origen, (int)sucesores.get(i));
                if(peso <= pesoMin){
                    if((int)sucesores.get(i) >= 100){
                        camino = camino * 1000 + (int)sucesores.get(i);
                    } else if ((int)sucesores.get(i) >= 10){
                        camino = camino * 100 + (int)sucesores.get(i);
                    } else {
                        camino = camino * 10 + (int)sucesores.get(i);
                    }
                    
                    recorrer(g, (int)sucesores.get(i), destino, peso, camino);
                }
                peso -= g.getWeight(origen, (int)sucesores.get(i));
                if((int)sucesores.get(i) >= 100){
                    camino /= 1000;
                } else if ((int)sucesores.get(i) >= 10){
                    camino /= 100;
                }else{
                    camino /= 10;
                }
                visitados[origen] = false;
            }
        }
    }

    public static void main(String [] args){
        EjercicioEnLinea gr = new EjercicioEnLinea();
        System.out.println();
        try{
            DigraphAM g = gr.inputManager();
            gr.recorrer(g, 1, gr.nodos, 0, 1);
        } catch(IOException e){}
        ArrayList<Integer> solucion = new ArrayList<Integer>();
        do{
            solucion.add(gr.caminoFinal % 10);
            gr.caminoFinal /= 10;
        } while  (gr.caminoFinal > 0);
        for(int i = solucion.size()-1; i >= 0; i--){
            System.out.print(solucion.get(i) + " ");
        }
    }
}