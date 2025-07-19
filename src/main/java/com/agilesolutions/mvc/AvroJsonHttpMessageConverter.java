package com.agilesolutions.mvc;

import org.springframework.http.MediaType;

public class AvroJsonHttpMessageConverter<T> extends AvroHttpMessageConverter<T> {

    public AvroJsonHttpMessageConverter() {
        super(false, new MediaType("application", "avro+json", DEFAULT_CHARSET),
                new MediaType("application", "*+avro+json", DEFAULT_CHARSET));
    }

}