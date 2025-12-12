import java.util.*;

/**
 * Главный класс Лабораторной 4 (Вариант 9).
 *
 * Единственный main.
 * Дружественный интерфейс.
 * Все исходные данные вводятся с клавиатуры.
 * Присутствует проверка ввода.
 */
public class Main4Var9 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА 4 (Вариант 9) ===");

        // -------------------------
        // Задача 1.4 (и 1.3)
        // -------------------------
        System.out.println("\n[Задача 1.4] Сравнимый студент (метод compare из 1.3)");

        Lab4Var9.Student st1 = readStudent(sc, 1);
        Lab4Var9.Student st2 = readStudent(sc, 2);

        System.out.println("\nСтудент 1: " + st1);
        System.out.println("Студент 2: " + st2);

        int cmp = st1.compare(st2);
        if (cmp == 1) {
            System.out.println("Результат сравнения: студент 1 лучше (средний балл выше).");
        } else if (cmp == -1) {
            System.out.println("Результат сравнения: студент 1 хуже (средний балл ниже).");
        } else {
            System.out.println("Результат сравнения: средние оценки одинаковы.");
        }

        // -------------------------
        // Задача 2.3
        // -------------------------
        System.out.println("\n[Задача 2.3] Начало отсчёта (Box + Point3D)");

        Lab4Var9.Box<Object> boxObj = new Lab4Var9.Box<>();
        Lab4Var9.Box<Lab4Var9.Point3D> boxPoint = new Lab4Var9.Box<>();

        Lab4Var9.StartPoint start = new Lab4Var9.StartPoint();
        System.out.println("Создан объект: " + start);

        start.putRandomPoint(boxObj);
        start.putRandomPoint(boxPoint);

        System.out.println("Коробка Box<Object>: " + boxObj);
        System.out.println("Коробка Box<Point3D>: " + boxPoint);

        // -------------------------
        // Задача 3.1 (map)
        // -------------------------
        System.out.println("\n[Задача 3.1] Функция (map)");

        List<String> strings = readStringList(sc,
                "Введите строки через пробел (пример: qwerty asdfg zx): ");

        List<Integer> lengths = Lab4Var9.FunctionalTools.map(strings, s -> s.length());
        System.out.println("Длины строк: " + lengths);

        List<Integer> numbers = readIntList(sc,
                "Введите целые числа через пробел (пример: 1 -3 7): ");

        List<Integer> absValues = Lab4Var9.FunctionalTools.map(numbers, n -> (n < 0 ? -n : n));
        System.out.println("Модули чисел: " + absValues);

        List<int[]> arrays = readIntArrayList(sc);

        List<Integer> maxValues = Lab4Var9.FunctionalTools.map(arrays, arr -> {
            int mx = arr[0];
            for (int v : arr) if (v > mx) mx = v;
            return mx;
        });
        System.out.println("Максимумы массивов: " + maxValues);

        // -------------------------
        // Задача 3.2 (filter)
        // -------------------------
        System.out.println("\n[Задача 3.2] Фильтр (filter)");

        List<String> filteredStr = Lab4Var9.FunctionalTools.filter(strings, s -> s.length() >= 3);
        System.out.println("Строки длиной >= 3: " + filteredStr);

        List<Integer> onlyNegative = Lab4Var9.FunctionalTools.filter(numbers, n -> n < 0);
        System.out.println("Только отрицательные числа: " + onlyNegative);

        List<int[]> noPositiveArrays = Lab4Var9.FunctionalTools.filter(arrays, arr -> {
            for (int v : arr) if (v > 0) return false;
            return true;
        });
        System.out.println("Массивы без положительных элементов: " + Lab4Var9.arraysToString(noPositiveArrays));

        // -------------------------
        // Задача 3.3 (reduce)
        // -------------------------
        System.out.println("\n[Задача 3.3] Сокращение (reduce) — безопасно для пустого списка");

        String joined = Lab4Var9.FunctionalTools.reduce(strings, "", (a, b) -> a + b);
        System.out.println("Склеенная строка: " + joined);

        Integer sum = Lab4Var9.FunctionalTools.reduce(numbers, 0, Integer::sum);
        System.out.println("Сумма чисел: " + sum);

        System.out.println("\nПодзадача: список списков -> общее количество элементов");

        List<List<Integer>> listOfLists = readListOfIntLists(sc);
        List<Integer> sizes = Lab4Var9.FunctionalTools.map(listOfLists, list -> list.size());
        Integer totalCount = Lab4Var9.FunctionalTools.reduce(sizes, 0, Integer::sum);

        System.out.println("Количество элементов во всех списках: " + totalCount);

        // -------------------------
        // Задача 3.4 (collect)
        // -------------------------
        System.out.println("\n[Задача 3.4] Коллекционирование (collect)");

        // 3.4.1: два подсписка: положительные и отрицательные
        List<List<Integer>> twoLists = Lab4Var9.FunctionalTools.collect(
                numbers,
                () -> {
                    List<List<Integer>> res = new ArrayList<>();
                    res.add(new ArrayList<>()); // положительные и нули
                    res.add(new ArrayList<>()); // отрицательные
                    return res;
                },
                (res, v) -> {
                    if (v >= 0) res.get(0).add(v);
                    else res.get(1).add(v);
                }
        );
        System.out.println("Положительные/нули: " + twoLists.get(0));
        System.out.println("Отрицательные: " + twoLists.get(1));

        // 3.4.2: строки по длинам
        Map<Integer, List<String>> byLen = Lab4Var9.FunctionalTools.collect(
                strings,
                HashMap::new,
                (map, s) -> map.computeIfAbsent(s.length(), k -> new ArrayList<>()).add(s)
        );
        System.out.println("Группировка строк по длине: " + byLen);

        // 3.4.3: Set без повторов
        Set<String> unique = Lab4Var9.FunctionalTools.collect(
                strings,
                HashSet::new,
                Set::add
        );
        System.out.println("Уникальные строки (Set): " + unique);

        System.out.println("\n=== КОНЕЦ ЛАБОРАТОРНОЙ 4 ===");
    }

    // --------------------- ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ВВОДА ---------------------

    private static Lab4Var9.Student readStudent(Scanner sc, int index) {
        System.out.println("Введите данные для студента #" + index);

        String name;
        while (true) {
            System.out.print("Имя: ");
            name = sc.nextLine().trim();
            if (!name.isEmpty()) break;
            System.out.println("Ошибка: имя не должно быть пустым.");
        }

        int count = readIntInRange(sc, "Количество оценок (0..20): ", 0, 20);
        int[] grades = new int[count];

        for (int i = 0; i < count; i++) {
            grades[i] = readInt(sc, "Оценка #" + (i + 1) + " (целое число): ");
        }

        return new Lab4Var9.Student(name, grades);
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int v = readInt(sc, prompt);
            if (v >= min && v <= max) return v;
            System.out.println("Ошибка: число должно быть в диапазоне [" + min + ";" + max + "].");
        }
    }

    private static List<String> readStringList(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Ошибка: список не должен быть пустым.");
                continue;
            }
            String[] parts = line.split("\\s+");
            return Arrays.asList(parts);
        }
    }

    private static List<Integer> readIntList(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Ошибка: список не должен быть пустым.");
                continue;
            }

            String[] parts = line.split("\\s+");
            List<Integer> res = new ArrayList<>();
            boolean ok = true;

            for (String p : parts) {
                try {
                    res.add(Integer.parseInt(p));
                } catch (NumberFormatException e) {
                    ok = false;
                    break;
                }
            }

            if (ok) return res;
            System.out.println("Ошибка: вводите только целые числа через пробел.");
        }
    }

    private static List<int[]> readIntArrayList(Scanner sc) {
        System.out.println("\nВведите список массивов целых чисел.");
        int arraysCount = readIntInRange(sc, "Сколько массивов? (1..10): ", 1, 10);

        List<int[]> list = new ArrayList<>();
        for (int i = 1; i <= arraysCount; i++) {
            System.out.println("Массив #" + i);
            int len = readIntInRange(sc, "Длина массива (1..20): ", 1, 20);
            int[] arr = new int[len];

            for (int j = 0; j < len; j++) {
                arr[j] = readInt(sc, "Элемент [" + j + "]: ");
            }
            list.add(arr);
        }
        return list;
    }

    private static List<List<Integer>> readListOfIntLists(Scanner sc) {
        int cnt = readIntInRange(sc, "Сколько вложенных списков? (1..10): ", 1, 10);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 1; i <= cnt; i++) {
            List<Integer> one = readIntList(sc, "Список #" + i + " (числа через пробел): ");
            res.add(one);
        }
        return res;
    }
}
