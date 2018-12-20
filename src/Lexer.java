import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

class Lexer {
    int pos; // �������
    String acc = ""; // �������������� ����������
    String baseString = ""; // ������
    Lexeme currentLexeme = null; // �������

    List<Token> listOfString(String string) { // �������� ������ �� main
        List<Token> tokens = new ArrayList<>(); // ������ �������
        System.out.println("\n������ ������ ������� / Full list of tokens:");
        for (pos = 0; pos < string.length(); pos++) {
            char tmp = string.charAt(pos);
            acc += tmp;
            if (!matcher()){
                tokens.add(new Token(currentLexeme, baseString)); // ���������� ������_������� �������� � �� ���������
                System.out.println("[" + baseString + "]" + " = " + currentLexeme);
                acc = "";
                pos--;
            }
            baseString = acc;
        }
        return tokens;
    }

    private boolean matcher() { // ��������� ���������� ������� �� ������� ������ � ������ ���� �������
        for (Lexeme lexeme : Lexeme.values()) {
            Matcher matcher = lexeme.getPattern().matcher(acc);
            if (matcher.matches()) {
                currentLexeme = lexeme;
                return true;
            }
        }
        return false;
    }

    class Token {
        Lexeme lex; // �������
        String str; // �����

        Token(Lexeme lex, String str) { // ������ �������� ������_������� (�������, ��� �����)
            this.lex = lex;
            this.str = str;
        }

        Lexeme getLex() {
            return lex;
        }

        String getStr() {
            return str;
        }
    }
}