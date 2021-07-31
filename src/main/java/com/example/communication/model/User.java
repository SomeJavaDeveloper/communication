package com.example.communication.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "usr")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 16, message = "Username should be in range from 3 to 16 symbols")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 60, message = "Password should be at least 8 characters")
    private String password;

    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    private String email;

//    private String activationCode;

    private boolean active;

    private String profilePic;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "city")
    private String city;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
    )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "reposts",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id")}
    )
    private Set<Message> reposts = new HashSet<>();

    public User(String username, String password, String email, boolean active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
    }

    public User(
            @Size(min = 6, max = 16) String username,
            @Size(min = 6, max = 16) String password,
            @Email String email, boolean active, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public User(Long id,
                @NotEmpty(message = "Username cannot be empty") @Size(min = 3, max = 16, message = "Username should be in range from 3 to 16 symbols") String username,
                @NotEmpty(message = "Password cannot be empty") @Size(min = 6, max = 60, message = "Password must be at least 8 characters") String password,
                boolean active, @NotEmpty(message = "Email cannot be empty") String email,
                String profilePic, String realName, String dateOfBirth, String city,
                Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.profilePic = profilePic;
        this.realName = realName;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    //Bad
    public boolean isAuthorized() {
        return !roles.isEmpty();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return Objects.equals(id, user.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return  isActive() == user.isActive() && getId().equals(user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getRealName(), user.getRealName()) &&
                Objects.equals(getDateOfBirth(), user.getDateOfBirth()) &&
                Objects.equals(getCity(), user.getCity()) &&
                Objects.equals(getRoles(), user.getRoles())
//                Objects.equals(getSubscribers(), user.getSubscribers()) &&
//                Objects.equals(getSubscriptions(), user.getSubscriptions())
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", active=" + active +
            ", profilePic='" + profilePic + '\'' +
            ", realName='" + realName + '\'' +
            ", dateOfBirth='" + dateOfBirth + '\'' +
            ", city='" + city + '\'' +
            ", roles=" + roles +
            '}';
    }
}
