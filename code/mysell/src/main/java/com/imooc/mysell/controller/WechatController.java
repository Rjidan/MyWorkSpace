package com.imooc.mysell.controller;

import com.imooc.mysell.config.ProjectUrlConfig;
import com.imooc.mysell.enums.ResultEnum;
import com.imooc.mysell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.UriEncoder;

import java.net.URLEncoder;

/**
 * @Author lzj
 * @Date 2020/9/29 19:44
 * @Version 1.0
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){

//        String redirectURI = "http://jidan.natapp1.cc/sell/wechat/userInfo";
//
//        String redirectUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(
//                redirectURI, WxConsts.OAuth2Scope.SNSAPI_BASE, UriEncoder.encode(returnUrl));
//
//        log.info("url={}",UriEncoder.decode(redirectUrl));
        String openId = "oTgZpwUD66sISh2XmmmbOyv5LQ1I";
        return "redirect:" + returnUrl + "?openid=" + openId;

//        return "redirect:" + redirectUrl;

    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService
                    .getOAuth2Service().getAccessToken(code);
        }catch (WxErrorException e) {
            log.error("[微信网页授权] {}", e);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR.getCode(),e.getError().getErrorMsg());
        }

//        String openId = wxMpOAuth2AccessToken.getOpenId();
        String openId = "oTgZpwUD66sISh2XmmmbOyv5LQ1I";

        log.info("openId={}",openId);
        log.info("redirect={}",returnUrl + "?openid=" + openId);

        return "redirect:" + returnUrl + "?openid=" + openId;

    }


    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(
                url,
                WxConsts.QrConnectScope.SNSAPI_LOGIN,
                URLEncoder.encode(returnUrl));

        String openId = "o9AREv3aro1pyALjrx5iJEuZdVLQ";
//        return "redirect:" + returnUrl + "?openid=" + openId;

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
//        String openId = wxMpOAuth2AccessToken.getOpenId();

        String returnUrl = "http://sell.com/sell/seller/login";

        String openId="o9AREv3aro1pyALjrx5iJEuZdVLQ";

        return "redirect:" + returnUrl + "?openid=" + openId;
    }

}
