package Enumerators;

import exceptions.InvalidServiceTypeException;

public enum ServiceType {

    EATING ("eating"),
    LODGING ("lodging"),
    LEISURE ("leisure"),;

    private String serviceType;

    public static ServiceType fromString(String type) throws InvalidServiceTypeException {
        for (ServiceType t : values()) {
            if (t.name().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new InvalidServiceTypeException();
    }

    ServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String get() {
        return serviceType;
    }

}
