package com.lhsz.bandian.AOP;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义操作日志注解
 * @author  lizhiguo
 * 2020-8-5
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface OperLog {
    String operModul() default "模块名称"; // 操作模块
    Type operType() default Type.ADD;// 操作类型
    String operDesc() default "";  // 操作说明

    enum Type{
        OTHER(0),ADD(1),UPDATE(2),DETELE(3);
        private final int value;
        Type(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    };

}
