package com.kingmeter.charging.serverold.rest;

import com.kingmeter.charging.serverold.rest.dto.SiteMapDto;
import com.kingmeter.chargingold.socket.rest.ChargingSocketApplication;
import com.kingmeter.chargingold.socket.rest.ConnectionDto;
import com.kingmeter.socket.framework.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequestMapping("/attach")
@RestController
public class AttachedApi {

    @Autowired
    private ChargingSocketApplication chargingSocketApplication;

    @Value("${kingmeter.default.timezone}")
    private int defaultTimezone;


    @GetMapping("/connection")
    public List<ConnectionDto> queryConnection() {
        return chargingSocketApplication.queryConnection();
    }

    @GetMapping("/count")
    public long count(){
        return CacheUtil.getInstance().getDeviceIdAndChannelMap().size();
    }

    @GetMapping("/{siteId}")
    public Map<String, String> getDeviceMap(@PathVariable("siteId") long siteId){
        Map<String, String> siteMap = CacheUtil.getInstance().getDeviceInfoMap().get(siteId);
        return siteMap;
    }

    @PutMapping("/{siteId}/all")
    public String setDeviceMap(@RequestBody SiteMapDto siteMapDto){
        long siteId = siteMapDto.getSiteId();
        ConcurrentHashMap<Long,Map<String, String>> deviceMap = CacheUtil.getInstance().getDeviceInfoMap();
        Map<String, String> siteMap = deviceMap.get(siteId);
        siteMap.put("ret",String.valueOf(siteMapDto.getRet()));
        siteMap.put("ast",String.valueOf(siteMapDto.getAst()));
        siteMap.put("acm",String.valueOf(siteMapDto.getAcm()));
        siteMap.put("cum",String.valueOf(siteMapDto.getCum()));
        siteMap.put("tim",String.valueOf(siteMapDto.getTim()));
        siteMap.put("minbsoc",String.valueOf(siteMapDto.getMinbsoc()));
        deviceMap.put(siteId,siteMap);
        CacheUtil.getInstance().setDeviceInfoMap(deviceMap);
        return "okay";
    }


    @PutMapping("/{siteId}")
    public String setTempTime(@PathVariable("siteId") long siteId,
                              @RequestParam("tempTime")long tempTime){
        ConcurrentHashMap<Long,Map<String, String>> deviceMap = CacheUtil.getInstance().getDeviceInfoMap();
        Map<String, String> siteMap = deviceMap.get(siteId);
        siteMap.put("tempTime",String.valueOf(tempTime));
        deviceMap.put(siteId,siteMap);
        CacheUtil.getInstance().setDeviceInfoMap(deviceMap);
        return "okay";
    }


    @PutMapping("/restart/{siteId}")
    public String restart1minLater(@PathVariable("siteId") long siteId){
        ConcurrentHashMap<Long,Map<String, String>> deviceMap = CacheUtil.getInstance().getDeviceInfoMap();
        Map<String, String> siteMap = deviceMap.get(siteId);
        int timezone = Integer.parseInt(siteMap.getOrDefault("timezone", String.valueOf(defaultTimezone)));

        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        long tempTime =  (timezone-hour)*3600 - (min+1)*60;

        siteMap.put("tempTime",String.valueOf(tempTime));
        deviceMap.put(siteId,siteMap);
        CacheUtil.getInstance().setDeviceInfoMap(deviceMap);
        return "okay";
    }

}
