package ru.koryruno.springbootaopt1.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserFullDto {

    private Long id;
    private String name;
    private String email;

}
