package com.touceng.finance.mapper;

import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.vo.ChannelReportVO;
import com.touceng.domain.vo.ShareProfitVO;
import com.touceng.domain.vo.UserVolumVo;
import com.touceng.domain.vo.WalletLogVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tc_dev on 2018/9/7.
 */
@Repository
public interface DataReportMapper {

    /** 
    * @Description: 渠道报表查询 
    * @Param: [listPageDTO] 
    * @return: java.util.List<com.touceng.domain.vo.ChannelReportVO> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    List<ChannelReportVO> pageChannelReport(ListPageDTO listPageDTO);

    /** 
    * @Description: 分润报表查询 
    * @Param: [listPageDTO] 
    * @return: java.util.List<com.touceng.domain.vo.ShareProfitVO> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    List<ShareProfitVO> pageShareProfitReport(ListPageDTO listPageDTO);

    /** 
    * @Description: 商户交易量统计
    * @Param: [listPageDTO] 
    * @return: java.util.List<com.touceng.domain.vo.UserVolumVo> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    List<UserVolumVo> pageUserVolumReport(ListPageDTO listPageDTO);

    /**
     * @methodDesc: 方法描述: 分页查询用户分润明细
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    @Select("select ul.user_code as userCode, ul.income,cl.order_no as orderNo,cl.order_amount as orderAmount,cl.outcome ,cl.create_time as createTime from tc_user_wallet_log ul LEFT JOIN  tc_charge_wallet_log  cl on ul.order_no = cl.order_no  where ul.`status` = 1 and  ul.product_code = 'dodo_profit_001' and cl.product_code = 'dodo_profit_001' and user_code = #{listPageDTO.code} ")
    List<WalletLogVO> pageAgentShareList(@Param("listPageDTO") ListPageDTO listPageDTO);


    /**
     * @methodDesc: 方法描述: 根据订单查询分润明细
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    @Select("select ul.user_code as userCode ,ul.income from tc_user_wallet_log ul  where ul.order_no= #{orderNo} and ul.product_code = 'dodo_profit_001'")
    List<WalletLogVO> findAgentShareItem(@Param("orderNo") String orderNo);


}
