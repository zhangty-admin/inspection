package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.PageHelper;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.bean.UnifiedRemind;
import net.whir.hos.inspection.pc.bean.UnifiedRemindDepartment;
import net.whir.hos.inspection.pc.bean.UnifiedRemindDepartmentIds;
import net.whir.hos.inspection.pc.dao.RemindDao;
import net.whir.hos.inspection.pc.dao.UnifiedRemindDepartmentDao;
import net.whir.hos.inspection.pc.service.RemindService;
import net.whir.hos.inspection.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/15 10:10 上午
 */

@Service
@Transactional
public class RemindServiceImpl implements RemindService {

    @Autowired
    private RemindDao remindDao;
    @Autowired
    private UnifiedRemindDepartmentDao unifiedRemindDepartmentDao;

    /**
     * 分页查询提醒
     *
     * @param remindPageRequest
     * @return
     */
    @Override
    public List<UnifiedRemind> findPage(PageRequest<UnifiedRemind> remindPageRequest) {
        PageHelper.startPage(remindPageRequest.getPageNum(), remindPageRequest.getPageSize());
        return remindDao.selectRemindPage(remindPageRequest.getObj());
    }

    /**
     * 新增统一提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void add(UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        //统一提醒新增
        remindDao.insertSelective(unifiedRemindDepartment.getUnifiedRemind());
        //新增部门和提醒的中间表
        MappingTableUtil.relateMapping(unifiedRemindDepartmentDao, new UnifiedRemindDepartment(),
                unifiedRemindDepartment.getUnifiedRemind().getId(), unifiedRemindDepartment.getDepartmentIds(), "insert");

    }

    /**
     * 删除统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void deleteRemindById(UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        //删除部门和提醒的中间表
        if (!delete(unifiedRemindDepartment)) return;
        //删除统一提醒
        remindDao.deleteByPrimaryKey(unifiedRemindDepartment.getUnifiedRemind().getId());
    }

    /**
     * 修改统一新增提醒
     *
     * @param unifiedRemindDepartment
     */
    @Override
    public void updateRemind(UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        //修改部门和提醒的中间表
        if (!delete(unifiedRemindDepartment)) return;
        MappingTableUtil.relateMapping(unifiedRemindDepartmentDao, new UnifiedRemindDepartment(),
                unifiedRemindDepartment.getUnifiedRemind().getId(), unifiedRemindDepartment.getDepartmentIds(), "insert");

        //修改统一新增
        remindDao.updateByPrimaryKeySelective(unifiedRemindDepartment.getUnifiedRemind());
    }

    /**
     * 删除中间表信息
     *
     * @param unifiedRemindDepartment
     * @return
     */
    private boolean delete(UnifiedRemindDepartmentIds unifiedRemindDepartment) {
        Long id = unifiedRemindDepartment.getUnifiedRemind().getId();
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        Example example = new Example(UnifiedRemindDepartment.class);
        example.createCriteria().andEqualTo("unifiedRemindId", id);
        unifiedRemindDepartmentDao.deleteByExample(example);
        return true;
    }
}
