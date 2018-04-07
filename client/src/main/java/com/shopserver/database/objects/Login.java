package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class Login {
    @NonNull String login;
    @NonNull String password;
}
