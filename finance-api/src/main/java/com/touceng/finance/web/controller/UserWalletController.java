package com.touceng.finance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.touceng.common.base.BaseController;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.common.utils.RedisCacheUtils;
import com.touceng.domain.entity.finance.UserWallet;
import com.touceng.finance.service.IUserWalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

/**
 * <p>
 * 商户钱包账户 前端控制器
 * </p>
 *
 * @author Wu,Hua-Zheng
 * @since 2018-08-27
 */
@Api(value = "商户账户管理接口", description = "商户账户管理接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/user/wallet")
public class UserWalletController extends BaseController {

	@Autowired
	private IUserWalletService userWalletService;
	@Autowired
	RedisCacheUtils redisCacheUtils;

	@ApiOperation(value = "商户账户余额查询接口", notes = "商户账户余额查询接口")
	@GetMapping(value = "/get")
	public BaseResponse withdrawCreate(@RequestParam final String currency, @RequestParam final String userId) {

		UserWallet userWallet = userWalletService.getUserWallet(userId, currency,null, null, null);
		return ResultUtil.success(null == userWallet ? 0 : userWallet.getBalance());

	}

	@ApiOperation(value = "商户账户余额查询接口", notes = "商户账户余额查询接口")
	@GetMapping(value = "/apiget")
	public BaseResponse apiwithdrawCreate(@RequestParam final String currency, @RequestParam final String userCode) {

		BigDecimal userWallet = userWalletService.getUserWalletBelence(userCode, currency);
		return ResultUtil.success(null == userWallet ? 0 : userWallet);

	}


}
