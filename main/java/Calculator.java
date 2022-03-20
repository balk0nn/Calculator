import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends Fraction {
    protected String Expression;
    ArrayList<String> fractions = new ArrayList<>();
    ArrayList<String> symbols = new ArrayList<>();

    public Calculator() {
        this.Expression = null;
    }

    //просьба ввести выражение с клавиатуры
    public void askForExpression() {
        System.out.println("""
                Введите строчное выражение вида\s
                a/b +-*: c/d +-*: e/f ...\s
                ПРОБЕЛЫ МЕЖДУ ДРОБЯМИ И ЗНАКАМИ ОПЕРАЦИЙ ОБЯЗАТЕЛЬНЫ""");
    }

    //получение списка дробей
    public ArrayList<String> getListOfFractions(String Expression) {

        //Ищем последовательности символов, удовлетворяющим записи дроби
        String pattern = "[-]?\\d+[/][-]?[1-9]\\d*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Expression);

        //Каждую найденную группу символов добавляем к списку дробей
        while (m.find()) {
            fractions.add(m.group());
        }

        //Метод возвращает список с дробями
        return fractions;
    }

    //получение списка знаков операций
    public ArrayList<String> getListOfSymbols(String Expression) {

        //Ищем знаки операций(обязательно с пробелами)
        String symbolPattern = "[ ][*:+-][ ]";
        Pattern p1 = Pattern.compile(symbolPattern);
        Matcher m1 = p1.matcher(Expression);
        while (m1.find()) {

            //Добавляем знаки в список слева направо
            switch (m1.group()) {
                case " + " -> symbols.add("+");
                case " - " -> symbols.add("-");
                case " * " -> symbols.add("*");
                case " : " -> symbols.add(":");
            }
        }

        //Метод возвращает список со знаками операций
        return symbols;
    }

    //Получает массив из строк из списка строк с дробями(чтобы удобнее было работать со сплитом)
    protected String[] toArray(ArrayList<String> InputList) {
        String[] OutputArray = new String[InputList.size()];
        for (int j = 0; j < InputList.size(); j++) {
            OutputArray[j] = InputList.get(j);
        }
        return OutputArray;
    }

    //Преобразует массив из строк в список дробей
    protected ArrayList<Fraction> toFractions(String[] InputArray) {
        ArrayList<Fraction> OutputList = new ArrayList<>();
        for (String s : InputArray) {
            Fraction temp = new Fraction();
            temp.numerator = Integer.parseInt(s.split("/")[0]);
            temp.denominator = Integer.parseInt(s.split("/")[1]);
            OutputList.add(temp);
        }
        return OutputList;
    }

    //Процесс вычисления выражения без скобок
    protected Fraction Calculation(ArrayList<Fraction> InputList, ArrayList<String> symbols) {

        //Условие проверяет, что в выражении более одной дроби
        if (InputList.size() > 1) {

            /*
            В цикле два списка, список дробей и список знаков между ними.
            Длина списка со знаками всегда на 1 меньше длины списка с дробями.
            Цикл идет по списку знаков, сначала ищет операции умножения и деления,
            а потом сложения и вычитания.
            Когда в списке знаков встречется какой - либо символ с индексом j, в списке дробей
            выполняется соответсвенн операция между дробями с индексом j и j + 1,
            поскольку выражение не может начинаться со знака(кроме минуса перед дробью без пробела)
            и символ с индексом j стоит справа от дроби с индексом j:
            2/3(индекс j в списке дробей) +(индекс j в списке знаков) 3/4(индекс j + 1 в списке дробей)
             */
            for (int j = symbols.size() - 1; j >= 0; j--) {
                if (symbols.get(j).equals("*")) {
                    InputList.set(j, InputList.get(j + 1).multiplication(InputList.get(j + 1), InputList.get(j)));
                    InputList.remove(j + 1);
                    symbols.remove(j);
                } else if (symbols.get(j).equals(":")) {
                    InputList.set(j, InputList.get(j + 1).division(InputList.get(j), InputList.get(j + 1)));
                    InputList.remove(j + 1);
                    symbols.remove(j);
                }
            }
            for (int j = symbols.size() - 1; j >= 0; j--) {
                if (symbols.get(j).equals("+")) {
                    InputList.set(j, InputList.get(j + 1).addition(InputList.get(j + 1), InputList.get(j)));
                    InputList.remove(j + 1);
                    symbols.remove(j);
                } else if (symbols.get(j).equals("-")) {
                    InputList.set(j, InputList.get(j + 1).subtraction(InputList.get(j), InputList.get(j + 1)));
                    InputList.remove(j + 1);
                    symbols.remove(j);
                }
            }
        }
        return InputList.get(0);
    }

    //Метод вычисления беcскобочного выражения из строки
    public Fraction CalculationFromString(String expression) {
        //if(Check(expression)) {
        //  System.out.println("Your expression doesn't meet required conditions");
        //}

        //Получаем листы дробей и массивов
        ArrayList<String> symbolsAsList = getListOfSymbols(expression);
        ArrayList<String> fractionsAsString = getListOfFractions(expression);
        //System.out.println(symbolsAsList);
        //System.out.println(fractionsAsString);

        //Переводим в массив чтобы разделить строку через split
        String[] ArrayOfFractionsInString = toArray(fractionsAsString);
        fractionsAsString.clear();

        //System.out.println(Arrays.toString(ArrayOfFractionsInString));

        //Возвращаемся к листу, но уже с типом данных Fraction
        ArrayList<Fraction> fractionsAsList = toFractions(ArrayOfFractionsInString);

        /*
        Метод возвращает значение выражения с двумя аргументами, список дробей и список знаков операций,
        собранных из строки - аргумента к методу
         */
        return Calculation(fractionsAsList, symbolsAsList);
    }

    //Метод проверяет, что строка не начинается со знака операции(кроме минуса, который относится к знаку дроби)
    protected boolean CheckStart(String line) {

        //Случай когда строка начинается со знака операции и после него идет не число
        String pattern1 = "^[+*:-]\\D";
        Pattern start1 = Pattern.compile(pattern1);
        Matcher m1 = start1.matcher(line);

        //Случай когда строка начинается со знака операции(не минус) и после него идет число
        String pattern2 = "^[+*:]\\d";
        Pattern start = Pattern.compile(pattern2);
        Matcher m2 = start.matcher(line);
        return m1.find() || m2.find();
    }

    protected boolean Check(String line) {
        return !OpenAndCloseBrackets(new StringBuilder(line)) || zeroDivision(line) || CheckExpressionOddSymbols(line) || CheckExpressionWhiteSpace(line) || DenominatorZeroCheck(line) || CheckStart(line);
    }

    //Чтобы убедиться, что нет никаких символов, кроме нужных
    protected boolean CheckExpressionOddSymbols(String line) {
        String pattern = "[^[-]\\d()+\\-*:/ ]";
        Pattern fraction = Pattern.compile(pattern);
        Matcher forFraction = fraction.matcher(line);
        return forFraction.find();
    }

    //Проверка на пробелы вокруг знаков операций
    protected boolean CheckExpressionWhiteSpace(String line) {
        String pattern = "\\d+[()]*[+*:-][()]*\\d+";
        Pattern CheckWhiteSpace = Pattern.compile(pattern);
        Matcher WhiteSPace = CheckWhiteSpace.matcher(line);
        return WhiteSPace.find();
    }

    //Проверка на ноль в знаеменателе
    protected boolean DenominatorZeroCheck(String line) {
        String pattern = "/[0]";
        Pattern CheckWhiteSpace = Pattern.compile(pattern);
        Matcher WhiteSPace = CheckWhiteSpace.matcher(line);
        return WhiteSPace.find();
    }

    //Проверка на наличие скобок в выражении
    protected boolean hasBrackets(StringBuilder s) {
        String pattern;
        pattern = "\\(";
        Pattern bracket = Pattern.compile(pattern);
        Matcher brackets = bracket.matcher(s);
        return brackets.find();
    }
    protected boolean zeroDivision(String s) {
        String pattern;
        pattern = "[:][ ][0]";
        Pattern zeroDivision = Pattern.compile(pattern);
        Matcher brackets = zeroDivision.matcher(s);
        return brackets.find();
    }

    protected boolean OpenAndCloseBrackets(StringBuilder brackets){

        //Cоздаем счетчики открывающих и закрывающих скобок
        int opencount = 0;
        int closecount = 0;

        //Прибавляем к соотвествующему счетчику 1 при нахождении скобок
        for (int j = 0; j < brackets.length(); j++) {
            if (brackets.charAt(j) == '(') {
                opencount += 1;
            }
            if (brackets.charAt(j) == ')') {
                closecount += 1;
            }
        }

        //Проверка что количество открывающих и закрывающих скобок одинаково
        return (opencount == closecount);
    }

    //Метод, раскрывающий скобки, возвращает итоговый результат в виде Fraction
    protected Fraction brackets(StringBuilder brackets) {

        /*
        Цикл раскрывает скобки, считая выражение во внутренних скобках
        и заменяет выражение в них на результат, убирая одну пару скобок.
        Цикл выполняется до тех пор, пока все скобки не будут раскрыты
        */
        while (hasBrackets(brackets)) {
            //System.out.println("NEW ITERATION");

            /*
            Ищем первое вхождение закрывающейся скобки в строку, когда находим,
            ищем ближайшее слева вхождение открывающейся скобки
             */
            int close = 0;
            int open = 0;
            for (int j = 0; j < brackets.length(); j++) {
                if (brackets.charAt(j) == ')') {
                    close = j;
                    for (int i = 0; i < brackets.length(); i++) {
                        if (brackets.charAt(i) == '(' & (j - i > 0)) {
                            open = i;
                        }
                    }
                    break;
                }
            }
            //System.out.println(open);
            //System.out.println(close);
            /*
            System.out.println("open " + open);
            System.out.println("close " + close);
            System.out.println("brackets " + brackets);
            */

            //Получение строки с выражением в скобках
            String gotBrackets = brackets.substring(open, close + 1);

            //Удаление найденной строки со скобками
            brackets.delete(open, close + 1);
            //System.out.println("brackets " + brackets);

            //System.out.println("gotBrackets " + gotBrackets);

            //Получение результата выражения в скобках в виде строки
            StringBuilder StringToAppend = new StringBuilder(toString(CalculationFromString(gotBrackets)));
            //System.out.println("StringToAppend " + StringToAppend);

            //Вставляем полученную строку с результатом на нужное место
            brackets.insert(open, StringToAppend);
            //System.out.println("brackets " + brackets);
        }

        //Возвращает результат выражения, после раскрытия всех скобок, если они были
        return reduction(CalculationFromString(brackets.toString()));
    }
}