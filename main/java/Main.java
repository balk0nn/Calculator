import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fraction fract = new Fraction();
        Scanner scan = new Scanner(System.in);
        Calculator express = new Calculator();
        express.askForExpression();
        String a = scan.nextLine();
        while (!(a.equals("quit"))) {
            while (express.Check(a)) {

                System.out.println("Your expression doesn't meet required conditions");
                a = scan.nextLine();

            }
            fract.print(express.brackets(new StringBuilder(a)));
            a = scan.nextLine();
            Fraction f = new Fraction(3, 5);
            f.print(f.addition(new Fraction(2, 5)));

        }

/*
        Fraction a = new Fraction(2,3);
        Fraction b = new Fraction(3,4);
        Fraction c = new Fraction(4,5);
        ArrayList<Fraction> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        for (Fraction fraction : list) {
            fraction.print(fraction);
        }
        System.out.println("______________");
        for(int j = list.size() - 1; j > 0; j--){
            list.set(j - 1, list.get(j).multiplication(list.get(j), list.get(j - 1)));
            list.remove(j);
        }
        for (Fraction fraction : list) {
            fraction.print(fraction);
        }*/
    }
}
