package net.whir.hos.inspection.pc.service.impl;

import net.whir.hos.inspection.pc.bean.Files;
import net.whir.hos.inspection.pc.dao.FileDao;
import net.whir.hos.inspection.pc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/27 10:07 上午
 */

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    /**
     * 根据特殊事件ID 查询详细信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Files> selectById(Long id) {
        Example example = new Example(Files.class);
        example.createCriteria().andEqualTo("specialId", id);
        return fileDao.selectByExample(example);
    }

    @Override
    public void insert(Files files) {
        fileDao.insert(files);
    }
}
