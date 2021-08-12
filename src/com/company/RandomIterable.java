package com.company;
import java.util.Iterator;
import java.util.Random;

public class RandomIterable<E> implements Iterable {
    private final int length;
    private final Random random = new Random();

    public RandomIterable(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public Iterator iterator() {

        return new Iterator() {
            private int innerLength = length;

            @Override
            public boolean hasNext() {
                return this.innerLength != 0;
            }

            @Override
            public Integer next() {
                 this.innerLength--;
                 return random.nextInt(100);
            }
        };
    }
}
