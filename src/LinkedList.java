public class LinkedList {
    private Node next; // ��������� �� ��������� �������
    private Node last; // ��������� �� ��������� �������
    private Node current; // ��������� �� ������� �������
    private Node prev; // ��������� �� ���������� �������
    private Node first; // ��������� �� ������ �������
    int size = 0; // ������ ������

    LinkedList() {
    }

    Object add(Object object){ // ���������� �������
        size++;
        Node tmp = last; // ��������� ������ ������������
        Node x = new Node(tmp, object, null); // ����� ������; ��������� ������� ���������� ���������� ��� ������, ����� ���������� �������, ��������� = null
        last = x; // ��������� ���������� ����� ������
        if (first == null){ // ���� ������ - ������, �� ��� ��������� ����� ��������� ������ �������
            current = x;
            next = x;
            prev = x;
            first = x;
        }
        else { // ���������� ��������� ������� � ����� ������
            current = x;
            next = x;
            prev = tmp;
        }
        return object;
    }

    Object getObject (int x) { // ������� ������� (�� ��������. ��������, �� ��������!)
        int y = 0;

        Node current = first;
        while (current != null) {
            if (y == x) {
                return Node.getCurrent();
            }
            y++;
            current = Node.getNext();
        }
        return null;
    }

    Object remove (Object object) { // �������� �������
        Node current = first;
        while (current != null) { // ����� ����� ������
            if (Node.getCurrent().equals(object)) { // ��������� ������ ������ ������������ � ������
                if (prev != null && next != null) { // �������� � �������� ������
                    Node tmp = next;
                    current = prev;
                    next = tmp;
                }
                if (next == null && prev != null) { // �������� ��������� ��������
                    last = prev;
                    current = prev;
                }
                if (current == first && current == last) { // ���� � ������ ���������� ������ ���� ������
                    prev = null;
                    next = null;
                    last = null;
                    first = null;
                    current = null;
                }
                else { // �������� ������� ��������
                    current = next;
                    prev = current;
                    first = current;
                }
                size--;
            }
        }
        return true;
    }

    boolean contains(Object object){ // ���������� �� ������
        Node current = first;
        while (current != null){ // ����� ������
            if (Node.getCurrent().equals(object)){ // ������ ������ ������������ � ������
                return true;
            }
            current = Node.getNext();
        }
        return false;
    }

    public void clear(){ // ������ �������� ������
        prev = null;
        next = null;
        last = null;
        first = null;
        current = null;
        size = 0;
    }

    int getSize(){ // ����� ������� ������
        return size;
    }
}

class Node {
    private static Object current; // �������� �������
    private static Node prev; // ��������� �� ���������� �������
    private static Node next; // ��������� �� ��������� �������

    Node(Node prev, Object current, Node next) { // �������� �������� (Node)
        this.prev = prev;
        this.current = current;
        this.next = next;
    }

    static Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }

    static Node getPrev() {
        return prev;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

    static Object getCurrent() {
        return current;
    }

    void setCurrent(Object current) {
        this.current = current;
    }
}