package by.training.сonfectionery.domain;

import java.io.Serializable;

public class Entity implements Cloneable, Serializable { //TODO CONVERT INT TO INTEGER 23.09.21
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Entity() {
    }

    public Entity(int id) {
        this.setId(id);
    }

}
