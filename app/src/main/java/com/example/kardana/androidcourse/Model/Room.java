package com.example.kardana.androidcourse.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.kardana.androidcourse.RoomType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 20-May-18.
 */
@Entity
public class Room implements Parcelable
{
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String address;
    private String description;
    private String imagePath;
    private double rank;
    private String ownerId;
    private int minNumOfPeople;
    private int maxNumOfPeople;
    private String roomSite;
    private List<RoomType> types;

    public final static String IMAGE_PATH="Rooms";

    public Room(String name, String address, String description,
                String image_path, double rank, String owner_id,
                int min_num_of_people, int max_num_of_people,
                String room_site, List<RoomType> types)
    {
        this.id = "1";
        this.name = name;
        this.address = address;
        this.description = description;
        this.imagePath = image_path;
        this.rank = rank;
        this.ownerId = owner_id;
        this.minNumOfPeople = min_num_of_people;
        this.maxNumOfPeople = max_num_of_people;
        this.roomSite = room_site;
        this.types = types;
    }

    public Room(Parcel p){
        this.types = new ArrayList<>();
        ArrayList<String> stringData = new ArrayList<>();
        p.readStringList(stringData);
        this.id = stringData.get(0);
        this.name = stringData.get(1);
        this.address = stringData.get(2);
        this.description = stringData.get(3);
        this.ownerId = stringData.get(4);
        this.imagePath = stringData.get(5);
        this.rank = p.readDouble();
        int[] intData = new int[4];
        p.readIntArray(intData);
        this.minNumOfPeople = Integer.valueOf(intData[1]);
        this.maxNumOfPeople = Integer.valueOf(intData[2]);
        stringData = new ArrayList<>();
        p.readStringList(stringData);
        this.roomSite = stringData.get(1);

        int typesLength = p.readInt();

        for(int i = 0; i < typesLength; i++){
            this.types.add(RoomType.valueOf(p.readString()));
        }
    }

    public Room()
    {
        this.setId("1");
    }

    public Room(String name, String address, String description, String imagePath, double rank)
    {
        this.setId("1");
        this.name=name;
        this.address=address;
        this.description=description;
        this.imagePath = this.imagePath;
        this.rank=rank;
    }
    public Room(String name, String address, String description)
    {
        this.setId("1");
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public Room(String name, String address, String description, double rank)
    {
        this.setId("1");
        this.name=name;
        this.address=address;
        this.description=description;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RoomType> getTypes() {
        return types;
    }

    public void setTypes(List<RoomType> types) {
        this.types = types;
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

    public int getMinNumOfPeople() {
        return minNumOfPeople;
    }

    public void setMinNumOfPeople(int minNumOfPeople) {
        this.minNumOfPeople = minNumOfPeople;
    }

    public int getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public void setMaxNumOfPeople(int maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRoomSite() {
        return roomSite;
    }

    public void setRoomSite(String roomSite) {
        this.roomSite = roomSite;
    }

    public void addType(RoomType type)
    {
        this.types.add(type);
    }

    public void removeType(RoomType type)
    {
        this.types.remove(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String []{
                this.id,
                this.name,
                this.address,
                this.description,
                this.ownerId,
                this.imagePath});
        parcel.writeDouble(this.rank);
        parcel.writeIntArray(new int [] {
                this.minNumOfPeople,
                this.maxNumOfPeople});
        parcel.writeStringArray(new String []{
                this.roomSite});
        parcel.writeInt(this.types.size());
        for(RoomType type : this.types) {
            parcel.writeString(type.getName());
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
