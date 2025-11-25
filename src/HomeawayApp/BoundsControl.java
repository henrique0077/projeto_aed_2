package HomeawayApp;

import dataStructures.ListInArray;
import exceptions.BoundNameDoesntExistException;
import exceptions.LocationOutsideTheBoundException;
import exceptions.SameAreaNameException;

import java.io.*;

public interface BoundsControl extends Serializable{



    /**
     * Checks if the bound name already exists
     *
     * @param areaName The name of the bound to check
     * @return True if it already exists. False otherwise
     */
    boolean checkBoundsNameIsValid(String areaName);

    /**
     * Checks if the bound name already exists
     * @param name The name of the bound to check
     * @return True if it already exists. False otherwise
     */
   boolean boundExists(String name);

    boolean checkBoundsIsValid(long topLat, long leftLon, long bottomLat, long rightLon);

   boolean hasCurrentBounds();

   String getCurrentBoundName();

    String getSavedArea();

    String getBoundName();
}
