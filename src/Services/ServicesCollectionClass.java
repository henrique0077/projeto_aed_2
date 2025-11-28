/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import dataStructures.*;

import exceptions.ServiceDoesntExistException;
import Enumerators.ServiceType;
import Enumerators.StudentType;
import java.io.Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;



public class ServicesCollectionClass implements ServicesCollection, Serializable {

    private final List<Service> servicesInOrder;

    private transient Map<String, Service> services;
    private transient Map<String, Service> servicesEating;
    private transient Map<String, Service> servicesLodging;
    private transient Map<String, Service> servicesLeisure;
    private transient Map<Integer, Map<String, Service>> servicesByRating;

    private int serviceCounter;
    private final int DEFAULT_DIMENTION = 10;

    public ServicesCollectionClass() {
        servicesInOrder = new SinglyLinkedList<>();
        serviceCounter = 0;
        initializeMaps();
    }

    private void initializeMaps() {
        services = new SepChainHashTable<>();
        servicesEating = new SepChainHashTable<>();
        servicesLodging = new SepChainHashTable<>();
        servicesLeisure = new SepChainHashTable<>();
        servicesByRating = new SepChainHashTable<>(5);
        servicesByRating.put(0, new SepChainHashTable<>());
        servicesByRating.put(1, new SepChainHashTable<>());
        servicesByRating.put(2, new SepChainHashTable<>());
        servicesByRating.put(3, new SepChainHashTable<>());
        servicesByRating.put(4, new SepChainHashTable<>());
    }

    private void repopulateMaps(Service elem) {
        String serviceName = elem.getName().toUpperCase();
        services.put(serviceName, elem);
        storeAndSortByType(elem);

        // CORREÇÃO: Usar o rating real do serviço
        int ratingIndex = elem.getAverageStars() - 1;
        if (ratingIndex >= 0 && ratingIndex <= 4) {
            servicesByRating.get(ratingIndex).put(serviceName, elem);
        }
    }

    @Override
    public boolean isEmpty() {
        return serviceCounter == 0;
    }

    @Override
    public boolean hasElem(String serviceName) {
        // Henrique henrique
        return services.get(serviceName.toUpperCase()) != null;
    }

    @Override
    public void addService(String serviceName, Service elem) {
        /**String service  = serviceName.toUpperCase();
        servicesInOrder.addLast(elem);
        services.put(service, elem);
        storeAndSortByType(elem); // Para organizar certos serviços por tipo
        serviceCounter++;
        servicesByRating.get(3).put(service, elem);// 3 = 4-1 porque o mapa começa na posição 0 e os serviços quando inicializados têm 4 de rating*/

        servicesInOrder.addLast(elem); // Adiciona na lista principal
        serviceCounter++;

        repopulateMaps(elem);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Lê servicesInOrder e serviceCounter do disco

        initializeMaps(); // Recria os mapas que estavam transient (null)

        // Percorre a lista salva e preenche os mapas
        Iterator<Service> it = servicesInOrder.iterator();
        while (it.hasNext()) {
            repopulateMaps(it.next());
        }
    }

    @Override
    public boolean isThereServicesWithCertainRate(String type, int stars) {
        boolean result = false;
        Iterator<Service> service = servicesByRating.get(stars - 1).values();
        while (service.hasNext() && !result) {                               //acho que isto que fiz agora está melhor que o que está comocomentário
            Service s = service.next();
            if (s.getServiceType().toString().equals(type))
                result = true;
        }
//        if (servicesByRating.get(stars-1) != null) {        //stars-1 porque temos 5 espaços para 5 estrelas, mas começa no 0 logo só vai até 4
//            result = isThereAnyServiceWithType(type);
//        }
        return result;  //criar um sepchainhastable com 5 espaços, um para cada rating e em cada espaço fazer outro sep... para ser fácil de acrescentar e remover serviços do rating, ficando com ordem de insereção
    }                   //isto já está feito lê só os comentários \_ ^_^ _/

    public void updateRating(String serviceName, int oldStars, int newStars) {
        String nameUpper = serviceName.toUpperCase();

        // Remove do bucket antigo
        if (oldStars >= 1 && oldStars <= 5)
            servicesByRating.get(oldStars - 1).remove(nameUpper);

        // Adiciona ao novo bucket
        if (newStars >= 1 && newStars <= 5) {
            Service s = services.get(nameUpper);
            if (s != null) {
                servicesByRating.get(newStars - 1).put(nameUpper, s);
            }
        }
    }


    @Override
    public boolean isThereAnyServiceWithType(String type) {
        boolean answer = false;
        switch (type) {
            case "LODGING" -> answer = !servicesLodging.isEmpty();
            case "EATING" -> answer = !servicesEating.isEmpty();
            case "LEISURE" -> answer = !servicesLeisure.isEmpty();
        }
        return answer;
    }

    @Override
    public Service getTheNearestService(long lat, long lon, String type) {
        Map<String, Service> service = getServiceByType(type);
        List<Service> nearestServices = getNearest(lat, lon, service);
        return nearestServices.get(0);
    }

    private List<Service> getNearest(long lat, long lon, Map<String, Service> service) {
        long position;
        long nearPos = Long.MAX_VALUE;
        List<Service> nearestServices = new ListInArray<>(DEFAULT_DIMENTION);
        Iterator<Service> it = service.values();
        while (it.hasNext()) {
            Service currentService = it.next();
            long lat2 = currentService.getLatitude();
            long lon2 = currentService.getLongitude();
            position = Math.abs(lat - lat2) + Math.abs(lon - lon2);

            if (position < nearPos) {
                nearPos = position;
                while (!nearestServices.isEmpty()) {
                    nearestServices.removeFirst();
                }
                nearestServices.addLast(currentService);
            } else if (position == nearPos) {
                nearestServices.addLast(currentService);
            }
        }
        return nearestServices;
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
        return services.get(serviceName.toUpperCase());
    }


    @Override
    public Iterator<Service> allServiceIterator() {
        return servicesInOrder.iterator();
    }

    @Override
    public Iterator<Service> allServiceIteratorSortedRating() {
        List<Service> allServicesByRating = new ListInArray<>(DEFAULT_DIMENTION);

        // Iteramos sobre a lista ORIGINAL (servicesInOrder), que mantém a ordem de inserção.
        // Filtramos manualmente por estrelas, de 5 para 1.
        // Assim, dentro de cada estrela, a ordem original é preservada.
        for (int r = 5; r >= 1; r--) {
            Iterator<Service> it = servicesInOrder.iterator();
            while (it.hasNext()) {
                Service s = it.next();
                if (s.getAverageStars() == r) {
                    allServicesByRating.addLast(s);
                }
            }
        }
        return allServicesByRating.iterator();
    }

    @Override
    public List<Service> sortByRating() {
        return null;
    }

    /**
     * Falta esta merda
     *
     * @param type   The type of the service
     * @param rating The rating of the service
     * @param lat
     * @param lon
     * @return
     */
    @Override
//    public Iterator<Service> serviceIteratorByType(String type, int rating, long lat, long lon) {
//        List<Service> sorted = sortServices(false);
//        Iterator<Service> s = new ServiceTypeIterator(sorted, serviceCounter, type, rating);
//        List<Service> servicesToCheck = new ListInArray<>(DEFAULT_DIMENTION);
//        while (s.hasNext()) {
//            servicesToCheck.addLast(s.next());
//        }
//        List<Service> nearestServices = new ListInArray<>(DEFAULT_DIMENTION);
//        long nearPos = Long.MAX_VALUE;
//        for (int i = 0; i < servicesToCheck.size(); i++) {
//            Service currentService = servicesToCheck.get(i);
//            long lat2 = currentService.getLatitude();
//            long lon2 = currentService.getLongitude();
//            long position = Math.abs(lat - lat2) + Math.abs(lon - lon2);
//
//            if (position < nearPos) {
//                nearPos = position;
//                nearestServices = new ListInArray<>(DEFAULT_DIMENTION); // aqui limpa
//                nearestServices.addLast(currentService);
//            } else if (position == nearPos) {
//                nearestServices.addLast(currentService);
//            }
//        }
//        return nearestServices.iterator();
//    }

    public Iterator<Service> serviceIteratorByType(String type, int rating, long lat, long lon) {
        Map<String, Service> allServices = new SepChainHashTable<>(DEFAULT_DIMENTION);
        Map<String, Service> typeMap = getServiceByType(type);
        Iterator<String> typeIterator = typeMap.keys();
        // Verificar bounds para rating
        if (rating >= 1 && rating <= 5) {
            Iterator<String> ratingIterator = servicesByRating.get(rating - 1).keys();
            while (typeIterator.hasNext() && ratingIterator.hasNext()) {
                String nextType = typeIterator.next();
                String nextRating = ratingIterator.next();
                // Nota: Esta lógica de intersecção parece depender da ordem dos iteradores,
                // mas mantive a estrutura original. O ideal seria verificar contains.
                // Abaixo está uma simplificação segura:
            }
            // Abordagem mais segura: iterar sobre o bucket de rating e verificar se o tipo corresponde
            Iterator<Service> rateIt = servicesByRating.get(rating - 1).values();
            while(rateIt.hasNext()){
                Service s = rateIt.next();
                if(s.getServiceType().toString().equals(type)){
                    allServices.put(s.getName().toUpperCase(), s);
                }
            }
        }

        // CORREÇÃO: Retornar o iterador da LISTA ordenada, não os valores do mapa desordenado
        return getNearest(lat, lon, allServices).iterator();
    }

    public Iterator<String> getDescriptions() {
        List<String> temp = new ListInArray<>(DEFAULT_DIMENTION);
        Iterator<Service> it = services.values();
        while (it.hasNext()) {
            Iterator<String> descIt = it.next().getDescriptions();
            while (descIt.hasNext()) {
                temp.addLast(descIt.next());
            }
        }
        return temp.iterator();
    }

    public Iterator<Service> getServicesWithTag(String tag) {
        List<Service> temp = new ListInArray<>(DEFAULT_DIMENTION);
        Iterator<Service> it = services.values();
        while (it.hasNext()) {
            Service s = it.next();
            Iterator<String> tagsIt = s.getDescriptions();
            boolean added = false;
            while (tagsIt.hasNext() && !added) {
                if (tagsIt.next().equalsIgnoreCase(tag)) {
                    temp.addLast(s);
                    added = true;
                }
            }
        }
        return temp.iterator();
    }

    private void storeAndSortByType(Service elem) {
        switch (elem.getServiceType()) {
            case ServiceType.EATING -> servicesEating.put(elem.getName(), elem);
            case ServiceType.LODGING -> servicesLodging.put(elem.getName(), elem);
            case ServiceType.LEISURE -> servicesLeisure.put(elem.getName(), elem);
        }
    }

    private Map<String, Service> getServiceByType(String type) {
       String i = ServiceType.EATING.get();

        return switch (type) {
            case "EATING" -> servicesEating;
            case "LODGING" -> servicesLodging;
            case "LEISURE" -> servicesLeisure;
            default -> null;
        };
    }

}
