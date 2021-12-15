# tech-of-prog-labs
Лабораторные по дисциплине "Технологии Программирования"

## Лабораторная №1

Работа со списками в C# и Java

Этапы:
1. Создать одно- или двунаправленный список на языке C# или Java, пользуясь методами класса или интерфейса List или LinkedList.
2. Добавить элементы списка по ключу.
3. Удалить элемент списка по ключу.
4. Изменить элемент списка по ключу.
5. Просмотреть список.
6. Выполнить запрос.

### Решение:

*Main.lab1()*

Для начала необходимо используя Java, создать двунаправленный список используя реализацию интерфейса List – класс LinkedList.
```java
// Создание списка
List<String> linkedList = new LinkedList<>();
```
Затем заполним список используя добавление элементов по ключу. Ключ в списках – индекс – номер элемента в списке.
```java
// Добавление элементов по ключу (индексу)
linkedList.add(0, "first");
linkedList.add(1, "second");
linkedList.add(2, "third");
```
Теперь элемент необходимо удалить по индексу:
```java
// Удалить элемент списка по ключу (индексу)
linkedList.remove(2);
```
Изменение элемента списка по ключу выполняется похоже на создание:
```java
//Изменить элемент списка по ключу (индексу)
linkedList.set(1, "last");
```
Чтобы просмотреть список можно использовать функцию System.out.println(Object x), которая вызовет функцию x.toString() – выразив передаваемый ей объект как строку.
```java
// Просмотреть список
System.out.println(linkedList);
```
Выполним запрос, обратившись к элементу списка по индексу (ключу), и выведем результат на экран.
```java
// Выполнить запрос (получение элемента по ключу (индексу))
System.out.println(
        linkedList.get(1)
);
```

## Лабораторная №2

Работа с динамическими структурами данных (стек, очередь) в C# и Java

Этапы: 
1. Создание коллекции, используя один из классов или интерфейсов C# или Java Stack или Queue.
2. Добавление элемента.
3. Удаление элемента.
4. Выполнение запроса. 
5. Просмотр коллекции.

### Решение:

*Main.lab2()*

В языке Java существует интерфейс Queue, который предоставляет функциональность для структур данных в виде очереди. Очереди представляют собой структуру данных, использующую принцип «первым пришел - первым вышел» (FIFO). Это означает, что чем раньше элемент будет добавлен в коллекцию, тем скорее он будет удален из коллекции.
Создадим такую коллекцию-очередь используя класс LinkedList который реализует интерфейс Queue.
```java
// Создание очереди
Queue<String> queue = new LinkedList<>();
```
Далее добавим в очередь объекты с помощью метода offer(E obj) (в качестве примера это будет строка). Так как мы используем очередь – все новые элементы будут добавляться в конец коллекции.
```java
// Добавление элементов
queue.offer("first");
queue.offer("second");
queue.offer("third");
queue.offer("fourth");
```
Получение элемента и удаление элемента может быть связано. Метод poll() возвращает элемент из начала очереди и удаляет его из очереди.
```java
// Получим первый элемент и удали его
System.out.println(
        queue.poll()
);
```
Но элемент можно получать не удаляя его. Для этого используется метод peek().
```java
// Получим первый элемент без удаления
System.out.println(
        queue.peek()
);
```
Чтобы просмотреть список можно использовать функцию System.out.println(Object x), которая вызовет функцию x.toString() – возвращающая передаваемого объекта коллекции в виде строки.
```java
// Получим представление очереди в виде строки
System.out.println(queue);
```

## Лабораторная №3

Работа с двоичными деревьями в C# и Java 

Этапы:
1. Создание классов для работы с двоичными деревьями на языке C# или Java (BinaryTree и BinaryTreeNode).
2. Добавление элемента.
3. Удаление элемента.
4. Выполнение запроса.
5. Просмотр коллекции. 

### Решение

*Main.lab3()*

Бинарное дерево — это структура данных, где каждый объект является узлом. Каждый узел может иметь два узла-потомка: левый узел и правый узел. Основные правила такого дерева:
- Узлы можно сравнивать (например, по ключу)
- Потомки слева - меньше чем предок
- Потомки справа - больше чем предок

Для начала опишем интерфейс будущего дереваю В данном случае дерево будет содержать в себе пары объектов ключ-значение. Все операции в таком дереве будут проходить с ключом.

```java
public interface BinaryTree<V extends Comparable, E> {

    void add(V key, E value) throws NullPointerException;

    boolean remove(V key) throws NullPointerException;
	
    E get(V key) throws NullPointerException;
	
    void printToPrintStream(PrintStream ps);

}
```

Создадим класс, который будет реализовывать этот интерфейс

```java
public class MyBinaryTree<V extends Comparable<V>, E> implements BinaryTree<V, E> {

    @Override
    public void add(V key, E value) throws NullPointerException {

    }

    @Override
    public boolean remove(V key) {
        return false;
    }

    @Override
    public E get(V key) throws NullPointerException {
        return null;
    }

    @Override
    public void printToPrintStream(PrintStream ps) {

    }
}
```

В этом классе опишем так же класс узла дерева:

```java
private static class Node<V extends Comparable<V>, E> {
	private final V key;
	private E value;
	private Node <V, E> left;
	private Node <V, E> right;

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
```

Добавим поле в класс MyBinaryTree которое будет хранить в себе верхний элемент дерева. Также опишем инициализацию этого поля в конструкторе.

```java
…
private Node<V, E> top;

public MyBinaryTree() {
	top = null;
} 
…
```

Теперь опишем метод добавления элемента в дерево. Добавление в дерево происходит по следующему принципу: большие элементы добавляются справа, меньшие - слева. Если места заняты, то необходимо смещаться ниже по дереву. Если ключи во время прохода по дереву сравняются - просто заменить значение в узле.

```java
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

private int compareNodes(Node<V, E> a, Node<V, E> b) {
	if (a == null) {
		throw new NullPointerException("Node 'a' is null");
	}
	if (b == null) {
		throw new NullPointerException("Node 'b' is null");
	}
	return b.getKey().compareTo(a.getKey());
}
```

После реализации добавление необходимо реализовать удаление. Удаление в дереве происходит по следующему принципу:
- Если у удаляемого узла нет детей, то просто очищаем ссылки на удаляемый узел у родительского узла.
- Если у удаляемого узла нет правого узла - заменяем ссылки на удаляемый узел ссылками на левый узел удаляемого узла.
- Если у удаляемого узла есть правый узел - заменяем ссылки на удаляемый узел ссылками на минимальный дочерний узел правого узла удаляемого узла (влево до упора).

```java
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
```

Так как дерево хранит пары значений «ключ-значение» должна быть реализована возможность получения элемента по ключу. Такой метод уже объявлен в интерфесе дерева, перейдём к реализации.

```java
@Override
public E get(V key) throws NullPointerException {
	if (key == null) {
		throw new NullPointerException("key is null");
	}

	Node<V, E> node = getNode(key);

	return node != null ? node.getValue() : null;
}

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
```

Также, для визуализации дерева нужна функция, создающая какое-либо представление созданного дерева. В данном случае реализуем метод, который выводит дерево в поток обёрнутый в PrintStream. При выводе дерева правый узел будет выше левого. Выводиться при этом будут значения узла, без ключей.

```java
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
```

Все объявленные интерфейсом методы реализованы. Теперь необходимо перейти к проверки работоспособности данной реализации.

Сначала создадим новое дерево:

```java
BinaryTree<Integer, String> binaryTree = new MyBinaryTree<>();
```

Заполним его парами «ключ-значение». Для примера будут пары, где ключ – номер буквы английского алфавита, а значение – буква в нижнем регистре, соответствующая этому номеру.

```java
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
```

Теперь удалим узел, используя его ключ. Также получим возвращаемое значение.

```java
System.out.println(
		binaryTree.remove(9)
);
```

Проверим получение значения по ключу:

```java
System.out.println(
		binaryTree.get(14)
);
```

И проверим правильность работы предыдущих действий, выведя дерево в консоль:

```java
binaryTree.printToPrintStream(System.out);
```
