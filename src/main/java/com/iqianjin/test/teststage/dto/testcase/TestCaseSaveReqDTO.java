package com.iqianjin.test.teststage.dto.testcase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("手动添加用例时新增的对象")
public class TestCaseSaveReqDTO {
    @ApiModelProperty(value = "包括现有的项目，AppServer，Activity等", required = true)
    private String platInfo;

    @ApiModelProperty(value = "现有项目对应的功能模块", required = true)
    private String featureModule;

    @ApiModelProperty("用例编号，非必须")
    private String caseNo;

//    private String env;

    @ApiModelProperty(value = "用例描述信息", required = true)
    private String description;

    @ApiModelProperty(value = "具体api接口", required = true)
    private String apiInfo;

//    private String headers;

    @ApiModelProperty(value = "请求接口方式", required = true)
    private String method;

    @ApiModelProperty(value = "参数，json格式直接传入，普通格式，各参数key=value用&连接，例如a=1111&b=2222&c=3333，如果没有参数，可以为空")
    private String params;

    @ApiModelProperty("格式为： userId,nickName")
    private String userInfo;

    @ApiModelProperty("如果接口需要带cookie，填Y；如果不需要，为空即可")
    private String cookie;

    @ApiModelProperty("同Cookie，需要填Y；不需要，为空即可")
    private String token;

    @ApiModelProperty("接口执行前的sql，sql除了qianjin库外一定要带有库名，要不执行不成功,格式为：sql语句;sql语句;... ，多个语句以分号隔开；如果不需要清除，为空即可\"")
    private String mysql;

    @ApiModelProperty("接口执行前的mongo")
    private String mongodb;

    @ApiModelProperty("通mysql，格式：set key value;del key（多个语句以;隔开，仅支持set和del）")
    private String redis;

    @ApiModelProperty("接口调用成功后，验证数据库")
    private String expectMysql;

    @ApiModelProperty("接口调用成功后，验证返回值，格式为：需验证字段的jsonpath:期望值,需验证字段的jsonpath2:期望值2...，多个验证点以逗号隔开；如果不需验证，为空即可")
    private String expectResponse;

    @ApiModelProperty("测试完毕后，清空数据库中测试生成的数据，sql除了qianjin库外一定要带有库名，要不执行不成功,格式为：sql语句;sql语句;... ，多个语句以分号隔开；如果不需要清除，为空即可")
    private String clearMysql;

    @ApiModelProperty(" 清除Redis的key，格式为：del key1;del key2...，多个语句以逗号隔开；如果不需要清除，为空即可")
    private String clearRedis;


    @ApiModelProperty("新增时，默认为1")
    private Integer flag = 1;

    @ApiModelProperty("新增时，默认为All")
    private String branch = "All";

    @ApiModelProperty("新增时，默认为0")
    private Integer failCount = 0;

    @ApiModelProperty("新增时，默认为0")
    private Integer succCount = 0;

    @ApiModelProperty("预留位——现在作为连接另外redis的参数")
    private String reserved;
}
