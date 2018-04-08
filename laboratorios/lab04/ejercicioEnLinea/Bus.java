
/**
 * Write a description of class Empresa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bus
{
    private int rutas;
    private int horasMax;
    private int valorHorasExtra;
    
    private int[] temprano;
    private int[] tarde;
    
    public Bus(int r, int hm, int he, int[] m, int[] t){
        rutas = r;
        horasMax = hm;
        valorHorasExtra = he;
        
        temprano = m;
        tarde = t;
    }
    
    public int pagoExtra(){
        int extraTotal = 0;
        for(int i = 0; i < rutas; i++){
            int horasTotal = temprano[i] + tarde[i];
            int horasExtra = horasTotal - horasMax;
            if(horasExtra > 0){
                extraTotal += valorHorasExtra*horasExtra;
            }
        }
        return extraTotal;
    }
}
