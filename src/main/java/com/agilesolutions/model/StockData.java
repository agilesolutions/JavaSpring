package com.agilesolutions.model;

import com.agilesolutions.jpa.model.DailyStockData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StockData {

    private List<DailyStockData> values;

}