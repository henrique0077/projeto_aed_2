/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;
import Enumerators.Message;
import Enumerators.ServiceType;
import Enumerators.StudentType;
import Services.*;
import dataStructures.List;
import dataStructures.ListInArray;
import dataStructures.Iterator;
import dataStructures.TwoWayDoublyIterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public abstract class AbstractStudent implements Student, Serializable {

    private final String name;
    private final String country;
    private final StudentType type;

    private Service currentLocation;
    private Service home;
    protected List<Service> visitedLocations;
    protected int counterLocations;
    private final int DEFAULT_DIMENTION = 1;

    public AbstractStudent(String name, StudentType type, Service initialHome, String country) {
        this.name = name;
        this.type = type;
        this.country = country;
        this.home = initialHome;
        this.currentLocation = initialHome; // Starts at home
        this.visitedLocations = new ListInArray<>(DEFAULT_DIMENTION);
        counterLocations = 0;
    }

    public String getCountry (){
        return this.country;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StudentType getType() {
        return type;
    }
    @Override
    public String getCurrentLocation() {
        return currentLocation.getName();
    }

    @Override
    public String getHome() {
        return home.getName();
    }

    public void setHome(Service home) {
        this.home = home;
    }

    public void changeCapacityOfTheLodging(Student studentName) {
        EatingLodging lodging = (LodgingServiceClass) currentLocation;
        lodging.addClient(studentName);
    }

    @Override
    public void goTo(Service location, Student studentName) {
        if (currentLocation instanceof EatingServiceClass eating) {
                eating.removeClient(studentName);
        }
        this.currentLocation = location;
        addVisitedLocation(location);
        if (currentLocation instanceof EatingServiceClass eating) {
        if (eating.hasCapacity())
            eating.addClient(studentName);
        }
    }

    @Override
    public void moveHome(Service lodgingService, Student studentName) {
        if (this.home instanceof LodgingServiceClass lodging) {
            lodging.removeClient(studentName);
        }

        this.home = lodgingService;
        this.currentLocation = lodgingService; // Estudante vai para casa após mover
        addVisitedLocation(lodgingService);

        if (this.currentLocation instanceof LodgingServiceClass lodging) {
            lodging.addClient(studentName);
        }
    }

    @Override
    public int getLocationsVisitedNum(){
        return counterLocations;
    }

    @Override
    public Iterator<Service> getVisitedLocationsIterator() {
        //return new ServiceIterator(visitedLocations,counterLocations);
        return visitedLocations.iterator();
    }
}