package ua.epamcourses.natalia_markova.homework.task06_07.subtask01_02;

/**
 * Created by natalia_markova on 12.05.2016.
 */
public class Main {

    public static void main(String[] args) {

        List<Integer>[] lists = new List[2];
        lists[0] = new SimpleList<>();
        lists[1] = new LinkedList<>();

        for (List<Integer> list : lists) {
            for (int i = 0; i < 10; i++) {
                list.add(i);
            }
            System.out.println("size = " + list.size());
            System.out.println(list);
            System.out.println(list.toStringReverse());
            list.set(0, 100);
            System.out.println(list);
            System.out.println(list.toStringReverse());
            list.set(7, 700);
            System.out.println(list);
            System.out.println(list.toStringReverse());
            list.remove(7);
            System.out.println(list);
            System.out.println(list.toStringReverse());
            list.remove(0);
            System.out.println(list);
            System.out.println(list.toStringReverse());
            System.out.println("has 6: " + list.contains(6));
            System.out.println("has 600: " + list.contains(600));
            list.clear();
            System.out.println(list);
            System.out.println(list.toStringReverse());
            System.out.println();
        }

    }

}
