package com.shier.mall.config;

import com.shier.mall.common.Constants;
import com.shier.mall.interceptor.AdminLoginInterceptor;
import com.shier.mall.interceptor.CartNumberInterceptor;
import com.shier.mall.interceptor.MallLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Demo class
 *
 * @author shier
 * @date 2021/3/16
 */
@Configuration
public class MallWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    private MallLoginInterceptor mallLoginInterceptor;

    @Autowired
    private CartNumberInterceptor cartNumberInterceptor;


    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/js/**")
                .excludePathPatterns("/admin/css/**")
                .excludePathPatterns("/layui/**")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/upload/**");
        // 购物车中的数量统一处理
        registry.addInterceptor(cartNumberInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/register")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout");
        // 商城页面登陆拦截
        registry.addInterceptor(mallLoginInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/jvshijimall/register")
                .excludePathPatterns("/jvshijimall/login")
                .excludePathPatterns("/jvshijimall/logout")
                .addPathPatterns("/jvshijimall/index")
                .addPathPatterns("/jvshijimall/goods/detail/**")
                .addPathPatterns("/jvshijimall/shop-cart")
                .addPathPatterns("/jvshijimall/shop-cart/**")
                .addPathPatterns("/jvshijimall/saveOrder")
                .addPathPatterns("/jvshijimall/orders")
                .addPathPatterns("/jvshijimall/orders/**")
                .addPathPatterns("/jvshijimall/personal")
                .addPathPatterns("/jvshijimall/personal/updateInfo")
                .addPathPatterns("/jvshijimall/selectPayType")
                .addPathPatterns("/goods/**")
                .addPathPatterns("/jvshijimall/payPage");
    }

    /**
     * 添加虚拟目录
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_PATH);
//        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_PATH);

        //================上面是Window上的路径配置
        //================下面是Linux上的路径配置
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String gitPath = path.getParentFile().getParentFile().getParent() + File.separator + "myresource" + File.separator + "upload" + File.separator;
        registry.addResourceHandler("/upload/**").addResourceLocations(gitPath);
        registry.addResourceHandler("/goods-img/**").addResourceLocations(gitPath);
        WebMvcConfigurer.super.addResourceHandlers(registry);

    }
}
