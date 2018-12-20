import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

class Lexer {
    int pos; // счетчик
    String acc = ""; // дополнительная переменная
    String baseString = ""; // токены
    Lexeme currentLexeme = null; // лексемы

    List<Token> listOfString(String string) { // загрузка строки из main
        List<Token> tokens = new ArrayList<>(); // список токенов
        System.out.println("\nПолный список токенов / Full list of tokens:");
        for (pos = 0; pos < string.length(); pos++) {
            char tmp = string.charAt(pos);
            acc += tmp;
            if (!matcher()){
                tokens.add(new Token(currentLexeme, baseString)); // заполнение списка_токенов токенами и их лексемами
                System.out.println("[" + baseString + "]" + " = " + currentLexeme);
                acc = "";
                pos--;
            }
            baseString = acc;
        }
        return tokens;
    }

    private boolean matcher() { // сравнение полученной лексемы со списком лексем и запись этой лексемы
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
        Lexeme lex; // лексема
        String str; // токен

        Token(Lexeme lex, String str) { // состав элемента списка_токенов (лексема, сам токен)
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