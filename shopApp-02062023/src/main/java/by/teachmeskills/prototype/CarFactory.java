package by.teachmeskills.prototype;

public class CarFactory {
    Car car;

    public CarFactory(Car car) {
        this.car = car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car cloneCar(){
        return (Car) car.copy();
    }
}
