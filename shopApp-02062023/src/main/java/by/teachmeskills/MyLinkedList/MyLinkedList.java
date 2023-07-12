package by.teachmeskills.MyLinkedList;

import java.util.NoSuchElementException;

public class MyLinkedList<T> {
    private Node<T> head;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T t) {
        //если список пустой, то элемент будет начальным, и не будет ни на кого ссылаться.
        if (isEmpty())
            head = new Node<>(null, t, null);
        else {
            //Иначе, сохраняю начальный элемент в промежуточную переменную, чтобы не потерять значение;
            //перезаписываю начальный элемент на передаваемый в параметре и даю ему ссылку на промежуточный элемент(старый начальный);
            //промежуточному элементу даю ссылку на новый начальный;
            Node<T> temp = head;
            head = new Node<>(null, t, temp);
            head.next.prev = head;
        }
        size++;
    }

    public void addLast(T t) {
        //если список пустой, то элемент будет первым.
        if (isEmpty())
            head = new Node<>(null, t, null);
        else {
            //Иначе, беру сохраняю головной элемент в промежуточную переменную, а затем перезаписываю промежуточную переменную, пока ссылка на ее следующий;
            //элемент не будет равна null. Таким образом нахожу последний элемент в листе;
            //Назначаю ссылку последнего листа на новый, а новому на последний;
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new Node<>(temp, t, null);
        }
        size++;
    }

    public void addBefore(T o, T t) {
        //если список пустой, то выбрасываю исключение, что данного элемента не существует;
        if (isEmpty())
            throw new NoSuchElementException("Element " + o.toString() + " not found.");

        Node<T> currentNode = head;

        //перебираю список, пока не буден найден элемент, перед которым необходимо вставить узел либо не упрусь в null;
        while (currentNode != null && !currentNode.value.equals(o))
            currentNode = currentNode.next;

        //Если промежуточный элемент равен null, т. е. список закончился, а нужный элемент, перед которым нужно вставить новый, так и не нашелся,
        //бросаю исключение, что данного элемента нет в списке.
        if (currentNode == null)
            throw new NoSuchElementException("Element " + o.toString() + " not found.");

        //Создаю узел, где ссылка "предыдущий" указывает на предыдущий элемент промежуточной переменной, а "последующим" выступает сама промежуточная переменная.
        Node<T> newNode = new Node<>(currentNode.prev, t, currentNode);

        //проверка на то, что промежуточный элемент не является головным
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;}
        else {
            head = newNode;
        currentNode.prev = newNode;}

        size++;
    }

    public void addAfter(T o, T t) {
        if (isEmpty())
            throw new NoSuchElementException("Element " + o.toString() + " not found.");

        Node<T> currentNode = head;

        while (currentNode != null && !currentNode.value.equals(o))
            currentNode = currentNode.next;

        if (currentNode == null)
            throw new NoSuchElementException("Element " + o.toString() + " not found.");

        Node<T> newNode = new Node<>(currentNode, t, currentNode.next);

        //проверка на то, что промежуточный элемент не является конечным.
        if (currentNode.next != null)
            currentNode.next.prev = newNode;
        currentNode.next = newNode;

        size++;


    }

    public void remove(T t) {
        //если список пустой, то выбрасываю исключение, что данного элемента не существует;
        if (isEmpty())
            throw new NoSuchElementException("Element " + t.toString() + " not found");

        //Если удаляемый элемент является головным, то просто перезаписываю его на следующий. Если в списке лишь один элемент,
        // который удаляю, то ссылка на следующий указывает на null;
        if (head.value.equals(t)) {
            head = head.next;
            return;
        }

        Node<T> currentNode = head;

        //перебираю список, пока не буден найден удаляемый элемент;
        while (currentNode != null && !currentNode.value.equals(t))
            currentNode = currentNode.next;

        //Если промежуточный элемент равен null, т. е. список закончился, а нужный элемент, перед которым нужно вставить новый, так и не нашелся,
        //бросаю исключение, что данного элемента нет в списке.
        if (currentNode == null)
            throw new NoSuchElementException("Element " + t.toString() + " not found.");

        //Если нужный элемент найден, но не достигнут конец списка, то последующему элементу перебиваю ссылку на предыдущий элемент текущего,
        if (currentNode.next != null)
            currentNode.next.prev = currentNode.prev;

        currentNode.prev.next = currentNode.next;

        size--;
    }

    @Override
    public String toString() {
        Node<T> temp = head;
        StringBuilder builder = new StringBuilder("[");

        while (temp != null) {
            builder.append(temp.value).append(",");
            temp = temp.next;
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }

    private static class Node<T> {
        private Node<T> prev;
        private final T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
