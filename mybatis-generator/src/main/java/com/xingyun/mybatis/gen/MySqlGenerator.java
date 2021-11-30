package com.xingyun.mybatis.gen;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MySqlGenerator {


    public static void main(String[] args) {


        // TODO   !!!先指定配置文件!!!
        Properties properties = LoadConfigUtil.loadConfig("guildx.properties");


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setAuthor(properties.getProperty("mybatis.author"));
        gc.setFileOverride(true);
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        //gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(properties.getProperty("jdbc.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(properties.getProperty("jdbc.driver"));
        dsc.setUsername(properties.getProperty("jdbc.username"));
        dsc.setPassword(properties.getProperty("jdbc.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String packageName = properties.getProperty("project.package.name");
        pc.setModuleName(packageName.substring(packageName.lastIndexOf(".")+1));
        pc.setParent(packageName.substring(0,packageName.lastIndexOf(".")));

        String codePath = "/src/main/java/";
        String resourcePath = "/src/main/resources/";
        String apiPath = projectPath + File.separator + properties.getProperty("project.parent.module.name") + File.separator + properties.getProperty("project.api.module.name") + codePath;
        String srvPath = projectPath + File.separator + properties.getProperty("project.parent.module.name") + File.separator + properties.getProperty("project.srv.module.name") + codePath;
        Map<String,String> pathInfo = new HashMap<>();

        pathInfo.put(ConstVal.SERVICE_PATH, (srvPath + packageName+"."+pc.getService()).replaceAll("\\.",  File.separator));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, (srvPath + packageName+"."+pc.getServiceImpl()).replaceAll("\\.",  File.separator));
        pathInfo.put(ConstVal.ENTITY_PATH, (srvPath +packageName+"."+pc.getEntity()).replaceAll("\\.", File.separator));
        pathInfo.put(ConstVal.MAPPER_PATH, (srvPath + packageName+"."+pc.getMapper()).replaceAll("\\.",  File.separator));
        String xmlSuffix = packageName.substring(packageName.lastIndexOf(".") + 1);
        pathInfo.put(ConstVal.XML_PATH, projectPath + File.separator + properties.getProperty("project.parent.module.name") + File.separator + properties.getProperty("project.srv.module.name") + resourcePath+"mapper/" + ("dao".equals(xmlSuffix)? "":xmlSuffix)) ;
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        for (Map.Entry<String, String> entry : pathInfo.entrySet()) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        /*List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });*/
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        //cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //templateConfig.setService(null);
        //templateConfig.setServiceImpl(null);
       // templateConfig.setXml(null);
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        //strategy.setTablePrefix("llhd_");

        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //strategy.setInclude("member","member_account","member_account_record","member_info","order","trans_order","trade");

        String tableNames =  properties.getProperty("table.name");
        strategy.setInclude(tableNames.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setLogicDeleteFieldName("deleted");
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
