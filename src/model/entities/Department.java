package model.entities;

import java.io.Serializable;

public class Department implements Serializable {

    private Integer id;
    private String dep_name;

    public Department(){

    }

    public Department(Integer id, String dep_name) {
        this.id = id;
        this.dep_name = dep_name;
    }

    public Integer getId() {
        return id;
    }

    public String getdep_Name() {
        return dep_name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setdep_Name(String dep_name) {
        this.dep_name = dep_name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + dep_name + "]";
    }

}
