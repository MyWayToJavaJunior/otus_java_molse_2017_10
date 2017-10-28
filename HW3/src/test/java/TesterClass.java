import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.hw.MyArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TesterClass {

    private List<Integer> ints;
    private List<Integer> ints2;

    @Before
    public void init(){
       ints = new MyArrayList<>();
        ints.add(5);
        ints.add(3);
        ints.add(2);
       ints2 = new MyArrayList<>();

    }


    @Test
    public void checkSize(){
        Assert.assertEquals(3,ints.size());
    }

    @Test
    public void checkNullSize(){
        Assert.assertEquals(0,ints2.size());
    }

    @Test
    public void checkEmptyTrue(){
        Assert.assertEquals(true,ints2.isEmpty());
    }

    @Test
    public void checkEmptyFalse(){
        Assert.assertEquals(false,ints.isEmpty());
    }

    @Test
    public void checkContainsTrue(){
        Assert.assertEquals(true,ints.contains(3));
    }

    @Test
    public void checkContainsFalse(){
        Assert.assertEquals(false,ints.contains(4));
    }

    @Test
    public void checkContainsInNullArray(){
        Assert.assertEquals(false,ints.contains(10));
    }

    @Test
    public void checkAddElement(){
        ints.add(10);
        Assert.assertEquals(Integer.valueOf(10),ints.get(ints.size() -1));
        Assert.assertEquals(4,ints.size());
    }

    @Test
    public void checkGetCorrectElement(){
        Assert.assertEquals(Integer.valueOf(3),ints.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkGetException(){
       ints.get(10);
    }

    @Test
    public void checkSetElement(){
        int previous = ints.set(1,10);
        Assert.assertEquals(previous,3);
        Assert.assertEquals(Integer.valueOf(10),ints.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkSetElementInCorretlyIndex(){
        ints.set(10,10);
    }

    @Test
    public void checkAddByIndex(){
        ints.add(1,10);
        Assert.assertEquals(Integer.valueOf(10),ints.get(1));
        Assert.assertEquals(Integer.valueOf(3),ints.get(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkAddByIndexException(){
        ints.add(10,10);
    }

    @Test
    public void checkRemove(){
        int result = ints.remove(1);
        Assert.assertEquals(3,result);
        Assert.assertEquals(2,ints.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkRemoveIndexError(){
        ints.remove(10);
    }

    @Test
    public void chechSort(){
        List<Integer> test = new ArrayList<>(Arrays.asList(5,3,2));
        Collections.sort(ints,(a,b) -> a>=b ? 1: -1);
        Assert.assertEquals(Integer.valueOf(2),ints.get(0));
    }

}
