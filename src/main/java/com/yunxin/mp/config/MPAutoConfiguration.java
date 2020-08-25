package com.yunxin.mp.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.yunxin.mp.util.SnowKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huangzuwang
 * @date: 2019-12-02 11:47
 * @description:
 */

@Configuration
public class MPAutoConfiguration {


    @Value("${project.workId}")
    private Long workId;

    /**
     * 物理分页插件【MP默认是逻辑分页】
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     * 自定义ID生成器【雪花】
     * @return
     */
    @Bean
    public SnowKey snowKey(){
        return new SnowKey(workId);
    }
}
