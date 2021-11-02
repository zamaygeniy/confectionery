package by.training.сonfectionery.model.domain;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    protected Entity() {
    }

}
