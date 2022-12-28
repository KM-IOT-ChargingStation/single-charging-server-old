package com.kingmeter.charging.serverold.rest;

import com.alibaba.fastjson.JSON;
import com.kingmeter.chargingold.socket.rest.ChargingSocketApplication;
import com.kingmeter.dto.charging.v1.rest.request.*;
import com.kingmeter.dto.charging.v1.rest.response.*;
import com.kingmeter.dto.charging.v1.socket.in.*;
import com.kingmeter.socket.framework.application.SocketApplication;
import com.kingmeter.socket.framework.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/site")
@Slf4j
@RestController
public class ChargingApi {

    @Autowired
    private ChargingSocketApplication chargingSocketApplication;


    @PutMapping(value = "/unLock")
    public ScanUnlockResponseRestDto scanUnLock(@RequestBody ScanUnlockRequestRestDto requestDto) {
        return chargingSocketApplication.scanUnlock(requestDto);
    }

    @GetMapping("/queryDockInfo")
    public QueryDockInfoResponseRestDto sendQueryAllDockInfoCommand(
            @RequestParam long siteId,
            @RequestParam long dockId,
            @RequestParam String userId) {
        return chargingSocketApplication.dealWithQueryDockInfo(siteId, dockId, userId);
    }

    @GetMapping("/queryDockInfo2")
    public String sendQueryAllDockInfoCommand2(
            @RequestParam long siteId,
            @RequestParam long dockId,
            @RequestParam String userId,
            @RequestParam int times) {
        chargingSocketApplication.dealWithQueryDockInfo2times(siteId, dockId, userId, times);
        return "ok";
    }

    //2,强制开锁
    @PutMapping("/forceUnLock")
    public ForceUnLockResponseRestDto forceUnLock(@RequestBody ForceUnlockRequestRestDto requestDto) {
        return chargingSocketApplication.foreUnlock(requestDto.getSiteId(),
                requestDto.getDockId(), requestDto.getUserId());
    }

    /**
     * 锁检测
     */
    @GetMapping("/checkDockLock")
    public QueryDockLockStatusResponseRestDto queryDockLockStatus(
            @RequestParam long siteId,
            @RequestParam long dockId,
            @RequestParam String userId) {
        return chargingSocketApplication.queryDockLockStatus(siteId, dockId, userId);
    }

    @GetMapping("/querySiteInfo")
    public QuerySiteInfoRequestDto querySiteInfo(@RequestParam long siteId) {
        return chargingSocketApplication.querySiteInfo(siteId);
    }

    //configureSiteInfo
    @PutMapping("/configureSiteInfo")
    public ConfigureSiteInfoRequestDto configureSiteInfo(@RequestBody ConfigureSiteInfoRequestRestDto restDto) {
        return chargingSocketApplication.configureSiteInfo(restDto);
    }


    //configureSiteInfo
    @PutMapping("/configureSiteInfo2times")
    public String configureSiteInfo2times(@RequestBody ConfigureSiteInfoRequestRestDto restDto) {
        chargingSocketApplication.configureSiteInfo2times(restDto);
        return "ok";
    }

    @PutMapping("/restartSite")
    public RestartSiteResponseRestDto restartSite(@RequestParam long siteId) {
        return chargingSocketApplication.restartSite(siteId);
    }

    @GetMapping("/queryVersionOfComponents")
    public QueryVersionOfComponentsRequestDto queryVersionOfComponents(
            @RequestParam long siteId, @RequestParam int update_dev) {
        QueryVersionOfComponentsRequestRestDto restDto =
                new QueryVersionOfComponentsRequestRestDto(siteId, update_dev);
        return chargingSocketApplication.queryVersionOfComponents(restDto);
    }

    @PutMapping("/exchangeBootLoad")
    public ExchangeBootLoadResponseRestDto exchangeBootLoad(ExchangeBootLoadRequestRestDto restDto) {
        return chargingSocketApplication.exchangeBootLoad(restDto);
    }

    @GetMapping("/queryLog")
    public QueryLogRequestRestDto queryLog(long siteId){
        return chargingSocketApplication.queryLog(siteId);
    }

    @PutMapping("/openOrCloseLog")
    public OpenOrCloseLogRequestDto openOrCloseLog(long siteId, int flag){
        return chargingSocketApplication.openOrCloseLog(siteId,flag);
    }

    @DeleteMapping("/clearLog")
    public ClearLogRequestDto clearLog(long siteId){
        return chargingSocketApplication.clearLog(siteId);
    }

}
