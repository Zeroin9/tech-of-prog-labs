package com.company.zeroing.binarytree;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Реализация бинарного дерева
 * @param <V> объект способный к сравнению с ему подобными
 * @param <E> значение
 */
public class MyBinaryTree<V extends Comparable<V>, E> implements BinaryTree<V, E> {
    /**
     * Верхний элемент дерева
     */
    private Node<V, E> top;

    public MyBinaryTree() {
        top = null;
    }

    /**
     * Добавление в дерево
     * @param key ключ
     * @param value значение
     * @throws NullPointerException если передаваемый ключ это null
     */
    @Override
    public void add(V key, E value) throws NullPointerException {
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

    private void addRecursive(Node<V, E> existing, Node<V, E> newNode) {
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
     * Удаление узла из дерева
     * @param key ключ
     * @return true если элемент удалён (был найден)
     * @throws NullPointerException если передаваемый ключ это null
     */
    @Override
    public boolean remove(V key) {
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

                // У родительского узла previous заменяем ссылку на minNode
                //  ссылкой на правый узел minNode
                previous.setLeft(minNode.getRight());

                // У родительского узла удаляемого узла меняем ссылку
                //  с удаляемого узла на minNode (минимум от правого узла)
                replaceChild(parentNode, nodeToRemove, minNode);

                // Новому узлу minNode назначаем ссылки от удалённого nodeToRemove
                minNode.setRight(nodeToRemove.getRight());
                minNode.setLeft(nodeToRemove.getLeft());
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
     * @throws NullPointerException если передаваемый ключ это null
     */
    @Override
    public E get(V key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        Node<V, E> node = getNode(key);

        return node != null ? node.getValue() : null;
    }

    /**
     * Получение узла из дерева очень просто:
     * Начинаем от вверхнего эелемента - если ключ больше - идём вправо,
     *  если меньше - влево.
     * Окончание поиска - равенство искомого и найденного ключа.
     * @param key ключ
     * @return узел с соответствующим ключом или null, если не найден ключ
     * @throws NullPointerException если передаваемый ключ это null
     */
    private Node<V, E> getNode(V key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key is null");
        }

        // Если дерево пустое
        if (top == null) {
            return null;
        }

        return getNodeRecursive(top, key);
    }

    private Node<V, E> getNodeRecursive(Node<V, E> node, V key) {
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
    private Node<V, E>[] getNodeWithParent(V key) {
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
    private Node<V, E>[] getNodeWithParentRecursive(Node<V, E> parent, Node<V, E> node, V key) {
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
                Node<V, E>[] result = (Node<V, E>[]) Array.newInstance(parent.getClass(), 2);
                result[0] = parent;
                result[1] = node;
                return result;
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

    private int compareNodes(Node<V, E> a, Node<V, E> b) {
        if (a == null) {
            throw new NullPointerException("Node 'a' is null");
        }
        if (b == null) {
            throw new NullPointerException("Node 'b' is null");
        }
        return b.getKey().compareTo(a.getKey());
    }

    private int compareKeys(V a, V b) {
        if (a == null) {
            throw new NullPointerException("Node 'a' is null");
        }
        if (b == null) {
            throw new NullPointerException("Node 'b' is null");
        }
        return b.compareTo(a);
    }

    private void removeChild(Node<V, E> parent, Node<V, E> child) {
        replaceChild(parent, child, null);
    }

    private void replaceChild(Node<V, E> parent, Node<V, E> child, Node<V, E> newChild) {
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

    @Override
    public void printToPrintStream(PrintStream ps) {
        recursivePrint(top, 0, ps);
    }

    private void recursivePrint(Node<V, E> node, int indent, PrintStream ps) {
        ps.println(getIndent(indent) + "└──" + node.getValue());
        if (node.getRight() != null) {
            recursivePrint(node.getRight(), indent + 1, ps);
        }
        if (node.getLeft() != null) {
            recursivePrint(node.getLeft(), indent + 1, ps);
        }
    }

    private String getIndent(int size) {
        String s = "";
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                s+= "│  ";
            }
        }
        return s.toString();
    }

    /**
     * Класс узла дерева.
     * @param <V> объект способный к сравнению с ему подобными
     */
    private static class Node<V extends Comparable<V>, E> {
        /**
         * Ключ не должен быть null
         */
        private final V key;
        private E value;
        private Node <V, E> left;
        private Node <V, E> right;

        /**
         * Создание узла дерева. По умолчанию left и right равны null
         * @param key не-null ключ
         * @param value значение
         */
        public Node(V key, E value) {
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
