public class Main {
    public static void main(String[] args) {
        final String string = "init a barashek c; a = 5 + 3 * (2 - 1 * 2) - 1; if(a == 4) {barashek = a - 3}; end;";
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        StackMachine stackMachine = new StackMachine();

        System.out.println("\nИсходная строка / Original string: \n" + string); // исходная строка
        //System.out.println(lexer.listOfString(string)); // проверка лексера / вывод списка токенов(значение, лексема)
        System.out.println(parser.startParser(lexer.listOfString(string))); // лексер + данные парсера + ПОЛИЗ
        System.out.println(parser.result(stackMachine.stackMachine(parser))); // результат стек_машины
    }
}

// init a barashek c; a = 5 + 3 * (2 - 1 * 2) - 1; if(a == 4) {barashek = a - 3}; end;
// init one six two; two = 1 + 1; six = 9 / 3 * 2; if(six > two) {one = 1}; end;
// init a b c d e; a = 3 - 2 * 6 + (1 * 2) - 5 + 2; b = a + 1; c = a + b - 3; if (c < b) {d = 5}; if(c > b) {e = 4}; end;
