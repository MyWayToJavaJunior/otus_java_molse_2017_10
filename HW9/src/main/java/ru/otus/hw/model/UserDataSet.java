package ru.otus.hw.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.otus.hw.annotations.Entity;

@Entity(name = "user")
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
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
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
