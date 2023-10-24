package seedu.address.model.person.comparer;

import seedu.address.model.person.Person;

/**
 * A comparator that sorts Person objects by email.
 */
public class EmailComparator extends SortComparator {

    /**
     * The sort type for this comparator.
     */
    public static final String SORT_TYPE = "email";

    /**
     * Creates a new AddressComparator with the given parameters.
     *
     * @param isActive Whether this comparator is currently active.
     * @param isReverse Whether to sort in reverse order.
     * @param priority The priority of this comparator.
     */
    public EmailComparator(boolean isActive, boolean isReverse, int priority) {
        super(isActive, isReverse, priority);
    }

    /**
     * Compares two Person objects by email.
     *
     * @param p1 The first Person to compare.
     * @param p2 The second Person to compare.
     * @return A negative integer, zero, or a positive integer as the first argument is less than,
     *     equal to, or greater than the second.
     */
    @Override
    public int compare(Person p1, Person p2) {
        if (p1.equals(p2)) {
            return 0;
        }
        return p1.getEmail().value.compareTo(p2.getEmail().value);
    }
}
