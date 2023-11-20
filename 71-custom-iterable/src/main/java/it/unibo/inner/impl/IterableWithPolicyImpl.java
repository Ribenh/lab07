package it.unibo.inner.impl;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{

    private final T[] elements;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(T[] elements) {
        this(elements, item -> true);
    }

    public IterableWithPolicyImpl(T[] elements, Predicate<T> filter) {
        this.elements = elements;
        this.filter = filter;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<T>{

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            while (currentIndex < elements.length) {
                if (filter == null || filter.test(elements[currentIndex]) ) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public T next() {
            while (currentIndex < elements.length) {
                if (filter == null || filter.test(elements[currentIndex])) {
                    return elements[currentIndex++];
                }
                currentIndex++;
            }
            throw new java.util.NoSuchElementException();
        }
        
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    
}