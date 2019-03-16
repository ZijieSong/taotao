package com.ithema.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class GenHtml {

    private static final String diretorypath = "/Users/songzijie/Documents/project/taotao/itheima-freemarker/src/main/resources";

    private static final String prehtmlfilepath = "/Users/songzijie/Documents/project/taotao/itheima-freemarker/src/main/resources";

    //使用模板技术实现静态网页的输出
    @Test
    public void genHtml() throws Exception {
        //1.创建个configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.设置模板文件所在的路径的目录
        configuration.setDirectoryForTemplateLoading(new File(diretorypath));
        //3.设置模板文件的字符集
        configuration.setDefaultEncoding("utf-8");
        //4.首先创建模板文件，再加载模板文件 模板文件的后缀官方提供是.ftl 其实任何类型都行。
        Template template = configuration.getTemplate("test.ftl");//相对路径
        //5.创建模板文件需要展示数据的数据集对象，可以使用POJO，也可以使用map 一般是使用map
        Map model = new HashMap<>();
        model.put("hello", "hello");
        //6.创建一个FileWriter对象 指定生成的静态文件的文件路径及文件名
        //拼接一个前缀和后缀
        FileWriter writer = new FileWriter(new File(prehtmlfilepath + "/result.html"));
        //7.调用模板对象的process方法，执行输出文件。
        template.process(model, writer);
        //8.关闭流
        writer.close();
    }

}
