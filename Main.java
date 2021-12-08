package com.company.zeroing;

import com.company.zeroing.binarytree.BinaryTree;
import com.company.zeroing.binarytree.MyBinaryTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        //lab1();
        //lab2();
        lab3();
    }

    private static void lab1() {
        // Создание списка
        List<String> linkedList = new LinkedList<>();

        // Добавление элементов по ключу (индексу)
        linkedList.add(0, "first");
        linkedList.add(1, "second");
        linkedList.add(2, "third");

        // Удалить элемент списка по ключу (индексу)
        linkedList.remove(2);

        //Изменить элемент списка по ключу (индексу)
        linkedList.set(1, "last");

        // Просмотреть список
        System.out.println(linkedList);

        // Выполнить запрос (получение элемента по ключу (индексу))
        System.out.println(
                linkedList.get(1)
        );
    }

    private static void lab2() {
        // Создание очереди
        Queue<String> queue = new LinkedList<>();

        // Добавление элементов
        queue.offer("first");
        queue.offer("second");
        queue.offer("third");
        queue.offer("fourth");

        // Получим первый элемент и удали его
        System.out.println(
                queue.poll()
        );

        // Получим первый элемент без удаления
        System.out.println(
                queue.peek()
        );

        // Получим представление очереди в виде строки
        System.out.println(queue);
    }

    private static void lab3() {
        // Создаём дерево
        BinaryTree<Integer, String> binaryTree = new MyBinaryTree<>();
        // Заполняем его узлами
        binaryTree.add(6, "f");
        binaryTree.add(4, "d");
        binaryTree.add(9, "i");
        binaryTree.add(2, "b");
        binaryTree.add(5, "e");
        binaryTree.add(7, "g");
        binaryTree.add(18, "s");
        binaryTree.add(1, "a");
        binaryTree.add(3, "c");
        binaryTree.add(15, "o");
        binaryTree.add(12, "l");
        binaryTree.add(16, "p");
        binaryTree.add(14, "n");

        // Удалим узел по ключу
        System.out.println(
                binaryTree.remove(9)
        );

        // Получим значение узла по ключу
        System.out.println(
                binaryTree.get(14)
        );

        System.out.println();

        // Вывод дерева в консольный вывод
        binaryTree.printToPrintStream(System.out);
    }
}
