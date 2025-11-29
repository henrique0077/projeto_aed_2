/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import dataStructures.*;

import exceptions.ServiceDoesntExistException;
import Enumerators.ServiceType;
import java.io.Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServicesCollectionClass implements ServicesCollection, Serializable {

    private final List<Service> servicesInOrder;

    private transient Map<String, Service> services;
    private transient Map<String, Service> servicesEating;
    private transient Map<String, Service> servicesLodging;
    private transient Map<String, Service> servicesLeisure;
    // Map de Inteiro -> Lista para preservar a ordem de chegada (FIFO) no Ranking
    private transient Map<Integer, List<Service>> servicesByRating;

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
        // Inicializa listas de ranking
        servicesByRating = new SepChainHashTable<>(5);
        for (int i = 0; i < 5; i++) {
            servicesByRating.put(i, new DoublyLinkedList<>());
        }
    }

    private void repopulateMaps(Service elem) {
        String serviceName = elem.getName().toUpperCase();
        services.put(serviceName, elem);
        storeAndSortByType(elem);

        // Adiciona à lista de ranking correta
        int ratingIndex = elem.getAverageStars() - 1;
        if (ratingIndex >= 0 && ratingIndex <= 4) {
            servicesByRating.get(ratingIndex).addLast(elem);
        }
    }

    @Override
    public boolean isEmpty() {
        return serviceCounter == 0;
    }

    @Override
    public boolean hasElem(String serviceName) {
        return services.get(serviceName.toUpperCase()) != null;
    }

    @Override
    public void addService(String serviceName, Service elem) {
        servicesInOrder.addLast(elem); // Preserva ordem de inserção global
        serviceCounter++;
        repopulateMaps(elem);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initializeMaps();
        Iterator<Service> it = servicesInOrder.iterator();
        while (it.hasNext()) {
            repopulateMaps(it.next());
        }
    }

    @Override
    public boolean isThereServicesWithCertainRate(String type, int stars) {
        boolean result = false;
        Iterator<Service> service = servicesByRating.get(stars - 1).iterator();
        while (service.hasNext() && !result) {
            Service s = service.next();
            if (s.getServiceType().toString().equals(type))
                result = true;
        }
        return result;
    }

    public void updateRating(String serviceName, int oldStars, int newStars) {
        String nameUpper = serviceName.toUpperCase();
        Service s = services.get(nameUpper);

        if (s == null) return;

        // Remove do bucket antigo
        if (oldStars >= 1 && oldStars <= 5) {
            List<Service> list = servicesByRating.get(oldStars - 1);
            int pos = list.indexOf(s);
            if (pos != -1) {
                list.remove(pos);
            }
        }

        // Adiciona ao novo bucket (fim da lista -> FIFO)
        if (newStars >= 1 && newStars <= 5) {
            servicesByRating.get(newStars - 1).addLast(s);
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
        List<Service> cheapestServices = new ListInArray<>(DEFAULT_DIMENTION);
        Iterator<Service> it = services.values();

        while (it.hasNext()) {
            Service next = it.next();
            int servicePrice = next.getServicePrice();

            if (next.getServiceType().toString().equals(type)) {
                if (servicePrice < minPrice) {
                    minPrice = servicePrice;
                    if (!cheapestServices.isEmpty())
                        cheapestServices.removeFirst();
                    cheapestServices.addLast(next);
                }
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
        // Itera de 5 estrelas até 1 estrela
        for (int i = 4; i >= 0; i--) {
            Iterator<Service> it = servicesByRating.get(i).iterator();
            while (it.hasNext())
                allServicesByRating.addLast(it.next());
        }
        return allServicesByRating.iterator();
    }

    @Override
    public List<Service> sortByRating() {
        return null;
    }

    @Override
    public Iterator<Service> serviceIteratorByType(String type, int rating, long lat, long lon) {
        Map<String, Service> allServices = new SepChainHashTable<>(DEFAULT_DIMENTION);

        if (rating >= 1 && rating <= 5) {
            Iterator<Service> rateIt = servicesByRating.get(rating - 1).iterator();
            while(rateIt.hasNext()){
                Service s = rateIt.next();
                if(s.getServiceType().toString().equals(type)){
                    allServices.put(s.getName().toUpperCase(), s);
                }
            }
        }
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
        Iterator<Service> it = servicesInOrder.iterator();
        String pattern = tag.toLowerCase();

        while (it.hasNext()) {
            Service s = it.next();
            Iterator<String> tagsIt = s.getDescriptions();
            boolean added = false; // Evita duplicados do mesmo serviço

            while (tagsIt.hasNext() && !added) {
                String description = tagsIt.next();
                String text = description.toLowerCase();
                int searchStart = 0;

                while (searchStart < text.length() && !added) {
                    int idx = StringMatching.boyerMoore(text.substring(searchStart), pattern);

                    if (idx != -1) {
                        int trueIdx = searchStart + idx;

                        // CORREÇÃO: !isLetterOrDigit permite pontuação (.,;) como separador
                        char charBefore = (trueIdx == 0) ? ' ' : text.charAt(trueIdx - 1);
                        boolean startOk = !Character.isLetterOrDigit(charBefore);

                        char charAfter = (trueIdx + pattern.length() == text.length()) ? ' ' : text.charAt(trueIdx + pattern.length());
                        boolean endOk = !Character.isLetterOrDigit(charAfter);

                        if (startOk && endOk) {
                            temp.addLast(s);
                            added = true;
                        } else {
                            searchStart = trueIdx + 1;
                        }
                    } else {
                        searchStart = text.length();
                    }
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
        return switch (type) {
            case "EATING" -> servicesEating;
            case "LODGING" -> servicesLodging;
            case "LEISURE" -> servicesLeisure;
            default -> null;
        };
    }
}