package HomeawayApp;

import dataStructures.ListInArray;
import exceptions.*;

import java.io.*;

public class BoundsControlClass implements BoundsControl {
    private static final long serialVersionUID = 1L;
    private Bound currentBound;
    private ListInArray<String> boundNames; //todo não sei se vamos fazer assim


    public BoundsControlClass() {
        this.currentBound = null;

    }

//    public void createBounds(String areaName, long topLat, long leftLon, long bottomLat, long rightLon) throws LocationOutsideTheBoundException, SameAreaNameException {
//        if (!checkBoundsNameIsValid(areaName)) throw new SameAreaNameException();
//        if (!checkBoundsIsValid(topLat, leftLon, bottomLat, rightLon)) throw new LocationOutsideTheBoundException();
//        if (hasCurrentBounds()) {
//            saveCurrentBounds();
//           // setNewBounds(new BoundClass(areaName, topLat, bottomLat, bottomLat, rightLon));
//            //TODO falta sendo que se tem o array com os nomes por lá o nome caso ainda não esteja
//            currentBound = new BoundClass(areaName, topLat, leftLon, bottomLat, rightLon);
//        }
//    }
//
//    public void saveCurrentBounds() throws SystemBoundsNotDefinedException {
//        if (currentBound == null) return; //é o mesmo que está aqui em baixo.
//        if (!hasCurrentBounds()) throw new SystemBoundsNotDefinedException();
//
//        String fileName = currentBound.getName().toLowerCase().replace(" ", "_") + ".ser";
//
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
//            oos.writeObject(currentBound);
//            oos.flush();
//            oos.close();
//        } catch (IOException e) {
//            System.out.println("Erro da escrita.");
//        }
//    }
//
//    public Bound loadBounds(String areaName) throws BoundNameDoesntExistException { //não devia ser void? Apenas mudamos a current bound, não precisamos de dar return a nada
//        if (!boundExists(areaName)) throw new BoundNameDoesntExistException();
//        Bound bound = null;
//        String fileName = areaName.toLowerCase().replace(" ", "_") + ".ser";
//
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
//            bound = (Bound) ois.readObject();
//            ois.close();
//        } catch (IOException e) {
//            throw new FileDoesNotExistsException();
//        } finally {
//            return bound;
//        }
//    }

    /**
     * Checks if the bound name already exists
     *
     * @param areaName The name of the bound to check
     * @return True if it already exists. False otherwise
     */
    public boolean checkBoundsNameIsValid(String areaName) {
        for (int i = 0; i < boundNames.size(); i++) {       //todo ou isto ou o de ver nos ficheiros (mais certo)
            if (boundNames.get(i).equals(areaName)) {
                return false;
            }
        }
        return true;
    }


//    //TODO supostamente verifica se não há nenhum ficheiro com o nome da area
//    private static boolean checkSameAreaName(String areaName) {
//        String fileName = areaName.replace(" ", "_") + ".ser";
//        File file = new File(fileName);
//        return file.exists();
//    }

    @Override
    public boolean boundExists(String name) {
        return false;
    }

    public boolean checkBoundsIsValid(long topLat, long leftLon, long bottomLat, long rightLon) {
        return (topLat > bottomLat && leftLon < rightLon);
    }

    @Override
    public boolean hasCurrentBounds() {
        return false;
    }

    /**
     * public boolean hasCurrentBounds() {
     * return currentBound != null;
     * }
     */

    public String getCurrentBoundName(){
        return currentBound.getName();
    }

    public String getSavedArea() {
        return "";
    }

    @Override
    public String getBoundName() {
        return currentBound.getName();
    }
}