package com.yanghb.application.service;

import com.yanghb.application.filter.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UtilsService {

    /**
     * 过滤敏感词
     */
    public List<String> filterSensitiveWords(List<String> list) {
        log.info("List:[{}]", list);
        try {
            SensitiveFilter sensitiveFilter = SensitiveFilter.DEFAULT;
            for (int index = 0; index < list.size(); index++) {
                String word = list.get(index);
                if (StringUtils.isNotBlank(word)) {
                    String filter = sensitiveFilter.filter(word, '*');
                    list.remove(index);
                    list.add(index, filter);
                }
            }
        } catch (Exception e) {
            log.error("过滤敏感词错误：", e);
        }
        log.info("Chge:[{}]", list);
        return list;
    }
}
