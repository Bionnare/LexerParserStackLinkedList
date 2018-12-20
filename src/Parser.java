import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Parser {
    LinkedList linkList = new LinkedList(); // связный список
    List<Lexer.Token> altTokens = new ArrayList<>(); // список токенов без пробелов / загруженный из лексера
    List<String> initVar = new ArrayList<>(); // список 'включённых' переменных
    List<String> stackList = new ArrayList<>(); // список_ПОЛИЗ
    Stack<String> stackOperation = new Stack<>(); // стек для операций
    int pos = 0; // счетчик
    int p = 0; // переход в цикле 'если'

    boolean startParser(List<Lexer.Token> tokens) { // загрузка из лексера
        boolean tmp = false;

        for (Lexer.Token token : tokens) { // удаление пробелов
            if (token.getLex() != Lexeme.SPACEBAR) {
                altTokens.add(token);
            }
        }
        //for (pos = 0; pos < altTokens.size(); pos++){ // надежный
        //while (pos != altTokens.size()) { // более простой
        if (initialization());{ // || assign() || operationIf()); {
            tmp = true;
        }
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            System.err.println("\nРабота кода прекращена! / The code is no longer working!");
        }
                                                                                                                        //}
        System.out.println("\nПОЛИЗ / Reverse Polish notation: " + stackList);

        System.out.print("\nСвязный список / Linked list: ");
        for (Object x : stackList){
            linkList.add(x);
            System.out.print("[" + Node.getCurrent() + "] ");
        }
        System.out.print("\n");
        return tmp;
    }

    boolean initialization(){ // инициализация переменных / 'их включение'
        boolean tmp = false;
        int i = 0;

        if (altTokens.get(pos).getLex() == Lexeme.INIT) { // лексема == init
            System.out.println("\nСледующие переменные инициализированы / The following variables are initialized:");
            for (i = pos + 1; altTokens.get(i).getLex() != Lexeme.SEMICOLON; i++){
                //while (getNextLexeme() != Lexeme.SEMICOLON) { // навсякий случай
                if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // следующая лексема == переменной
                    pos = i; // возращение значения позиции счетчика для проверки на запись в списке инициализированных переменных
                    if (!initBoolean()) { // проверка одинаковые ли переменные (работает!)
                        initVar.add(altTokens.get(i).getStr()); // список инициализированных переменных / добавление следующего значения
                        System.out.println("[" + altTokens.get(i).getStr() + "]");
                        tmp = true;
                    }
                    else { // переменная уже инициализирована
                        System.err.println("The variable [" + altTokens.get(i).getStr() + "] is initialized already!");
                        System.exit(1);
                    }
                }
                else { // переменные могут состоять только из строчных латинских букв
                    if (altTokens.get(i).getLex() != Lexeme.SEMICOLON) {
                        System.err.println("Variables can only consist of lowercase letters! \n" + "The variable [" + altTokens.get(i).getStr() + "] is incorrectly composed!");
                        System.exit(1);
                    }
                }
            }
        }
        pos = i + 1;
        //System.out.println("initPos = " + pos);
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        if (assign()) { // после инициализации переменным должны задать значения или же они будут равны '0'

        }
        return tmp;
    }

    private boolean Menu() { // вызов определенного метода составления ПОЛИЗА
        boolean tmp = false;

        while (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // ПОЛИЗ выражения
            if (assign()) {
                tmp = true;
            }
            //System.out.println("Pos1 = " + pos);
        }
        while (altTokens.get(pos).getLex() == Lexeme.IF) { // ПОЛИЗ цикла
            if (operationIf()) {
                tmp = true;
            }
            //System.out.println("Pos2 = " + pos);
        }
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        return tmp;
    }

    private boolean assign(){ // присваивание (a = b)
        boolean tmp = false;
        String impStr; // для хранения целевой переменной

        //pos++; // для цикла 'если' (обращение к assign())
        if (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // если переменная
            impStr = altTokens.get(pos).getStr();
            if (altTokens.get(pos + 1).getLex() == Lexeme.EQUALS) { // и '='
                if (initBoolean()) { // проверка на инициализацию переменной
                    //System.out.println("impStr = " + impStr);
                    //System.out.println("assPos1 = " + pos);
                    stackList.add(impStr);
                    if (operation()) { // сортировка ПОЛИЗА выражения
                        tmp = true;
                    }
                    //System.out.println("assPos2 = " + pos);
                    while (!stackOperation.empty()){
                        stackList.add(stackOperation.pop()); // добавление последних операций из стека
                    }
                    stackList.add("="); // добавление в конец списка-ПОЛИЗА '='
                }
                else { // переменная не инициализирована
                    System.err.println("The variable [" + altTokens.get(pos).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }

        }
        //System.out.println("assPos2 (stackList) = " + pos);
        //System.out.println("\nПОЛИЗ / Reverse Polish notation: " + stackList);
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        if (Menu()){ // переход к следующей части кода

        }
        //System.out.println("Pos3 = " + pos);
        return tmp;
    }

    private boolean operation(){ // составление ПОЛИЗА
        boolean tmp = false;
        int i;

        //System.out.println("operBegin = " + pos);
        for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.SEMICOLON; i++){
            if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // добавление в ПОЛИЗ переменных
                pos = i;
                if (initBoolean()) { // если они инициализированы конечно же
                    stackList.add(altTokens.get(i).getStr());
                    tmp = true;
                }
                else { // переменная не инициализирована
                    System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }
            if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // добавление в ПОЛИЗ чисел
                stackList.add(altTokens.get(i).getStr());
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.ARITHMETIC) { // добавление в ПОЛИЗ операций (только арифметических)
                pos = i;
                if (operationBoolean()) { // проверка на правильное расположение знаков операций
                    if (!stackOperation.empty()) { // если стек не пустой
                        while (getOperation(altTokens.get(i).getStr()) <= getOperation(stackOperation.peek())) { // сравнивание приоритета операций
                            stackList.add(stackOperation.pop()); // работа обратной польской записи
                            if (stackOperation.empty()) {
                                break;
                            }
                        }
                    }
                }
                else { // неправильное расположение арифм. / логич. знаков
                    System.err.println("Incorrect arrangement of arithmetic / logical signs!");
                    System.exit(1);
                }
                stackOperation.push(altTokens.get(i).getStr()); // добавление в стек
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.LPARENTHESIS) { // если встречается '('
                stackOperation.push(altTokens.get(i).getStr()); // добавляем в стек
            }
            if (altTokens.get(i).getLex() == Lexeme.RPARENTHESIS) { // как только находится вторая половинка
                while (!stackOperation.peek().equals("(")) { // выгружаем из стека все операции пока не возвратимся к '('
                    stackList.add(stackOperation.pop());
                }
                stackOperation.pop(); // а от нее просто избавляемся
                tmp = true;
            }
        }
        pos = i + 1;
        //System.out.println("operFinish = " + pos);
        return tmp;
    }

    private boolean operationIf(){ // цикл 'если (условие) {сделать}'
        boolean tmp = false;
        String op = ""; // переменная для записи логич. знака из условия цикла
        int i = 0;
        String impStr = "";

        if (altTokens.get(pos).getLex() == Lexeme.IF) { // если находим 'if'
            System.out.println("\nПрисутствует цикл 'если' / There is a 'if' cycle!");
            if (altTokens.get(pos+1).getLex() == Lexeme.LPARENTHESIS) { // а дальше идет '('
                //System.out.println("Start of expr! (IF-RPN)" + altTokens.get(pos+1).getStr());
                for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.RPARENTHESIS; i++){
                    if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // добавление в ПОЛИЗ переменных
                        pos = i;
                        //System.out.println("posIF №1 = " + pos);
                        if (initBoolean()) { // если они инициализированы конечно же
                            stackList.add(altTokens.get(i).getStr());
                            tmp = true;
                        }
                        else { // переменная не инициализирована
                            System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                            System.exit(1);
                        }
                    }
                    if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // добавление в ПОЛИЗ чисел
                        stackList.add(altTokens.get(i).getStr());
                        tmp = true;
                    }
                    if (altTokens.get(i).getLex() == Lexeme.LOGIC) { // добавление логич. знака из условия / для начала запись в переменную
                        op = altTokens.get(i).getStr();
                        tmp = true;
                    }
                    if (altTokens.get(i).getLex() != Lexeme.SYMBOLS && altTokens.get(i).getLex() != Lexeme.NUMBERS && altTokens.get(i).getLex() != Lexeme.LOGIC && altTokens.get(i).getLex() != Lexeme.RPARENTHESIS && altTokens.get(i).getLex() != Lexeme.LPARENTHESIS) {
                        System.err.println("In the condition of the 'if' loop, only a logical expression can be used!");
                        System.exit(1); // проверка на то, что в условии цикла содержится логич. знак
                    }
                }
                pos = i + 1;
                stackList.add(op); // запись в список-ПОЛИЗ ранее полученного логич. знака
                p = stackList.size(); // получаем индекс в списке-ПОЛИЗЕ элемента перехода
                stackList.add("p"); // запись элемента перехода
                stackList.add("!F"); // запись знака операции
                if (altTokens.get(pos).getLex() == Lexeme.LBRACE) { // переход к блоку цикла
                    pos++;
                    //System.out.println("This is block of cycle! (IF-RPN)" + pos);



/////////////////////////КОПИЯ assign() / [ОБРАЩЕНИЕ К ДАННОМУ МЕТОДУ КОРРЕКТНО НЕ РАБОТАЕТ!]/////////////////////////
                    if (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // если переменная
                        impStr = altTokens.get(pos).getStr();
                        if (altTokens.get(pos+1).getLex() == Lexeme.EQUALS) { // и '='
                            if (initBoolean()) { // проверка на инициализацию переменной
                                //System.out.println("impStr = " + impStr);
                                //System.out.println("assPos1 if = " + pos);
                                stackList.add(impStr);
                                if (operationBlockIf()) { // переход к слегка изменной копии метода operation(), так как тоже нормально не работает!
                                    tmp = true;
                                }
                                //System.out.println("assPos2 if = " + pos);
                                while (!stackOperation.empty()){
                                    stackList.add(stackOperation.pop()); // добавление последних операций из стека
                                }
                                stackList.add("="); // добавление в конец списка-ПОЛИЗА '='
                            }
                            else { // переменная не инициализирована
                                System.err.println("The variable [" + altTokens.get(pos).getStr() + "] is not initialized!");
                                System.exit(1);
                            }
                        }
                    }
/////////////////////////КОПИЯ assign() / [ОБРАЩЕНИЕ К ДАННОМУ МЕТОДУ КОРРЕКТНО НЕ РАБОТАЕТ!]/////////////////////////



                    stackList.set(p, String.valueOf(pos + 1)); // изменение ранее записанного элемента перехода на его реальное значение
                }
            }
        }
        //System.out.println("posEndIf = " + pos);
        //System.out.println("finishSize = " + altTokens.size());
        //System.out.println("\nФинальный ПОЛИЗ / Final Reverse Polish notation: " + stackList);
        pos++;
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        if (Menu()) { // переход к следующей части кода

        }
        //System.out.println("Pos6 = " + pos);
        return tmp;
    }



/////////////////////////КОПИЯ operation() / [ИЗ-ЗА ПРОБЛЕМ С assign() ТАКЖЕ НЕ РАБОТАЕТ КОРРЕКТНО!]/////////////////////////
    private boolean operationBlockIf(){ // составление ПОЛИЗА (для цикла 'если')
        boolean tmp = false;
        int i;

        //System.out.println("operBegin if = " + pos);
        for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.RBRACE; i++){ // !!!единственное изменение!!!
            if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // добавление в ПОЛИЗ переменных
                pos = i;
                if (initBoolean()) { // если они инициализированы конечно же
                    stackList.add(altTokens.get(i).getStr());
                    tmp = true;
                }
                else { // переменная не инициализирована
                    System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }
            if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // добавление в ПОЛИЗ чисел
                stackList.add(altTokens.get(i).getStr());
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.ARITHMETIC) { // добавление в ПОЛИЗ операций (только арифметических)
                pos = i;
                if (operationBoolean()) { // проверка на правильное расположение знаков операций
                    if (!stackOperation.empty()) { // если стек не пустой
                        while (getOperation(altTokens.get(i).getStr()) <= getOperation(stackOperation.peek())) { // сравнивание приоритета операций
                            stackList.add(stackOperation.pop()); // работа обратной польской записи
                            if (stackOperation.empty()) {
                                break;
                            }
                        }
                    }
                }
                else { // неправильное расположение арифм. / логич. знаков
                    System.err.println("Incorrect arrangement of arithmetic / logical signs!");
                    System.exit(1);
                }
                stackOperation.push(altTokens.get(i).getStr()); // добавление в стек
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.LPARENTHESIS) { // если встречается '('
                stackOperation.push(altTokens.get(i).getStr()); // добавляем в стек
            }
            if (altTokens.get(i).getLex() == Lexeme.RPARENTHESIS) { // как только находится вторая половинка
                while (!stackOperation.peek().equals("(")) { // выгружаем из стека все операции пока не возвратимся к '('
                    stackList.add(stackOperation.pop());
                }
                stackOperation.pop();  // а от нее просто избавляемся
                tmp = true;
            }
        }
        pos = i + 1;
        //System.out.println("operFinish if = " + pos);
        return tmp;
    }
/////////////////////////КОПИЯ operation() / [ИЗ-ЗА ПРОБЛЕМ С assign() ТАКЖЕ НЕ РАБОТАЕТ КОРРЕКТНО!]/////////////////////////

    boolean result (List<StackMachine.Variable> obj) { // вывод полученных значений кода

        System.out.println("\nРезультат / Result: \n");
        for (int x = 0; x < obj.size(); x++){
            System.out.println("    - Переменная / The Variable [" + obj.get(x).getVar() + " = " + obj.get(x).getVal() + "]");
        }
        System.out.println("\n");
        return true;
    }

/////////////////////////НЕ ОСНОВНЫЕ ОПЕРАЦИИ(МЕТОДЫ ДОПОЛНЕНИЯ)/////////////////////////
    private boolean initBoolean() { // проверка на инициализацию переменной
        boolean tmp = false;

        for (String str : initVar){
            if (altTokens.get(pos).getStr().equals(str)){
                tmp = true;
            }
        }
        return tmp;
    }

    private boolean operationBoolean() { // арифм. / логич. знаки не должны распологаться рядом
        boolean tmp = false;

        if (altTokens.get(pos).getLex() == Lexeme.ARITHMETIC || altTokens.get(pos).getLex() == Lexeme.LOGIC){
            if (altTokens.get(pos+1).getLex() == Lexeme.SYMBOLS || altTokens.get(pos+1).getLex() == Lexeme.NUMBERS || altTokens.get(pos+1).getLex() == Lexeme.LPARENTHESIS || altTokens.get(pos+1).getLex() == Lexeme.RPARENTHESIS){
                if (altTokens.get(pos-1).getLex() == Lexeme.SYMBOLS || altTokens.get(pos-1).getLex() == Lexeme.NUMBERS || altTokens.get(pos-1).getLex() == Lexeme.LPARENTHESIS || altTokens.get(pos-1).getLex() == Lexeme.RPARENTHESIS){
                    tmp = true;
                }

            }
        }
        return tmp;
    }

    private int getOperation(String str) { // приоритет операций для составления ПОЛИЗА

        switch (str) {
            case "(":
                return 0;
            case "+":
                return 1;
            case "-":
                return 1;
            case "*":
                return 2;
            case "/":
                return 2;
            default:
                System.err.println("Error!");
                System.exit(1);
                return 0;
        }
    }

/////////////////////////НЕ ИСПОЛЬЗУЕТСЯ/////////////////////////
    private Lexeme getNextLexeme() { // вывод лексемы следующего токена
        return altTokens.get(pos++).getLex();
    }
}