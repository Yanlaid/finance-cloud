package com.touceng.finance.service.impl;

import com.touceng.common.base.PageInfo;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.vo.ChannelReportVO;
import com.touceng.domain.vo.ShareProfitVO;
import com.touceng.domain.vo.UserVolumLogVo;
import com.touceng.domain.vo.UserVolumVo;
import com.touceng.domain.vo.WalletLogVO;
import com.touceng.finance.mapper.DataReportMapper;
import com.touceng.finance.service.IDataReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc_dev on 2018/9/7.
 */
@Slf4j
@Service
public class DataReportServiceImpl implements IDataReportService {

    @Autowired
    private DataReportMapper dataReportMapper;

    /***
    * @Description: 渠道报表查询
    * @Param: [listPageDTO]
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.ChannelReportVO>
    * @Author: yangxu
    * @Date: 2018/9/12
    */
    @Override
    public PageInfo<ChannelReportVO> pageChannelReport(ListPageDTO listPageDTO) {
        List<ChannelReportVO> list = dataReportMapper.pageChannelReport(listPageDTO);
        //for (ChannelReportVO channelReportVO : list) {
        //    if (channelReportVO.getOrderType() == 0) {
        //        channelReportVO.setCount(channelReportVO.getCount() / 2);
        //        channelReportVO.setAmount(channelReportVO.getAmount().divide(BigDecimal.valueOf(2)));
        //        channelReportVO.setCostAmount(channelReportVO.getCostAmount().divide(BigDecimal.valueOf(2)));
        //    }
        //}

        return new PageInfo<ChannelReportVO>(list);
    }

    /** 
    * @Description: 分润报表查询 
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.ShareProfitVO> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    @Override
    public PageInfo<ShareProfitVO> pageShareProfitReport(ListPageDTO listPageDTO) {
        return new PageInfo<ShareProfitVO>(dataReportMapper.pageShareProfitReport(listPageDTO));
    }

    /** 
    * @Description: 商户交易量统计 
    * @Param: [listPageDTO] 
    * @return: com.touceng.common.base.PageInfo<com.touceng.domain.vo.UserVolumLogVo> 
    * @Author: yangxu 
    * @Date: 2018/9/12 
    */
    @Override
    public PageInfo<UserVolumLogVo> pageUserVolumReport(ListPageDTO listPageDTO) {
        List<UserVolumVo> list = dataReportMapper.pageUserVolumReport(listPageDTO);
        List<UserVolumLogVo> userVolumlist = new ArrayList<>();
        for (UserVolumVo userVolumVo:list) {
            //if(userVolumVo.getOrderType()==0){
            //    userVolumVo.setTotalCount(userVolumVo.getTotalCount()/2);
            //    userVolumVo.setTotalAmount(userVolumVo.getTotalAmount().divide(BigDecimal.valueOf(2)));
            //    userVolumVo.setCostAmount(userVolumVo.getCostAmount().divide(BigDecimal.valueOf(2)));
            //}
            UserVolumLogVo userVolumLogVo = new UserVolumLogVo();
            userVolumLogVo.setCreateTime(userVolumVo.getCreateTime());
            userVolumLogVo.setProductName(userVolumVo.getProductName());
            userVolumLogVo.setTotalCount(userVolumVo.getTotalCount());
            userVolumLogVo.setUserCode(userVolumVo.getUserCode());
            userVolumLogVo.setTotalAmount(userVolumVo.getTotalAmount());
            userVolumLogVo.setTotalProfit(userVolumVo.getChargeAmount().subtract(userVolumVo.getCostAmount()).subtract(userVolumVo.getFenAmount()));
            userVolumlist.add(userVolumLogVo);
        }

        return new PageInfo<UserVolumLogVo>(userVolumlist);
        //return new PageInfo<UserVolumVo>(list);
    }

    @Override
    public PageInfo<WalletLogVO> queryAgentShareList(ListPageDTO listPageDTO) {
        return new PageInfo<WalletLogVO>(dataReportMapper.pageAgentShareList(listPageDTO));
    }

    @Override
    public List<WalletLogVO> findAgentShareItem(String orderNo) {
        return dataReportMapper.findAgentShareItem(orderNo);
    }
}
