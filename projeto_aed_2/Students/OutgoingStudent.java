/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import Enumerators.StudentType;
import Services.EatingLodging;
import Services.Service;

import java.io.Serializable;

public class OutgoingStudent extends AbstractStudent implements Serializable {

    public OutgoingStudent(String name, Service home, String country) {
        super(name, StudentType.OUTGOING, home, country);
        visitedLocations.add(counterLocations++, home);
    }

    @Override
    public void addVisitedLocation(Service location) {
        if(searchIfLocationWasAlreadyVisited(location)) // Para nao repetir
            visitedLocations.add(counterLocations++, location); // Stores everything
    }

    private boolean searchIfLocationWasAlreadyVisited(Service location){
        for(int i = 0; i < counterLocations; i++){
            if(location.getName().equals(visitedLocations.get(i).getName()))
                return false;
        }
        return true;
    }
}