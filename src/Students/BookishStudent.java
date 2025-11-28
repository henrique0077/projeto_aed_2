/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import Enumerators.ServiceType;
import Enumerators.StudentType;
import Services.Service;

import java.io.Serializable;

public class BookishStudent extends AbstractStudent implements Serializable {

    public BookishStudent(String name, Service home, String country) {
        super(name, StudentType.BOOKISH, home, country);
    }

    @Override
    public void addVisitedLocation(Service location) {
        // Only stores leisure locations (logic to check type belongs elsewhere)
        if (location.getServiceType() == ServiceType.LEISURE && searchIfLocationWasAlreadyVisited(location))
            visitedLocations.add(counterLocations++, location);
    }

    private boolean searchIfLocationWasAlreadyVisited(Service location){
        for(int i = 0; i < counterLocations; i++){
            if(location.getName().equals(visitedLocations.get(i).getName()))
                return false;
        }
        return true;
    }
}
