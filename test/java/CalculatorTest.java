import org.junit.*;


import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    private Calculator cal;
    private Fraction fract = new Fraction(1,1);

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before CalculatorTest.class");
    }

    @Before
    public void initTest() {
        cal = new Calculator();
    }



    @Test
    public void TestbracketsAddition() throws Exception {
        Assert.assertEquals("5/6",fract.toString(cal.brackets(new StringBuilder("1/2 + 1/3"))));
    }

    @Test
    public void TestbracketsSubtraction() throws Exception {
        Assert.assertEquals("1/6",fract.toString(cal.brackets(new StringBuilder("1/2 - 1/3"))));
    }

    @Test
    public void TestbracketsMultiplication() throws Exception {
        Assert.assertEquals("1/6", fract.toString(cal.brackets(new StringBuilder("1/2 * 1/3"))));
    }

    @Test
    public void TestbracketsDivision() throws Exception {
        Assert.assertEquals("3/2", fract.toString(cal.brackets(new StringBuilder("1/2 : 1/3"))));
    }

    @Test
    public void Testbrackets2operationsSimple() throws Exception {
        Assert.assertEquals("1/6" , fract.toString(cal.brackets(new StringBuilder("1/2 + 1/3 - 2/3"))));
    }

    @Test
    public void Testbrackets2operationsHard1() throws Exception {
        Assert.assertEquals("3/4" , fract.toString(cal.brackets(new StringBuilder("1/2 + 1/3 * 3/4"))));
    }

    @Test
    public void Testbrackets2operationsHard2() throws Exception {
        Assert.assertEquals("-1/4", fract.toString(cal.brackets(new StringBuilder("-1/-3 * 3/4 - 1/2"))));
    }

    @Test
    public void WrongExpression() {
        Assert.assertTrue(cal.Check("--1/3 * 3/4 - 1/2*2/1"));
    }

    @Test
    public void zeroDivision() {
        Assert.assertTrue(cal.Check("2/1 : 0/1"));
    }

    @Test
    public void WrongSymbols() {
        Assert.assertTrue(cal.Check("2/c : 0/1!"));
    }

    @After
    public void afterTest() {
        cal = null;
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After CalculatorTest.class");
    }
}


