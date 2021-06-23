package com.github.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegDto {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @Valid
    @JsonProperty("accounts")
    private List<AccountRegDto> accounts;

}
