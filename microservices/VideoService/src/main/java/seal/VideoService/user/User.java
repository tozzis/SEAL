package seal.VideoService.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_at", "updated_at", "hibernateLazyInitializer", "password", "handler" }, allowGetters = true)

@Table(name = "user")
@Entity
public class User implements Serializable{

    @Id
    private long id;

    @NotNull
    @JsonIgnore
    private String password;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    //@NotNull
    private String department;

    //@NotNull
    private String faculty;

    //@NotNull
    private int year;
    
    //@NotNull
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;

    public User() {
        
    }

    public User(long id, String firstname, String lastname, 
        String department, String faculty, int year, String image){
            this.id=id;
            this.firstname=firstname;
            this.lastname=lastname;
            this.department=department;
            this.faculty=faculty;
            this.year=year;
            this.image=image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", password=" + password + ", firstname=" + firstname + ", lastname=" + lastname + ", department=" + department + ", faculty=" + faculty + ", year=" + year + ", image=" + image + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }
    
}
