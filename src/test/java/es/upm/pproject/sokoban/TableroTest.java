package es.upm.pproject.sokoban;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TableroTest {
    @Test
    @DisplayName("Test Constructor: nivel no existe") 
    void test1(){
        assertThrows(FileNotFoundException.class, 
            () -> new Tablero("./niveles/level_2000.txt"));
    }

    @Test
    @DisplayName("Test Constructor: nivel mal construido (distinto número de cajas y endpoints)") 
    void test2(){
        assertThrows(es.upm.pproject.sokoban.exceptions.IncorrectLevelException.class, 
            () -> new Tablero("./niveles/level_400.txt"));
    }

    @Test
    @DisplayName("Test Constructor: nivel mal construido (sin cajas)") 
    void test3(){
        assertThrows(es.upm.pproject.sokoban.exceptions.IncorrectLevelException.class, 
            () -> new Tablero("./niveles/level_417.txt"));
    }

    @Test
    @DisplayName("Test Constructor: datos correctos") 
    void test4() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_1.txt");
        assertNotNull(tablero);
    }

    @Test
    @DisplayName("Test Constructor: datos correctos") 
    void test5(){
        Tablero tablero = new Tablero();
        assertNotNull(tablero);
    }

    @Test
    @DisplayName("Test comprobar fin: datos correctos") 
    void test6() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_401.txt");
        tablero.mover('r');
        assertTrue(tablero.comprobarFin());
    }

    @Test
    @DisplayName("Test comprobar fin: datos correctos") 
    void test7() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_401.txt");
        assertFalse(tablero.comprobarFin());
    }

    @Test
    @DisplayName("Test comprobar fin: datos correctos") 
    void test8() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_418.txt");
        assertFalse(tablero.comprobarFin());
    }

    @Test
    @DisplayName("Test setMatriz y getMatriz: datos correctos") 
    void test9() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_1.txt");
        tablero.setMatriz(new char[0][0]);
        assertEquals(0, tablero.getMatriz().length);
    }

    @Test
    @DisplayName("Test getNombreMatriz: datos correctos") 
    void test10() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_401.txt");
        assertEquals("Custom level", tablero.getNombreNivel());
    }

    @Test
    @DisplayName("Test toString: matriz nula") 
    void test11(){
        Tablero tablero = new Tablero();
        assertEquals("", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba hacia casilla vacía (correcto)") 
    void test12() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_402.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*W \n" + //
                     "   \n" + //
                     "  #\n", tablero.toString());
    }
    
    @Test
    @DisplayName("Test mover: W izquierda hacia casilla vacía (correcto)") 
    void test13() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_402.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*  \n" + //
                     "W  \n" + //
                     "  #\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha hacia casilla vacía (correcto)") 
    void test14() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_402.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*  \n" + //
                     "  W\n" + //
                     "  #\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo hacia casilla vacía (correcto)") 
    void test15() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_402.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*  \n" + //
                     "   \n" + //
                     " W#\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba hacia endpoint (correcto)") 
    void test16() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_403.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "#&#\n" + //
                     "* *\n" + //
                     "#*#\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W izquierda hacia endpoint (correcto)") 
    void test17() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_403.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "#*#\n" + //
                     "& *\n" + //
                     "#*#\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha hacia endpoint (correcto)") 
    void test18() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_403.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "#*#\n" + //
                     "* &\n" + //
                     "#*#\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo hacia endpoint (correcto)") 
    void test19() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_403.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "#*#\n" + //
                     "* *\n" + //
                     "#&#\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba hacia caja bloqueda por límite tablero (correcto)") 
    void test20() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_404.txt");
        assertFalse(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*#*\n" + //
                     "#W#\n" + //
                     "*#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W izquierda hacia caja bloqueda por límite tablero (correcto)") 
    void test21() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_404.txt");
        assertFalse(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*#*\n" + //
                     "#W#\n" + //
                     "*#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha hacia caja bloqueda por límite tablero (correcto)") 
    void test22() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_404.txt");
        assertFalse(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*#*\n" + //
                     "#W#\n" + //
                     "*#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo hacia caja bloqueda por límite tablero (correcto)") 
    void test23() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_404.txt");
        assertFalse(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "*#*\n" + //
                     "#W#\n" + //
                     "*#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (en esquina superior izquierda) arriba hacia límite tablero (correcto)") 
    void test24() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_405.txt");
        assertFalse(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "W  \n" + //
                     "   \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (en esquina superior izquierda) izquierda hacia límite tablero (correcto)") 
    void test25() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_405.txt");
        assertFalse(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "W  \n" + //
                     "   \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (en primera fila) arriba hacia límite tablero (correcto)") 
    void test26() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_406.txt");
        assertFalse(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     " W \n" + //
                     "   \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (en esquina superior derecha) arriba hacia límite tablero (correcto)") 
    void test27() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_407.txt");
        assertFalse(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  W\n" + //
                     "   \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (en esquina superior derecha) derecha hacia límite tablero (correcto)") 
    void test28() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_407.txt");
        assertFalse(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  W\n" + //
                     "   \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (primera columna) izquierda hacia límite tablero (correcto)") 
    void test29() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_408.txt");
        assertFalse(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     "W  \n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (primera columna) derecha hacia límite tablero (correcto)") 
    void test30() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_409.txt");
        assertFalse(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     "  W\n" + //
                     " #*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (esquina inferior izquierda) izquierda hacia límite tablero (correcto)") 
    void test31() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_410.txt");
        assertFalse(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     "   \n" + //
                     "W#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (esquina inferior izquierda) abajo hacia límite tablero (correcto)") 
    void test32() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_410.txt");
        assertFalse(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     "   \n" + //
                     "W#*\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (última fila) abajo hacia límite tablero (correcto)") 
    void test33() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_411.txt");
        assertFalse(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     " #*\n" + //
                     "   \n" + //
                     " W \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (última columna) derecha hacia límite tablero (correcto)") 
    void test34() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_412.txt");
        assertFalse(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     " #*\n" + //
                     "   \n" + //
                     "  W\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W (última columna) abajo hacia límite tablero (correcto)") 
    void test35() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_412.txt");
        assertFalse(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     " #*\n" + //
                     "   \n" + //
                     "  W\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba y mueve caja a endpoint (correcto)") 
    void test36() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_413.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  _  \n" + //
                     "  W  \n" + //
                     "*# #*\n" + //
                     "  #  \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W izquierda y mueve caja a endpoint (correcto)") 
    void test37() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_413.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     "  #  \n" + //
                     "_W #*\n" + //
                     "  #  \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha y mueve caja a endpoint (correcto)") 
    void test38() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_413.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     "  #  \n" + //
                     "*# W_\n" + //
                     "  #  \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo y mueve caja a endpoint (correcto)") 
    void test39() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_413.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     "  #  \n" + //
                     "*# #*\n" + //
                     "  W  \n" + //
                     "  _  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba y mueve caja a vacío (correcto)") 
    void test40() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_414.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  #  \n" + //
                     " *W* \n" + //
                     " # # \n" + //
                     " *#* \n" + //
                     "     \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W izquierda y mueve caja a vacío (correcto)") 
    void test41() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_414.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "     \n" + //
                     " *#* \n" + //
                     "#W # \n" + //
                     " *#* \n" + //
                     "     \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha y mueve caja a vacío (correcto)") 
    void test42() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_414.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "     \n" + //
                     " *#* \n" + //
                     " # W#\n" + //
                     " *#* \n" + //
                     "     \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo y mueve caja a vacío (correcto)") 
    void test43() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_414.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "     \n" + //
                     " *#* \n" + //
                     " # # \n" + //
                     " *W* \n" + //
                     "  #  \n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba hacia pared (correcto)', 'u'",
        "'Test mover: W izquierda hacia pared (correcto)', 'l'",
        "'Test mover: W derecha hacia pared (correcto)', 'r'",
        "'Test mover: W abajo hacia pared (correcto)', 'd'"
    })
    void test44(String displayName, char movimiento) throws Exception {
        Tablero tablero = new Tablero("./niveles/level_415.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel() + "\n" +
                     "     \n" + // 
                     "  +  \n" + //
                     " +W+ \n" + //
                     "  +  \n" + //
                     "   #*\n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja inmovilizada por pared (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja inmovilizada por pared (correcto)', 'l'",
        "'Test mover: W derecha empuja caja inmovilizada por pared (correcto)', 'r'",
        "'Test mover: W abajo empuja caja inmovilizada por pared (correcto)', 'd'"
    })
    void test45(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_416.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   *   \n" + //
                     "   +   \n" + //
                     "   #   \n" + //
                     "*+#W#+*\n" + //
                     "   #   \n" + //
                     "   +   \n" + //
                     "   *   \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: & arriba hacia vacío (correcto)") 
    void test46() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_418.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     " W \n" + //
                     " * \n" + //
                     "  #\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: & izquierda hacia vacío (correcto)") 
    void test47() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_418.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     "W* \n" + //
                     "  #\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: & derecha hacia vacío (correcto)") 
    void test48() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_418.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     " *W\n" + //
                     "  #\n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: & abajo hacia vacío (correcto)") 
    void test49() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_418.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   \n" + //
                     " * \n" + //
                     " W#\n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja inmovilizada por caja (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja inmovilizada por caja (correcto)', 'l'",
        "'Test mover: W derecha empuja caja inmovilizada por caja (correcto)', 'r'",
        "'Test mover: W abajo empuja caja inmovilizada por caja (correcto)', 'd'"
    })
    void test50(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_419.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   *   \n" + //
                     "   #   \n" + //
                     "  *#*  \n" + //
                     "*##W##*\n" + //
                     "  *#*  \n" + //
                     "   #   \n" + //
                     "   *   \n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja inmovilizada por caja sobre endPoint (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja inmovilizada por caja sobre endpoint (correcto)', 'l'",
        "'Test mover: W derecha empuja caja inmovilizada por caja sobre endpoint (correcto)', 'r'",
        "'Test mover: W abajo empuja caja inmovilizada por caja sobre endpoint (correcto)', 'd'"
    })
    void test51(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_420.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "       \n" + //
                     "   _   \n" + //
                     "  *#*  \n" + //
                     " _#W#_ \n" + //
                     "  *#*  \n" + //
                     "   _   \n" + //
                     "       \n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja sobre endpoint inmovilizada por pared (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja sobre endpoint inmovilizada por pared (correcto)', 'l'",
        "'Test mover: W derecha empuja caja sobre endpoint inmovilizada por pared (correcto)', 'r'",
        "'Test mover: W abajo empuja caja sobre endpoint inmovilizada por pared (correcto)', 'd'"
    })
    void test52(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_421.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "       \n" + //
                     "   +   \n" + //
                     "   _   \n" + //
                     " +_W_+ \n" + //
                     "   _   \n" + //
                     "   +   \n" + //
                     "    *# \n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja sobre endpoint inmovilizada por caja (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja sobre endpoint inmovilizada por caja (correcto)', 'l'",
        "'Test mover: W derecha empuja caja sobre endpoint inmovilizada por caja (correcto)', 'r'",
        "'Test mover: W abajo empuja caja sobre endpoint inmovilizada por caja (correcto)', 'd'"
    })
    void test53(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_422.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "   *   \n" + //
                     "   #   \n" + //
                     "   _   \n" + //
                     "*#_W_#*\n" + //
                     "   _   \n" + //
                     "   #   \n" + //
                     "   *   \n", tablero.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "'Test mover: W arriba empuja caja sobre endpoint inmovilizada por caja sobre endpoint (correcto)', 'u'",
        "'Test mover: W izquierda empuja caja sobre endpoint inmovilizada por caja sobre endpoint (correcto)', 'l'",
        "'Test mover: W derecha empuja caja sobre endpoint inmovilizada por caja sobre endpoint (correcto)', 'r'",
        "'Test mover: W abajo empuja caja sobre endpoint inmovilizada por caja sobre endpoint (correcto)', 'd'"
    })
    void test54(String displayName, char movimiento) throws Exception{
        Tablero tablero = new Tablero("./niveles/level_423.txt");
        assertFalse(tablero.mover(movimiento));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "       \n" + //
                     "   _   \n" + //
                     "   _   \n" + //
                     " __W__ \n" + //
                     "   _   \n" + //
                     "   _   \n" + //
                     "   *#  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W arriba empuja caja sobre endpoint a endpoint (correcto)") 
    void test55() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_424.txt");
        assertTrue(tablero.mover('u'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  _  \n" + //
                     " #&# \n" + //
                     "*_ _*\n" + //
                     " #_# \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W izquierda empuja caja sobre endpoint a endpoint (correcto)") 
    void test56() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_424.txt");
        assertTrue(tablero.mover('l'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     " #_# \n" + //
                     "_& _*\n" + //
                     " #_# \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W derecha empuja caja sobre endpoint a endpoint (correcto)") 
    void test57() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_424.txt");
        assertTrue(tablero.mover('r'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     " #_# \n" + //
                     "*_ &_\n" + //
                     " #_# \n" + //
                     "  *  \n", tablero.toString());
    }

    @Test
    @DisplayName("Test mover: W abajo empuja caja sobre endpoint a endpoint (correcto)") 
    void test58() throws Exception{
        Tablero tablero = new Tablero("./niveles/level_424.txt");
        assertTrue(tablero.mover('d'));
        assertEquals(tablero.getNombreNivel()+ "\n"+
                     "  *  \n" + //
                     " #_# \n" + //
                     "*_ _*\n" + //
                     " #&# \n" + //
                     "  _  \n", tablero.toString());
    }
}