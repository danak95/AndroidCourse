package Model;

/**
 * Created by Dana on 20-May-18.
 */

public class Room
{
    private String name;
    private String address;
    private String description;
    private String imagePath;
    private double rank;

    public Room(String name, String address, String description, String imagePath, double rank)
    {
        this.name=name;
        this.address=address;
        this.description=description;
        this.imagePath=imagePath;
        this.rank=rank;
    }
    public Room(String name, String address, String description)
    {
        this.name=name;
        this.address=address;
        this.description=description;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
