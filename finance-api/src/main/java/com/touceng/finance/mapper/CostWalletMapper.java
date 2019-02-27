package com.touceng.finance.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.touceng.common.base.BaseMapper;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.CostWallet;

/**
 * <p>
 * 内部成本钱包账户 Mapper 接口
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface CostWalletMapper extends BaseMapper<CostWallet> {

    /**
     * @methodDesc: 方法描述: 成本账户【收款/付款】
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    //@Update("update tc_cost_wallet set balance =balance+ #{amount} , update_time =now() where channel_account_no = #{channelAccountNo} and currency = #{currency} and balance+ #{amount} >=0")
    @Update("update tc_cost_wallet set balance =balance+ #{amount} , update_time =now() where channel_account_no = #{channelAccountNo} and currency = #{currency} ")
    int updateWallet(@Param("channelAccountNo") String channelAccountNo, @Param("currency") String currency, @Param("amount") BigDecimal amount);


    List<CostWallet> pageCostWallet(ListPageDTO listPageDTO);
}