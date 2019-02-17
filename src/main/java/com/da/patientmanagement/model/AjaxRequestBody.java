package com.da.patientmanagement.model;

import lombok.Data;

@Data
public class AjaxRequestBody {
    private Integer start;
    private Integer length;
    private Integer draw;
    private DataTablesOrder[] order;
    private DataTablesColumn[] columns;
    private DataTableSearch search;
    private Boolean showHidden;
}
