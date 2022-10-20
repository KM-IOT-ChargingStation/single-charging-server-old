package com.kingmeter.charging.serverold.rest.dto;


import lombok.Data;

@Data
public class SiteMapDto {
    private long siteId;
    private int ret;
    private int ast;
    private int acm;
    private int cum;
    private int tim;
    private int minbsoc;
    private String uid;
}
