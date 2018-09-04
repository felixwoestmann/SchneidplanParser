import org.junit.Test;
import processing.Parser;

public class TestParser {

@Test
public void testParseSchneidplan() {
Parser parser=new Parser();
parser.parseSchneidplan("Reference/einrichteplan_1.htm");

}
}
