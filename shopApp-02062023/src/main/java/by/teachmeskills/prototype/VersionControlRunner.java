package by.teachmeskills.prototype;

public class VersionControlRunner {
    public static void main(String[] args) {
        Car car = new Car("Honda", "Black");
        System.out.println(car);

        CarFactory factory = new CarFactory(car);
        Car cloneCar = factory.cloneCar();
        System.out.println(cloneCar);

    }
}
