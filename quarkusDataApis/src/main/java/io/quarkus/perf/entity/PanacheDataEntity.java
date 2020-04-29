package io.quarkus.perf.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "DATA_OBJECTS")
public class PanacheDataEntity extends PanacheEntity {
    @Column(name = "DATA_NAME")
    public String name;

    public static PanacheDataEntity findByName(String name){
        return find("name", name).firstResult();
    }
}
