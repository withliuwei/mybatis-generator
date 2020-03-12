package vip.liuw.mybatisenerator;

import vip.liuw.mybatisenerator.util.FileUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args) throws Exception {
        //配置文件
        String fileName = "/myproject/config.xml";

        //先清空test文件夹
        String classPath = Run.class.getResource("/").getPath();
        String test = classPath.substring(0, classPath.lastIndexOf("/target")) + "/src/test";
        File testFile = new File(test);
        FileUtils.deleteFile(testFile);
        File javaFile = new File(test + "/java");
        javaFile.mkdirs();
        File testResources = new File(test + "/resources");
        testResources.mkdirs();

        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);

        File file = new File(Run.class.getResource(fileName).toURI());
        Configuration config = cp.parseConfiguration(file);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
        warnings.forEach(System.out::println);

    }
}