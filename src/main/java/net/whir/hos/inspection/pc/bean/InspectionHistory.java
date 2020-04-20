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
 * @Date: 2020/4/20 4:29 下午
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "INSPECTION_HISTORY")
@ApiModel(value = "InspectionHistory/历史巡检")
public class InspectionHistory implements Serializable {

    @Id
    private Long id;
}
