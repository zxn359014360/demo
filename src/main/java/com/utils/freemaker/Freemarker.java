package com.utils.freemaker;

import com.utils.PathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Locale;
import java.util.Map;

/**
/**
 * 创建人：FH 创建时间：2015年2月8日
 */
public class Freemarker {



    public static void printFile(String ftlName, Map<String, Object> root, String outFile, String ftlPath) throws Exception {
        try {
            File file = new File(outFile);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);                    //模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File printFile(String ftlName, Map<String, Object> root, String outFile, String savePath, String ftlPath) throws Exception {
        File file = new File(PathUtil.getClasspath() + savePath + outFile);
        try {
            if (!file.getParentFile().exists()) {                //判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();                //不存在就全部创建
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);                    //模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File printFile(String ftlName, Map<String, Object> root, String outFile, String path, String savePath, String ftlPath) throws Exception {
        File file = new File(path + savePath + outFile);
        try {
            if (!file.getParentFile().exists()) {                //判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();                //不存在就全部创建
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);                    //模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void printFile(String ftlName, Map<String, Object> root, File file, String ftlPath) throws Exception {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);                    //模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过文件名加载模版
     *
     * @param ftlName
     */
    public static Template getTemplate(String ftlName, String ftlPath) throws Exception {
        try {
            Configuration cfg = new Configuration();                                                //通过Freemaker的Configuration读取相应的ftl
            cfg.setEncoding(Locale.CHINA, "utf-8");
            cfg.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources() + "/" + ftlPath));        //设定去哪里读取相应的ftl模板文件
            Template temp = cfg.getTemplate(ftlName);                                                //在模板文件目录中找到名称为name的文件
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}