package com.iqianjin.test.teststage.service.Imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.tools.CreateInterestDTO;
import com.iqianjin.test.teststage.dto.tools.CreateRedBagDTO;
import com.iqianjin.test.teststage.dto.tools.SendInterestDTO;
import com.iqianjin.test.teststage.dto.tools.SendRedBagDTO;
import com.iqianjin.test.teststage.service.CommonToolsService;
import com.iqianjin.test.teststage.utils.NewHttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import com.iqianjin.test.teststage.coupon.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;

@Service
@Slf4j
public class CommonToolsServiceImpl implements CommonToolsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RestTemplate restTemplate;

    private static String driveClass = "com.mysql.jdbc.Driver";

    @Value("${coupon.redBag}")
    private String redBagUrlPart;

    @Value("${coupon.interest}")
    private String interestUrlPart;

    @Override
    public Result createRedBag(CreateRedBagDTO createRedBagDTO) {
        String url = "jdbc:mysql://" + createRedBagDTO.getDataHost() +":3306/qianjin?useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(driveClass)
                .username(createRedBagDTO.getDataUseName())
                .password(createRedBagDTO.getDataPassword())
                .url(url)
                .build();
        jdbcTemplate.setDataSource(dataSource);
        String sql = "";
        if (createRedBagDTO.getRedbagParType() == 1) {
            sql = "INSERT INTO `qianjin`.`redbag`(`redbag_name`, " +
                    "`redbag_par_type`, " +
                    "`redbag_par`, " +
                    "`redbag_status`, " +
                    "`redbag_validity_date`, " +
                    "`redbag_create_user`, " +
                    "`redbag_create_date`, " +
                    "`redbag_scope`, " +
                    "`remark`, " +
                    "`redbag_update_user`, " +
                    "`redbag_datetime`, " +
                    "`redbag_type`, " +
                    "`quota`, " +
                    "`can_export`, `is_all`, `redbag_period`, `redbag_validity_date_feature`, `redbag_biz_type`) VALUES " +
                    "('" + createRedBagDTO.getRedbagName() + "', "
                    + createRedBagDTO.getRedbagParType() +", "
                    + createRedBagDTO.getRedbagPar() + ", "
//                + createRedBagDTO.getRedbagParRule() + ", "
                    + "2, "
                    + "100, "
                    + "'" + createRedBagDTO.getRedbagCreateUser() + "'" + ", "
                    + "now(), "
                    + "2, "
                    + "'" + createRedBagDTO.getRemark() + "'" + ", "
                    + "'" + createRedBagDTO.getRedbagCreateUser() + "'" + ", "
                    + "now(), "
                    + createRedBagDTO.getRedbagType() + ", "
                    + createRedBagDTO.getQuota() + ", "
                    + "0, 0, 12, 0, 1);";
        }else {
            sql = "INSERT INTO `qianjin`.`redbag`(`redbag_name`, " +
                    "`redbag_par_type`, " +
                    "`redbag_par_rule`, " +
                    "`redbag_par`, " +
                    "`redbag_status`, " +
                    "`redbag_validity_date`, " +
                    "`redbag_create_user`, " +
                    "`redbag_create_date`, " +
                    "`redbag_scope`, " +
                    "`remark`, " +
                    "`redbag_update_user`, " +
                    "`redbag_datetime`, " +
                    "`redbag_type`, " +
                    "`quota`, " +
                    "`can_export`, `is_all`, `redbag_period`, `redbag_validity_date_feature`, `redbag_biz_type`) VALUES " +
                    "('" + createRedBagDTO.getRedbagName() + "', "
                    + 2 + ", "
                    + "null, "
                    + createRedBagDTO.getRedbagParRule() + ", "
                    + "2, "
                    + "100, "
                    + "'" + createRedBagDTO.getRedbagCreateUser() + "'" + ", "
                    + "now(), "
                    + "2, "
                    + "'" + createRedBagDTO.getRemark() + "'" + ", "
                    + "'" + createRedBagDTO.getRedbagCreateUser() + "'" + ", "
                    + "now(), "
                    + createRedBagDTO.getRedbagType() + ", "
                    + createRedBagDTO.getQuota() + ", "
                    + "0, 0, 12, 0, 1);";
        }
        try {
            log.info("开始在{}数据库里执行sql： {}", createRedBagDTO.getDataHost(), sql);
            jdbcTemplate.execute(sql);
            log.info("执行sql成功");
        }catch (Exception e) {
            e.printStackTrace();
            log.info("创建红包时执行sql失败");
            return Result.failMsg("创建红包失败");
        }
        return Result.success("创建成功");
    }

    @Override
    public Result createInterest(CreateInterestDTO createInterestDTO) {
        String url = "jdbc:mysql://" + createInterestDTO.getDataHost() +":3306/qianjin?useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(driveClass)
                .username(createInterestDTO.getDataUseName())
                .password(createInterestDTO.getDataPassword())
                .url(url)
                .build();
        jdbcTemplate.setDataSource(dataSource);
        String sql = "INSERT INTO  `qianjin`.`finance_plan_add_interest` (`interest_name`, " +
                "`interest_type`, " +
                "`interest_limit`, " +
                "`interest_validity_date`, " +
                "`interest_continue_day`, " +
                "`interest_status`, " +
                "`create_time`, " +
                "`update_time`, " +
                "`create_user`, " +
                "`update_user`, " +
                "`memo`, " +
                "`interest_feature`, " +
                "`interest_validity_date_feature`, " +
                "`is_all`, " +
                "`quota`, " +
                "`interest_condition`, " +
                "`ceiling`, " +
                "`interest_product_days`, " +
                "`plan_period`, " +
                "`product_scope`, " +
                "`send_channel`,) VALUES (" +
                "'" + createInterestDTO.getInterestName() +"'," +
                "'" + createInterestDTO.getInterestType() +"'," +
                "'" + createInterestDTO.getInterestLimit() +"'," +
                "'" + createInterestDTO.getInterestValidityDate() +"'," +
                "'" + createInterestDTO.getInterestContinueDay() +"'," +
                "2," +
                "new()," +
                "new()," +
                "'" + createInterestDTO.getCreateUser() +"'," +
                "'" + createInterestDTO.getCreateUser() +"'," +
                "'" + createInterestDTO.getMemo() +"'," +
                "'" + createInterestDTO.getInterestFeature() +"'," +
                "0," +
                "0," +
                "'" + createInterestDTO.getQuota() +"'," +
                "'" + createInterestDTO.getInterestCondition() +"'," +
                "0," +
                "null," +
                "'" + createInterestDTO.getPlanPeriod() +"'," +
                "'" + createInterestDTO.getProductScope() +"'," +
                "0" +
                ")";
        try {
            jdbcTemplate.execute(sql);
            log.info("创建加息券:{}时执行sql成功", createInterestDTO.getInterestName());
        }catch (Exception e) {
            e.printStackTrace();
            log.info("创建加息券:{}时执行sql失败,{}", createInterestDTO.getInterestName(), e);
            return Result.failMsg("创建加息券失败");
        }
        return Result.success("创建成功");
    }

    @Override
    public Result sendRedBag(SendRedBagDTO sendRedBagDTO) {
        try {
            String url = sendRedBagDTO.getCouponUrl() + redBagUrlPart;
            //拼接请求coupon服务的发放红包的接口的请求体
            SendRedBagReqDTO sendRedBagReqDTO = new SendRedBagReqDTO();
            sendRedBagReqDTO.setBizId(String.valueOf(new Date()));
            sendRedBagReqDTO.setUserId(sendRedBagDTO.getUserId());
            sendRedBagReqDTO.setRedBagIdList(sendRedBagDTO.getRedBagIdList());

            CouponHeader couponHeader = new CouponHeader();
            couponHeader.setAppCode("iqianjinTest");

            CouponRequest<SendRedBagReqDTO> request = new CouponRequest<>();
            request.setBody(sendRedBagReqDTO);
            request.setHeader(couponHeader);

            log.info("拼接后的请求体为:{}", request);
            log.info("开始请求Coupon服务url:{}",url);

            CouponResult result = restTemplate.postForObject(url, NewHttpClientUtil.getRequestParam(request), CouponResult.class);
            log.info("请求coupon服务的返回体为:{}", JSON.toJSONString(result));
            if (result.getCode() == 1) {
                return Result.success("发送成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.info("发送红包失败");
        }
        return Result.failMsg("发送红包失败");
    }

    @Override
    public Result sendInterest(SendInterestDTO sendInterestDTO) {
        try {
            String url = sendInterestDTO.getCouponUrl() + interestUrlPart;
            //拼接请求coupon服务的发放加息券的接口的请求体
            SendAddInterestReqDTO sendAddInterestReqDTO = new SendAddInterestReqDTO();
            sendAddInterestReqDTO.setAddInterestIdList(sendInterestDTO.getAddInterestList());
            sendAddInterestReqDTO.setUserId(sendInterestDTO.getUserId());
            sendAddInterestReqDTO.setBizId(String.valueOf(new Date()));

            CouponHeader couponHeader = new CouponHeader();
            couponHeader.setAppCode("iqianjinTest");

            CouponRequest<SendAddInterestReqDTO> request = new CouponRequest<>();
            request.setBody(sendAddInterestReqDTO);
            request.setHeader(couponHeader);

            log.info("拼接后的请求体为:{}", request);
            log.info("开始请求Coupon服务url:{}",url);

            CouponResult result = restTemplate.postForObject(url, NewHttpClientUtil.getRequestParam(request), CouponResult.class);
            log.info("请求coupon服务的返回体为:{}", JSON.toJSONString(result));
            if (result.getCode() == 1) {
                return Result.success("发送成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.info("发送红包失败");
        }
        return Result.failMsg("发送加息券失败");
    }
}
