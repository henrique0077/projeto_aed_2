package HomeawayApp;


import java.io.Serializable;

public interface Bound extends Serializable {


    /**
     * Getter method to get the name of certain bound
     *
     * @pre bound != null
     * @return The name of certain bound
     */
    String getName();

    /**
     * Getter method to get the value of the top left latitude of certain bound
     *
     * @pre bound != null
     * @return The value of the top left latitude of certain bound
     */
    long getTopLat();

    /**
     * Getter method to get the value of the top left longitude of certain bound
     *
     * @pre bound != null
     * @return The value of the top left longitude of certain bound
     */
    long getLeftLon();


    /**
     * Getter method to get the value of the bottom right latitude of certain bound
     *
     * @pre bound != null
     * @return The value of the bottom right latitude of certain bound
     */
    long getBottomLat();

    /**
     * Getter method to get the value of the bottom right longitude of certain bound
     *
     * @pre bound != null
     * @return The value of the bottom right longitude of certain bound
     */
    long getRightLon();


}
