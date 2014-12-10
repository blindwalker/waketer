package at.kropf.waketer.interfaces;

/**
 * Created by martinkropf on 08.11.14.
 */
public interface Observable {

    public void registerObserver(Observer a);

    public void removeObserver(Observer a);

    public void notifyObserver(Object a);
}
