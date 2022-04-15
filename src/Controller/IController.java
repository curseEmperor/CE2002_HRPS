package Controller;

public interface IController {

    public void create(Object entities);

    public void read();

    public void delete(Object entities);

    public void update(Object entities, int choice, String value);

    public void storeData();

    public void loadData();

}
