/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.ServiceType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LeisureServiceClass extends AbstractService implements Serializable {

    private final int discount;
    // private final int ticketPrice;
    public LeisureServiceClass(String name, long lat, long lon, int ticketPrice, int discount, int updateCounter){
        super(name,lat,lon, ticketPrice, updateCounter);
        this.discount = discount;
       // this.ticketPrice = ticketPrice; //TODO NÃO SEI SE ISTO FICA ASSIM TAS A GUARDAR EM 2 SITIOS
    }
    @Override
    public ServiceType getServiceType() { //!!! Podemos Fazer ISTO !!!
        return ServiceType.LEISURE;
    }

    @Override
    public int getServicePrice(){
        int ticketPrice = super.getServicePrice();
        return ticketPrice - (( ticketPrice* discount) / 100); //tudo acho que é assim pus um get price
    }

}
