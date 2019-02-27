package com.touceng.finance.web.controller;


import com.touceng.common.base.BaseController;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.domain.constant.TouCengDataConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商户钱包账户 前端控制器
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Api(value = "账户数据查询接口", description = "账户数据查询接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/data/query")
public class DataQueryController extends BaseController {


    @ApiOperation(value = "商户账户信息查询接口", notes = "商户账户信息查询接口")
    @GetMapping(value = "/user/info")
    public BaseResponse userInfo() {
        return ResultUtil.success(TouCengDataConstant.userMap);
    }

    @ApiOperation(value = "商户账户日志查询接口", notes = "商户账户日志查询接口")
    @GetMapping(value = "/user/log")
    public BaseResponse userLog() {
        return ResultUtil.success(TouCengDataConstant.userLogList);
    }


//    @ApiOperation(value = "代理账户信息查询接口", notes = "代理账户信息查询接口")
//    @GetMapping(value = "/agent/info")
//    public BaseResponse agentInfo() {
//        return ResultUtil.success(TouCengDataConstant.agentMap);
//    }
//
//
//    @ApiOperation(value = "代理账户日志查询接口", notes = "代理账户日志查询接口")
//    @PostMapping(value = "/agent/log")
//    public BaseResponse agentLog(@RequestBody AgentWalletLog agentWalletLog) {
//
//        return ResultUtil.success(iAgentWalletLogService.queryAgentWalletLog(agentWalletLog));
//    }

    @ApiOperation(value = "代理账户信息查询接口", notes = "代理账户信息查询接口")
    @GetMapping(value = "/agent/info")
    public BaseResponse agentInfo() {
        return ResultUtil.success(ResultUtil.success(TouCengDataConstant.userLogList));
    }


    @ApiOperation(value = "代理账户日志查询接口", notes = "代理账户日志查询接口")
    @PostMapping(value = "/agent/log")
    public BaseResponse agentLog() {

        return ResultUtil.success(ResultUtil.success(TouCengDataConstant.userLogList));
    }


    @ApiOperation(value = "内部渠道账户信息查询接口", notes = "内部渠道账户信息查询接口")
    @GetMapping(value = "channel/info")
    public BaseResponse channelInfo() {
        return ResultUtil.success(TouCengDataConstant.channelMap);
    }

    @ApiOperation(value = "内部渠道账户日志查询接口", notes = "内部渠道账户日志查询接口")
    @GetMapping(value = "channel/log")
    public BaseResponse channelLog() {
        return ResultUtil.success(TouCengDataConstant.channelLogList);
    }


    @ApiOperation(value = "成本底价账户信息查询接口", notes = "成本底价账户信息查询接口")
    @GetMapping(value = "/cost/info")
    public BaseResponse costInfo() {
        return ResultUtil.success(TouCengDataConstant.costMap);
    }

    @ApiOperation(value = "成本底价账户日志查询接口", notes = "成本底价账户日志查询接口")
    @GetMapping(value = "/cost/log")
    public BaseResponse costLog() {
        return ResultUtil.success(TouCengDataConstant.costLogList);
    }

    @ApiOperation(value = "手续费账户信息查询接口", notes = "手续费账户信息查询接口")
    @GetMapping(value = "/charge/info")
    public BaseResponse chargeInfo() {
        return ResultUtil.success(TouCengDataConstant.chargeMap);
    }

    @ApiOperation(value = "手续费账户日志查询接口", notes = "手续费账户日志查询接口")
    @GetMapping(value = "/charge/log")
    public BaseResponse chargeLog() {
        return ResultUtil.success(TouCengDataConstant.chargeLogList);
    }

}
