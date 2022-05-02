package com.someget.admin.common.config;

import com.someget.admin.common.sys.freemark.SysUserTempletModel;
import freemarker.template.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 前端渲染固定资源加载配置
 * @author zyf
 * @date 2022-03-25 20:03
 */
@Component
public class FreemarkerConfig {

    @Resource
    private Configuration configuration;

    @Resource
    private SysUserTempletModel sysUserTempletModel;

    /**
     * 带CDN的oss
     */
    @Value("https://dashixiongerp.oss-cn-hangzhou.aliyuncs.com")
    private String staticUrl;
    @Value("http://myshares.oss-cn-hangzhou.aliyuncs.com")
    private String staticUrl2;

    @PostConstruct
    public void setSharedVariable() {
        //获取系统用户信息
        configuration.setSharedVariable("sysuser",sysUserTempletModel);
    }

    @Resource
    void configureFreemarkerConfigurer(FreeMarkerConfig configurer) {
        configurer.getConfiguration().setObjectWrapper(new CustomObjectWrapper());
    }

    private static class CustomObjectWrapper extends DefaultObjectWrapper {
        @Override
        public TemplateModel wrap(Object obj) throws TemplateModelException {
            if (obj instanceof LocalDateTime) {
                Timestamp timestamp = Timestamp.valueOf((LocalDateTime) obj);
                return new SimpleDate(timestamp);
            }
            if (obj instanceof LocalDate) {
                Date date = Date.valueOf((LocalDate) obj);
                return new SimpleDate(date);
            }
            if (obj instanceof LocalTime) {
                Time time = Time.valueOf((LocalTime) obj);
                return new SimpleDate(time);
            }
            return super.wrap(obj);
        }
    }

    /**
     * Spring 初始化的时候加载配置
     */
    @SneakyThrows
    @PostConstruct
    public void setConfigure() {
        // 加载静态资源路径
        configuration.setSharedVariable("staticUrl", staticUrl);
        configuration.setSharedVariable("staticUrl2", staticUrl2);
    }
}
