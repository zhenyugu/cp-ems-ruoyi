package com.cpems.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cpems.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * 供应商库管理视图对象 purveyor_info
 *
 * @author cpems
 * @date 2023-09-11
 */
@Data
@ExcelIgnoreUnannotated
public class PurveyorInfoVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long purveyorId;

    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码")
    private String purveyorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String purveyorName;

    /**
     * 供应商性质
     */
    @ExcelProperty(value = "供应商性质")
    private String nature;

    /**
     * 供货类型
     */
    @ExcelProperty(value = "供货类型")
    private String supplyType;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名")
    private String contractName;

    /**
     * 联系人职位
     */
    @ExcelProperty(value = "联系人职位")
    private String contractPost;

    /**
     * 联系人电话
     */
    @ExcelProperty(value = "联系人电话")
    private String contractPhone;

    /**
     * 联系人邮箱
     */
    @ExcelProperty(value = "联系人邮箱")
    private String contractEmail;

    /**
     * 供应商地址
     */
    @ExcelProperty(value = "供应商地址")
    private String address;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}

