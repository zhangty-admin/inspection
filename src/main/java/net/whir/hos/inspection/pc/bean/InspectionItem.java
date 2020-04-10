package net.whir.hos.inspection.pc.bean;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/9 8:39 下午
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "INSPECTION_ITEM")
@ApiModel(value = "巡检计划与检查项中间表")
public class InspectionItem implements Serializable {

    @Id
    private Long id;

    private Long inspectionId;

    private Long itemId;
}
