import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ConcurrentModificationException;

public class CMDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        try {
            // Demonstrating ConcurrentModificationException
            for (String item : list) {
                if (item.equals("two")) {
                    list.remove(item); // This will throw ConcurrentModificationException
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException: " + e.getMessage());
        }

        // Proper way to remove elements using iterator
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("two")) {
                iterator.remove(); // This is the correct way to remove an element during iteration
            }
        }

        System.out.println("List after removal: " + list);
    }
}
