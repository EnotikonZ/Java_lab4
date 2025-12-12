import java.util.*;

/**
 * Лабораторная работа 4 (Вариант 9)
 *
 * В этом файле: вся логика и классы для задач:
 * 1.3 Сравнимое
 * 1.4 Сравнимый студент
 * 2.3 Начало отсчета (Box + Point3D + wildcards)
 * 3.1 Функция (map)
 * 3.2 Фильтр (filter)
 * 3.3 Сокращение (reduce, безопасно для пустого списка)
 * 3.4 Коллекционирование (collect)
 *
 * Пакеты не используются.
 */
public class Lab4Var9 {

    /* =========================================================
       Задача 1.3 — Сравнимое
       ========================================================= */

    /**
     * "Сравнимое": гарантирует наличие метода "compare".
     * Тип T можно менять без изменения интерфейса.
     */
    public interface ComparableItem<T> {
        int compare(T other);
    }

    /* =========================================================
       Задача 1.4 — Сравнимый студент (из 2.1.9 по смыслу)
       ========================================================= */

    public static class Student implements ComparableItem<Student> {

        private String name;
        private int[] grades;

        public Student(String name, int[] grades) {
            this.name = name;
            this.grades = Arrays.copyOf(grades, grades.length);
        }

        public String getName() {
            return name;
        }

        public int[] getGrades() {
            return Arrays.copyOf(grades, grades.length);
        }

        public double average() {
            if (grades.length == 0) return 0.0;
            int sum = 0;
            for (int g : grades) sum += g;
            return (double) sum / grades.length;
        }

        /**
         * Требование задачи 1.4:
         *  1  если средняя оценка текущего студента больше
         *  0  если средние равны
         * -1  если средняя оценка меньше
         */
        public int compare(Student other) {
            double a1 = this.average();
            double a2 = other.average();
            if (a1 > a2) return 1;
            if (a1 < a2) return -1;
            return 0;
        }

        public String toString() {
            return "Студент{name='" + name + "', grades=" + Arrays.toString(grades) +
                    ", avg=" + String.format(Locale.US, "%.2f", average()) + "}";
        }
    }

    /* =========================================================
       Задача 2.3 — Начало отсчета (Коробка + 3D точка)
       ========================================================= */

    public static class Point3D {

        private double x;
        private double y;
        private double z;

        public Point3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String toString() {
            return "{" + x + ";" + y + ";" + z + "}";
        }
    }

    /**
     * Коробка с параметризацией.
     */
    public static class Box<T> {

        private T value;

        public Box() {
            // пустая коробка
        }

        public void put(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public String toString() {
            return "Box{value=" + value + "}";
        }
    }

    /**
     * Метод "начало отсчёта": кладет случайную 3D-точку в коробку.
     * Box<? super Point3D> позволяет передавать коробки разных параметризаций:
     *   Box<Point3D>, Box<Object> и т.д.
     */
    public static class StartPoint {

        private Random rnd;

        public StartPoint() {
            rnd = new Random();
        }

        public void putRandomPoint(Box<? super Point3D> box) {
            double x = rnd.nextInt(101); // 0..100
            double y = rnd.nextInt(101);
            double z = rnd.nextInt(101);
            box.put(new Point3D(x, y, z));
        }

        public String toString() {
            return "StartPoint{randomRange=0..100}";
        }
    }

    /* =========================================================
       Задачи 3.1–3.4 — функциональные методы
       ========================================================= */

    public static class FunctionalTools {

        // поля/конструктор/toString (по требованию)
        private String info;

        public FunctionalTools() {
            this.info = "FunctionalTools(map/filter/reduce/collect)";
        }

        public String toString() {
            return info;
        }

        // ===== 3.1 Функция (map) =====

        public interface Applier<T, P> {
            P apply(T value);
        }

        public static <T, P> List<P> map(List<T> list, Applier<T, P> applier) {
            List<P> result = new ArrayList<>();
            for (T v : list) {
                result.add(applier.apply(v));
            }
            return result;
        }

        // ===== 3.2 Фильтр (filter) =====

        public interface Tester<T> {
            boolean test(T value);
        }

        public static <T> List<T> filter(List<T> list, Tester<T> tester) {
            List<T> result = new ArrayList<>();
            for (T v : list) {
                if (tester.test(v)) {
                    result.add(v);
                }
            }
            return result;
        }

        // ===== 3.3 Сокращение (reduce) =====

        public interface Reducer<T> {
            T combine(T a, T b);
        }

        /**
         * Безопасный reduce:
         * - не возвращает null
         * - не падает на пустом списке
         * потому что есть identity (начальное значение).
         */
        public static <T> T reduce(List<T> list, T identity, Reducer<T> reducer) {
            T acc = identity;
            for (T v : list) {
                acc = reducer.combine(acc, v);
            }
            return acc;
        }

        // ===== 3.4 Коллекционирование (collect) =====

        public interface SupplierP<P> {
            P create();
        }

        public interface Accumulator<P, T> {
            void add(P collection, T value);
        }

        public static <T, P> P collect(List<T> list, SupplierP<P> supplier, Accumulator<P, T> accumulator) {
            P result = supplier.create();
            for (T v : list) {
                accumulator.add(result, v);
            }
            return result;
        }
    }

    /* =========================================================
       Утилиты для вывода списков массивов (для main)
       ========================================================= */

    public static String arraysToString(List<int[]> arrays) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arrays.size(); i++) {
            sb.append(Arrays.toString(arrays.get(i)));
            if (i + 1 < arrays.size()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
