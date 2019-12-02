package com.iqianjin.test.teststage.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "model基类")
public class BaseBean implements Serializable {

    private static final long serialVersionUID = 6582433931486644561L;

    @ApiModelProperty(value = "表的主键")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
