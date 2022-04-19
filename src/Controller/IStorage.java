package Controller;

/***
 * Represents an inteface for storage
 * Required to have both load and store
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public interface IStorage {

    public void storeData();

    public void loadData();
}
