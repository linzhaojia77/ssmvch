package lzj.baba.annotation;

import java.lang.annotation.*;

/**
 * @Description: TODO
 * @author: lzj
 * @date: 2021年08月24日 16:51
 * 用于标记匿名访问方法
 */
@Inherited//类继承关系中子类会继承父类的这个注解，接口继承关系是无论有没这注解都不会继承注解的
@Documented//表明需要被javadoc工具记录，生产对应API
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})//注解范围，用于方法和注解类型上
@Retention(RetentionPolicy.RUNTIME)//注解被保留范围：在运行时保留有效
public @interface AnonymousAccess {
}
