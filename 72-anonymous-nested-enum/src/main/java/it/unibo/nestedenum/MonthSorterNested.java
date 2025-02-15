package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;
        
        Month(int days) {
            this.days = days;
        }

        public int getDays() {
            return days;
        }

        public static Month fromString(String name) {
            Objects.requireNonNull(name);
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                // Fallback to manual scan before giving up
                Month match = null;
                for (final Month month: values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                name + " is ambiguous: both " + match + " and " + month + " would be valid matches",
                                e
                            );
                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + name, e);
                }
                return match;
            }
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    public static class SortByDays implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);
            return Integer.compare(m1.getDays(), m2.getDays());
        }
    
        
    }

    public static class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            Month m1 = Month.fromString(o1);
            Month m2 = Month.fromString(o2);
            return Integer.compare(m1.ordinal(), m2.ordinal());
        }
    
        
    }
}
