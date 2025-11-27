package HomeawayApp;
/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

import Enumerators.ServiceType;
import Enumerators.StudentType;
import Services.Service;
import Students.Student;
import dataStructures.Iterator;
import dataStructures.exceptions.InvalidPositionException;
import exceptions.*;

public interface HomeAwayApp {

    /////////////////////////////////////////////////////// boundsControlClass

    void createBounds(String areaName, long topLat, long leftLon, long bottomLat, long rightLon) throws InvalidBoundLocationException, SameAreaNameException;

    void saveCurrentBounds();

    void loadBounds(String areaName) throws BoundNameDoesntExistException, FileDoesNotExistsException;


    /**
     * Checks if the bound name already exists
     *
     * @param areaName The name of the bound to check
     * @return True if it already exists. False otherwise
     */
    boolean checkBoundsNameIsValid(String areaName);

    /**
     * Checks if the bound name already exists
     * @return True if it already exists. False otherwise
     */

    boolean checkBoundsIsValid(long topLat, long leftLon, long bottomLat, long rightLon);

    boolean getHasBounds();


    String getBoundName();

    /////////////////////////////////////////////////////// boundsControlClass

    void addService(ServiceType serviceType, long lat, long lon, int price, String nameService, int value) throws InvalidServiceTypeException, InvalidPositionException, PriceEqualOrLessThenZeroException, InvalidServiceDiscountException, InvalidServiceCapacityException, ServiceAlreadyExistsException;

    //Iterator<tags>getTags();

     /**
      * Checks if the service name already exists.
      * @param serviceName name of the service to check
      * @return <code>true</code> if there isn't any service with the same name, false otherwise
      */
     boolean isValidServiceName(String serviceName);


    boolean hasValidServiceByType(String type);

     /**
      * Checks if the service already exists and is a lodging type service
      * @param serviceName name of the service to check
      * @return true if exists and is lodging type, false otherwise
      */
     boolean isValidLodgingService(String serviceName);

     /**
      * Checks if the student name is valid
      * @param studentName name of the student to check
      * @return true if the name doesn't exist, false otherwise
      */
     boolean isValidStudentName(String studentName);

     /**
      * Checks if the student is in the given location
      * @param studentName name of the student to check
      * @param location location to check
      * @return true if the student is in the given location, false otherwise
      */
     boolean isAlreadyThere(String studentName,String location);

     /**
      * Checks if the student has visited any services
      * @param studentName name of the student to check
      * @return true if the array of visited places isn't empty, false otherwise
      */
     boolean didStudentVisitedAnyLocation(String studentName);

     /**
      * Checks if the student is a certain type
      * @param studentName name of the student to check
      * @param type type of the student
      * @return true if the student is from that type, false otherwise
      */
     boolean isStudentType(String studentName, StudentType type);

     /**
      * Checks if the service is inside bounds
      * @param serviceLat the service latitude
      * @param serviceLon the service longitude
      * @return true if serviceLat is between bounds latitudes and serviceLon is between bounds longitude, false otherwise
      */
     boolean isValidPlace(long serviceLat,long serviceLon);

     /**
      * Check if there are students in the program
      * @return <code>true</code> if StudentCollection isn't empty, false otherwise
      */
     boolean isThereNoStudents();

     /**
      * Checks if it's worthy for a thrifty student to move into another service
      * @param studentName name of the student to check
      * @param newService name of the service where the student is going
      * @return true is the price of the new service is higher than the previous, false otherwise
      */
     boolean isStudentDistracted(String studentName,String newService);

     /**
      * Checks if a thrifty student is doing a right choice
      * @param studentName The name of the student
      * @param newService The name of the service where he wants to move
      * @return True if the student is thrifty and is trying to go to a cheaper lodging,
      * False if the student isn't thrifty or if he is, but is trying to go to a more expensive lodging
      */
     boolean isStudentDistractedToMoveHome(String studentName, String newService);
     /**
      * Checks if the lodging place given isn't already student's home
      * @param studentName The name of the student
      * @param home The name of the place the student is trying to go
      * @return True if it isn't student's home, False otherwise
      */
     boolean isStudentHome(String studentName,String home);


     /**
      * Gets the name of student's home
      * @param studentName name of the student to check
      * @return the name of the student's home
      */
     String getStudentHomeLocation(String studentName);




     /**
      * Adds a student to the program
      * @param studentType type of the student
      * @param studentName name of the student
      * @param lodgingName nem of student's initial "home"
      */
     void addStudent(StudentType studentType, String studentName, String country , String lodgingName) throws InvalidStudentTypeException, LodgingDoesntExistException, LodgingWithoutCapacityException, StudentAlreadyExistsException;

     /**
      * Removes a student from the program
      * @param studentName name of the student
      */
     void removeStudent(String studentName) throws StudentDoesNotExistException;

     /**
      * Changes the location of a student to a service, or home
      * @param studentName name of the student
      * @param locationName location where he is going
      */
     void go(String studentName, String locationName) throws UnknownLocationException, StudentDoesNotExistException, InvalidGoServiceTypeException, AlreadyThereException, EatingWithoutCapacityException;

     /**
      * Changing the students lodging place/"home" if that is acceptable(he can't go to the same home he is already in)
      * @param studentName name of the student
      * @param lodgingName name of the lodging service the student is going
      */
     void move(String studentName, String lodgingName) throws LodgingDoesntExistException, StudentDoesNotExistException, ItsStudentHomeException, LodgingWithoutCapacityException, MoveIsNotAcceptableException;

     /**
      * Evaluates a service
      * @param rating the rating given to que service
      * @param serviceName the service who is given the rating
      */
     void star(int rating, String serviceName, String description) throws InvalidRatingException, ServiceDoesntExistException;

     /**
      * Lists services of a certain type, with a specific star evaluation
      * @param stars the number of stars the listed services need to have
      * @param serviceType the type of service to be shown
      */
     boolean ranked(int stars, String serviceType);

     boolean hasServices();

     /**
      * Checks if there is any service with that type
      * @param type Type of the service
      * @return True if there is any, False otherwise
      * @pre <Service> != null
      */
     boolean isValidServiceType(String type);

     /**
      * It will find the current Location of a certain Student
      * @param studentName The name of the Student
      * @return <>String</> of the location of the Student
      */
     String studentCurrentLocation(String studentName) throws StudentDoesNotExistException;
     boolean getWasUpdated();
     long getServiceLatitude(String serviceName);
     long getServiceLongitude(String serviceName);

     /**
      * Finds the most relevant service of a certain type, for a specific student
      * @param studentName the name of the student, to check his type
      * @param serviceType the type of service that's shown
      */
     String find(String studentName, String serviceType) throws InvalidServiceTypeException, StudentDoesNotExistException, NoServiceWithTypeException;

     /**
      * The iterator of users
      * @return the list of users
      */
     Iterator<Student> getUserIterator(String choice);

     /**
      * Lists the services in the program by rating
      * @return the sorted list of services
      */
     Iterator<Service> getServiceIteratorSortedByRating();

     /**
      * Lists the services in the program
      * @return the list of services
      */
     Iterator<Service> getServiceIterator() throws NoServicesYetException;

     /**
      * Lists the services in the program with that type and rating
      * @param type The type of the service
      * @param rating The rating of the service
      * @return list with services of that type and rating
      */
     Iterator<Service> getServiceIteratorByType(String type, int rating, String studentName) throws InvalidStarsException, StudentDoesNotExistException, InvalidServiceTypeException, NoServiceOfTypeException, NoTypeServiceWithThatRatingException;

     /**
      * Lists the visited locations of a student
      * @param studentName the name of the student whose list is going to be shown
      * @return the list of visited locations
      */
    Iterator<Service> getVisitedLocationsIterator(String studentName) throws  StudentDoesNotExistException, InvalidVisitedStudentTypeException, NoServicesYetException;

    Iterator<Student> listUsers(String order, String service) throws InvalidOrderException, ServiceDoesntExistException, DontControlStudentEntryAndExitException;

    boolean existsServiceWithTag(String tag);

    boolean hasStudents(String service);

    String getServiceName(String name);

    String getServiceType(String studentLocation);

    String getStudentName(String name);

    int getServiceCapacity(String service);

    boolean hasClientsInTheService(String service);

    boolean isThereNoStudentsFromCountry(String country);
}