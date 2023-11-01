package com.haidong.test;

import com.haidong.storysharing.utils.PageUtils;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/25/21:19
 * @Description:
 */
public class TokenManager {

    private long tokenExpiration = 24*60*60*1000;

    private String tokenSignKey = "123456";

    /*public String createToken(String username){
    }*/

    @Test
    public void so(){
        System.out.println(PageUtils.getCurrent());
        Integer current = PageUtils.getCurrent();
        current++;
        PageUtils.getPage().setCurrent(current);
        System.out.println(PageUtils.getCurrent());
    }

    @Test
    public void test(){
        String s1 = "A";
        String s2 = "C";

        String str ="Hello";
        String substring = str.substring(0,3);
        System.out.println(substring);
        int i = str.indexOf('o');
        char c = str.charAt(4);
        System.out.println(c);
    }
}
