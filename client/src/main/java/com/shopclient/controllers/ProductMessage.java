package com.shopclient.controllers;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProductMessage {
    @NonNull String url;
    @NonNull int number;
}
