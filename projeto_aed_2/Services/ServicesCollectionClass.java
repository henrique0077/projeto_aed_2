/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.*;
import Students.Student;
import Students.StudentCollection;
import dataStructures.*;
import dataStructures.FilterIterator;
import exceptions.InvalidPositionException;
import exceptions.ServiceDoesntExistException;

import java.io.Serializable;
import java.util.HashMap;

//import java.util.ArrayList;

public class ServicesCollectionClass implements ServicesCollection, Serializable {

    private final Map<String, Service> services;
    private final Map<String, Service> servicesEating;
    private final Map<String, Service> servicesLodging;
    private final Map<String, Service> servicesLeisure;
    private final Map<Integer,Map<String,Service>> servicesByRating;
    private int serviceCounter;
    private final int DEFAULT_DIMENTION = 10;

    public ServicesCollectionClass(){
        services = new SepChainHashTable<>();
        servicesEating = new SepChainHashTable<>();
        servicesLodging = new SepChainHashTable<>();
        servicesLeisure = new SepChainHashTable<>();
        servicesByRating = new SepChainHashTable<>(5);
        serviceCounter = 0;
    }

    @Override
    public boolean isEmpty() {
        return serviceCounter == 0;
    }

    @Override
    public boolean hasElem(String serviceName) {
        return services.get(serviceName) != null;
    }

    @Override
    public void addService(Service elem) {
        services.put(elem.getName(), elem);
        storeAndSortByType(elem); // Para organizar certos serviços por tipo
        servicesByRating.get(3).put(elem.getName(),elem);// 3 = 4-1 porque o mapa começa na posição 0 e os serviços quando inicializados têm 4 de rating
        serviceCounter++;
    }

    @Override
    public boolean isThereServicesWithCertainRate(String type, int stars){
        boolean result = false;
        Iterator<Service> service = servicesByRating.get(stars-1).values();
        while(service.hasNext() && !result){                               //acho que isto que fiz agora está melhor que o que está comocomentário
            Service s = service.next();
            if(s.getServiceType().toString().equals(type))
                result = true;
        }
//        if (servicesByRating.get(stars-1) != null) {        //stars-1 porque temos 5 espaços para 5 estrelas, mas começa no 0 logo só vai até 4
//            result = isThereAnyServiceWithType(type);
//        }
        return result;  //criar um sepchainhastable com 5 espaços, um para cada rating e em cada espaço fazer outro sep... para ser fácil de acrescentar e remover serviços do rating, ficando com ordem de insereção
    }                   //isto já está feito lê só os comentários \_ ^_^ _/

    @Override
    public boolean isThereAnyServiceWithType(String type) {
        boolean answer = false;
        switch (type){
            case "LODGING" -> answer = !servicesLodging.isEmpty();
            case "EATING" -> answer = !servicesEating.isEmpty();
            case "LEISURE" -> answer = !servicesLeisure.isEmpty();
        }
        return answer;
    }

    @Override
    public Service getTheNearestService(long lat, long lon, String type) {
        long position;
        long nearPos = Long.MAX_VALUE;
        List<Service> nearestServices = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 0 ou se temos que mudar
        Map<String,Service> services = getServiceByType(type);
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
        List<Service> cheapestServices = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 10 ou se temos que mudar
        Iterator<Service> it = services.values();

        while (it.hasNext()) {
            Service next = it.next();
            int servicePrice = next.getServicePrice();

            if (next.getServiceType().toString().equals(type)) {        //ver se podemos usar o toString no projeto por causa das novas regras
                if (servicePrice < minPrice) {
                    // If it finds a new smaller Price, clears the array and starts a new one
                    minPrice = servicePrice;
                    if (!cheapestServices.isEmpty())
                        cheapestServices.removeFirst();
                    cheapestServices.addLast(next);
                }
//                else if (servicePrice == minPrice) {        //no fim vai ser passado o último, logo não é necessário estarmos a adicionar o segundo, apenas atualizar o mais alto
//                    // If they have the same minPrice, it adds to an Array
//                    cheapestServices.addLast(next);
//                }
            }
        }
        return cheapestServices.getFirst();
    }

    @Override
    public Service getElement(String serviceName) throws ServiceDoesntExistException {
        return services.get(serviceName);
    }


    @Override
    public Iterator<Service> allServiceIterator() {
        return services.values();
    }

    @Override
    public Iterator<Service> allServiceIteratorSortedRating() {
        List<Service> allServicesByRating = new ListInArray<>(DEFAULT_DIMENTION);
        for (int i = 0; i < 5; i++){
            Iterator<Service> it = servicesByRating.get(i).values();
            while (it.hasNext())
                allServicesByRating.addLast(it.next());     //o addLast já tem o ensureCapacity, logo não nos temos que preocupar com o resize
        }
        return allServicesByRating.iterator();
    }

    /**
     * Falta esta merda
     * @param type The type of the service
     * @param rating The rating of the service
     * @param lat
     * @param lon
     * @return
     */
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

//    private int compareTo(Service other, Service thisService){
//        // (descending)
//        if(other.getAverageStars() == 0 || thisService.getAverageStars() == 0) return 1;
//        if (thisService.getAverageStars() < other.getAverageStars()) return 1;
//        if (thisService.getAverageStars() > other.getAverageStars()) return -1;
//        // (descending: newer first)
//        return  thisService.getLastUpdatedOrder() - other.getLastUpdatedOrder();
//    }

    /**
     * Acho que este método não faz falta, pois com o mapa com 5 posições já temos os ratings ordenados. Logo este aqui em cima tamém de ve poder desaparecer, pois este é o único método que o chama
     * @return
     */
//    private List<Service> sortServices(boolean sortByRating) {
//        int length = services.size();
//        List<Service> temp = new ListInArray<>(DEFAULT_DIMENTION); //ver se a dimenção pode ser 0 ou se temos que mudar
//        // Copy to a TEMP array
//        for (int i = 0; i < length; i++)
//            temp.addLast(services.get(i));
//        //  sort the Array<Service>
//        for (int i = 0; i < length - 1; i++) {
//            for (int j = 0; j < length - i - 1; j++) {
//                boolean shouldSwap;
//                if (sortByRating)
//                    shouldSwap = compareTo(temp.get(j+1), temp.get(j)) > 0;
//                else
//                    shouldSwap = temp.get(j).getLastUpdatedOrder() - temp.get(j+1).getLastUpdatedOrder() > 0;
//
//                if (shouldSwap) {
//                    // Swap elements in the Array<Service>
//                    Service swap = temp.get(j);
//                    temp.remove(j);                 //acho que assim fica bem
//                    temp.add(j+1, swap);
//                    //temp.set(temp.get(j+1),j);        era assim que funcionava. Vou meter em cima como acho que deve funcionar agora com listInArray
//                    //temp.set(swap,j+1);
//                }
//            }
//        }
//        return temp; // Return the sorted array directly
//    }

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

    private void storeAndSortByType(Service elem){
        switch (elem.getServiceType().get()) {
            case "eating" -> servicesEating.put(elem.getName(), elem);
            case "lodging" -> servicesLodging.put(elem.getName(), elem);
            case "leisure" -> servicesLeisure.put(elem.getName(), elem);
        }
    }

    private Map<String,Service> getServiceByType(String type){
        return switch (type) {
            case "eating" -> servicesEating;
            case "lodging" -> servicesLodging;
            case "leisure" -> servicesLeisure;
            default -> null;
        };
    }

}
