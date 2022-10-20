package com.kingmeter.charging.serverold.rest;

import com.alibaba.fastjson.JSON;
import com.kingmeter.chargingold.socket.rest.ChargingSocketApplication;
import com.kingmeter.dto.charging.v1.rest.request.ForceUnlockRequestRestDto;
import com.kingmeter.dto.charging.v1.rest.request.ScanUnlockRequestRestDto;
import com.kingmeter.dto.charging.v1.rest.response.ForceUnLockResponseRestDto;
import com.kingmeter.dto.charging.v1.rest.response.QueryDockInfoResponseRestDto;
import com.kingmeter.dto.charging.v1.rest.response.QueryDockLockStatusResponseRestDto;
import com.kingmeter.dto.charging.v1.rest.response.ScanUnlockResponseRestDto;
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
                requestDto.getDockId());
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


}
