package user;
import java.util.ResourceBundle;

class PropertiesData {
    ResourceBundle resource = ResourceBundle.getBundle("data");
    String name = resource.getString("name");
    String ID = resource.getString("ID");

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
