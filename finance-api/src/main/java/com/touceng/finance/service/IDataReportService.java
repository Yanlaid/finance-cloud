package com.touceng.finance.service;

import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.vo.ChannelReportVO;
import com.touceng.domain.vo.ShareProfitVO;
import com.touceng.domain.vo.UserVolumLogVo;
import com.touceng.domain.vo.UserVolumVo;
import com.touceng.domain.vo.WalletLogVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by tc_dev on 2018/9/7.
 */
public interface IDataReportService {
    /***
    * @Description:  渠道报表查询接口
    * @Param:
    * @return:
    * @Author: yangxu
    * @Date: 2018/9/12
    */
    PageInfo<ChannelReportVO> pageChannelReport(ListPageDTO listPageDTO);

    /** 
    * @Description: 分润报表查询 
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.ShareProfitVO> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    PageInfo<ShareProfitVO> pageShareProfitReport(ListPageDTO listPageDTO);

    /** 
    * @Description: 商户交易量统计
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.UserVolumLogVo> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    PageInfo<UserVolumLogVo> pageUserVolumReport(ListPageDTO listPageDTO);


    /**
     * @methodDesc: 方法描述: 分页查询用户分润明细
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    PageInfo<WalletLogVO> queryAgentShareList(ListPageDTO listPageDTO);


    /**
     * @methodDesc: 方法描述: 根据订单查询分润明细
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    List<WalletLogVO> findAgentShareItem(String orderNo);
}
