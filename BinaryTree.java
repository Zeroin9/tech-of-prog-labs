package com.company.zeroing;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация бинарного дерева
 * @param <V> объект способный к сравнению с ему подобными
 */
public class BinaryTree<V extends Comparable, E> {
    /**
     * Верхний элемент дерева
     */
    private Node<V, E> top;

    public BinaryTree() {
        top = null;
    }

    /**
     * Добавление в дерево происходит по следующему принципу:
     *  бОльшие элементы добавляются справа, меньшие - слева.
     * Если места заняты, то необходимо смещаться ниже по дереву.
     * Если ключи во время прохода по дереву сравняются - просто заменить значение в узле.
     * @param key ключ
     * @param value значение
     */
    public void add(@NotNull V key, E value) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        Node<V, E> newNode = new Node<>(key, value);

        // Если дерево пустое, то добавляем новый узел наверх
        if (top == null) {
            top = newNode;
            return;
        }

        addRecursive(top, newNode);
    }

    private void addRecursive(@NotNull Node<V, E> existing, @NotNull Node<V, E> newNode) {
        if (existing == null) {
            throw new NullPointerException("'existing' is null");
        }
        if (newNode == null) {
            throw new NullPointerException("'newNode' is null");
        }

        int compareResult = compareNodes(existing, newNode);
        switch(compareResult) {
            // Если ключ меньше
            case -1:
                if (existing.getLeft() != null) {
                    addRecursive(existing.getLeft(), newNode);
                } else {
                    existing.setLeft(newNode);
                }
                break;
            // Если ключ равен
            case 0:
                existing.setValue(newNode.getValue());
                break;
            // Если ключ больше
            case 1:
                if (existing.getRight() != null) {
                    addRecursive(existing.getRight(), newNode);
                } else {
                    existing.setRight(newNode);
                }
                break;
        }
    }

    /**
     * Удаление в дереве происходит по следующему принципу:
     * 1. Если у удаляемого узла нет детей, то просто очищаем ссылки на удаляемый узел у родительского узла
     * 2. Если у удаляемого узла нет правого узла - заменяем ссылки на удаляемый узел
     *  ссылками на левый узел удаляемого узла
     * 3. Если у удаляемого узла есть правый узел - заменяем ссылки на удаляемый узел
     *  ссылками на минимальный дочерний узел правого узла удаляемого узла (влево до упора)
     * @param key ключ
     * @return true если элемент удалён (был найден)
     */
    public boolean remove(@NotNull V key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        // Ищем что удалять
        Node<V, E>[] nodeWithParent = getNodeWithParent(key);

        if (nodeWithParent == null) {
            return false;
        }

        Node<V, E> parentNode = nodeWithParent[0];
        Node<V, E> nodeToRemove = nodeWithParent[1];

        // Если есть правый узел
        if (nodeToRemove.getRight() != null) {
            // Правый узел
            Node<V, E> rightNode = nodeToRemove.getRight();

            // Если есть узлы слева от правого узла
            if (rightNode.getLeft() != null) {
                // Ищем минимум от правого узла
                Node<V, E> previous = rightNode;
                Node<V, E> minNode = previous.getLeft();
                while (minNode.getLeft() != null) {
                    previous = minNode;
                    minNode = previous.getLeft();
                }

                // Удаляем ссылку на minNode у родительского узла previous
                previous.setLeft(null);

                // У родительского узла удаляемого узла меняем ссылку
                //  с удаляемого узла на minNode (минимум от правого узла)
                replaceChild(parentNode, nodeToRemove, minNode);
            } else {
                // Если узлов слева от правого узла нет - он и есть минимум

                // У родительского узла удаляемого узла меняем ссылку
                //  с удаляемого узла на правый узел
                replaceChild(parentNode, nodeToRemove, rightNode);

                // Левый узел удаляемого узла назначаем левым узлом правого узла
                rightNode.setLeft(nodeToRemove.getLeft());
            }
        } else {
            // Если нет правого узла, но есть левый узел
            if (nodeToRemove.getLeft() != null) {
                // У родительского узла удаляемого узла меняем ссылку
                //  с удаляемого узла на левый узел
                replaceChild(parentNode, nodeToRemove, nodeToRemove.getLeft());
            } else {
                // Если нет узлов
                // У родительского узла удаляемого узла удаляем ссылку
                //  на удаляемый узел
                removeChild(parentNode, nodeToRemove);
            }
        }

        // Удаляем ссылки из удаляемого узла
        nodeToRemove.setLeft(null);
        nodeToRemove.setRight(null);

        return true;
    }

    /**
     * Получение значения по ключу
     * @param key ключ
     * @return значение (value) или null, если не найден ключ
     */
    @Nullable
    public E get(@NotNull V key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        return getNodeRecursive(top, key).getValue();
    }

    /**
     * Получение узла из дерева очень просто:
     * Начинаем от вверхнего эелемента - если ключ больше - идём вправо,
     *  если меньше - влево.
     * Окончание поиска - равенство искомого и найденного ключа.
     * @param key ключ
     * @return узел с соответствующим ключом или null, если не найден ключ
     */
    @Nullable
    private Node<V, E> getNode(@NotNull V key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        // Если дерево пустое
        if (top == null) {
            return null;
        }

        return getNodeRecursive(top, key);
    }

    @Nullable
    private Node<V, E> getNodeRecursive(@NotNull Node<V, E> node, @NotNull V key) {
        if (node == null) {
            throw new NullPointerException("'node' is null");
        }
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        int compareResult = compareKeys(node.getKey(), key);
        switch(compareResult) {
            // Если ключ меньше
            case -1:
                if (node.getLeft() != null) {
                    return getNodeRecursive(node.getLeft(), key);
                } else {
                    return null;
                }
                // Если ключ равен
            case 0:
                return node;
            // Если ключ больше
            case 1:
                if (node.getRight() != null) {
                    return getNodeRecursive(node.getRight(), key);
                } else {
                    return null;
                }
        }
        throw new IllegalStateException("compareTo() return not -1, 0 or 1");
    }

    /**
     * Аналогично методу getNode(), но другой возврат
     * @param key ключ
     * @return Массив из двух значений:
     *  0 - родительский узел искомого узла,
     *  1 - искомый узел.
     *  Или null если узел не найден
     */
    @Nullable
    private Node<V, E>[] getNodeWithParent(@NotNull V key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        // Если дерево пустое
        if (top == null) {
            return null;
        }

        return getNodeWithParentRecursive(null, top, key);
    }

    /**
     *
     * @param parent вышестоящий узел
     * @param node узел для сравнения
     * @param key ключ
     * @return Массив из двух значений:
     *  0 - родительский узел искомого узла,
     *  1 - искомый узел
     */
    @Nullable
    private Node<V, E>[] getNodeWithParentRecursive(Node<V, E> parent, @NotNull Node<V, E> node, @NotNull V key) {
        if (node == null) {
            throw new NullPointerException("'node' is null");
        }
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        int compareResult = compareKeys(node.getKey(), key);
        switch(compareResult) {
            // Если ключ меньше
            case -1:
                if (node.getLeft() != null) {
                    return getNodeWithParentRecursive(node, node.getLeft(), key);
                } else {
                    return null;
                }
                // Если ключ равен
            case 0:
                List<Node<V, E>> resultList = new ArrayList<>();
                resultList.add(0, parent);
                resultList.add(1, node);
                return (Node<V, E>[]) Array.newInstance(parent.getClass(), resultList.size());
            // Если ключ больше
            case 1:
                if (node.getRight() != null) {
                    return getNodeWithParentRecursive(node, node.getRight(), key);
                } else {
                    return null;
                }
        }
        throw new IllegalStateException("compareTo() return not -1, 0 or 1");
    }

    /**
     * Поиск минимального элемента
     *  (до упора влево)
     * @return минимальный элемент или null если дерево пустое
     */
    @Nullable
    public E getMinKey() {
        // Если дерево пустое
        if (top == null) {
            return null;
        }

        Node<V, E> previous = null;
        Node<V, E> current = top;

        while (current != null) {
            previous = current;
            current = previous.getLeft();
        }

        return previous.getValue();
    }

    /**
     * Поиск максимального элемента
     *  (до упора вправо)
     * @return масимальный элемент или null если дерево пустое
     */
    @Nullable
    public E getMaxKey() {
        // Если дерево пустое
        if (top == null) {
            return null;
        }

        Node<V, E> previous = null;
        Node<V, E> current = top;

        while (current != null) {
            previous = current;
            current = previous.getRight();
        }

        return previous.getValue();
    }

    private int compareNodes(@NotNull Node<V, E> a, @NotNull Node<V, E> b) {
        if (a == null) {
            throw new NullPointerException("Node 'a' is null");
        }
        if (b == null) {
            throw new NullPointerException("Node 'b' is null");
        }
        return a.getKey().compareTo(b.getKey());
    }

    private int compareKeys(@NotNull V a, @NotNull V b) {
        if (a == null) {
            throw new NullPointerException("Node 'a' is null");
        }
        if (b == null) {
            throw new NullPointerException("Node 'b' is null");
        }
        return a.compareTo(b);
    }

    private void removeChild(@NotNull Node<V, E> parent, @NotNull Node<V, E> child) {
        replaceChild(parent, child, null);
    }

    private void replaceChild(@NotNull Node<V, E> parent, @NotNull Node<V, E> child, @Nullable Node<V, E> newChild) {
        if (parent == null) {
            throw new NullPointerException("'parent' is null");
        }
        if (child == null) {
            throw new NullPointerException("'child' is null");
        }
        if (Objects.equals(parent.getLeft(), child)) {
            parent.setLeft(newChild);
        } else if (Objects.equals(parent.getRight(), child)) {
            parent.setRight(newChild);
        } else {
            throw new IllegalStateException("No such child in parent");
        }
    }

    /**
     * Класс узла дерева.
     * @param <V> объект способный к сравнению с ему подобными
     */
    private class Node<V extends Comparable, E> {
        /**
         * Ключ не должен быть null
         */
        private V key;
        private E value;
        private Node <V, E> left;
        private Node <V, E> right;

        /**
         * Создание узла дерева. По умолчанию left и right равны null
         * @param key не-null ключ
         * @param value значение
         */
        public Node(@NotNull V key, E value) {
            if (key == null) {
                throw new NullPointerException("key is null");
            }

            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public V getKey() {
            return key;
        }

        public void setKey(V key) {
            this.key = key;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<V, E> getLeft() {
            return left;
        }

        public void setLeft(Node<V, E> left) {
            this.left = left;
        }

        public Node<V, E> getRight() {
            return right;
        }

        public void setRight(Node<V, E> right) {
            this.right = right;
        }
    }
}
