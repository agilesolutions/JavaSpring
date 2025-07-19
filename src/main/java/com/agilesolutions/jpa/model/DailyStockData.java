package com.agilesolutions.jpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DailyStockData {
    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("open")
    private String open;

    @JsonProperty("high")
    private String high;

    @JsonProperty("low")
    private String low;

    @JsonProperty("close")
    private String close;

    @JsonProperty("volume")
    private String volume;
}