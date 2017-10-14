package hw01;

import com.google.common.collect.Lists;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String... args) {
        MySampleObfuscatorClass mySampleObfuscatorClass = new MySampleObfuscatorClass(IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList()));
        Lists.partition(mySampleObfuscatorClass.getIntegers(), 10).forEach(System.out::println);
    }
}
