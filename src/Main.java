/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

import Enumerators.ServiceType;
import Enumerators.StudentType;
import Enumerators.Message;
import Enumerators.Commands;
import dataStructures.exceptions.InvalidPositionException;
import exceptions.*;
import HomeawayApp.*;
import Students.*;
import Services.*;
import dataStructures.*;


import java.util.Scanner;

public class Main {

    //private static boolean hasBounds = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HomeAwayAppClass systemApp = new HomeAwayAppClass();
        Commands comm = null;
        String command;
        do {
            command = in.next().toUpperCase().trim();
            try {
                comm = Commands.valueOf(command);
                if (systemApp.getHasBounds()) {
                    checkCommand(in, systemApp, comm);
                } else {
                    switch (comm) {
                        case BOUNDS -> boundsCommand(in, systemApp);
                        case LOAD -> loadBound(in, systemApp);
                        case HELP -> helpCommand();
                        case EXIT -> System.out.println(Message.EXITING);
                        default -> isValidCommand(in, comm);
                    }
                }
            } catch (IllegalArgumentException e1) {
                invalidCommand();
            }
        } while (comm == null || !comm.equals(Commands.EXIT));
        in.close();
    }

    /**
     * Method used to print Unknown Command message
     */
    private static void invalidCommand() {
        System.out.println(Message.COMMAND_ERROR.get());
    }


    /**
     * Method used to process the command based on the user input.
     *
     * @param in        Scanner of System input responsible for reading user commands.
     * @param systemApp Object of class HomeAwayApp, responsible for handling all the Application mechanics.
     * @param comm      The user command to process.
     * @pre bound != null
     */
    private static void checkCommand(Scanner in, HomeAwayAppClass systemApp, Commands comm) {
        //TODo tag students
        switch (comm) {
            case BOUNDS -> boundsCommand(in, systemApp);
            case SAVE -> saveBound(systemApp);
            case LOAD -> loadBound(in, systemApp);
            case SERVICE -> addNewService(in, systemApp);
            case SERVICES -> servicesCommand(systemApp);
            case STUDENT -> studentCommand(in, systemApp);
            case LEAVE -> leaveCommand(in, systemApp);
            case STUDENTS -> studentsCommand(in, systemApp);
            case GO -> goCommand(in, systemApp);
            case MOVE -> moveCommand(in, systemApp);
            case USERS -> usersCommand(in, systemApp);
            case WHERE -> whereCommand(in, systemApp);
            case VISITED -> visitedCommand(in, systemApp);
            case STAR -> starCommand(in, systemApp);
            case RANKING -> rankingCommand(systemApp);
            case RANKED -> rankedCommand(in, systemApp);
            case TAG -> tagCommand(in, systemApp);
            case FIND -> findCommand(in, systemApp);
            case HELP -> helpCommand();
            case EXIT -> exitCommand(systemApp);
            default -> isValidCommand(in, comm);
        }
    }

    private static void exitCommand(HomeAwayAppClass systemApp) {
        systemApp.saveCurrentBounds();
        System.out.println(Message.EXITING);
    }


    /**
     * Skips the command input
     *
     * @param in   The Scanner
     * @param comm The command
     */
    //TODO falta os comandos save, load, users,
    private static void isValidCommand(Scanner in, Commands comm) {
        switch (comm) {
            case SERVICES, LEAVE, STUDENTS,
                 WHERE, VISITED, RANKING, RANKED, SAVE, LOAD, TAG -> {
                in.nextLine();
                System.out.println(Message.SYSTEM_BOUNDS_NOT_DEFINED);
            }
            case GO, MOVE, FIND, USERS, STAR -> {
                in.nextLine();
                in.nextLine();
                System.out.println(Message.SYSTEM_BOUNDS_NOT_DEFINED);
            }
            case STUDENT -> {
                in.nextLine();
                in.nextLine();
                in.nextLine();
                System.out.println(Message.SYSTEM_BOUNDS_NOT_DEFINED);
            }
            default -> System.out.println(Message.COMMAND_ERROR);
        }
    }


    /**
     * Creates the geographic bounds of the system
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void boundsCommand(Scanner in, HomeAwayAppClass systemApp) {
        long topLat = in.nextLong();
        long leftLon = in.nextLong();
        long bottomLat = in.nextLong();
        long rightLon = in.nextLong();
        String areaName = in.nextLine().trim();
        try {
            systemApp.createBounds(areaName, topLat, leftLon, bottomLat, rightLon);
            System.out.printf(Message.BOUNDS_CREATED.get(), areaName);
        } catch (SameAreaNameException | InvalidBoundLocationException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * private static void boundsCommand(Scanner in, HomeAwayAppClass systemApp) {
     * long topLat = in.nextLong();
     * long leftLon = in.nextLong();
     * long bottomLat = in.nextLong();
     * long rightLon = in.nextLong();
     * String areaName = in.nextLine().trim();
     * if (systemApp.checkBoundsNameIsValid(areaName)) {
     * if (systemApp.checkBoundsIsValid(topLat, leftLon, bottomLat, rightLon)) {
     * systemApp.createBounds(areaName, topLat, leftLon, bottomLat, rightLon);
     * System.out.printf(Message.BOUNDS_CREATED.get(), areaName);
     * }
     * System.out.println(Message.INVALID_BOUNDS.get());
     * }
     * System.out.println(Message.BOUND_ALREADY_EXISTS.get());
     * }
     */

    private static void saveBound(HomeAwayApp systemApp) {
        try {
            systemApp.saveCurrentBounds();
            System.out.printf(Message.BOUNDS_SAVED.get(), systemApp.getBoundName());
        } catch (SystemBoundsNotDefinedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadBound(Scanner in, HomeAwayAppClass systemApp) {
        String boundName = in.nextLine().trim();
        try {
            systemApp.loadBounds(boundName);
            System.out.printf(Message.BOUND_NAME_LOADED.get(), systemApp.getBoundName());
        } catch (BoundNameDoesntExistException e) {
            System.out.printf(e.getMessage(), boundName);
        } catch (FileDoesNotExistsException e) { //TODO o que por aqui???
            System.out.printf(e.getMessage(), boundName);
        }
    }

    //TODO TO DO!!!

    private static void addNewService(Scanner in, HomeAwayApp systemApp) {
        String serviceTypeStr = in.next().toUpperCase();
        long latitude = in.nextLong(), longitude = in.nextLong();
        int price = in.nextInt(), value = in.nextInt();
        String name = in.nextLine().trim();
        ServiceType serviceType = null;

        try {
            serviceType = ServiceType.fromString(serviceTypeStr);
            systemApp.addService(serviceType, latitude, longitude, price, name, value);
            System.out.printf(Message.SERVICE_CREATED.get(), serviceTypeStr.toLowerCase(), name); //todo mudei aqui

        } catch (InvalidServiceTypeException | InvalidPositionException | InvalidServiceDiscountException |
                 InvalidServiceCapacityException e1) {
            System.out.println(e1.getMessage());
        } catch (PriceEqualOrLessThenZeroException e2) {
            switch (serviceType) {
                case EATING -> System.out.println(Message.INVALID_MENU_PRICE);
                case LODGING -> System.out.println(Message.INVALID_ROOM_PRICE);
                case LEISURE -> System.out.println(Message.INVALID_TICKET_PRICE);
            }
        } catch (ServiceAlreadyExistsException e3) {
            System.out.printf(e3.getMessage(), systemApp.getServiceName(name));
        }
    }

    /**
     * Displays the list of services in the system
     *
     * @param systemApp The application
     */
    private static void servicesCommand(HomeAwayAppClass systemApp) {
        try {
            Iterator<Service> it = systemApp.getServiceIterator();
            while (it.hasNext()) {
                Service service = it.next();
                String serviceType = service.getServiceType().toString().toLowerCase();
                System.out.printf(Message.SERVICES_COMMAND.get(), service.getName(), serviceType, service.getLatitude(), service.getLongitude());
            }
        } catch (NoServicesYetException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a student to the system
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void studentCommand(Scanner in, HomeAwayAppClass systemApp) {
        String type = in.nextLine().toUpperCase().trim();
        String studentName = in.nextLine().trim();
        String country = in.nextLine().trim();
        String lodging = in.nextLine().trim();
        StudentType studentType;
        try {
            studentType = StudentType.valueOf(type);
            systemApp.addStudent(studentType, studentName, country, lodging);
            System.out.printf(Message.NAME_ADDED.get(), studentName);
        } catch (IllegalArgumentException e) {
            System.out.println(Message.INVALID_STUDENT_TYPE.get());
        } catch (LodgingDoesntExistException | LodgingWithoutCapacityException e) {
            System.out.printf(e.getMessage(), lodging);
        } catch (StudentAlreadyExistsException e) {
            System.out.printf(e.getMessage(), systemApp.getStudentName(studentName));
        }
    }


    /**
     * Removes a student from te system
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void leaveCommand(Scanner in, HomeAwayAppClass systemApp) {
        String studentName = in.nextLine().trim();
        try {
            systemApp.removeStudent(studentName);
            System.out.printf(Message.HAS_LEFT.get(), studentName);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(e.getMessage(), studentName);
        }
    }

    /**
     * Lists all the students in the community
     *
     * @param systemApp The application
     */
    private static void studentsCommand(Scanner in, HomeAwayAppClass systemApp) {
        String argument = in.nextLine().trim();
        Iterator<Student> it = systemApp.getUserIterator(argument);
        if (systemApp.isThereNoStudents()) {
            if (argument.equalsIgnoreCase(Message.ALL.get()))
                System.out.println(Message.NO_STUDENTS_YET.get());
            else
                System.out.printf(Message.NO_STUDENTS_FROM.get(), argument);
        } else if (!it.hasNext()) {
            System.out.printf(Message.NO_STUDENTS_FROM.get(), argument);
        }
        while (it.hasNext()) {
            Student student = it.next();
            System.out.printf(Message.STUDENTS_COMMAND.get(), student.getName(), student.getType().name().toLowerCase(), student.getCurrentLocation());
        }
    }

    /**
     * Changes the location of a student to home, or a service
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void goCommand(Scanner in, HomeAwayAppClass systemApp) {
        String studentName = in.nextLine().trim();
        String location = in.nextLine().trim();
        try {
            systemApp.go(studentName, location);
            if (systemApp.isStudentDistracted(studentName, location))
                System.out.printf(Message.GO_COMMAND_DISTRACTED.get(), systemApp.getStudentName(studentName), systemApp.getServiceName(location), systemApp.getStudentName(studentName));
            else
                System.out.printf(Message.GO_COMMAND.get(), systemApp.getStudentName(studentName), systemApp.getServiceName(location));
        } catch (UnknownLocationException e) {
            System.out.printf(Message.UNKNOWN_LOCATION.get(), location);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(Message.STUDENT_DOES_NOT_EXIST.get(), studentName);
        } catch (InvalidGoServiceTypeException | ServiceDoesntExistException e) {
            System.out.printf(Message.LOCATION_ISNT_VALID_SERVICE.get(), location);
        } catch (AlreadyThereException e) {
            System.out.println(Message.ALREADY_THERE.get());
        } catch (EatingWithoutCapacityException e) {
            System.out.printf(e.getMessage(), location);
        }
    }

    /**
     * Changes the student's home if that's acceptable
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void moveCommand(Scanner in, HomeAwayAppClass systemApp) {
        String studentName = in.nextLine().trim();
        String lodging = in.nextLine().trim();
        try {
            systemApp.move(studentName, lodging);
            System.out.printf(Message.MOVE_COMMAND.get(), systemApp.getServiceName(lodging), systemApp.getStudentName(studentName), systemApp.getStudentName(studentName));
        } catch (LodgingDoesntExistException | LodgingWithoutCapacityException e) {
            System.out.printf(e.getMessage(), lodging);
        } catch (StudentDoesNotExistException | MoveIsNotAcceptableException e) {
            System.out.printf(e.getMessage(), studentName);
        } catch (ItsStudentHomeException e) {
            System.out.printf(e.getMessage(), systemApp.getStudentName(studentName));
        }
    }

    private static void usersCommand(Scanner in, HomeAwayAppClass systemApp) {
        String order = in.next();
        String service = in.nextLine().trim();
        try {
            Iterator<Student> it = systemApp.listUsers(order, service); //TODO tratar do iterador
            //System.out.println(systemApp.getServiceCapacity(service));
            if (systemApp.hasClientsInTheService(service) && it.hasNext()) {
                while (it.hasNext()) {
                    Student student = it.next();
                    System.out.printf(Message.USERS_LIST.get(), student.getName(), student.getType().name().toLowerCase());
                }
            } else
                System.out.printf(Message.EMPTY_SERVICE.get(), systemApp.getServiceName(service));
        } catch (InvalidOrderException e) {
            System.out.println(e.getMessage());
        } catch (ServiceDoesntExistException e) {
            System.out.printf(e.getMessage(), service);
        } catch (DontControlStudentEntryAndExitException e) {
            System.out.printf(e.getMessage(), systemApp.getServiceName(service));
        }
    }

    /**
     * Finds the student
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void whereCommand(Scanner in, HomeAwayAppClass systemApp) {
        String name = in.nextLine().trim();
        try {
            String studentLocation = systemApp.studentCurrentLocation(name);
            long serviceLat = systemApp.getServiceLatitude(studentLocation);
            long serviceLon = systemApp.getServiceLongitude(studentLocation);
            String serviceType = systemApp.getServiceType(studentLocation).toLowerCase();
            String studentName = systemApp.getStudentName(name);

            System.out.printf(Message.WHERE_COMMAND.get(), studentName, studentLocation, serviceType, serviceLat, serviceLon);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(Message.NAME_NE.get(), name);
        }
    }

    /**
     * Lists the locations visited and stored by one student
     *
     * @param in        The Scanner
     * @param systemApp the application
     */
    private static void visitedCommand(Scanner in, HomeAwayAppClass systemApp) {
        String studentName = in.nextLine().trim();
        try {
            Iterator<Service> it = systemApp.getVisitedLocationsIterator(studentName);
            while (it.hasNext()) {
                Service service = it.next();
                System.out.println(service.getName());
            }
        } catch (StudentDoesNotExistException e) {
            System.out.printf(Message.STUDENT_DOES_NOT_EXIST.get(), studentName);
        } catch (InvalidVisitedStudentTypeException e) {
            System.out.printf(Message.IS_THRIFTY.get(), systemApp.getStudentName(studentName));
        } catch (NoVisitedAnyLocationException e) {
            System.out.printf(Message.HAS_NOT_VISITED_ANY_LOCATIONS.get(), systemApp.getStudentName(studentName));
        }
    }

    /**
     * Gives a rating to a service
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void starCommand(Scanner in, HomeAwayAppClass systemApp) {
        int rating = in.nextInt();
        String service = in.nextLine().trim();
        String description = in.nextLine().trim();  //ver se o trim está bem
        try {
            systemApp.star(rating, service, description);
            System.out.println(Message.HAS_BEEN_REGISTERED);
        } catch (InvalidRatingException e1) {
            System.out.println(e1.getMessage());
        } catch (ServiceDoesntExistException e) {
            System.out.printf(Message.SERVICE_DOES_NOT_EXIST.get(), service);
        }
    }

    /**
     * Lists all services ordered by rating
     *
     * @param systemApp The application
     */
    private static void rankingCommand(HomeAwayAppClass systemApp) {
        if (systemApp.hasServices()) {
            System.out.println(Message.DESCENDING_ORDER);
            Iterator<Service> it = systemApp.getServiceIteratorSortedByRating();
            while (it.hasNext()) {
                Service service = it.next();
                System.out.printf(Message.RANKING_COMMAND.get(), service.getName(), service.getAverageStars());
            }
        } else System.out.println(Message.NO_SERVICES_IN_THE_SYSTEM);
    }

    /**
     * Lists a certain type of service with a certain star rating
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void rankedCommand(Scanner in, HomeAwayAppClass systemApp) {
        String serviceType = in.next();
        int rating = in.nextInt();
        String studentName = in.nextLine().trim();
        try {
            Iterator<Service> it = systemApp.getServiceIteratorByType(serviceType, rating, studentName);
            System.out.printf(Message.RANKED_COMMAND.get(), serviceType, rating);
            while (it.hasNext()) {
                Service service = it.next();
                System.out.println(service.getName());
            }
        } catch (InvalidStarsException | InvalidServiceTypeException e) {
            System.out.println(e.getMessage());
        } catch (StudentDoesNotExistException e) {
            System.out.printf(e.getMessage(), studentName);
        } catch (NoServiceOfTypeException | NoTypeServiceWithThatRatingException e) {
            System.out.printf(e.getMessage(), serviceType);
        }
    }

    private static void tagCommand(Scanner in, HomeAwayAppClass systemApp) {
        String tag = in.nextLine().trim();
        if (systemApp.existsServiceWithTag(tag)) {
            Iterator<Service> it = systemApp.getServicesWithTagIterator(tag);
            while (it.hasNext()) {
                Service service = it.next();
                System.out.printf(Message.TAG_COMMAND.get(), service.getServiceType().get(), service.getName());
            }
        } else
            System.out.println(Message.NO_SERVICES_WITH_TAG.get());
    }

    /**
     * Finds the most relevant service for the student
     *
     * @param in        The Scanner
     * @param systemApp The application
     */
    private static void findCommand(Scanner in, HomeAwayAppClass systemApp) {
        String studentName = in.nextLine().trim();
        String serviceType = in.nextLine().trim();
        try {
            String nearestServiceName = systemApp.find(studentName, serviceType);
            System.out.println(nearestServiceName);
            if (systemApp.getWasUpdated()) //todo queria mudar mas não sei como
                System.out.printf(Message.THRIFTY_STUDENT_INFO_STORED.get(), studentName);
        } catch (InvalidServiceTypeException e) {
            System.out.println(Message.INVALID_SERVICE_TYPE);
        } catch (StudentDoesNotExistException e) {
            System.out.printf(Message.STUDENT_DOES_NOT_EXIST.get(), studentName);
        } catch (NoServiceWithTypeException e) {
            System.out.printf(Message.NO_SERVICES.get(), serviceType);
        }
    }

    /**
     * Prints the list of commands
     */
    private static void helpCommand() {
        System.out.println(Message.HELP_MESSAGE.get());
    }

}