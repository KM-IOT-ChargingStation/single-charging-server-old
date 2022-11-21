package com.kingmeter.charging.serverold.rest;

import com.alibaba.fastjson.JSON;
import com.kingmeter.chargingold.socket.rest.ChargingSocketApplication;
import com.kingmeter.dto.charging.v1.rest.request.ConfigureSiteInfoRequestRestDto;
import com.kingmeter.dto.charging.v1.rest.request.ForceUnlockRequestRestDto;
import com.kingmeter.dto.charging.v1.rest.request.ScanUnlockRequestRestDto;
import com.kingmeter.dto.charging.v1.rest.response.*;
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

    @GetMapping("/queryFirmwareVersion")
    public QueryDockInfoResponseRestDto sendQueryAllDockInfoCommand(
            @RequestParam long siteId,
            @RequestParam long dockId,
            @RequestParam String  userId) {
        return chargingSocketApplication.dealWithQueryDockInfo(siteId, dockId,userId);
    }



    //2,强制开锁
    @PutMapping("/forceUnLock")
    public ForceUnLockResponseRestDto forceUnLock(@RequestBody ForceUnlockRequestRestDto requestDto) {
        return chargingSocketApplication.foreUnlock(requestDto.getSiteId(),
                requestDto.getDockId(),requestDto.getUserId());
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
    public QuerySiteInfoResponseRestDto querySiteInfo(@RequestParam long siteId){
        return chargingSocketApplication.querySiteInfo(siteId);
    }

    //configureSiteInfo
    @PutMapping("/configureSiteInfo")
    public ConfigureSiteInfoResponseRestDto configureSiteInfo(@RequestBody ConfigureSiteInfoRequestRestDto restDto){
        return chargingSocketApplication.configureSiteInfo(restDto);
    }

    @PutMapping("/restartSite")
    public RestartSiteResponseRestDto restartSite(@RequestParam long siteId){
        return chargingSocketApplication.restartSite(siteId);
    }
}
