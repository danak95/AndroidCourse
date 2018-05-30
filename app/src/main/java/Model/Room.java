package Model;

/**
 * Created by Dana on 20-May-18.
 */

public class Room
{
    private String name;
    private String address;
    private String description;
    private String image_path;
    private double rank;
    private int company_id;
    private int owner_id;
    private int min_num_of_people;
    private int max_num_of_people;
    private String comments;
    private String room_site;

    public Room(String name, String address, String description, String image_path, double rank, int company_id, int owner_id, int min_num_of_people, int max_num_of_people, String comments, String room_site)
    {
        this.name=name;
        this.address=address;
        this.description=description;
        this.image_path=image_path;
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
        return image_path;
    }

    public void setImagePath(String imagePath) {
        this.image_path = imagePath;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getMin_num_of_people() {
        return min_num_of_people;
    }

    public void setMin_num_of_people(int min_num_of_people) {
        this.min_num_of_people = min_num_of_people;
    }

    public int getMax_num_of_people() {
        return max_num_of_people;
    }

    public void setMax_num_of_people(int max_num_of_people) {
        this.max_num_of_people = max_num_of_people;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRoom_site() {
        return room_site;
    }

    public void setRoom_site(String room_site) {
        this.room_site = room_site;
    }
}
