package org.hero.renche.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决导Excel报 Could not find acceptable representation
 * 参考博文：① https://blog.csdn.net/qq_28289405/article/details/80939841
 * ② https://blog.csdn.net/qq_18600061/article/details/80227490
 * Created by Love丶TG on 2018/8/10 16:27.
 */
@Configuration
public class FastJsonConfiguration implements WebMvcConfigurer {

//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        //1.需要定义一个convert转换消息的对象;
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //2:添加fastJson的配置信息;
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        //3处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        //4.在convert中添加配置信息.
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
//        return new HttpMessageConverters(converter);
//
//    }

    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        return fastJsonConfig;
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(
            @Qualifier("fastJsonConfig") FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * @Description 修改自定义消息转换器
     * @Param converters 消息转换器列表
     **/
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //调用父类的配置
//        super.configureMessageConverters(converters);
//        //创建fastjson消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //创建配置类
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        //修改配置返回内容的过滤
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty
//        );
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        //将fastJson 添加到视图消息转换器列表内
//        converters.add(fastConverter);
//    }
}