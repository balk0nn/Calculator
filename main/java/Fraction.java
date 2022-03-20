import java.util.Scanner;

class Fraction {

    protected int numerator;
    protected int denominator;

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    //проверка на 0 в числителе
    private boolean numeratorZeroCheck(Fraction a) {
        return a.numerator == 0;
    }

    //проверка на 0 в знаменателе
    private boolean denominatorZeroCheck(Fraction a) {
        return a.denominator == 0;
    }

    //проверка на единицу в знаменателе
    private boolean denominatorOneCheck(Fraction a) {
        return a.denominator == 1;
    }

    //Ввод дроби с клавиатуры
    public Fraction getFraction() {
        Scanner scan = new Scanner(System.in);
        Fraction get = new Fraction(1, 1);
        System.out.println("Enter the Numerator(must be an integer)");
        while (!scan.hasNextInt()) {
            System.out.println("Not an integer");
            scan.next();
        }
        get.numerator = scan.nextInt();
        System.out.println("Enter the Denominator(must be an integer)");
        while (!scan.hasNextInt()) {
            System.out.println("Not an integer");
            scan.next();
        }
        get.denominator = scan.nextInt();
        while (denominatorZeroCheck(get)) {
            System.out.println("Denominator must not be a 0");
            while (!scan.hasNextInt()) {
                System.out.println("Not an integer");
                scan.next();
            }
            get.denominator = scan.nextInt();
        }
        return get;
    }

    //Операция сложения с двумя аргументами
    public Fraction addition(Fraction a, Fraction b) {
        Fraction add = new Fraction(1, 1);
        add.numerator = (a.numerator * b.denominator) + (b.numerator * a.denominator);
        add.denominator = a.denominator * b.denominator;
        return add;
    }

    //Операция сложения с ондим аргументом
    public Fraction addition(Fraction b) {
        Fraction add = new Fraction(1, 1);
        add.numerator = (numerator * b.denominator) + (b.numerator * denominator);
        add.denominator = denominator * b.denominator;
        return add;
    }


    //Операция вычитания с двумя аргументами
    public Fraction subtraction(Fraction a, Fraction b) {
        Fraction sub = new Fraction(1, 1);
        sub.numerator = (a.numerator * b.denominator) - (b.numerator * a.denominator);
        sub.denominator = a.denominator * b.denominator;
        return sub;
    }

    //Операция вычитания с одним аргументом
    public Fraction subtraction(Fraction b) {
        Fraction sub = new Fraction(1, 1);
        sub.numerator = (numerator * b.denominator) - (b.numerator * denominator);
        sub.denominator = denominator * b.denominator;
        return sub;
    }


    //Операция умножения с двумя аргументами
    public Fraction multiplication(Fraction a, Fraction b) {
        Fraction mult = new Fraction(1, 1);
        mult.numerator = a.numerator * b.numerator;
        mult.denominator = a.denominator * b.denominator;
        return mult;
    }

    //Операция умножения с одним аргументом
    public Fraction multiplication(Fraction b) {
        Fraction mult = new Fraction(1, 1);
        mult.numerator = numerator * b.numerator;
        mult.denominator = denominator * b.denominator;
        return mult;
    }

    //Операция деления с двумя аргументами
    public Fraction division(Fraction a, Fraction b) {
        Fraction div = new Fraction(1, 1);
        div.numerator = a.numerator * b.denominator;
        div.denominator = a.denominator * b.numerator;
        return div;
    }

    //Операция деления с одним аргументом
    public Fraction division(Fraction b) {
        Fraction div = new Fraction(1, 1);
        div.numerator = numerator * b.denominator;
        div.denominator = denominator * b.numerator;
        return div;
    }

    //Приведение дроби к нормальной записи с точки зрения знаков
    private Fraction signs(Fraction a) {
        if ((a.numerator < 0 & a.denominator < 0) || (a.numerator > 0 & a.denominator < 0)) {
            a.numerator = a.numerator * (-1);
            a.denominator = a.denominator * (-1);
        }
        return a;
    }


    //Вывод дроби
    public void print(Fraction print) {
        Fraction finalPrint = signs(reduction(print));
        if ((denominatorOneCheck(finalPrint)) || (numeratorZeroCheck(finalPrint))) {
            System.out.println(finalPrint.numerator);
        } else if (denominatorZeroCheck(finalPrint)) {
            System.out.println("Undefined");
        } else
            System.out.println(finalPrint.numerator + "/" + finalPrint.denominator);
    }

    //Сокращение дроби
    protected Fraction reduction(Fraction a) {
        int min = Math.min(a.numerator, a.denominator);
        for (int j = 1; j <= Math.abs(min); j++) {
            if ((a.numerator % j == 0) & (a.denominator % j == 0)) {
                a.numerator = a.numerator / j;
                a.denominator = a.denominator / j;
                j = 1;
            }
        }
        return signs(a);
    }

    //Метод записывает дробь в виде строки
    protected String toString(Fraction a){
        return a.numerator + "/" + a.denominator;
    }
}