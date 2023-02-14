import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDemoTest {

    @Test
    void demoMethodTest() {
        assertTrue(90>=TestDemo.demoMethod(10,80));
       assertEquals(100,TestDemo.demoMethod(10,90));

    }
}