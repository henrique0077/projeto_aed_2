package HomeawayApp;
/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

import Enumerators.ServiceType;
import Enumerators.StudentType;
import Services.*;
import Students.*;
import dataStructures.Iterator;
import dataStructures.exceptions.InvalidPositionException;
import exceptions.*;

import java.io.*;

public class HomeAwayAppClass implements HomeAwayApp, Serializable {


    private Bound currentBound;
    private ServicesCollection servicesCollection;
    private StudentCollection studentCollection;
    private int updateCounter;
    private boolean wasUpdated;

    public HomeAwayAppClass() {
        currentBound = null;
        servicesCollection = new ServicesCollectionClass();
        studentCollection = new StudentCollectionClass();
        wasUpdated = false;
        updateCounter = 0;


    }

    public boolean getHasBounds() {
        return currentBound != null;
    }

    public void createBounds(String areaName, long topLat, long leftLon, long bottomLat, long rightLon) throws InvalidBoundLocationException, SameAreaNameException {

        if (!checkBoundsIsValid(topLat, leftLon, bottomLat, rightLon)) throw new InvalidBoundLocationException();
        if (getHasBounds()) {
            if (!checkBoundsNameIsValid(areaName)) throw new SameAreaNameException();
        }
        if (getHasBounds()) {
            saveCurrentBounds();
        }
        currentBound = new BoundClass(areaName, topLat, leftLon, bottomLat, rightLon);
    }

//    public void saveCurrentBounds() throws SystemBoundsNotDefinedException {
//        if (!getHasBounds()) throw new SystemBoundsNotDefinedException();
//
//        String fileName = getBoundName().toLowerCase().replace(" ", "_") + ".ser";
//
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
//            oos.writeObject(currentBound);
//            oos.writeObject(servicesCollection);
//            oos.writeObject(studentCollection);
//            oos.flush();
//            oos.close();
//        } catch (IOException e) {
//            System.out.println("Erro da escrita.");
//        }
//    }

    public void saveCurrentBounds() throws SystemBoundsNotDefinedException {
        if (!getHasBounds()) throw new SystemBoundsNotDefinedException();
        String fileName = getBoundName().toLowerCase().replace(" ", "_") + ".ser";

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(currentBound);
            oos.writeObject(servicesCollection);
            oos.writeObject(studentCollection);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro da escrita.");
        }
    }

//    @Override
//    public void loadBounds(String areaName) throws BoundNameDoesntExistException, FileDoesNotExistsException {
//        String fileName = areaName.toLowerCase().replace(" ", "_") + ".ser";
//        File file = new File(fileName);
//        if (!file.exists()) throw new BoundNameDoesntExistException();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
//            this.currentBound = (Bound) ois.readObject();
//            this.servicesCollection = (ServicesCollection) ois.readObject();
//            this.studentCollection = (StudentCollection) ois.readObject();
//
////            Iterator<Student> s = studentCollection.allStudentIterator();
////            while (s.hasNext()) {
////                Student service = s.next();
////                System.out.printf("type: %s, name: %s\n",service.getHome(), service.getName(), service.getType(), service.getCurrentLocation());
////            }
//        } catch (IOException e) {
//            throw new FileDoesNotExistsException();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

@Override
public void loadBounds(String areaName) throws BoundNameDoesntExistException, FileDoesNotExistsException {
    String fileName = areaName.toLowerCase().replace(" ", "_") + ".ser";

    File file = new File(fileName);
    if (!file.exists()) throw new BoundNameDoesntExistException();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
        this.currentBound = (Bound) ois.readObject();
        this.servicesCollection = (ServicesCollection) ois.readObject();
        this.studentCollection = (StudentCollection) ois.readObject();

    } catch (IOException e) {
        e.printStackTrace();
        throw new FileDoesNotExistsException();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
}

    /**
     * Checks if the bound name already exists
     *
     * @param areaName The name of the bound to check
     * @return True if it already exists. False otherwise
     */
    public boolean checkBoundsNameIsValid(String areaName) {
        if (currentBound.getName().equalsIgnoreCase(areaName)) return false;
        String fileName = areaName.toLowerCase().replace(" ", "_") + ".ser";
        File file = new File(fileName);
        boolean result = file.exists();
        return result;
    }


    public boolean checkBoundsIsValid(long topLat, long leftLon, long bottomLat, long rightLon) {
        return (topLat > bottomLat && leftLon < rightLon);
    }


    public String getBoundName() {
        return currentBound.getName();
    }

    @Override
    public boolean isValidServiceName(String serviceName) {
        // If there is not an Elem with that name inside services array return TRUE
        return !servicesCollection.hasElem(serviceName);
    }

    @Override
    public boolean isValidLodgingService(String serviceName) {
        return servicesCollection.hasElem(serviceName) && servicesCollection.getElement(serviceName).getServiceType() == (ServiceType.LODGING);
    }

    @Override
    public boolean isValidStudentName(String studentName) {
        return !studentCollection.hasStudent(studentName);
    }

    @Override
    public boolean isAlreadyThere(String studentName, String location) {
        return studentCollection.getElement(studentName).getCurrentLocation().equals(location);
    }

    @Override
    public boolean didStudentVisitedAnyLocation(String studentName) {
        return studentCollection.getElement(studentName).getLocationsVisitedNum() > 0;
    }

    @Override
    public boolean isStudentType(String studentName, StudentType type) {
        return studentCollection.getElement(studentName).getType() == type;
    }

    @Override
    public boolean isThereNoStudents() {
        return studentCollection.isEmpty();
    }

    @Override
    public boolean isStudentDistracted(String studentName, String newServiceName) {
        Student obj = studentCollection.getElement(studentName);
        if (!(obj instanceof ThriftyStudentClass thriftyStudent))
            return false;

        Service newService = servicesCollection.getElement(newServiceName);
        if (newService.getServiceType() == ServiceType.EATING) {
            // It Updates Eating Service Price, if is less than the one he knows
            thriftyStudent.updateCheapestMeal(newService.getServicePrice());
            return thriftyStudent.getCheapestMealPrice() < servicesCollection.getElement(newServiceName).getServicePrice();
        } else if (newService.getServiceType() == ServiceType.LODGING) {
            int currentCheapest = thriftyStudent.getCheapestRoomPrice();
            int newPrice = newService.getServicePrice();
            // same price, not distracted, no update
            if (newPrice < currentCheapest) {
                thriftyStudent.updateCheapestRoom(newPrice);
                move(studentName, newServiceName); // Moves to the cheaper lodging
                return false;
            } else return newPrice > currentCheapest; // Student is distracted!
        }
        return false;
    }

    @Override
    public boolean isStudentDistractedToMoveHome(String studentName, String newService) {
        if (!isStudentType(studentName, StudentType.THRIFTY))
            return false;
        String studentHome = studentCollection.getElement(studentName).getHome();
        int homePrice = servicesCollection.getElement(studentHome).getServicePrice();
        int newServicePrice = servicesCollection.getElement(newService).getServicePrice();
        return homePrice <= newServicePrice;
    }

    @Override
    public boolean isStudentHome(String studentName, String home) {
        return studentCollection.getElement(studentName).getHome().equalsIgnoreCase(home);
    }

    @Override
    public String getStudentHomeLocation(String studentName) {
        return studentCollection.getElement(studentName).getHome();
    }

    public void addService(ServiceType serviceType, long lat, long lon, int price, String serviceName, int value) throws InvalidServiceTypeException,
            InvalidPositionException, PriceEqualOrLessThenZeroException, InvalidServiceDiscountException, InvalidServiceCapacityException, ServiceAlreadyExistsException {
        //if (ServiceType.get(serviceType)) throw new InvalidServiceTypeException();
        if (isInvalidLocation(lat, lon))
            throw new InvalidPositionException();
        if (price <= 0)
            throw new PriceEqualOrLessThenZeroException();
        if (serviceType.equals(ServiceType.LEISURE) && (value < 0 || value > 100))
            throw new InvalidServiceDiscountException();
        if ((serviceType.equals(ServiceType.LODGING) || serviceType.equals(ServiceType.EATING)) && value <= 0)
            throw new InvalidServiceCapacityException();
        if (serviceNameAlreadyExists(serviceName))
            throw new ServiceAlreadyExistsException();
        switch (serviceType) {
            case EATING -> servicesCollection.addService(serviceName,
                    new EatingServiceClass(serviceName, lat, lon, price, value, updateCounter++));
            case LODGING -> servicesCollection.addService(serviceName,
                    new LodgingServiceClass(serviceName, lat, lon, price, value, updateCounter++));
            case LEISURE -> servicesCollection.addService(serviceName,
                    new LeisureServiceClass(serviceName, lat, lon, price, value, updateCounter++));
        }
    }

    public boolean isValidStudentType(StudentType serviceType) {
        return serviceType.equals(StudentType.BOOKISH) || serviceType.equals(StudentType.OUTGOING) || serviceType.equals(StudentType.THRIFTY);
    }

    public boolean isValidServiceType(String serviceType) {
        return serviceType.equals(ServiceType.EATING.get()) || serviceType.equals(ServiceType.LODGING.get()) || serviceType.equals(ServiceType.LEISURE.get());
    }

    private boolean isInvalidLocation(long lat, long lon) {
        long bottomLat = currentBound.getBottomLat();
        long topLat = currentBound.getTopLat();
        long leftLon = currentBound.getLeftLon();
        long rightLon = currentBound.getRightLon();
        return !(bottomLat <= lat && lat <= topLat && leftLon <= lon && lon <= rightLon);
    }


    private boolean serviceNameAlreadyExists(String serviceName) {
        return servicesCollection.hasElem(serviceName);
    }

    @Override
    public void addStudent(StudentType studentType, String studentName, String country, String lodgingName) throws InvalidStudentTypeException, LodgingDoesntExistException, LodgingWithoutCapacityException, StudentAlreadyExistsException{
        if (!isValidStudentType(studentType))
            throw new InvalidStudentTypeException();
        if (!isValidLodgingService(lodgingName))
            throw new LodgingDoesntExistException();
        EatingLodging lodging = (LodgingServiceClass)servicesCollection.getElement(lodgingName);
        if (servicesCollection.getElement(lodgingName).getServiceType().equals(ServiceType.LODGING) && !lodging.hasCapacity())
            throw new LodgingWithoutCapacityException();
        if (!isValidStudentName(studentName))
            throw new StudentAlreadyExistsException();
        Service lodgingService = servicesCollection.getElement(lodgingName);
//todo add the student to a country

        switch (studentType) {
            case OUTGOING -> studentCollection.addStudent(studentName, new OutgoingStudent(studentName, lodgingService, country), country);
            case BOOKISH -> studentCollection.addStudent(studentName, new BookishStudent(studentName, lodgingService, country), country);
            case THRIFTY -> studentCollection.addStudent(studentName, new ThriftyStudentClass(studentName, lodgingService, country), country);

        }
        studentCollection.getElement(studentName).changeCapacityOfTheLodging(studentCollection.getElement(studentName));
    }

    @Override
    public void removeStudent(String studentName) throws StudentDoesNotExistException {
        if (isValidStudentName(studentName))
            throw new StudentDoesNotExistException();
        studentCollection.removeStudent(studentName);
    }

    @Override
    public void go(String studentName, String locationName) throws UnknownLocationException, ServiceDoesntExistException,
            StudentDoesNotExistException, InvalidGoServiceTypeException, AlreadyThereException, EatingWithoutCapacityException {

        if (!servicesCollection.hasElem(locationName))
            throw new UnknownLocationException();
        if (isValidStudentName(studentName))
            throw new StudentDoesNotExistException();
        Service service = servicesCollection.getElement(locationName);
        if (!service.getServiceType().equals(ServiceType.EATING) && !service.getServiceType().equals(ServiceType.LEISURE)) {
            throw new InvalidGoServiceTypeException();
        }
        if (isAlreadyThere(studentName, locationName))
            throw new AlreadyThereException();
        if (service.getServiceType().equals(ServiceType.EATING)) {
            EatingServiceClass eating = (EatingServiceClass) service;
            if (!eating.hasCapacity())
                throw new EatingWithoutCapacityException();
        }

        studentCollection.getElement(studentName).goTo(service, studentCollection.getElement(studentName));
    }

    @Override
    public void move(String studentName, String lodgingName) throws LodgingDoesntExistException, StudentDoesNotExistException, ItsStudentHomeException, LodgingWithoutCapacityException, MoveIsNotAcceptableException{
        if (!isValidLodgingService(lodgingName))
            throw new LodgingDoesntExistException();
        if (isValidStudentName(studentName))
            throw new StudentDoesNotExistException();
        if (isStudentHome(studentName, lodgingName))
            throw new ItsStudentHomeException();
        EatingLodging lodging = (LodgingServiceClass)servicesCollection.getElement(lodgingName);
        if (!lodging.hasCapacity())
            throw new LodgingWithoutCapacityException();
        if (isStudentDistractedToMoveHome(studentName, lodgingName))
            throw new MoveIsNotAcceptableException();
        Service lodgingService = servicesCollection.getElement(lodgingName);
        studentCollection.getElement(studentName).moveHome(lodgingService, studentCollection.getElement(studentName));
    }

    @Override
    public Iterator<Student> listUsers(String order, String service) throws InvalidOrderException, ServiceDoesntExistException, DontControlStudentEntryAndExitException{
        if (!order.equals("<") && !order.equals(">"))
            throw new InvalidOrderException();
        if (!serviceNameAlreadyExists(service))
            throw new ServiceDoesntExistException();
        if (!servicesCollection.getElement(service).getServiceType().equals(ServiceType.LODGING) && !servicesCollection.getElement(service).getServiceType().equals(ServiceType.EATING))
            throw new DontControlStudentEntryAndExitException();
        return servicesCollection.getElement(service).clientsIterator(order, servicesCollection.getElement(service));
    }

    public boolean hasClientsInTheService(String service){ //era para o users, mas dá mais erros que o hasStudents
        Service serviceObj = servicesCollection.getElement(service);
        if (serviceObj instanceof EatingServiceClass eating) {
            return !eating.getClientsList().isEmpty();
        } else if (serviceObj instanceof LodgingServiceClass lodging) {
            return !lodging.getClientsList().isEmpty();
        }
        return false;
    }

    @Override
    public String getServiceName(String name) {
        return servicesCollection.getElement(name).getName();
    }

    @Override
    public String getServiceType(String studentLocation) {
        return servicesCollection.getElement(studentLocation).getServiceType().get();
    }

    @Override
    public String  getStudentName(String name) {
        return studentCollection.getElement(name.toUpperCase()).getName(); //meti to uppercase
    }

    @Override
    public void star(int rating, String serviceName, String description) throws InvalidRatingException, ServiceDoesntExistException {
        if (rating < 1 || rating > 5)
            throw new InvalidRatingException();
        if (!isValidServiceName(serviceName))
            throw new ServiceDoesntExistException();
        Service service = servicesCollection.getElement(serviceName);
        service.addDescription(description);
        int oldUpdateCounter = service.getLastUpdatedOrder();
        int oldStars = service.getAverageStars();
        // Increment FIRST, then pass to addRating
        float newTotal = service.getTotalStars();
        int newCount = service.getRatingCount();
        int newStars = Math.round((newTotal + rating) / (newCount + 1));
        if (oldStars != newStars) {
            servicesCollection.updateRating(serviceName, oldStars, newStars);
            updateCounter++;
            service.updateCounterRating();
            service.addRating(rating, updateCounter);
        } else
            service.addRating(rating, oldUpdateCounter);
    }

    @Override
    public boolean ranked(int stars, String serviceType) {
        return servicesCollection.isThereServicesWithCertainRate(serviceType, stars);
    }

    @Override
    public boolean hasServices() {
        return !servicesCollection.isEmpty();
    }

    @Override
    public boolean hasValidServiceByType(String type) {
        return servicesCollection.isThereAnyServiceWithType(type);
    }

    @Override
    public boolean getWasUpdated() {
        return wasUpdated;
    }

    @Override
    public String studentCurrentLocation(String studentName) throws StudentDoesNotExistException {
        if(isValidStudentName(studentName))
            throw new  StudentDoesNotExistException();
        return studentCollection.getElement(studentName).getCurrentLocation();
    }

    @Override
    public long getServiceLatitude(String serviceName) {
        return servicesCollection.getElement(serviceName).getLatitude();
    }

    @Override
    public long getServiceLongitude(String serviceName) {
        return servicesCollection.getElement(serviceName).getLongitude();
    }



    @Override
    public String find(String studentName, String serviceType) throws InvalidServiceTypeException, StudentDoesNotExistException, NoServiceWithTypeException {
        if (!isValidServiceType(serviceType))
            throw new InvalidServiceTypeException();
        if (isValidStudentName(studentName))
            throw new StudentDoesNotExistException();
        if (hasValidServiceByType(serviceType))
            throw new NoServiceWithTypeException();
        wasUpdated = false;
        Student student = studentCollection.getElement(studentName);
        String currentStudentPos = student.getCurrentLocation();
        Service service = servicesCollection.getElement(currentStudentPos);
        long studentLat = service.getLatitude();
        long studentLon = service.getLongitude();
        if (student instanceof ThriftyStudentClass thriftyStudent) {
            Service cheapestService = servicesCollection.getTheCheapestServiceThrifty(studentLat, studentLon, serviceType);
            return handleFindLogic(currentStudentPos, serviceType, thriftyStudent, cheapestService, studentName);
        }
        Service nearestService = servicesCollection.getTheNearestService(studentLat, studentLon, serviceType);
        return nearestService.getName();
    }

    /**
     * It handles the logic of the Find command
     *
     * @param currentStudentPos real time position of the Student
     * @param serviceType       Type of the Service
     * @param thriftyStudent    Object of type thrifty
     * @param cheapestService   Object of the cheapest Service
     * @return <code>String</code> of the nearest localization from the Student Position
     */
    private String handleFindLogic(String currentStudentPos, String serviceType, ThriftyStudentClass thriftyStudent, Service cheapestService, String studentName) {

        if (!cheapestService.getName().equals(currentStudentPos) && serviceType.equalsIgnoreCase(ServiceType.LODGING.name()) && cheapestService.getServicePrice() < thriftyStudent.getCheapestRoomPrice()) {
            int newPrice = cheapestService.getServicePrice();
            //Change the Home and don't change the student pos
            EatingLodging lodging = (LodgingServiceClass)servicesCollection.getElement(cheapestService.getName());
            if (!lodging.hasCapacity()) {
                thriftyStudent.moveHome(cheapestService, studentCollection.getElement(studentName));
                thriftyStudent.goTo(servicesCollection.getElement(currentStudentPos), studentCollection.getElement(studentName));
            }
            //Update the new cheapest Room
            thriftyStudent.updateCheapestRoom(newPrice);
            wasUpdated = true;
            return cheapestService.getName();
        } else if (serviceType.equalsIgnoreCase(ServiceType.EATING.name()) && cheapestService.getServicePrice() < thriftyStudent.getCheapestMealPrice()) {
            int newPrice = cheapestService.getServicePrice();
            //Update the new cheapest Meal
            thriftyStudent.updateCheapestMeal(newPrice);
            wasUpdated = true;
            return cheapestService.getName();
        } else
            return cheapestService.getName();
    }

    @Override
    public Iterator<Student> getUserIterator(String choice) {
        if (choice.equalsIgnoreCase("all"))
            return studentCollection.allStudentIterator(); //falta ordenar por ordem alfabética
        else
            return studentCollection.byCountryIterator(choice); //falta criar o iterador por países     (choice = país) -> else
    }

    @Override
    public Iterator<Service> getServiceIteratorSortedByRating() {
        return servicesCollection.allServiceIteratorSortedRating();
    }

    @Override
    public Iterator<Service> getServiceIterator() throws NoServicesYetException{
        if (!hasServices()) throw new NoServicesYetException();
        return servicesCollection.allServiceIterator();
    }

    @Override
    public Iterator<Service> getServiceIteratorByType(String type, int rating, String studentName) throws InvalidStarsException, StudentDoesNotExistException, InvalidServiceTypeException, NoServiceOfTypeException, NoTypeServiceWithThatRatingException {
        if (rating < 1 || rating > 5)
            throw new InvalidStarsException();
        if (isValidStudentName(studentName))
            throw new StudentDoesNotExistException();
        if (!type.equals(ServiceType.EATING.get()) && !type.equals(ServiceType.LODGING.get()) && !type.equals(ServiceType.LEISURE.get()))
            throw new InvalidServiceTypeException();
        if (!hasValidServiceByType(type))
            throw new NoServiceOfTypeException();
        if (servicesCollection.isThereServicesWithCertainRate(type, rating))
            throw new NoTypeServiceWithThatRatingException();
        long lat = servicesCollection.getElement(studentName).getLatitude();
        long lon = servicesCollection.getElement(studentName).getLongitude();
        return servicesCollection.serviceIteratorByType(type, rating, lat, lon);
    }

    @Override
    public Iterator<Service> getVisitedLocationsIterator(String studentName) throws  StudentDoesNotExistException, InvalidVisitedStudentTypeException, NoServicesYetException {
        if (isValidStudentName(studentName))
            throw new  StudentDoesNotExistException();
        if (isStudentType(studentName, StudentType.THRIFTY))
            throw new  InvalidVisitedStudentTypeException();
        if (!didStudentVisitedAnyLocation(studentName))
            throw new  NoVisitedAnyLocationException();

        return studentCollection.getElement(studentName).getVisitedLocationsIterator();
    }

    public boolean existsServiceWithTag(String tag) {
        Iterator<String> it = servicesCollection.getDescriptions();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(tag))
                return true;
        }
        return false;
    }

    public Iterator<Service> getServicesWithTagIterator(String tag){
        return servicesCollection.getServicesWithTag(tag);
    }

    public boolean isThereNoStudentsFromCountry (String country) {
        return studentCollection.areThereStudentsFromCountry(country);
    }

//    public boolean hasCapacity (String serviceName) {
//        return servicesCollection.getElement(serviceName).hasCapacity();
//    }
}
