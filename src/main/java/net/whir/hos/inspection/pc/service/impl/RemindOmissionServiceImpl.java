package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.*;
import net.whir.hos.inspection.pc.dao.RemindOmissionDao;
import net.whir.hos.inspection.pc.dao.RemindOmissionDepartmentDao;
import net.whir.hos.inspection.pc.service.RemindOmissionService;
import net.whir.hos.inspection.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/17 3:33 下午
 */

@Service
@Transactional
public class RemindOmissionServiceImpl implements RemindOmissionService {

    @Autowired
    private RemindOmissionDao remindOmissionDao;
    @Autowired
    private RemindOmissionDepartmentDao remindOmissionDepartmentDao;

    /**
     * 分页查询漏检提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public Page<RemindOmission> findPage(PageRequest<RemindOmission> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return (Page<RemindOmission>) remindOmissionDao.findPageRemindOmission(remindPageRequest.getObj());
    }

    /**
     * 新增漏检提醒
     *
     * @param remindOmissionDepartmentIds
     */
    @Override
    public void add(RemindOmissionDepartmentIds remindOmissionDepartmentIds) {
        //添加漏检提醒
        remindOmissionDao.insert(remindOmissionDepartmentIds.getRemindOmission());
        //添加中间表信息
        MappingTableUtil.relateMapping(remindOmissionDepartmentDao, new RemindOmissionDepartment(),
                Long.valueOf(remindOmissionDepartmentIds.getRemindOmission().getId()), remindOmissionDepartmentIds.getDepartmentIds(), "insert");
    }

    /**
     * 删除漏检新增提醒
     *
     * @param remindOmissionId
     */
    @Override
    public void deleteRemindById(Integer remindOmissionId) {
        //删除中间表信息
        delete(remindOmissionId);
        //删除漏检信息
        remindOmissionDao.deleteByPrimaryKey(remindOmissionId);
    }

    /**
     * 修改漏检新增提醒
     *
     * @param remindOmissionDepartmentIds
     */
    @Override
    public void updateRemind(RemindOmissionDepartmentIds remindOmissionDepartmentIds) {
        //删除中间表信息
        delete(remindOmissionDepartmentIds.getRemindOmission().getId());
        //新增中间表信息
        MappingTableUtil.relateMapping(remindOmissionDepartmentDao, new RemindOmissionDepartment(),
                Long.valueOf(remindOmissionDepartmentIds.getRemindOmission().getId()), remindOmissionDepartmentIds.getDepartmentIds(), "insert");
        //修改漏检信息
        remindOmissionDao.updateByPrimaryKeySelective(remindOmissionDepartmentIds.getRemindOmission());
    }


    /**
     * 删除中间表信息
     *
     * @param remindOmissionId
     * @return
     */
    private boolean delete(Integer remindOmissionId) {
        if (StringUtils.isEmpty(remindOmissionId)) {
            return false;
        }
        Example example = new Example(RemindOmissionDepartment.class);
        example.createCriteria().andEqualTo("remindOmissionId", remindOmissionId);
        remindOmissionDepartmentDao.deleteByExample(example);
        return true;
    }
}
