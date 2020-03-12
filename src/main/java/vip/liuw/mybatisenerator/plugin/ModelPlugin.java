package vip.liuw.mybatisenerator.plugin;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 添加自定义的BaseEntity用于model的继承，删除model中的一些字段，这些字段在BaseEntity中定义
 * 使用lombok注解，删除所有setter、getter方法
 */
public class ModelPlugin extends PluginAdapter {

    private String superClass;

    private String isSamePackage;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        superClass = properties.getProperty("superClass");
        isSamePackage = properties.getProperty("isSamePackage");
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //===============在model名字后面加上Entity=================
//        Class<TopLevelClass> tableClass = TopLevelClass.class;
//        try {
//            java.lang.reflect.Field typeField = tableClass.getSuperclass().getDeclaredField("type");
//            typeField.setAccessible(true);
//
//            FullyQualifiedJavaType tableType = (FullyQualifiedJavaType) typeField.get(topLevelClass);
//
//            java.lang.reflect.Field baseShortNameField = FullyQualifiedJavaType.class.getDeclaredField("baseShortName");
//            java.lang.reflect.Field baseQualifiedNameField = FullyQualifiedJavaType.class.getDeclaredField("baseQualifiedName");
//            baseShortNameField.setAccessible(true);
//            baseQualifiedNameField.setAccessible(true);
//            baseShortNameField.set(tableType, baseShortNameField.get(tableType).toString() + "Entity");
//            baseQualifiedNameField.set(tableType, baseQualifiedNameField.get(tableType).toString() + "Entity");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        //==================================================

        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        List<String> columnNames = new ArrayList<>();
        for (IntrospectedColumn column : columns) {
            columnNames.add(column.getActualColumnName());
        }
        //有这三个字段的继承BaseEntity,删除这三个字段和id字段
        if (columnNames.contains("create_date") && columnNames.contains("update_date") && columnNames.contains("expire_date")) {
            //添加继承
            if (StringUtils.isNotBlank(superClass)) {
                removeField(topLevelClass.getFields());
                topLevelClass.setSuperClass(superClass);
                if ("false".equals(isSamePackage))
                    topLevelClass.addImportedType(superClass);
            }
        } else {//把id的@GeneratedValue注解改一下，原来是@GeneratedValue(strategy = GenerationType.IDENTITY)
            List<String> annotations = topLevelClass.getFields().get(0).getAnnotations();
            for (int i = 0; i < annotations.size(); i++) {
                String annotation = annotations.get(i);
                if (!annotation.equals("@Id") && !annotation.startsWith("@Column")) {
                    annotations.remove(i--);
                }
            }
            //topLevelClass.getFields().get(0).getAnnotations().add("@GeneratedValue(generator = \"JDBC\")");

            //实现Serializable接口
            topLevelClass.addImportedType("java.io.Serializable");
            topLevelClass.addSuperInterface(new FullyQualifiedJavaType("Serializable"));
        }
        //去除所有方法，使用lombok注解
        topLevelClass.getMethods().clear();
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        return true;
    }


    public void removeField(List<Field> fieldList) {
        removeField(fieldList, "id");
        removeField(fieldList, "createDate");
        removeField(fieldList, "updateDate");
        removeField(fieldList, "expireDate");
    }

    public void removeField(List<Field> fieldList, String columnName) {
        for (int i = 0; i < fieldList.size(); i++) {
            if (fieldList.get(i).getName().equals(columnName)) {
                fieldList.remove(i--);
            }
        }
    }


    /**
     * Mapper接口修改
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加@Component注解
        interfaze.addAnnotation("@Component");
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }
}
