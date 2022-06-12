package hr.tvz.klobucaric.hardwareapp.dto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id","userName","hardwareCode"})
public class ReviewDto {
    private Long id;
    private String title;
    private String text;
    private Integer reviewGrade;
    private String userName;
    private String hardwareCode;
    private LocalDateTime dateOfCreation;

    public ReviewDto(Long id, String title, String text, Integer reviewGrade, String userName, String hardwareCode, LocalDateTime dateOfCreation) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.reviewGrade = reviewGrade;
        this.userName = userName;
        this.hardwareCode = hardwareCode;
        this.dateOfCreation = dateOfCreation;
    }

}
