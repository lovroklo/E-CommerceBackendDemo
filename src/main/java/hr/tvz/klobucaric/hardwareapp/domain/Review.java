package hr.tvz.klobucaric.hardwareapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "REVIEW")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id","user","hardware"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;
    private String text;
    private Integer reviewGrade;
    @ManyToOne
    @JoinColumn(name = "hardware_id")
    private Hardware hardware;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "date_of_creation")
    private LocalDateTime dateOfCreation;

    public Review(String headline, String text, Integer reviewGrade, Hardware hardware, User user) {
        this.headline = headline;
        this.text = text;
        this.reviewGrade = reviewGrade;
        this.hardware = hardware;
        this.user = user;
        this.dateOfCreation =  LocalDateTime.now();
    }

    public Review(Long id, String headline, String text, Integer reviewGrade, Hardware hardware, User user) {
        this.id = id;
        this.headline = headline;
        this.text = text;
        this.reviewGrade = reviewGrade;
        this.hardware = hardware;
        this.user = user;
        this.dateOfCreation = LocalDateTime.now();;
    }

}
