package ru.koryruno.springbootaopt1.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class UpdateOrderDto {

    private String description;
    private String status;

//    @NotNull
//    @NotBlank
//    @Size(min = 1, max = 2000)
//    private String description;
//
//    @NotNull
//    @NotBlank
//    @Size(min = 1)
//    private String status;

}
