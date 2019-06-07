package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class Student {
    private String name;

    private String institute, group;
    private Integer id;

    public Student(Integer id, String name, String institute, String group) {
        this.id = id;
        this.name = name;
        this.institute = institute;
        this.group = group;
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public String getInstitute() {
        return institute;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Student student = (Student) obj;
        if(this.id == student.id || this.name.equals(student.name)){
            return true;
        }
        return false;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

}