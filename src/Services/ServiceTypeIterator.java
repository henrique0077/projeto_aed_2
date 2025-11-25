/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import dataStructures.List;
import dataStructures.Iterator;

import java.io.Serializable;

public class ServiceTypeIterator implements Iterator<Service>, Serializable { //ver se não trocamos por um arrayIterator


    private final List<Service> services;
    private final int counter;
    private final String type;
    private final int rating;
    private int current;


    public ServiceTypeIterator(List<Service> services, int counter, String type, int rating) {
        this.services = services;
        this.counter = counter;
        this.type = type;
        this.rating = rating;
        searchNext();
    }


    @Override
    public boolean hasNext() {
        return current < counter;
    }

    @Override
    public Service next() {
        Service result = services.get(current++);
        searchNext();
        return result;
    }

    @Override
    public void rewind() {
        current = 0;
        searchNext();
    }

    private boolean isTheRightKind(Service service){
        return service.getServiceType().get().equals(this.type) && service.getAverageStars() == rating;
    }

    private void searchNext(){
        while(current < counter && !isTheRightKind(services.get(current)))
            current++;
    }


}
