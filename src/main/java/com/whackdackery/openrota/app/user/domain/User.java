package com.whackdackery.openrota.app.user.domain;

import com.whackdackery.openrota.app.common.domain.TimeAuditableDomainObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends TimeAuditableDomainObject {
    int id;
    @NotBlank
    String username;
    @NotBlank
    String email;
    @NotBlank
    String password;
    @NotNull
    List<Role> roles;
}
