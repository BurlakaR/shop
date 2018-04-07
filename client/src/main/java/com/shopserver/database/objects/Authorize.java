package com.shopserver.database.objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class Authorize {
    @NonNull Client clientAutor;
    @NonNull String clientIp;
}
