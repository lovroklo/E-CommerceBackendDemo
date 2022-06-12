package hr.tvz.klobucaric.hardwareapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "username","email"})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")}
    )

    @BatchSize(size = 20)
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Review> createdReviews = new ArrayList<>();



    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();


    public User(String firstName, String lastName, String email, String username, String password, Set<Authority> authorities) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

}
