package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable{
    private Integer id;
    private String name;
    private String email;
    private Date birthdate;
    private Double base_salary;
    
    private Department department;

    public Seller(){

    }

    public Seller(Integer id, String name, String email, Date birthdate, Double base_salary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.base_salary = base_salary;
        this.department = department;
    }

    public Seller(String name, String email, Date birthdate, Double base_salary, Department department) {        
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.base_salary = base_salary;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Double getBase_salary() {
        return base_salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setBase_salary(Double base_salary) {
        this.base_salary = base_salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        Seller other = (Seller) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Seller [id=" + id + ", name=" + name + ", email=" + email + ", birthdate=" + birthdate
                + ", base_salary=" + base_salary + ", department=" + department + "]";
    }


}
