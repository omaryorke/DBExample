package com.example.omar.examen;

/**
 * Created by omar on 30/03/2015.
 */
public class POJO {

    private String id;
    private String name;
    private String vicinity;

    public POJO(String _id, String _name, String _vicinity){
        this.id = _id;
        this.name = _name;
        this.vicinity = _vicinity;
    }

    public String getId() {
        return id;
    }

    public  void setId(String id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  String getVicinity() {
        return vicinity;
    }

    public  void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
