import model.Einzelteil;
import model.Parsable;
import model.Schneidplan;
import org.junit.Test;
import processing.Parser;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TestParser {

    @Test
    public void testParseSchneidplan() throws IllegalAccessException {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_1.htm");

    }


    /**
     * Test if all fields of Schneidplan are filled.
     */
    @Test
    public void testSchneidplanFilledWithHTML_1() {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_1.htm");
        checkFilledSchneidplan(schneidplan);
    }


    @Test
    public void testSchneidplanFilledWithHTML_2() {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_2.htm");
        checkFilledSchneidplan(schneidplan);
    }

    @Test
    public void testEinzelteileFilledWithHTML_1() {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_1.htm");
        ArrayList<Einzelteil> einzelteile = schneidplan.getEinzelteile();
        checkFilledEinzelteil(einzelteile);
    }

    @Test
    public void testEinzelteileFilledWithHTML_2() {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_2.htm");
        ArrayList<Einzelteil> einzelteile = schneidplan.getEinzelteile();
        checkFilledEinzelteil(einzelteile);
    }

    /**
     * Check if every Filed of Einzelteil is filled
     * @param einzelteile
     */
    private void checkFilledEinzelteil(ArrayList<Einzelteil> einzelteile) {
        for (Einzelteil teil : einzelteile) {
            assertNotNull(teil.getAbmessungen());
            assertNotNull(teil.getAnazhl());
            assertNotNull(teil.getAnzahl_einstechpunkte());
            assertNotNull(teil.getBearbeitungszeit());
            assertNotNull(teil.getEinstechzeit());
            assertNotNull(teil.getFlaeche());
            assertNotNull(teil.getGeofilename());
            assertNotNull(teil.getGewicht());
            assertNotNull(teil.getSchneidlaenge());

        }
    }

    /**
     * Check if every Filed of Schneideplan is filled
     * @param schneidplan
     */
    private void checkFilledSchneidplan(Schneidplan schneidplan) {
        assertNotNull(schneidplan.getAnzahl_programmdurchlaeufe());
        assertNotNull(schneidplan.getGesamtschnittlaenge());
        assertNotNull(schneidplan.getGewicht());
        assertNotNull(schneidplan.getMaschinenzeit());
        assertNotNull(schneidplan.getMaterial_id());
        assertNotNull(schneidplan.getMindestzuschnitt());
        assertNotNull(schneidplan.getProgrammname());
        assertNotNull(schneidplan.getVerschnitt());
        assertNotNull(schneidplan.getZuschnitt());


    }



}
