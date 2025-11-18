/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

public interface ThriftyStudent {

    /**
     * Updates the cheapest meal
     * @param price the price of the cheapest meal
     */
    void updateCheapestMeal(int price);

    /**
     * Gets the cheapest meal
     * @return the cheapest meal
     */
    int getCheapestMealPrice();

    /**
     * Updates the cheapest room
     * @param price the price of the cheapest room
     */
    void updateCheapestRoom(int price);

    /**
     * Gets the cheapest room
     * @return the cheapest room
     */
    int getCheapestRoomPrice();
}
