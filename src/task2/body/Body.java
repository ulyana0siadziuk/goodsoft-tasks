package task2.body;

public abstract class Body {

    public abstract double getVolume();

    public void print() {
        System.out.println("это тело с объемом " + getVolume());
    }
}