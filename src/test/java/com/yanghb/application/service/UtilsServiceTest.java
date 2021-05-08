package com.yanghb.application.service;

import com.yanghb.application.ApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UtilsServiceTest extends ApplicationTest {
    @Autowired
    UtilsService utilsService;

    @Test
    public void filterSensitiveWords() {
        List<String> list = new ArrayList<>();
        list.add("狗");
        list.add("你好");
        list.add("RMB");
        list.add("麻醉弹");
        utilsService.filterSensitiveWords(list);
    }
}