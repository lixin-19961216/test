package com.iqianjin.test.teststage.controller;

import com.iqianjin.test.teststage.base.Result;
import com.iqianjin.test.teststage.dto.TestUserDTO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/testUser")
@Api("创建测试用户")
@Slf4j
public class TestUserController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private String mysqlUserName = "qianjin20160304";
    private String mysqlPassWord = "qianjin20160304";
    private String driveClass = "com.mysql.jdbc.Driver";


    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public Result createUser(TestUserDTO testUserDTO) {
        String mysqlServerName = testUserDTO.getMysqlServerName();
        String url = "jdbc:mysql://" + mysqlServerName + ":3306/qianjin?useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull";
        if (testUserDTO.getMysqlUserName() != null) {
            mysqlUserName = testUserDTO.getMysqlUserName();
            mysqlPassWord = mysqlUserName;
        }
        //默认用户数1
        int userNum = 1;
        //默认格式化规则
        String str="%01d";
        if (testUserDTO.getUserNum() != null) {
            userNum = testUserDTO.getUserNum();
            int len=String.valueOf(userNum).length();
            if(len>4){
                return Result.failMsg("输入数据过大，目前仅支持创建不超过9999个用户！");
            }
            //昵称格式化规则
            str = "%0" + (len) + "d";
        }
        String oldNickName = testUserDTO.getNickName();
        DataSource baseDs = DataSourceBuilder.create().driverClassName(driveClass).url(url).username(mysqlUserName).password(mysqlPassWord).build();
        jdbcTemplate.setDataSource(baseDs);

        List<String> list = new ArrayList<>();
        Integer coverFlag=0;
        if(testUserDTO.getCoverFlag()!=null){
            coverFlag=testUserDTO.getCoverFlag().intValue();
        }

        for (int i = 0; i < userNum; i++) {
            String nickname = oldNickName + String.format(str,i + 1);
            log.info("用户昵称为：" + nickname);
            //检查该前缀昵称是否已存在
            Integer count = jdbcTemplate.queryForObject("select count(*) from user where nickname='" + nickname + "'", Integer.class);
            if(count != 0){
                //如果不覆盖，提示昵称重复，否则覆盖重复昵称
                if(coverFlag==0){
                    return Result.failMsg(nickname+"已存在!请换个昵称前缀或选择覆盖同名昵称！");
                }else{
                    jdbcTemplate.update("update qianjin.user set nickname=id where nickname='" + nickname+"';");
                }
            }
            Long user_id = null;
            //创建用户，并且查询用户id
            String user_create_sql = "INSERT INTO `qianjin`.`user`(`nickname`, `id_no`, `real_name`, `id_verified`, `id_verified_time`, `head_image`, `utm_source`, `invited_id`, `create_time`, `last_login_time`, `cash_password`, `login_permission`, `invest_permission`, `comment_permission`, `update_time`, `total_credit`, `available_credit`, `email`, `mobile_verified`, `email_verified`, `password`, `u_type`, `client_type`, `version`, `attribute`, `id_type`, `blacklist`, `pwd_salt`) VALUES ('" + nickname + "','xyc99aebf772f85224faa0d8aefc6e561858c6650bf647946d2680be12276a96ad20160926', '" + nickname + "', 1, 0, NULL, '100002', NULL, now(), NULL, NULL, 1, 1, 1, now(), 0, 0, NULL, 1, 1, 'E10ADC3949BA59ABBE56E057F20F883E', 1, 3, 0, NULL, 0, 0, '-1');";
            if (jdbcTemplate.update(user_create_sql) == 1) {
                user_id = jdbcTemplate.queryForObject("select id from user where nickname='" + nickname + "'", Long.class);
                jdbcTemplate.update("update user set mobile=id WHERE id=" + user_id);
            }
            //使用查询到的用户id继续创建其他表的数据
            if (user_id != null) {
                //1.准备SQL
                String user_verify_info_sql = "INSERT INTO `qianjin`.`user_verify_info`(`user_id`, `id_verified`, `bank_card_verified`, `entrust_verified`, `hx_account_verified`, `bank_no`, `account_no`, `open_account_time`, `loan_status`, `loan_amount`, `loan_years`, `loan_end_time`, `pay_status`, `pay_amount`, `pay_years`, `pay_end_time`, `create_time`, `update_time`, `version`) VALUES (" + user_id + ", 1, 1, 1, 1, '1', '" + user_id + "', now(), 1, 500, 10, now(), 1, 5000, 10, '2028-01-31 23:59:59',now(),now(), 1);";
                log.info("用户认证信息：" + user_verify_info_sql);
                //2.准备SQL
                String hx_user_sql = "INSERT INTO `qianjin`.`hx_user`(`user_id`, `status`, `create_time`, `update_time`, `version`, `processor`, `record_type`) VALUES (" + user_id + ", 2, now(), now(), 0, NULL, 1);";
                log.info("hx_user信息：" + hx_user_sql);
                //3.准备SQL
                String user_invest_questionnaire_sql = "INSERT INTO `qianjin`.`user_invest_questionnaire`(`user_id`, `level`, `create_time`, `update_time`) VALUES (" + user_id + ", 3,now(),now());";
                log.info("用户投资问卷：" + user_invest_questionnaire_sql);
                //4.准备main_account的数据，并且插入
                int[] category = new int[]{11, 12, 13, 14, 16, 17, 18};
                int amount=200000;
                for (int j = 0; j < category.length; j++) {
                    log.info("主账户类型：" + category[j]);
                    String main_account_sql = "INSERT INTO `qianjin`.`main_account`(`category`, `user_id`, `amount`, `create_time`, `update_time`, `version`) VALUES (" + category[j] + "," + user_id + ","+amount+", now(), now(), 0);";
                    amount=0;
                    log.info("用户主账户sql：" + main_account_sql);
                    jdbcTemplate.execute(main_account_sql);
                }
                //5.插入三张表的数据
                jdbcTemplate.update(user_verify_info_sql);
                jdbcTemplate.update(hx_user_sql);
                jdbcTemplate.update(user_invest_questionnaire_sql);
                //6.把创建的用户昵称放到list并返回
                list.add(nickname);
            }
        }
        try {
            jdbcTemplate.getDataSource().getConnection().close();
            log.info("创建用户时关闭连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
            log.info("创建用户时关闭连接失败");
        }
        return Result.successMsg("用户创建成功", list);
    }

}
