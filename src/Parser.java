import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Parser {
    LinkedList linkList = new LinkedList(); // ������� ������
    List<Lexer.Token> altTokens = new ArrayList<>(); // ������ ������� ��� �������� / ����������� �� �������
    List<String> initVar = new ArrayList<>(); // ������ '����������' ����������
    List<String> stackList = new ArrayList<>(); // ������_�����
    Stack<String> stackOperation = new Stack<>(); // ���� ��� ��������
    int pos = 0; // �������
    int p = 0; // ������� � ����� '����'

    boolean startParser(List<Lexer.Token> tokens) { // �������� �� �������
        boolean tmp = false;

        for (Lexer.Token token : tokens) { // �������� ��������
            if (token.getLex() != Lexeme.SPACEBAR) {
                altTokens.add(token);
            }
        }
        //for (pos = 0; pos < altTokens.size(); pos++){ // ��������
        //while (pos != altTokens.size()) { // ����� �������
        if (initialization());{ // || assign() || operationIf()); {
            tmp = true;
        }
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            System.err.println("\n������ ���� ����������! / The code is no longer working!");
        }
                                                                                                                        //}
        System.out.println("\n����� / Reverse Polish notation: " + stackList);

        System.out.print("\n������� ������ / Linked list: ");
        for (Object x : stackList){
            linkList.add(x);
            System.out.print("[" + Node.getCurrent() + "] ");
        }
        System.out.print("\n");
        return tmp;
    }

    boolean initialization(){ // ������������� ���������� / '�� ���������'
        boolean tmp = false;
        int i = 0;

        if (altTokens.get(pos).getLex() == Lexeme.INIT) { // ������� == init
            System.out.println("\n��������� ���������� ���������������� / The following variables are initialized:");
            for (i = pos + 1; altTokens.get(i).getLex() != Lexeme.SEMICOLON; i++){
                //while (getNextLexeme() != Lexeme.SEMICOLON) { // �������� ������
                if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // ��������� ������� == ����������
                    pos = i; // ���������� �������� ������� �������� ��� �������� �� ������ � ������ ������������������ ����������
                    if (!initBoolean()) { // �������� ���������� �� ���������� (��������!)
                        initVar.add(altTokens.get(i).getStr()); // ������ ������������������ ���������� / ���������� ���������� ��������
                        System.out.println("[" + altTokens.get(i).getStr() + "]");
                        tmp = true;
                    }
                    else { // ���������� ��� ����������������
                        System.err.println("The variable [" + altTokens.get(i).getStr() + "] is initialized already!");
                        System.exit(1);
                    }
                }
                else { // ���������� ����� �������� ������ �� �������� ��������� ����
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
        if (assign()) { // ����� ������������� ���������� ������ ������ �������� ��� �� ��� ����� ����� '0'

        }
        return tmp;
    }

    private boolean Menu() { // ����� ������������� ������ ����������� ������
        boolean tmp = false;

        while (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // ����� ���������
            if (assign()) {
                tmp = true;
            }
            //System.out.println("Pos1 = " + pos);
        }
        while (altTokens.get(pos).getLex() == Lexeme.IF) { // ����� �����
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

    private boolean assign(){ // ������������ (a = b)
        boolean tmp = false;
        String impStr; // ��� �������� ������� ����������

        //pos++; // ��� ����� '����' (��������� � assign())
        if (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // ���� ����������
            impStr = altTokens.get(pos).getStr();
            if (altTokens.get(pos + 1).getLex() == Lexeme.EQUALS) { // � '='
                if (initBoolean()) { // �������� �� ������������� ����������
                    //System.out.println("impStr = " + impStr);
                    //System.out.println("assPos1 = " + pos);
                    stackList.add(impStr);
                    if (operation()) { // ���������� ������ ���������
                        tmp = true;
                    }
                    //System.out.println("assPos2 = " + pos);
                    while (!stackOperation.empty()){
                        stackList.add(stackOperation.pop()); // ���������� ��������� �������� �� �����
                    }
                    stackList.add("="); // ���������� � ����� ������-������ '='
                }
                else { // ���������� �� ����������������
                    System.err.println("The variable [" + altTokens.get(pos).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }

        }
        //System.out.println("assPos2 (stackList) = " + pos);
        //System.out.println("\n����� / Reverse Polish notation: " + stackList);
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        if (Menu()){ // ������� � ��������� ����� ����

        }
        //System.out.println("Pos3 = " + pos);
        return tmp;
    }

    private boolean operation(){ // ����������� ������
        boolean tmp = false;
        int i;

        //System.out.println("operBegin = " + pos);
        for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.SEMICOLON; i++){
            if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // ���������� � ����� ����������
                pos = i;
                if (initBoolean()) { // ���� ��� ���������������� ������� ��
                    stackList.add(altTokens.get(i).getStr());
                    tmp = true;
                }
                else { // ���������� �� ����������������
                    System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }
            if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // ���������� � ����� �����
                stackList.add(altTokens.get(i).getStr());
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.ARITHMETIC) { // ���������� � ����� �������� (������ ��������������)
                pos = i;
                if (operationBoolean()) { // �������� �� ���������� ������������ ������ ��������
                    if (!stackOperation.empty()) { // ���� ���� �� ������
                        while (getOperation(altTokens.get(i).getStr()) <= getOperation(stackOperation.peek())) { // ����������� ���������� ��������
                            stackList.add(stackOperation.pop()); // ������ �������� �������� ������
                            if (stackOperation.empty()) {
                                break;
                            }
                        }
                    }
                }
                else { // ������������ ������������ �����. / �����. ������
                    System.err.println("Incorrect arrangement of arithmetic / logical signs!");
                    System.exit(1);
                }
                stackOperation.push(altTokens.get(i).getStr()); // ���������� � ����
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.LPARENTHESIS) { // ���� ����������� '('
                stackOperation.push(altTokens.get(i).getStr()); // ��������� � ����
            }
            if (altTokens.get(i).getLex() == Lexeme.RPARENTHESIS) { // ��� ������ ��������� ������ ���������
                while (!stackOperation.peek().equals("(")) { // ��������� �� ����� ��� �������� ���� �� ����������� � '('
                    stackList.add(stackOperation.pop());
                }
                stackOperation.pop(); // � �� ��� ������ �����������
                tmp = true;
            }
        }
        pos = i + 1;
        //System.out.println("operFinish = " + pos);
        return tmp;
    }

    private boolean operationIf(){ // ���� '���� (�������) {�������}'
        boolean tmp = false;
        String op = ""; // ���������� ��� ������ �����. ����� �� ������� �����
        int i = 0;
        String impStr = "";

        if (altTokens.get(pos).getLex() == Lexeme.IF) { // ���� ������� 'if'
            System.out.println("\n������������ ���� '����' / There is a 'if' cycle!");
            if (altTokens.get(pos+1).getLex() == Lexeme.LPARENTHESIS) { // � ������ ���� '('
                //System.out.println("Start of expr! (IF-RPN)" + altTokens.get(pos+1).getStr());
                for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.RPARENTHESIS; i++){
                    if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // ���������� � ����� ����������
                        pos = i;
                        //System.out.println("posIF �1 = " + pos);
                        if (initBoolean()) { // ���� ��� ���������������� ������� ��
                            stackList.add(altTokens.get(i).getStr());
                            tmp = true;
                        }
                        else { // ���������� �� ����������������
                            System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                            System.exit(1);
                        }
                    }
                    if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // ���������� � ����� �����
                        stackList.add(altTokens.get(i).getStr());
                        tmp = true;
                    }
                    if (altTokens.get(i).getLex() == Lexeme.LOGIC) { // ���������� �����. ����� �� ������� / ��� ������ ������ � ����������
                        op = altTokens.get(i).getStr();
                        tmp = true;
                    }
                    if (altTokens.get(i).getLex() != Lexeme.SYMBOLS && altTokens.get(i).getLex() != Lexeme.NUMBERS && altTokens.get(i).getLex() != Lexeme.LOGIC && altTokens.get(i).getLex() != Lexeme.RPARENTHESIS && altTokens.get(i).getLex() != Lexeme.LPARENTHESIS) {
                        System.err.println("In the condition of the 'if' loop, only a logical expression can be used!");
                        System.exit(1); // �������� �� ��, ��� � ������� ����� ���������� �����. ����
                    }
                }
                pos = i + 1;
                stackList.add(op); // ������ � ������-����� ����� ����������� �����. �����
                p = stackList.size(); // �������� ������ � ������-������ �������� ��������
                stackList.add("p"); // ������ �������� ��������
                stackList.add("!F"); // ������ ����� ��������
                if (altTokens.get(pos).getLex() == Lexeme.LBRACE) { // ������� � ����� �����
                    pos++;
                    //System.out.println("This is block of cycle! (IF-RPN)" + pos);



/////////////////////////����� assign() / [��������� � ������� ������ ��������� �� ��������!]/////////////////////////
                    if (altTokens.get(pos).getLex() == Lexeme.SYMBOLS) { // ���� ����������
                        impStr = altTokens.get(pos).getStr();
                        if (altTokens.get(pos+1).getLex() == Lexeme.EQUALS) { // � '='
                            if (initBoolean()) { // �������� �� ������������� ����������
                                //System.out.println("impStr = " + impStr);
                                //System.out.println("assPos1 if = " + pos);
                                stackList.add(impStr);
                                if (operationBlockIf()) { // ������� � ������ �������� ����� ������ operation(), ��� ��� ���� ��������� �� ��������!
                                    tmp = true;
                                }
                                //System.out.println("assPos2 if = " + pos);
                                while (!stackOperation.empty()){
                                    stackList.add(stackOperation.pop()); // ���������� ��������� �������� �� �����
                                }
                                stackList.add("="); // ���������� � ����� ������-������ '='
                            }
                            else { // ���������� �� ����������������
                                System.err.println("The variable [" + altTokens.get(pos).getStr() + "] is not initialized!");
                                System.exit(1);
                            }
                        }
                    }
/////////////////////////����� assign() / [��������� � ������� ������ ��������� �� ��������!]/////////////////////////



                    stackList.set(p, String.valueOf(pos + 1)); // ��������� ����� ����������� �������� �������� �� ��� �������� ��������
                }
            }
        }
        //System.out.println("posEndIf = " + pos);
        //System.out.println("finishSize = " + altTokens.size());
        //System.out.println("\n��������� ����� / Final Reverse Polish notation: " + stackList);
        pos++;
        if (altTokens.get(pos).getLex() == Lexeme.END) {
            return false;
        }
        if (Menu()) { // ������� � ��������� ����� ����

        }
        //System.out.println("Pos6 = " + pos);
        return tmp;
    }



/////////////////////////����� operation() / [��-�� ������� � assign() ����� �� �������� ���������!]/////////////////////////
    private boolean operationBlockIf(){ // ����������� ������ (��� ����� '����')
        boolean tmp = false;
        int i;

        //System.out.println("operBegin if = " + pos);
        for (i = pos + 2; altTokens.get(i).getLex() != Lexeme.RBRACE; i++){ // !!!������������ ���������!!!
            if (altTokens.get(i).getLex() == Lexeme.SYMBOLS) { // ���������� � ����� ����������
                pos = i;
                if (initBoolean()) { // ���� ��� ���������������� ������� ��
                    stackList.add(altTokens.get(i).getStr());
                    tmp = true;
                }
                else { // ���������� �� ����������������
                    System.err.println("The variable [" + altTokens.get(i).getStr() + "] is not initialized!");
                    System.exit(1);
                }
            }
            if (altTokens.get(i).getLex() == Lexeme.NUMBERS) { // ���������� � ����� �����
                stackList.add(altTokens.get(i).getStr());
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.ARITHMETIC) { // ���������� � ����� �������� (������ ��������������)
                pos = i;
                if (operationBoolean()) { // �������� �� ���������� ������������ ������ ��������
                    if (!stackOperation.empty()) { // ���� ���� �� ������
                        while (getOperation(altTokens.get(i).getStr()) <= getOperation(stackOperation.peek())) { // ����������� ���������� ��������
                            stackList.add(stackOperation.pop()); // ������ �������� �������� ������
                            if (stackOperation.empty()) {
                                break;
                            }
                        }
                    }
                }
                else { // ������������ ������������ �����. / �����. ������
                    System.err.println("Incorrect arrangement of arithmetic / logical signs!");
                    System.exit(1);
                }
                stackOperation.push(altTokens.get(i).getStr()); // ���������� � ����
                tmp = true;
            }
            if (altTokens.get(i).getLex() == Lexeme.LPARENTHESIS) { // ���� ����������� '('
                stackOperation.push(altTokens.get(i).getStr()); // ��������� � ����
            }
            if (altTokens.get(i).getLex() == Lexeme.RPARENTHESIS) { // ��� ������ ��������� ������ ���������
                while (!stackOperation.peek().equals("(")) { // ��������� �� ����� ��� �������� ���� �� ����������� � '('
                    stackList.add(stackOperation.pop());
                }
                stackOperation.pop();  // � �� ��� ������ �����������
                tmp = true;
            }
        }
        pos = i + 1;
        //System.out.println("operFinish if = " + pos);
        return tmp;
    }
/////////////////////////����� operation() / [��-�� ������� � assign() ����� �� �������� ���������!]/////////////////////////

    boolean result (List<StackMachine.Variable> obj) { // ����� ���������� �������� ����

        System.out.println("\n��������� / Result: \n");
        for (int x = 0; x < obj.size(); x++){
            System.out.println("    - ���������� / The Variable [" + obj.get(x).getVar() + " = " + obj.get(x).getVal() + "]");
        }
        System.out.println("\n");
        return true;
    }

/////////////////////////�� �������� ��������(������ ����������)/////////////////////////
    private boolean initBoolean() { // �������� �� ������������� ����������
        boolean tmp = false;

        for (String str : initVar){
            if (altTokens.get(pos).getStr().equals(str)){
                tmp = true;
            }
        }
        return tmp;
    }

    private boolean operationBoolean() { // �����. / �����. ����� �� ������ ������������� �����
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

    private int getOperation(String str) { // ��������� �������� ��� ����������� ������

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

/////////////////////////�� ������������/////////////////////////
    private Lexeme getNextLexeme() { // ����� ������� ���������� ������
        return altTokens.get(pos++).getLex();
    }
}