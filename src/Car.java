import org.frost.util.Component;
import org.frost.util.Inject;


@Component
public class Car extends Vehicle {

    //private Driver driver; do we need this field since were injecting the dependency? when would it be better to inject the field?
    private String licensePlate;

    public Car(){

    }

    @Inject
    public Car(VehicleDriver drivee){
        System.out.printf("\n Driver ", drivee.getName());//inject the driver object here!!!!!!!!
    }

}
