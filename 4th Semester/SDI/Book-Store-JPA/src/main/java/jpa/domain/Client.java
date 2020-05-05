package jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity
public class Client extends jpa.domain.BaseEntity<Long> {

    private String serialNumber;
    private String name;

    public Client() {
    }

    public Client(String serialNumber, String name) {
        this.serialNumber = serialNumber;
        this.name = name;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        jpa.domain.Client student = (jpa.domain.Client) o;
        return name.equals(student.name) && serialNumber.equals(student.serialNumber);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
