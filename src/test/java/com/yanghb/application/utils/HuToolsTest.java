package com.yanghb.application.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yanghb.application.ApplicationTest;
import com.yanghb.application.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HuToolsTest extends ApplicationTest {

    @Before
    public void before() {
        log.info("This is HuTool Test");
        User user = new User();

        Map<String, Object> map = BeanUtil.beanToMap(user);
    }

    @Test
    public void testHuTool() {
        DateTime time = DateUtil.date();
        log.info("time is {}", time.toString());

        Object[] a = {"a", "你", "好", "", 1};
        List<?> list = Convert.toList(a);
        log.info(list.toString());
    }
}
