package com.touceng.finance.mapper;

import com.touceng.common.base.BaseMapper;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.domain.vo.WalletLogVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 会员钱包账户记录日志 Mapper 接口
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface UserWalletLogMapper extends BaseMapper<UserWalletLog> {


    @Update("update tc_user_wallet_log set status = 2, update_time =now() where order_no = #{orderNo} and status =#{status} and currency = #{currency}")
    int updateUserWalletLog(@Param("orderNo") String orderNo, @Param("currency") String currency, @Param("status") int status);


    @Select("select sum(outcome) from tc_user_wallet_log where order_no = #{orderNo}  and currency = #{currency} and status = #{status}")
    public BigDecimal getAmountByParams(@Param("orderNo") String orderNo, @Param("currency") String currency, @Param("status") int status);

    List<WalletLogVO> pageSubUserWalletLog(ListPageDTO listPageDTO);

    List<WalletLogVO> pageUserWalletLog(ListPageDTO listPageDTO);
}