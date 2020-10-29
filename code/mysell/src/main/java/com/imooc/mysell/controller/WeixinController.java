package com.imooc.mysell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author lzj
 * @Date 2020/9/29 19:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
//
//    @GetMapping("/auth")
//    public void auth(@RequestParam("code") String code){
//        log.info("进入auth方法");
//        log.info("code={}",code);
//        String appid = "wx3c5f91d76288b07f";
//        String secret = "2efac8f2ee36ad97c0d098456f50565b";
//
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject(url, String.class);
//        log.info("response={}",response);
//
//    }
}
