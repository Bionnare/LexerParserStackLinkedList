import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackMachine {
    List<Variable> varForStack = new ArrayList<>(); // ������ '����������' ���������� � �� �������� (��� ����� � �����)
    Stack<String> stack = new Stack<>(); // ���� ��� �������� ���������� � �����
    List<String> machine = new ArrayList<>(); // ������_�����
    List<String> init = new ArrayList<>(); // ������ � ������������������� �����������
    List<String> vars = new ArrayList<>(); // ������ '����������' ���������� / ��� ��������

    List<Variable> stackMachine(Parser parser) {
        String a, b;
        int x, y, c;
        String tmp;

        for (String str : parser.initVar) { // ���������� ������ �������������
            init.add(str);
        }
        for (String txt : parser.stackList) { // ���������� ������_������
            machine.add(txt);
        }
        for (int i = 0; i < machine.size(); i++) {
            switch (machine.get(i)) {
                case "=": // �������������
                    a = stack.pop(); // ��������
                    b = stack.pop(); // ������� ����������
                    if (variableBoolean(b)) { // ���� ����� ���������� 'b' ��� ���� ���������
                        if (!initVarBoolean(a)){ // � �������� �������� ������
                            setValOfVariable(b, a); // ������������� �������� ���������� 'b' = ����� 'a'
                        }
                        else { // ���� �� 'a' - ��� ������ ����������
                            if (variableBoolean(a)) { // � ��� ���� ��� ��������� � ������
                                tmp = getValOfVariable(a); // ���������� �������� 'a'
                                if (variableBoolean(b)) { // ���� 'b'
                                    setValOfVariable(b, tmp); // ������������� � 'b' �������� 'a'
                                }
                            }
                            else { // ���� 'a' �������� ����������, �� ��� �� ���� ����������, ��
                                if (variableBoolean(a)) { // ���� 'b'
                                    setValOfVariable(b, "0"); // ������������� � 'b' �������� '0'
                                }
                            }
                        }
                    }
                    //System.out.println("var = " + getVarOfVariable(b) + ", val = " + getValOfVariable(b));
                    break;
                case "+": // ��������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2
                    //System.out.println("a =" + a);
                    //System.out.println("b =" + b);
                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    //System.out.println("x =" + x);
                    //System.out.println("y =" + y);
                    c = x + y; // ������ ������� ���������
                    tmp = Integer.toString(c); // ��������� ��������� ������� � ������
                    //System.out.println("tmp =" + tmp);
                    stack.push(tmp); // � ��������� � ���� ����� ��������
                    break;
                case "-": // ���������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2
                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    c = y - x; // ������ ������� ���������
                    tmp = Integer.toString(c); // ��������� ��������� ������� � ������
                    stack.push(tmp); // � ��������� � ���� ����� ��������
                    break;
                case "*": // ���������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2
                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    c = x * y; // ������ ������� ���������
                    tmp = Integer.toString(c); // ��������� ��������� ������� � ������
                    stack.push(tmp); // � ��������� � ���� ����� ��������
                    break;
                case "/": // �������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2
                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    c = y / x; // ������ ������� ���������
                    tmp = Integer.toString(c); // ��������� ��������� ������� � ������
                    stack.push(tmp); // � ��������� � ���� ����� ��������
                    break;
                case "==": // �����
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2
                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (x == y) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case "!=": // �� �����
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2

                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (x != y) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case "<=": // ������ ��� �����
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2

                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (y <= x) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case ">=": // ������ ��� �����
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2

                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (y >= x) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case "<": // ������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2

                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (y < x) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case ">": // ������
                    a = stack.pop(); // �������� 1
                    b = stack.pop(); // �������� 2

                    if (initVarBoolean(a)) { // ���� 'a' ����������
                        if (variableBoolean(a)) { // � ��� ���������� ��� ���� ��������� � ������
                            x = Integer.parseInt(getValOfVariable(a)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            x = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'a' - �����
                        x = Integer.parseInt(a); // ���������� ����� ��� int
                    }
                    if (initVarBoolean(b)) { // ���� 'b' ����������
                        if (variableBoolean(b)) { // � ��� ���������� ��� ���� ��������� � ������
                            y = Integer.parseInt(getValOfVariable(b)); // ���������� �� �������� ��� int
                        }
                        else{ // ���� ����� ���������� ��� � ������
                            y = 0; // ���������� '0'
                        }
                    }
                    else { // ���� 'b' - �����
                        y = Integer.parseInt(b); // ���������� ����� ��� int
                    }
                    if (y > x) { // ���������� ������� ���������
                        stack.push("true"); // �������� � ���� ���� '������'
                    }
                    else{
                        stack.push("false"); // ���� '���'
                    }
                    break;
                case "!F": // �������
                    a = stack.pop(); // �������� ��������
                    b = stack.pop(); // ������ ��� ����
                    if (b == "false") { // ���� ����
                        i = Integer.parseInt(a); // �� ���������� �������� ��������� �������� ��������
                    }
                    break;
                default: // ���������� � �����
                    if (initVarBoolean(machine.get(i))){ // ���� ���������� � ��� ���� �� ������ ������������������
                        stack.push(machine.get(i)); // ��������� � ����
                        if (!variableBoolean(machine.get(i))) { // ����� (���� ��� �� ���� �������)
                            varForStack.add(new Variable(machine.get(i), "0")); // ������� ������ ���������� (���, �������� [0])
                            vars.add(machine.get(i)); // ������ ���������� � �������������� ������ �������� ���������� ��� ������� ��������
                        }
                    }
                    else { // ���� �����, �� ������ � ����
                        stack.push(machine.get(i));
                    }
                    break;
            }
        }
        return varForStack;
    }

/////////////////////////�� �������� ��������(������ ����������)/////////////////////////
    private boolean initVarBoolean(String var) { // �������� �� ����������

        for (String str : init){
            if (var.equals(str)){
                return true;
            }
        }
        return false;
    }

    private boolean variableBoolean(String var) { // �������� �� ��������� ������ �����������

        for (String str : vars){
            if (var.equals(str)){
                return true;
            }
        }
        return false;
    }

    private String getVarOfVariable(String var) { // ����� ������ ���������� �� ������� �������

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())) {
                return varForStack.get(o).getVar();
            }
        }
        return null;
    }

    private String getValOfVariable(String var) { // ����� ������ �������� ���������� �� ������� �������

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())){
                return varForStack.get(o).getVal();
            }

        }
        return null;
    }

    private void setValOfVariable(String var, String val) { // ����� ��������� �������� ���������� � ������ �������

        for (int o = 0; o < varForStack.size(); o++){
            if (var.equals(varForStack.get(o).getVar())){
                varForStack.get(o).setVal(val);
            }
        }
    }

/////////////////////////�������������� �����/////////////////////////
    class Variable {
        String variable; // ��� ����������
        String value; // �������� ����������

        Variable(String var, String val) { // ���������� � �� ��������
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