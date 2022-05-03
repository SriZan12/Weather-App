package Model;

public class Weather {
    private  String main;
    private float id;
    private String description;
    private String icon;


    // Getter Methods

    public float getId() {
        return id;
    }

    public  String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
