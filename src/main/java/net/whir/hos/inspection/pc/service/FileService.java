package net.whir.hos.inspection.pc.service;

import net.whir.hos.inspection.pc.bean.Files;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/27 10:06 上午
 */

public interface FileService {

    /**
     * 根据特殊事件ID 查询详细信息
     *
     * @param id
     * @return
     */
    List<Files> selectById(Long id);

    /**
     *
     * @param files
     */
    void insert(Files files);
}
