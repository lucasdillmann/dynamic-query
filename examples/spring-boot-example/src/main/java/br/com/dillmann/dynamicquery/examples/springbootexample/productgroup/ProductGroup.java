package br.com.dillmann.dynamicquery.examples.springbootexample.productgroup;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductGroup {

    @Id
    private Integer id;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
