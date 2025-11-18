/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import dataStructures.List;
import dataStructures.Iterator;

import java.io.Serializable;

public class ServiceIterator implements Iterator<Service>, Serializable { //ver se não trocamos por um arrayIterator

    private final List<Service> services;
    private final int counter;
    private int nextService;

    public ServiceIterator(List<Service> services, int counter) {
        this.services = services;
        this.counter = counter;
    }

    @Override
    public boolean hasNext() {
        return nextService < counter;
    }

    @Override
    public Service next() {
        return services.get(nextService++);
    }

    @Override
    public void rewind() {
        nextService = 0;
    }

}
