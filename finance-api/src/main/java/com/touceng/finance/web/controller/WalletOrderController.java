package com.touceng.finance.web.controller;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseController;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.domain.dto.order.OrderCreateDTO;
import com.touceng.domain.dto.order.OrderNotifyDTO;
import com.touceng.finance.service.IWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 订单钱包账户 前端控制器
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Slf4j
@Api(value = "订单钱包账户账户管理接口", description = "订单钱包账户账户管理接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/payintc")
public class WalletOrderController extends BaseController {

    @Autowired
    private IWalletService IWalletService;

    @ApiOperation(value = "付款订单创建接口", notes = "付款订单创建接口")
    @PostMapping(value = "/withdraw/create")
    public BaseResponse withdrawCreate(@ApiParam @Valid @RequestBody final OrderCreateDTO dto,final BindingResult result) {


        log.info("【付款订单Controller】-info-付款订单创建，请求参数：{}", JSON.toJSONString(dto));

        // 数据校验
        super.valid("【付款订单Controller】-error-", result);

        return ResultUtil.success(IWalletService.withdrawCreate(dto));

    }

    @ApiOperation(value = "付款订单回调接口", notes = "付款订单回调接口")
    @PostMapping(value = "/withdraw/notify")
    public BaseResponse withdrawNotify(@ApiParam @Valid @RequestBody final OrderNotifyDTO dto,  final BindingResult result) {
        log.info("【付款订单回调Controller】-info-付款订单回调，请求参数：{}", JSON.toJSONString(dto));

        // 数据校验
        super.valid("【付款订单回调Controller】-error-", result);

        return ResultUtil.success(IWalletService.withdrawNotify(dto));

    }

    @ApiOperation(value = "收款订单创建接口", notes = "收款订单创建接口")
    @PostMapping(value = "/pay/create")
    public BaseResponse payCreate(@ApiParam @Valid @RequestBody final OrderCreateDTO dto, final BindingResult result) {
        log.info("【收款订单Controller】-info-收款订单创建接口，请求参数：{}", JSON.toJSONString(dto));

        // 数据校验
        super.valid("【收款订单Controller】-error-", result);

        return ResultUtil.success(IWalletService.payCreate(dto));

    }

}
