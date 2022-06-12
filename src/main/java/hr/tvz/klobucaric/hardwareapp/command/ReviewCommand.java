package hr.tvz.klobucaric.hardwareapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class ReviewCommand {
    @NotEmpty
    private String headline;

    @NotEmpty
    private String text;

    @NotNull(message = "Grade can't be null")
    @Min(value = 1, message = "Grade cant be lower than 1")
    @Max(value = 5, message = "Grade cant be higher than 5")
    private Integer reviewGrade;

    @NotNull
    private Long hardwareId;



}
