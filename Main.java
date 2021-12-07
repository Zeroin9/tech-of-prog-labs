package com.company.zeroing;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        lab1();
        //lab2();
        //lab3();
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
        // TODO
    }

    private static void lab3() {
        // TODO
    }
}
