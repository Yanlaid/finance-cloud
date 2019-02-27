package com.touceng.finance.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.touceng.common.base.BaseMapper;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWallet;

/**
 * <p>
 * 会员钱包账户 Mapper 接口
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Repository
public interface UserWalletMapper extends BaseMapper<UserWallet> {

    /**
     * @methodDesc: 方法描述: 商户账户【收款/付款】
     * @author Wu, Hua-Zheng
     * @createTime 2018年8月30日 下午9:37:40
     * @version v1.0.0
     */
    @Update("update tc_user_wallet set balance =balance+ #{amount} , update_time =now() where user_id = #{userId} and currency = #{currency} and balance+ #{amount} >=0 ")
    //@Update("update tc_user_wallet set balance =balance+ #{amount} , update_time =now() where user_id = #{userId} and currency = #{currency}")
    int updateUserWallet(@Param("userId") String userId, @Param("currency") String currency, @Param("amount") BigDecimal amount);

    List<UserWallet> pageUserWallet(ListPageDTO listPageDTO);


    @Select("select balance from tc_user_wallet where user_code = #{userCode} and currency = #{currency}")
    BigDecimal getUserWallet(@Param("userCode") String userCode, @Param("currency") String currency);


}