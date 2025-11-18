/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Students.Student;
import Students.StudentCollection;
import dataStructures.*;
import dataStructures.FilterIterator;
import exceptions.InvalidPositionException;
import exceptions.ServiceDoesntExistException;

import java.io.Serializable;

//import java.util.ArrayList;

public class ServicesCollectionClass implements ServicesCollection, Serializable {

    private final List<Service> services;
    private final List<Service> servicesEating;
    private final List<Service> servicesLodging;
    private final List<Service> servicesLeisure;
    private int serviceCounter;
    private final int DEFAULT_DIMENTION = 1;

    public ServicesCollectionClass(){
        services = new ListInArray<>(DEFAULT_DIMENTION);
        servicesEating = new ListInArray<>(DEFAULT_DIMENTION);
        servicesLodging = new ListInArray<>(DEFAULT_DIMENTION);
        servicesLeisure = new ListInArray<>(DEFAULT_DIMENTION);
        serviceCounter = 0;
    }

    @Override
    public boolean isEmpty() {
        return serviceCounter == 0;
    }

    @Override
    public boolean hasElem(String serviceName) {
        return searchServiceNameIndex(serviceName) >= 0; // If searchServiceNameIndex() returns a number greater than 0 it means that found an elem
    }

    @Override
    public void addElem(Service elem) {
        services.addLast(elem);
        storeAndSortByType(elem); // Para organizar certos serviços por tipo
        serviceCounter++;
    }

    @Override
    public boolean isThereServicesWithCertainRate(String type, int stars){
        for(int i = 0; i < serviceCounter; i++){
            if(services.get(i).getServiceType().get().equals(type) && services.get(i).getAverageStars() == stars)
                return true;
        }
        return false;
    }

    @Override
    public boolean isThereAnyServiceWithType(String type) {
        for(int i = 0; i < serviceCounter; i++){
            if(services.get(i).getServiceType().get().equals(type))
                return true;
        }
        return false;
    }

    @Override
    public Service getTheNearestService(long lat, long lon, String type) {
        long position;
        long nearPos = Long.MAX_VALUE;
        List<Service> nearestServices = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 0 ou se temos que mudar
        List<Service> services = getServiceByType(type);
        for (int i = 0; i < serviceCounter; i++) {
            Service currentService = services.get(i);
                long lat2 = services.get(i).getLatitude();
                long lon2 = services.get(i).getLongitude();
                position = Math.abs(lat-lat2) + Math.abs(lon-lon2);

                if (position < nearPos) {
                    // Encontrou um novo menor preço, limpa a lista e adiciona este
                    nearPos = position;
                    //Para dar clear Na array ? posso
                    for (int j = 0; j < nearestServices.size(); j++)
                        nearestServices.removeFirst();
                    nearestServices.addLast(currentService);
                } else if (position == nearPos) {
                    // Mesmo preço mínimo, adiciona também
                    nearestServices.addLast(currentService);
                }
        }
        return nearestServices.get(0); // ir buscar o primeiro
    }

    @Override
    public Service getTheCheapestServiceThrifty(long lat, long lon, String type) {
        int minPrice = Integer.MAX_VALUE;
        List<Service> cheapestServices = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 0 ou se temos que mudar

        for (int i = 0; i < serviceCounter; i++) {
            Service currentService = services.get(i);

            if (type.equals(currentService.getServiceType().get())) {
                int servicePrice = currentService.getServicePrice();
                if (servicePrice < minPrice) {
                    // If it finds a new smaller Price, clears the array and starts a new one
                    minPrice = servicePrice;
                    for (int j = 0; j < cheapestServices.size(); j++)
                        cheapestServices.removeFirst();
                    cheapestServices.addLast(currentService);
                } else if (servicePrice == minPrice) {
                    // If they have the same minPrice, it adds to an Array
                    cheapestServices.addLast(currentService);
                }
            }
        }
        return cheapestServices.get(0);
    }

    @Override
    public Service getElement(String serviceName) throws ServiceDoesntExistException {
        int serviceIndex = searchServiceNameIndex(serviceName);
        if (serviceIndex < 0) {
            throw new ServiceDoesntExistException();
        }
        return services.get(serviceIndex);
    }


    @Override
    public Iterator<Service> allServiceIterator() {
        return new ServiceIterator(services,serviceCounter);
    }

    @Override
    public Iterator<Service> allServiceIteratorSortedRating() {
        return new ServiceIterator(sortByRating(),serviceCounter);
    }

    @Override
    public Iterator<Service> serviceIteratorByType(String type, int rating, long lat, long lon) {
        // Sort by last updated order (ascending)
        List<Service> sorted = sortServices(false);
        Iterator<Service> s = new ServiceTypeIterator(sorted, serviceCounter, type, rating);
        List<Service> servicesToCheck = new ListInArray<>(DEFAULT_DIMENTION);
        while (s.hasNext()) {
            servicesToCheck.addLast(s.next());
        }
        List<Service> nearestServices = new ListInArray<>(DEFAULT_DIMENTION);
        long position;
        long nearPos = Long.MAX_VALUE;
        //TODO em vez de de neares meter o tocheck
        for (int i = 0; i < servicesToCheck.size(); i++) {
            Service currentService = nearestServices.get(i);
            long lat2 = servicesToCheck.get(i).getLatitude();
            long lon2 = servicesToCheck.get(i).getLongitude();
            position = Math.abs(lat - lat2) + Math.abs(lon - lon2);

            if (position < nearPos) {
                nearPos = position;
                //Para dar clear Na array ? posso
                for (int j = 0; j < nearestServices.size(); j++)
                    nearestServices.removeFirst();
                nearestServices.addLast(currentService);
            } else if (position == nearPos) {
                // Mesmo preço mínimo, adiciona também
                nearestServices.addLast(currentService);
            }
        }
        return nearestServices.iterator();
    }

    @Override
    public List<Service> sortByRating() {
        // Sort by rating (descending)
        return sortServices(true);
    }



    public Iterator<Student> allStudentIterator(){
        return null;
    }

    private int compareTo(Service other, Service thisService){
        // (descending)
        if(other.getAverageStars() == 0 || thisService.getAverageStars() == 0) return 1;
        if (thisService.getAverageStars() < other.getAverageStars()) return 1;
        if (thisService.getAverageStars() > other.getAverageStars()) return -1;
        // (descending: newer first)
        return  thisService.getLastUpdatedOrder() - other.getLastUpdatedOrder();
    }

    private List<Service> sortServices(boolean sortByRating) {
        int length = services.size();
        List<Service> temp = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 0 ou se temos que mudar
        // Copy to a TEMP array
        for (int i = 0; i < length; i++)
            temp.addLast(services.get(i));
        //  sort the Array<Service>
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                boolean shouldSwap;
                if (sortByRating)
                    shouldSwap = compareTo(temp.get(j+1), temp.get(j)) > 0;
                else
                    shouldSwap = temp.get(j).getLastUpdatedOrder() - temp.get(j+1).getLastUpdatedOrder() > 0;

                if (shouldSwap) {
                    // Swap elements in the Array<Service>
                    Service swap = temp.get(j);
                    temp.remove(j);                 //acho que assim fica bem
                    temp.add(j+1, swap);
                    //temp.set(temp.get(j+1),j);        era assim que funcionava. Vou meter em cima como acho que deve funcionar agora com listInArray
                    //temp.set(swap,j+1);
                }
            }
        }
        return temp; // Return the sorted array directly
    }

    public Iterator<String> getDescriptions(){
        List<String> temp = new ListInArray<>(DEFAULT_DIMENTION);
        for (int i = 0; i < serviceCounter; i++){
            Iterator<String> it = services.get(i).getDescriptions();
            while (it.hasNext()){
                temp.addLast(it.next());
            }
        }
        return temp.iterator();
    }

    public Iterator<Service> getServicesWithTag(String tag){
        List<Service> temp = new ListInArray<>(DEFAULT_DIMENTION);
        for (int i = 0; i < services.size(); i++){
            Iterator<String> tags = services.get(i).getDescriptions();
            while (tags.hasNext()){
                if (tags.next().equalsIgnoreCase(tag)){
                    temp.addLast(services.get(i));
                }
            }
        }
        return temp.iterator();
    }


    private int searchServiceNameIndex(String serviceName){
        for(int i = 0; i < serviceCounter; i++)
            if(serviceName.equalsIgnoreCase(services.get(i).getName()))
                return i;
        return -1;
    }

    private void storeAndSortByType(Service elem){
        switch (elem.getServiceType().get()) {
            case "eating" -> servicesEating.addLast(elem);
            case "lodging" -> servicesLodging.addLast(elem);
            case "leisure" -> servicesLeisure.addLast(elem);
        }
    }

    private List<Service> getServiceByType(String type){
        return switch (type) {
            case "eating" -> servicesEating;
            case "lodging" -> servicesLodging;
            case "leisure" -> servicesLeisure;
            default -> null;
        };
    }

}
