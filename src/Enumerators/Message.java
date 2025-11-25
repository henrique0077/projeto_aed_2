/**
 * @author Henrique Pereira, Aluno: 71122, hv.pereira@campus.fct.unl.pt
 * @author Tomás Ramos, Aluno: 70251, tc.ramos@campus.fct.unl.pt
 */

package Enumerators;

/**
 * Enum containing all system messages used in the application.
 * Includes success messages, error messages, and the help message.
 */
public enum Message {

    //-----------------//
    // Succeed messages
    EXITING ("Bye!"),
    BOUNDS_CREATED ("%s created.\n"),//bounds_name
    BOUNDS_SAVED ("%s saved.\n"),
    NAME_ADDED ("%s added.\n"),//service_name ou name
    SERVICES_COMMAND ("%s: %s (%d, %d).\n"),//service_name, type, latitude, longitude
    HAS_LEFT ("%s has left.\n"),//name
    GO_COMMAND( "%s is now at %s.\n"),//lodging_name, name
    SERVICE_CREATED ("%s %s added.\n"), //service type, service name
    STUDENTS_COMMAND( "%s: %s at %s.\n"),//lodging_name, name
    MOVE_COMMAND ("lodging %s is now %s's home. %s is at home.\n"),//loging_name, name, name
    HAS_BEEN_REGISTERED ("Your evaluation has been registered!"),
    WHERE_COMMAND ("%s is at %s %s (%d, %d).\n"),//name, place, type, lat, long
    RANKING_COMMAND ("%s: %d\n"),//service_name, star number
    DESCENDING_ORDER( "Services sorted in descending order"),
    RANKED_COMMAND ("%s services closer with %d average\n"),//service_type, star number
    THRIFTY_STUDENT_INFO_STORED ("%s updated.\n"),//name
    HOME ("home"),
    UNKNOWN_LOCATION ("Unknown %s!\n"), //location name
    LOCATION_ISNT_VALID_SERVICE ("%s is not a valid service!\n"), //location name
    SERVICE_DOES_NOT_EXIST ("%s does not exist!\n"), //serviceName
    DONT_CONTROL_STUDENT_ENTRY_AND_EXIT ("%s does not control student entry and exit!\n"), //serviceName
    USERS_LIST ("%s: %s\n"), //studentName, type
    TAG_COMMAND ("%s %s\n"), //serviceType, Service_name



    //---------------//
    // Error messages
    COMMAND_ERROR ("Unknown command. Type help to see available commands."),
    BOUND_ALREADY_EXISTS ("Bounds already exists. Please load it!"),
    BOUND_NAME_DOES_NOT_EXISTS ("Bounds %s does not exists.\n"),
    BOUND_NAME_LOADED ("%s loaded.\n"),
    SYSTEM_BOUNDS_NOT_DEFINED ("System bounds not defined."),
    INVALID_BOUNDS ("Invalid bounds."),
    INVALID_MENU_PRICE ("Invalid menu price!"),
    INVALID_SERVICE_TYPE ("Invalid service type!"),
    INVALID_STUDENT_TYPE ("Invalid student type!"),
    INVALID_LOCATION ("Invalid location!"),
    INVALID_DISCOUNT ("Invalid discount price!"),
    INVALID_ROOM_PRICE ("Invalid room price!"),
    NO_SERVICES_YET ("No services yet!"),
    NO_STUDENTS_YET ("No students yet!"),
    INVALID_CAPACITY ("Invalid capacity!"),
    STUDENT_DOES_NOT_EXIST("%s does not exist!\n"),//student name
    NO_STUDENTS_FROM("No students from %s!\n"), //país
    LODGING_FULL ("Lodging %S is full!\n"), //lodging_name
    EMPTY_SERVICE("No students on %s!\n"),
    INVALID_ORDER("This order does not exists!"),
    STUDENT_ALREADY_EXISTS("%s already exists!\n"), //studentName

    OUTSIDE_BOUND ("%s location invalid!\n"),//name
    ALREADY_EXISTS ("%s already exists!\n"),//service_name
    INVALID_TICKET_PRICE ("Invalid ticket price!"),
    NAME_NE ("%s does not exist!\n"),//lodging_name ou name
    LODGING_DOES_NOT_EXIST( "lodging %s does not exist!\n"), //service_name
    UNKNOWN_S ("Unknown %s!\n"),//location_name
    ALREADY_THERE ("Already there!"),
    MOVE_NOT_ACCEPTABLE ("Move is not acceptable for %s!\n"),
    INVALID_EVALUATION ("Invalid evaluation!"),
    HAS_NOT_VISITED_ANY_LOCATIONS ("%s has not visited any locations!\n"),//name
    NO_SERVICES_IN_THE_SYSTEM ("No services in the system."),
    INVALID_STARS ("Invalid stars!"),
    NO_SERVICES_WITH_AVERAGE ("No %s services with average!\n"),//service_type
    NO_SERVICES ( "No %s services!\n"),//service_type
    EATING_IS_FULL ("eating %s is full!\n"), //location name
    LODGING_IS_FULL ("lodging %s is full!\n"), //location name
    ITS_STUDENT_HOME("That is %s's home!\n"), //studentName
    ALL ("all"),
    NO_SERVICES_WITH_TAG ("There are no services with this tag!\n"),



    GO_COMMAND_DISTRACTED ("%s is now at %s. %s is distracted!\n"),//loging_name, name
    THAT_IS_NAME_HOME( "That is %s's home!\n"),//name
    IS_THRIFTY ("%s is thrifty!\n"),//name




    // Help message
    HELP_MESSAGE("""
    bounds - Defines the new geographic bounding rectangle
    save - Saves the current geographic bounding rectangle to a text file
    load - Load a geographic bounding rectangle from a text file
    service - Adds a new service to the current geographic bounding rectangle. The service may be eating, lodging or leisure
    services - Displays the list of services in current geographic bounding rectangle, in order of registration
    student - Adds a student to the current geographic bounding rectangle
    students - Lists all the students or those of a given country in the current geographic bounding rectangle, in alphabetical order of the student's name
    leave - Removes a student from the the current geographic bounding rectangle
    go - Changes the location of a student to a leisure service, or eating service
    move - Changes the home of a student
    users - List all students who are in a given service (eating or lodging)
    star - Evaluates a service
    where - Locates a student
    visited - Lists locations visited by one student
    ranking - Lists services ordered by star
    ranked - Lists the service(s) of the indicated type with the given score that are closer to the student location
    tag - Lists all services that have at least one review whose description contains the specified word
    find - Finds the most relevant service of a certain type, for a specific student
    help - Shows the available commands
    exit - Terminates the execution of the program""");


    private final String message;

    /**
     * Constructs a Message enum with the specified message text.
     *
     * @param message The message text
     */
    Message(String message) {
        this.message = message;
    }

    //TODO podemos por assim??
    public String toString() {
        return message;
    }

    /**
     * Method used to get the raw message text.
     *
     * @return The message as a String
     */
    public String get() {
        return message;
    }

    /**
     * Method used to format the message with the provided arguments.
     *
     * @param args The arguments to format into the message
     * @return The formatted message String
     */
    public String format(Object... args) {
        return String.format(message, args);
    }

}

