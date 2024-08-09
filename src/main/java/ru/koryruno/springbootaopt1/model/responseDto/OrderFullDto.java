package ru.koryruno.springbootaopt1.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderFullDto {

    private Long id;
    private String description;
    private String status;
    private Long initiator;

}
