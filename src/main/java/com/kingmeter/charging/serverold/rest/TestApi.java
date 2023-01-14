package com.kingmeter.charging.serverold.rest;


import com.kingmeter.chargingold.socket.rest.ChargingSocketTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/test")
@Slf4j
@RestController
public class TestApi {

    @Autowired
    private ChargingSocketTestApplication chargingSocketTestApplication;


    @DeleteMapping("/{siteId}")
    public void stopUnlock(@PathVariable("siteId") long siteId) {
        chargingSocketTestApplication.stopUnlock(siteId);
    }


    @GetMapping("/batchUnlock/{siteId}")
    public String batchUnlock(@PathVariable(name = "siteId") long siteId,
                              @RequestParam(name = "times") int times,
                              @RequestParam(name = "perSite") long perSite,
                              @RequestParam(name = "perDock") long perDock) {
        return chargingSocketTestApplication.batchUnlock(siteId,times,perSite,perDock);
    }


//    @DeleteMapping("/checkDockLock/{siteId}")
//    public void stopCheckDockLock(@PathVariable("siteId") long siteId) {
//        chargingSocketTestApplication.stopCheckDockLock(siteId);
//    }
//
//
//    @GetMapping("/batchCheckDockLock/{siteId}")
//    public String batchCheckDockLock(@PathVariable(name = "siteId") long siteId,
//                              @RequestParam(name = "times") int times,
//                              @RequestParam(name = "perSite") long perSite,
//                              @RequestParam(name = "perDock") long perDock) {
//        return chargingSocketTestApplication.batchCheckDockLock(siteId,times,perSite,perDock);
//    }
}
