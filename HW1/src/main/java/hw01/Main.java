package hw01;

import com.google.common.collect.Lists;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String... args) {
        List<Integer> integers = IntStream.rangeClosed(1, 50).mapToObj(Integer::new).collect(Collectors.toList());
         Lists.partition(integers, 10).forEach(System.out::println);

    }
}
