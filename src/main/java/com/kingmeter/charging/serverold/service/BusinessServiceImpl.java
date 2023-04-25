package com.kingmeter.charging.serverold.service;

import com.alibaba.fastjson.JSONObject;
import com.kingmeter.chargingold.socket.acl.BusinessService;
import com.kingmeter.common.ResponseData;
import com.kingmeter.dto.charging.v1.rest.business.LoginPermissionBSDto;
import com.kingmeter.dto.charging.v1.socket.in.*;
import com.kingmeter.dto.charging.v1.socket.out.BikeInDockResponseDto;
import com.kingmeter.dto.charging.v1.socket.out.LoginPermissionDto;
import com.kingmeter.dto.charging.v1.socket.out.LoginResponseDto;
import com.kingmeter.dto.charging.v1.socket.out.SwingCardUnLockResponseDto;
import com.kingmeter.socket.framework.util.CacheUtil;
import com.kingmeter.utils.HardWareUtils;
import com.kingmeter.utils.KMHttpClient;
import com.kingmeter.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {


    @Value("${kingmeter.default.companyCode}")
    private String defaultCompanyCode;

    @Value("${kingmeter.default.timezone}")
    private int defaultTimezone;

    @Autowired
    private KMHttpClient httpClient;

    @Override
    public LoginPermissionDto getLoginPermission(SiteLoginRequestDto requestDto) {

        long siteId = requestDto.getSid();
        String password = requestDto.getPwd();

        Map<String, Object> params = new HashMap<>();
        params.put("siteId", siteId);

//        LoginResponseDto response = new LoginResponseDto(0, "", "", 0,
//                -1, -1, defaultCompanyCode, temp);
//        return new LoginPermissionDto(response, defaultCompanyCode, -1);

        ResponseData responseData =
                httpClient.get(
                        params,
                        BusinessApiMappingPrefix.queryLoginPermission + siteId,
                        siteId);
        if (responseData.getCode() == 1000) {
            try {
                LoginPermissionBSDto result =
                        JSONObject.parseObject(responseData.getData().toString(),
                                LoginPermissionBSDto.class);

                int sls = result.getSls();
                String newPassword = result.getPassword();
                String newUrl = result.getSocketDomain();
                int newPort = result.getSocketPort();
                int timezone = result.getTimezone();
                String companyCode = result.getCompanyCode();

                byte[] passwordArray = newPassword.getBytes();
                String passwordMd5 = MD5Util.MD5Encode(passwordArray);

                if (sls == 0) {
                    //说明不需要让其跳转到新的服务器
                    //这里直接判断密码对不对
                    if (!password.equals(passwordMd5)) {
                        return null;
                    }
                    newUrl = "";
                    newPort = 0;
                } else if (sls == 4) {
                    //说明需要跳转到新的服务器
                    //这里不需要验证密码，直接给新的密码
                    //do nothing here
                } else {
                    return null;
                }
                LoginResponseDto response = new LoginResponseDto(sls, newPassword, newUrl, newPort,
                        -1, -1, HardWareUtils.getInstance()
                        .getUtcTimeStampOnDevice(timezone));
                return new LoginPermissionDto(response, companyCode, timezone);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public BikeInDockResponseDto createBikeInDockResponseDto(BikeInDockRequestDto requestDto) {

            return new BikeInDockResponseDto(requestDto.getKid(), 0, 0, 0, 0);
    }

    @Override
    public void forceUnlockNotify(ForceUnLockRequestDto forceUnLockRequestDto) {

    }

    @Override
    public void heartBeatNotify(SiteHeartRequestDto requestDto) {

    }

    @Override
    public void malfunctionUploadNotify(DockMalfunctionUploadRequestDto requestDto) {

    }


    @Override
    public void dealWithQueryDockInfo(QueryDockInfoRequestDto queryDockInfoRequestDto) {

    }

    @Override
    public void dealWithScanUnLock(ScanUnLockRequestDto requestDto) {

    }

    @Override
    public void dealWithScanUnLockII(ScanUnLockIIRequestDto scanUnLockIIRequestDto) {

    }

    @Override
    public void dealWithRemoteLock(RemoteLockRequestDto remoteLockRequestDto) {

    }

    @Override
    public SwingCardUnLockResponseDto dealWithSwingCardUnlock(SwingCardUnLockRequestDto requestDto) {
        return null;
    }

    @Override
    public void dealWithSwingCardConfirm(SwingCardUnLockRequestConfirmDto requestDto) {

    }

    @Override
    public void queryDockLockStatusNotify(QueryDockLockStatusRequestDto queryDockLockStatusRequestDto) {

    }


    @Override
    public void offlineNotify(Long siteId) {

    }

    private int getSiteTimeZone(long siteId) {
        Map<String, String> siteMap = CacheUtil.getInstance().getDeviceInfoMap().get(siteId);
        return Integer.parseInt(siteMap.getOrDefault("timezone", String.valueOf(defaultTimezone)));
    }

}
