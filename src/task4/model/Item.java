package task4.model;

import java.util.Objects;

public class Item implements Comparable<Item> {
    private final int number;
    private final String text;

    public Item(int number, String text) {
        this.number = number;
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Item item = (Item)obj;
        return number == item.number && text.equals(item.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, text);
    }

    @Override
    public String toString() {
        return number + " " + text;
    }

    @Override
    public int compareTo(Item item) {
        int compareNumber = Integer.compare(this.number, item.number);
        if(compareNumber != 0) {
            return compareNumber;
        }
        return this.text.compareTo(item.text);
    }
}
