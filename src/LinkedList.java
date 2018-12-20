public class LinkedList {
    private Node next; // указатель на следующий элемент
    private Node last; // указатель на последний элемент
    private Node current; // указатель на текущий элемент
    private Node prev; // указатель на предыдущий элемент
    private Node first; // указатель на первый элемент
    int size = 0; // размер списка

    LinkedList() {
    }

    Object add(Object object){ // добавление объекта
        size++;
        Node tmp = last; // последний объект запоминается
        Node x = new Node(tmp, object, null); // новый объект; последний элемент становится предыдущим для нового, новый становится текущим, следующий = null
        last = x; // последним становится новый объект
        if (first == null){ // если список - пустой, то все указатели равны указателю нового объекта
            current = x;
            next = x;
            prev = x;
            first = x;
        }
        else { // добавление указателя объекта в конец списка
            current = x;
            next = x;
            prev = tmp;
        }
        return object;
    }

    Object getObject (int x) { // возврат объекта (не пробовал. возможно, не работает!)
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

    Object remove (Object object) { // удаление объекта
        Node current = first;
        while (current != null) { // обход всего списка
            if (Node.getCurrent().equals(object)) { // удаляемый объект должен существовать в списке
                if (prev != null && next != null) { // удаление в середине списка
                    Node tmp = next;
                    current = prev;
                    next = tmp;
                }
                if (next == null && prev != null) { // удаление конечного элемента
                    last = prev;
                    current = prev;
                }
                if (current == first && current == last) { // если в списке существует только один объект
                    prev = null;
                    next = null;
                    last = null;
                    first = null;
                    current = null;
                }
                else { // удаление первого элемента
                    current = next;
                    prev = current;
                    first = current;
                }
                size--;
            }
        }
        return true;
    }

    boolean contains(Object object){ // содержится ли объект
        Node current = first;
        while (current != null){ // обход списка
            if (Node.getCurrent().equals(object)){ // объект должен существовать в списке
                return true;
            }
            current = Node.getNext();
        }
        return false;
    }

    public void clear(){ // полное очищение списка
        prev = null;
        next = null;
        last = null;
        first = null;
        current = null;
        size = 0;
    }

    int getSize(){ // вывод размера списка
        return size;
    }
}

class Node {
    private static Object current; // значение объекта
    private static Node prev; // указатель на предыдущий элемент
    private static Node next; // указатель на следующий элемент

    Node(Node prev, Object current, Node next) { // создание элемента (Node)
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