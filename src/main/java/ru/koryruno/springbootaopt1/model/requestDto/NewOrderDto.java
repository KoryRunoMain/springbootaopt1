package ru.koryruno.springbootaopt1.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class NewOrderDto {

    private String description;

//    @NotNull
//    @NotBlank
//    @Size(min = 1, max = 2000)
//    private String description;

}
