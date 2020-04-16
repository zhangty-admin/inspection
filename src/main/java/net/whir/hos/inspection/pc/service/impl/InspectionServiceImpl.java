package net.whir.hos.inspection.pc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import net.whir.hos.inspection.pc.bean.Inspection;
import net.whir.hos.inspection.pc.bean.InspectionItem;
import net.whir.hos.inspection.pc.bean.InspectionItemIds;
import net.whir.hos.inspection.commons.entity.PageRequest;
import net.whir.hos.inspection.pc.dao.InspectionDao;
import net.whir.hos.inspection.pc.dao.InspectionItemDao;
import net.whir.hos.inspection.pc.service.InspectionService;
import net.whir.hos.inspection.utils.MappingTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: zty
 * @Date: 2020/4/9 4:51 下午
 */
@Service
@Slf4j
@Transactional
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionDao inspectionDao;
    @Autowired
    private InspectionItemDao inspectionItemDao;

    /**
     * 新增巡检计划
     *
     * @param inspectionItemIds
     */
    @Override
    public void add(InspectionItemIds inspectionItemIds) {
        List<Long> itemIds = inspectionItemIds.getItemIds();
        Inspection inspection = inspectionItemIds.getInspection();

        InspectionItem inspectionItem = new InspectionItem();
        inspectionDao.insert(inspection);
        //建立和插入关系表操作
        MappingTableUtil.relateMapping(inspectionItemDao, inspectionItem, inspection.getId(), itemIds, "insert");
    }

    /**
     * 修改巡检计划
     *
     * @param inspectionItemIds
     */
    @Override
    public void update(InspectionItemIds inspectionItemIds) {
        List<Long> itemIds = inspectionItemIds.getItemIds();
        Inspection inspection = inspectionItemIds.getInspection();
        Long id = inspectionItemIds.getInspection().getId();

        if (StringUtils.isEmpty(inspectionItemIds.getInspection().getId())) {
            return;
        }
        Example example = new Example(InspectionItem.class);
        example.createCriteria().andEqualTo("inspectionId", id);
        inspectionItemDao.deleteByExample(example);
        if (!StringUtils.isEmpty(itemIds)) {
            InspectionItem inspectionItem = new InspectionItem();
            MappingTableUtil.relateMapping(inspectionItemDao, inspectionItem, inspection.getId(), itemIds, "insert");
        }
        inspectionDao.updateByPrimaryKeySelective(inspection);
    }

    /**
     * 删除巡检计划
     *
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            inspectionDao.deleteByPrimaryKey(id);
            Example example = new Example(InspectionItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("inspectionId", id);
            inspectionItemDao.deleteByExample(example);
        }
    }

    /**
     * 分页查询巡检计划
     *
     * @param pageRequest
     */
    @Override
    public Page<Inspection> findPage(PageRequest<Inspection> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        Inspection obj = pageRequest.getObj();
        List<Inspection> inspections = inspectionDao.selectInspectionPage(obj);
        return (Page<Inspection>) inspections;
    }

}
