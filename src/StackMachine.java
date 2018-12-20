import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackMachine {
    List<Variable> varForStack = new ArrayList<>(); // список 'включённых' переменных и их значений (для счета в стеке)
    Stack<String> stack = new Stack<>(); // стек для хранения переменных и чисел
    List<String> machine = new ArrayList<>(); // список_ПОЛИЗ
    List<String> init = new ArrayList<>(); // список с инициализированными переменными
    List<String> vars = new ArrayList<>(); // список 'включённых' переменных / для проверок

    List<Variable> stackMachine(Parser parser) {
        String a, b;
        int x, y, c;
        String tmp;

        for (String str : parser.initVar) { // перезапись списка инициализации
            init.add(str);
        }
        for (String txt : parser.stackList) { // перезапись списка_ПОЛИЗА
            machine.add(txt);
        }
        for (int i = 0; i < machine.size(); i++) {
            switch (machine.get(i)) {
                case "=": // приравнивание
                    a = stack.pop(); // значение
                    b = stack.pop(); // целевая переменная
                    if (variableBoolean(b)) { // если такая переменная 'b' уже была добавлена
                        if (!initVarBoolean(a)){ // а значение является числом
                            setValOfVariable(b, a); // устанавливаем значение переменной 'b' = числу 'a'
                        }
                        else { // если же 'a' - это другая переменная
                            if (variableBoolean(a)) { // и она была уже добавлена в список
                                tmp = getValOfVariable(a); // запоминаем значение 'a'
                                if (variableBoolean(b)) { // ищем 'b'
                                    setValOfVariable(b, tmp); // устанавливаем в 'b' значение 'a'
                                }
                            }
                            else { // если 'a' является переменной, но еще не была загруженна, то
                                if (variableBoolean(a)) { // ищем 'b'
                                    setValOfVariable(b, "0"); // устанавливаем в 'b' значение '0'
                                }
                            }
                        }
                    }
                    //System.out.println("var = " + getVarOfVariable(b) + ", val = " + getValOfVariable(b));
                    break;
                case "+": // сложение
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2
                    //System.out.println("a =" + a);
                    //System.out.println("b =" + b);
                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    //System.out.println("x =" + x);
                    //System.out.println("y =" + y);
                    c = x + y; // решаем сложное выражение
                    tmp = Integer.toString(c); // переводим результат обратно в строку
                    //System.out.println("tmp =" + tmp);
                    stack.push(tmp); // и загружаем в стек новое значение
                    break;
                case "-": // вычитание
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2
                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    c = y - x; // решаем сложное выражение
                    tmp = Integer.toString(c); // переводим результат обратно в строку
                    stack.push(tmp); // и загружаем в стек новое значение
                    break;
                case "*": // умножение
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2
                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    c = x * y; // решаем сложное выражение
                    tmp = Integer.toString(c); // переводим результат обратно в строку
                    stack.push(tmp); // и загружаем в стек новое значение
                    break;
                case "/": // деление
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2
                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    c = y / x; // решаем сложное выражение
                    tmp = Integer.toString(c); // переводим результат обратно в строку
                    stack.push(tmp); // и загружаем в стек новое значение
                    break;
                case "==": // равно
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2
                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (x == y) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case "!=": // не равно
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2

                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (x != y) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case "<=": // меньше или равно
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2

                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (y <= x) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case ">=": // больше или равно
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2

                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (y >= x) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case "<": // меньше
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2

                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (y < x) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case ">": // больше
                    a = stack.pop(); // значение 1
                    b = stack.pop(); // значение 2

                    if (initVarBoolean(a)) { // если 'a' переменная
                        if (variableBoolean(a)) { // и эта переменная уже была добавлена в список
                            x = Integer.parseInt(getValOfVariable(a)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            x = 0; // записываем '0'
                        }
                    }
                    else { // если 'a' - число
                        x = Integer.parseInt(a); // записываем число как int
                    }
                    if (initVarBoolean(b)) { // если 'b' переменная
                        if (variableBoolean(b)) { // и эта переменная уже была добавлена в список
                            y = Integer.parseInt(getValOfVariable(b)); // записываем ее значение как int
                        }
                        else{ // если такой переменной нет в списке
                            y = 0; // записываем '0'
                        }
                    }
                    else { // если 'b' - число
                        y = Integer.parseInt(b); // записываем число как int
                    }
                    if (y > x) { // производим сложное сравнение
                        stack.push("true"); // загрузка в стек либо 'правды'
                    }
                    else{
                        stack.push("false"); // либо 'лжи'
                    }
                    break;
                case "!F": // переход
                    a = stack.pop(); // значение перехода
                    b = stack.pop(); // правда или ложь
                    if (b == "false") { // если ложь
                        i = Integer.parseInt(a); // то переменная счетчика равняется значению перехода
                    }
                    break;
                default: // переменные и числа
                    if (initVarBoolean(machine.get(i))){ // если переменная и при этом из списка инициализированных
                        stack.push(machine.get(i)); // добавляем в стек
                        if (!variableBoolean(machine.get(i))) { // также (если еще не было сделано)
                            varForStack.add(new Variable(machine.get(i), "0")); // создаем объект переменной (имя, значение [0])
                            vars.add(machine.get(i)); // запись переменной в дополнительный список хранения переменных для методов проверки
                        }
                    }
                    else { // если число, то просто в стек
                        stack.push(machine.get(i));
                    }
                    break;
            }
        }
        return varForStack;
    }

/////////////////////////НЕ ОСНОВНЫЕ ОПЕРАЦИИ(МЕТОДЫ ДОПОЛНЕНИЯ)/////////////////////////
    private boolean initVarBoolean(String var) { // проверка на переменную

        for (String str : init){
            if (var.equals(str)){
                return true;
            }
        }
        return false;
    }

    private boolean variableBoolean(String var) { // проверка на созданный объект переменнной

        for (String str : vars){
            if (var.equals(str)){
                return true;
            }
        }
        return false;
    }

    private String getVarOfVariable(String var) { // метод вызова переменной из нужного объекта

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())) {
                return varForStack.get(o).getVar();
            }
        }
        return null;
    }

    private String getValOfVariable(String var) { // метод вызова значения переменной из нужного объекта

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())){
                return varForStack.get(o).getVal();
            }

        }
        return null;
    }

    private void setValOfVariable(String var, String val) { // метод установки значения переменной в нужном объекте

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())){
                varForStack.get(o).setVal(val);
            }
        }
    }

/////////////////////////ДОПОЛНИТЕЛЬНЫЙ КЛАСС/////////////////////////
    class Variable {
        String variable; // имя переменной
        String value; // значение переменной

        Variable(String var, String val) { // переменная и ее значение
            variable = var;
            value = val;
        }

        String getVar() {
            return variable;
        }

        void setVar(String var) {
            variable = var;
        }

        String getVal() {
            return value;
        }

        void setVal(String val) {
            value = val;
        }
    }
}