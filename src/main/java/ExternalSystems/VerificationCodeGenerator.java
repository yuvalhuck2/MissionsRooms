package ExternalSystems;

import org.hibernate.mapping.Collection;
import org.springframework.data.domain.Range;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//manage verification code algorithm with 5 numbers
public class VerificationCodeGenerator {
    private final Queue<Integer> numbers;

    public VerificationCodeGenerator() {
        this.numbers = new LinkedList<>();
        int end = 50000;
        int start = 10000;
        List<Integer> rangedNumbers=IntStream.range(start, end).boxed().collect(Collectors.toList());
        Collections.shuffle(rangedNumbers);
        numbers.addAll(rangedNumbers);
    }

    /**
     * get the next number and add it to the last of the queue
     * @return the next number from the queue
     */
    public String getNext(){
        Integer number=null;
        do {
            number = numbers.poll();
            numbers.add(number);
        }while(number==null);
        return number.toString();
    }
}
