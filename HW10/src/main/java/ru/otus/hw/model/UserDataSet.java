package ru.otus.hw.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<PhoneDataSet> phone;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.phone = Collections.emptySet();
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public UserDataSet(String name, int age, AddressDataSet address, Set<PhoneDataSet> phone) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public Set<PhoneDataSet> getPhone() {
        return phone;
    }

    public void setPhone(Set<PhoneDataSet> phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserDataSet)) return false;

        UserDataSet that = (UserDataSet) o;

        return new EqualsBuilder()
                .append(getAge(), that.getAge())
                .append(getName(), that.getName())
                .append(getId(),that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getAge())
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phone=" + phone +
                '}';
    }
}
