package com.imooc.mysell.utils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Random;

/**
 * @Author lzj
 * @Date 2020/9/24 21:00
 * @Version 1.0
 */
public class KeyUtils {

    public static synchronized String getUniqueKey(){
        Random random = new Random();

        // 六位随机数
        Integer i1 = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(i1);
    }
}
