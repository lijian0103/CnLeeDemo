package cn.cnlee.test.javamethod;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.cnlee.test.javamethod.bean.ObjectBean;

/**
 * @Description invoke getter
 * @Author cnlee
 * @Date 2021/6/7
 * @Version 1.0
 */
public class InvokeCase {

    /**
     * main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        ObjectBean bean = ObjectBean.builder().id(333).msg("msg").build();
        getProperty("id", bean);
        getProperty("msg", bean);
        setProperty("id", 444, bean);
        System.err.print(bean);
        setProperty("msg", "msg444", bean);
        System.err.print(bean);
    }

    /**
     * get property by invoke.
     *
     * @param fieldName fieldName
     * @param object    object
     * @return Object
     */
    public static Object getProperty(String fieldName, Object object) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, object.getClass());
            Method readMethod = descriptor.getReadMethod();
            return readMethod.invoke(object);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * set property by invoke.
     *
     * @param fieldName fieldName
     * @param value     value
     * @param object    object
     */
    public static void setProperty(String fieldName, Object value, Object object) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, object.getClass());
            Method writeMethod = descriptor.getWriteMethod();
            writeMethod.invoke(object, value);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }
}


