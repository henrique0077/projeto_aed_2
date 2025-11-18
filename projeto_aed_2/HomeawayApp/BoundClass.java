package HomeawayApp;

import Services.ServicesCollection;
import Services.ServicesCollectionClass;
import Students.StudentCollection;
import Students.StudentCollectionClass;

import java.io.Serializable;

public class BoundClass implements Bound, Serializable {

    private static final long serialVersionUID = 1L;

    private String areaName;
    private long topLat;
    private long leftLon;
    private long bottomLat;
    private long rightLon;

    public BoundClass(String areaName, long topLat, long leftLon, long bottomLat, long rightLon) {


        this.areaName = areaName;
        this.topLat = topLat;
        this.leftLon = leftLon;
        this.bottomLat = bottomLat;
        this.rightLon = rightLon;

    }

    public String getName() {
        return areaName;
    }

    @Override
    public long getTopLat() {
        return topLat;
    }

    @Override
    public long getLeftLon() {
        return leftLon;
    }

    @Override
    public long getBottomLat() {
        return bottomLat;
    }

    @Override
    public long getRightLon() {
        return rightLon;
    }

}
