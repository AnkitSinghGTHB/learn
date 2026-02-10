public class ArrayPractice {
    public static void main(String[] args) {
        int[] temperatures = { 72, 68, 75, 80, 69, 73, 77 };

        // 1. Find the hottest day
        int hottest = temperatures[0];
        int hottestDay = 0;
        for (int i = 0; i < temperatures.length; i++) {
            if (temperatures[i] > hottest) {
                hottest = temperatures[i];
                hottestDay = i;
            }
        }
        System.out.println("Hottest: " + hottest + " on day " + (hottestDay + 1));

        // 2. Count days above 73
        int warmDays = 0;
        for (int temp : temperatures) {
            if (temp > 73) {
                warmDays++;
            }
        }
        System.out.println("Days above 73: " + warmDays);

        // 3. Calculate weekly average
        int total = 0;
        for (int temp : temperatures) {
            total += temp;
        }
        double average = (double) total / temperatures.length;
        System.out.println("Average: " + average);
    }
}