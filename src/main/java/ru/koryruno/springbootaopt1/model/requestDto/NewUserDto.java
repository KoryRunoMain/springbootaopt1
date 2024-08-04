package ru.koryruno.springbootaopt1.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class NewUserDto {

    private String name;
    private String email;

//    @NotNull
//    @Size(min = 6, max = 254)
//    private String name;
//
//    @NotNull
//    @NotBlank
//    @Email
//    @Size(min = 2, max = 250)
//    private String email;

}
