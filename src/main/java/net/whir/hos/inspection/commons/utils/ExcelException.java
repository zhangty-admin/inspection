package net.whir.hos.inspection.commons.utils;

/**
 * ExcelException:自定义ExcelException异常
 *
 * @author zty
 * @date 2020/4/14 1:42 下午
 */
public class ExcelException extends RuntimeException {
    public ExcelException(String message) {
        super(message);
    }
}
