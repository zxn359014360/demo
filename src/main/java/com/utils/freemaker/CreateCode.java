package com.utils.freemaker;



import com.utils.DateUtil;
import com.utils.kit.StrKit;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * Created by LENOVO on 2016/2/27.
 */
public class CreateCode {

    private static String user = "root";
    private static String password = "";
    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf-8";

    private static String table = "user";
    private static String packageName = "user";

    private static String bastPath = "D:\\demo\\";

    private static String path = "demo\\";

    private static String author = "zhangxn";


    public static void main(String[] args) throws Exception {
        List<String[]> fieldList = getCloumsList();
        String model = StrKit.toCamelCase(table);
        String modelJava = StrKit.firstCharToUpperCase(model);
        String tableUpperStr = StrKit.firstCharToUpperCase(modelJava);
        String serverName = tableUpperStr + "Manager";
        String daoJava = tableUpperStr + "Dao";
        String daoJavastr = model + "Dao";
        String controllerName = tableUpperStr + "controllerName";
        String xmlName = modelJava + "Mapper";
        String serverJavastr = modelJava + "Manager";
        String updateStrserverJavastr = model + "Manager";
        Map<String, Object> root = new HashMap<String, Object>();        //创建数据模型
        StringBuffer columnStr = new StringBuffer();
        StringBuffer baseSqltr = new StringBuffer();
        StringBuffer javaStr = new StringBuffer();
        StringBuffer batchjavaStr = new StringBuffer();

        List<String> modelItemList = new ArrayList<String>();

        List<String[]> modelItemForEditList = new ArrayList<String[]>();

        List<String> updatesTrList = new ArrayList<String>();
        List<String> dynamicWhereList = new ArrayList<String>();
        List<String> dynamicUpdateList = new ArrayList<String>();
        List<String> pageTableList = new ArrayList<String>();
        List<String> pageAddList = new ArrayList<String>();
        String keyVar = "";
        String keyColumn = "";
        String keyType = "";

        String lastPackageName = packageName.substring(packageName.lastIndexOf(".") + 1);

        Set inportClassNameList = new HashSet();
        for (int i = 0; i < fieldList.size(); ++i) {

            StringBuffer dynamicWhere = new StringBuffer();
            StringBuffer dynamicUpdate = new StringBuffer();
            String[] cloums = fieldList.get(i);
            if (i == 0) {
                keyColumn = cloums[1];
                keyVar = cloums[2];
                keyType = cloums[4];
            }
            baseSqltr.append(baseSqltr.length() > 0 ? "," : "").append("a.").append(cloums[1]).append(" ").append(cloums[2]);
            columnStr.append(columnStr.length() > 0 ? "," : "").append(cloums[1]);
            javaStr.append(javaStr.length() > 0 ? "," : "").append("#").append(cloums[2]).append("#");

            modelItemList.add(cloums[2]);

            String[] array = new String[2];

            array[0] =cloums[2];

            StringBuffer buffer = new StringBuffer();
            buffer.append("${model.").append(cloums[2]).append("!}");
            array[1] =buffer.toString();

            modelItemForEditList.add(array);

            batchjavaStr.append(batchjavaStr.length() > 0 ? "," : "").append("#list[].").append(cloums[2]).append("#");

            dynamicWhere.append(" <isNotEmpty property=\"").append(cloums[2]).append("\" prepend=\"AND\">")
                    .append(" ").append("a.").append(cloums[1]).append(" = #").append(cloums[2]).append("#").append(" </isNotEmpty>");
            dynamicWhereList.add(dynamicWhere.toString());
            if (i > 0) {
                dynamicUpdate.append(" <isNotEmpty property=\"").append(cloums[2]).append("\" prepend=\",\">")
                        .append(" ").append("a.").append(cloums[1]).append(" = #").append(cloums[2]).append("#").append(" </isNotEmpty>");
                dynamicUpdateList.add(dynamicUpdate.toString());
            }

            inportClassNameList.add(cloums[0]);
            StringBuffer pageTableStr = new StringBuffer();
            pageTableStr.append("{title: '").append(cloums[2]).append("',field: '").append(cloums[2]).append("',align: 'center'},");
            pageTableList.add(pageTableStr.toString());

            StringBuffer pageAddStr = new StringBuffer();
            pageAddStr.append("<div class=\"clearfix\"><span class=\"destrct\">").append(cloums[2]).append("</span><div class=\"selects\"><input class=\"nl240_input\" name=\"").append(cloums[2])
                    .append("\" id=\"").append(cloums[2]).append("\" type=\"text\"></div><span class=\"desctr\"><em>*</em>请输入").append(cloums[2]).append("</span></div>");
            pageAddList.add(pageAddStr.toString());

        }

        root.put("modelItemList", modelItemList);
        root.put("keyColumn", keyColumn);
        root.put("keyVar", keyVar);
        root.put("keyType", keyType);
        root.put("fieldList", fieldList);
        root.put("modelJava", modelJava);
        root.put("daoJava", daoJava);
        root.put("modelValue", model);
        root.put("table", table);
        root.put("columnStr", columnStr);
        root.put("modelColumns", baseSqltr.toString());
        root.put("dynamicWhereList", dynamicWhereList);
        root.put("dynamicUpdateList", dynamicUpdateList);

        root.put("javaStr", javaStr);
        root.put("batchjavaStr", batchjavaStr);

        root.put("updatesTrList", updatesTrList);
        root.put("batchjavaStr", batchjavaStr.toString());
        root.put("pageTableList", pageTableList);
        root.put("pageAddList", pageAddList);
        root.put("packageName", packageName);
        root.put("author", author);
        root.put("createTime", DateUtil.getNowDateStr());
        root.put("serverName", serverName);
        root.put("xmlName", xmlName);
        root.put("controllerName", controllerName);
        root.put("serverJavastr", serverJavastr);
        root.put("updateStrserverJavastr", updateStrserverJavastr);
        root.put("inportClassNameList", inportClassNameList);
        root.put("daoJavastr", daoJavastr);

        root.put("modelItemForEditList", modelItemForEditList);


        root.put("lastPackageName", lastPackageName);

        String packageNamePath = packageName.replace(".", "\\");

        String lastPackageNamePath = lastPackageName.replace(".", "\\");

        //ftl路径
        String ftlPath = "ftl/createCode";


        String javaModelPath = bastPath + path + "\\src\\main\\java\\com\\model\\" + packageNamePath;
        (new File(javaModelPath)).mkdirs();
        /*生成model*/
        Freemarker.printFile("model.ftl", root, javaModelPath + modelJava + ".java", ftlPath);

        String javaDaoPath = bastPath + path + "\\src\\main\\java\\com\\dao\\" + packageNamePath ;
        (new File(javaDaoPath)).mkdirs();
        Freemarker.printFile("dao.ftl", root, javaDaoPath + daoJava + ".java", ftlPath);

        String xmflPath = bastPath + path + "\\src\\main\\resources\\mapper\\" + packageNamePath + "\\";
        (new File(xmflPath)).mkdirs();
        Freemarker.printFile("xml.ftl", root, xmflPath + xmlName + ".xml", ftlPath);

        String controllerPath = bastPath + path + "\\src\\main\\java\\com\\controller";
        (new File(controllerPath)).mkdirs();
        Freemarker.printFile("controller.ftl", root, controllerPath + modelJava + "Controller.java", ftlPath);

        String pagePath = bastPath + path + "\\src\\main\\webapp\\view\\" + lastPackageNamePath + "\\" + modelJava + "\\";
        (new File(pagePath)).mkdirs();
        Freemarker.printFile("page_list.ftl", root, pagePath + modelJava + ".html", ftlPath);

        Freemarker.printFile("page_add.ftl", root, pagePath + modelJava + "_add.html", ftlPath);

        Freemarker.printFile("page_edit.ftl", root, pagePath + modelJava + "_edit.html", ftlPath);

    }


    private static List<String[]> getCloumsList() throws Exception {
        List<String[]> fieldList = new ArrayList<String[]>();
        Connection conn = null;
        Class.forName(jdbcDriver);
        conn = DriverManager.getConnection(jdbcUrl, user, password);
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("select * from " + table + " where 1=2");
            ResultSetMetaData rsd = pst.executeQuery().getMetaData();
            for (int i = 0; i < rsd.getColumnCount(); i++) {
                String[] cloums = new String[6];
                String columnClassName = rsd.getColumnClassName(i + 1).replaceAll("sql", "lang").replaceAll("Date", "String").replaceAll("Timestamp", "String");
                int index = columnClassName.lastIndexOf(".");
                cloums[0] = columnClassName;
                cloums[1] = rsd.getColumnName(i + 1);//数据库字段
                cloums[2] = StrKit.toCamelCase(cloums[1].toLowerCase());//java字段
                cloums[3] = StrKit.firstCharToUpperCase(cloums[2]);//首字母大写的java字段
                cloums[4] = columnClassName.substring(index + 1);
                cloums[5] = rsd.getColumnTypeName(i + 1);
                fieldList.add(cloums);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                pst.close();
                pst = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return fieldList;
    }
}