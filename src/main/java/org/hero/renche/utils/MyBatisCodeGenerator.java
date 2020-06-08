package org.hero.renche.utils;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.val;
import org.springframework.data.redis.connection.DataType;

public class MyBatisCodeGenerator {

    public static void main(String[] args) {
        //需要构建一个代码自动生成器对象
        AutoGenerator mpg = new AutoGenerator();


        //1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath= System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("MeiJiaLin");
        gc.setSwagger2(true);
        gc.setFileOverride(false);//是否覆盖
        gc.setServiceName("%sService");//去I前缀
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);


        //2.设置数据源
        DataSourceConfig dsc=new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:4306/renche?characterEncoding=UTF-8&useUnicode=true&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("bid");
        dsc.setPassword("AT3oPjgUfDnkQe=B");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //3.包的配置

        PackageConfig pc=new PackageConfig();
        pc.setModuleName("hero.renche");
        pc.setParent("org");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setController("controller");
        pc.setService("service");

        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("tx_lease_return");//表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix("tx_");
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//自动Lombok
        strategy.setRestControllerStyle(true);//驼峰命名

        mpg.setStrategy(strategy);

        mpg.execute();//执行

    }
}
