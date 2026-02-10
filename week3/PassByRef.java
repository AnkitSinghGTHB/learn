public class PassByRef {
    public static void modify(int[] arr) {
        arr[0] = 100; // Modifies original array!
    }

    public static void reassign(int[] arr) {
        arr = new int[] { 9, 9, 9 }; // Creates new array, doesn't affect original
    }

    public static void main(String[] args) {
        int[] numbers = { 1, 2, 3 };

        reassign(numbers);
        System.out.println(numbers[0]); // 1 (not changed to 9)

        modify(numbers);
        System.out.println(numbers[0]); // 100 (changed!)
    }
}
