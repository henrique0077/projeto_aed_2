/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import Enumerators.StudentType;
import Services.Service;

import java.io.Serializable;

public class ThriftyStudentClass extends AbstractStudent implements ThriftyStudent, Serializable {

    private int cheapestMealPrice = Integer.MAX_VALUE;
    private int cheapestRoomPrice = Integer.MAX_VALUE;

    public ThriftyStudentClass(String name, Service home, String country) {
        super(name, StudentType.THRIFTY, home, country);
        counterLocations = 1; // Thrifty Student does not update locations so it starts with 1
    }

    @Override
    public void addVisitedLocation(Service location) {
        // Thrifty students don't track visits
    }

    @Override
    public void updateCheapestMeal(int price) {
        if (price < cheapestMealPrice)
            cheapestMealPrice = price;
    }
    @Override
    public void updateCheapestRoom(int price) {
        if (price < cheapestRoomPrice)
            cheapestRoomPrice = price;
    }

    @Override
    public int getCheapestMealPrice(){
        return cheapestMealPrice;
    }
    @Override
    public int getCheapestRoomPrice(){
        return cheapestRoomPrice;
    }
}
