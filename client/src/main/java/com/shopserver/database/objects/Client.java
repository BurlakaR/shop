package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;


@Data
public class Client implements Serializable {
    @NonNull String fio;
    @NonNull String login;
    @NonNull String password;
    @NonNull String email;
    @NonNull Date date;
}
