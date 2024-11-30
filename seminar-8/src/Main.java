import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        /*String result = numbers.stream()
                .filter(number -> number % 2 == 0 || number % 3 == 0)
                .map(number -> "A" + (number + 1) + "B")
                .collect(Collectors.joining());
        */
        numbers.stream().filter(n-> n % 4 == 0)
                        .map(n-> n+1)
                                .reduce("0", (accumulator, n) -> accumulator + n) % 2;
        System.out.println(result);
    }
}