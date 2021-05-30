package org.perscholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.perscholas.enums.Role;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mkemiche
 * @created 29/05/2021
 */
@Getter @ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class LoginInfos {

    @NotNull @NotBlank(message = "this field is required")
    String email;
    @NotNull @NotBlank(message = "this field is required")
    String password;
    Role role;
    boolean isAuth;


}
