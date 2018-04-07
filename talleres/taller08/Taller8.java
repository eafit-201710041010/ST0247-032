
/**
 * Write a description of class Taller8 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Taller8
{
    public static int[] quicksort(int[] a){
        int wall = 0;
        int e = 0;
        int pivot = a.length-1;
        quicksort(a, wall, pivot, e);
        return a;
    }
    
    private static void quicksort(int[] a, int wall, int pivot, int e){
        if(wall == pivot){
            return;
        }
        
        if(e == pivot){
            int temp = a[wall];
            a[wall] = a[e];
            a[e] = temp;
            wall++;
            quicksort(a, wall, pivot, wall);
        } else if(a[e] < a[pivot]){
            int temp = a[wall];
            a[wall] = a[e];
            a[e] = temp;
            quicksort(a, wall++, pivot, e);
        } else {
            quicksort(a, wall, pivot, e++);
        }
    }
}
