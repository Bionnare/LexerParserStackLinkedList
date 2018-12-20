import java.util.regex.Pattern;

public enum Lexeme {
    INIT(Pattern.compile("^init$")), // ��������� ���������� "int a b �"
    END(Pattern.compile("^end$")), // ���������� ���� "end;" (����� � ����� � ������������ ���������� ������� ���� �������)
    IF(Pattern.compile("^if$")), // ���� (�������) {�������} "if (four < 5) {four = 4}"
    LOGIC(Pattern.compile("^<|>|<=|>=|!=|==$")), // ���������� �������� "99 > 6" "1 == 1" "six != 7"
    SYMBOLS(Pattern.compile("^[a-z]+$")), // ���������� "a" "five" "sdpuwy"
    NUMBERS(Pattern.compile("^0|[1-9][0-9]*$")), // ����� �����
    ARITHMETIC(Pattern.compile("^\\+|-|\\*|/$")), // �������������� �������� "1+5" "six-1" "dva*sem" "op = (3-1)*7"
    SPACEBAR(Pattern.compile("^\\s+$")), // ������
    EQUALS(Pattern.compile("^=$")), // ��������� �������� ���������� "six = 6"
    LBRACE(Pattern.compile("^\\{$")), // ����� ������� ��� ����� ����
    RBRACE(Pattern.compile("^}$")), // ������ ������� ��� ����� ����
    LPARENTHESIS(Pattern.compile("^\\($")), // ����� ������ ��� ����� ����, ���� ��� �������������� ��������
    RPARENTHESIS(Pattern.compile("^\\)$")), // ������ ������ ��� ����� ����, ���� ��� �������������� ��������
    SEMICOLON(Pattern.compile("^;$")); // ���������� �������� "int x one; x = 10; one = 1; if(x > one) {x = x/10};"

    Pattern pattern;

    Lexeme(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}