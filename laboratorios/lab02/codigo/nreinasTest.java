import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class nreinasTest {

    @Test
    public void testReinas() {
        assertEquals(nreinas.queens(0),1);		
        assertEquals(nreinas.queens(1),1);
        assertEquals(nreinas.queens(2),0);
        assertEquals(nreinas.queens(3),0);
        assertEquals(nreinas.queens(4),2);
        assertEquals(nreinas.queens(5),10);
        assertEquals(nreinas.queens(6),4);
        assertEquals(nreinas.queens(7),40);
        assertEquals(nreinas.queens(8),92);
        assertEquals(nreinas.queens(9),352);
        assertEquals(nreinas.queens(10),724);

    }
}