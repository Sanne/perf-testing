package io.quarkus.perf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "DATA_OBJECTS")
public class DataEntity {
    @Id
    public long id;

    @Column(name = "DATA_NAME")
    public String name;

    public DataEntity() {
    }

    public DataEntity(long id, String name) {
        this.name = name;
        this.id = id;
    }
}
