import java.util.regex.Pattern;

public enum Lexeme {
    INIT(Pattern.compile("^init$")), // включение переменной "int a b с"
    END(Pattern.compile("^end$")), // завершение кода "end;" (нужен в связи с техническими проблемами данного кода парсера)
    IF(Pattern.compile("^if$")), // если (условие) {сделать} "if (four < 5) {four = 4}"
    LOGIC(Pattern.compile("^<|>|<=|>=|!=|==$")), // логические операции "99 > 6" "1 == 1" "six != 7"
    SYMBOLS(Pattern.compile("^[a-z]+$")), // переменные "a" "five" "sdpuwy"
    NUMBERS(Pattern.compile("^0|[1-9][0-9]*$")), // любые числа
    ARITHMETIC(Pattern.compile("^\\+|-|\\*|/$")), // арифметические операции "1+5" "six-1" "dva*sem" "op = (3-1)*7"
    SPACEBAR(Pattern.compile("^\\s+$")), // пробел
    EQUALS(Pattern.compile("^=$")), // установка значения переменной "six = 6"
    LBRACE(Pattern.compile("^\\{$")), // левая кавычка для цикла если
    RBRACE(Pattern.compile("^}$")), // правая кавычка для цикла если
    LPARENTHESIS(Pattern.compile("^\\($")), // левая скобка для цикла если, либо для арифметических операций
    RPARENTHESIS(Pattern.compile("^\\)$")), // правая скобка для цикла если, либо для арифметических операций
    SEMICOLON(Pattern.compile("^;$")); // разделений операций "int x one; x = 10; one = 1; if(x > one) {x = x/10};"

    Pattern pattern;

    Lexeme(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}