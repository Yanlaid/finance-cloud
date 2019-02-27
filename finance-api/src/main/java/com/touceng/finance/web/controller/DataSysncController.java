package com.touceng.finance.web.controller;

import com.touceng.common.base.BaseController;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.domain.entity.finance.*;
import com.touceng.finance.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商户钱包账户 前端控制器
 * </p>
 *
 * @author Wu, Hua-Zheng
 * @since 2018-08-27
 */
@Api(value = "商户账户同步管理接口", description = "商户账户同步管理接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/data/sysnc")
public class DataSysncController extends BaseController {

    @Autowired
    private DataService dataService;
    @Autowired
    private IUserWalletService userWalletService;
    @Autowired
    private IUserWalletLogService userWalletLogService;

    @Autowired
    private IChannelWalletService channelWalletService;
    @Autowired
    private IChannelWalletLogService channelWalletLogService;

//	@Autowired
//	private IAgentWalletService agentWalletService;
//	@Autowired
//	private IAgentWalletLogService agentWalletLogService;

    @Autowired
    private IChargeWalletService chargeWalletService;
    @Autowired
    private IChargeWalletLogService chargeWalletLogService;

    @Autowired
    private ICostWalletService costWalletService;
    @Autowired
    private ICostWalletLogService costWalletLogService;


    @ApiOperation(value = "同步账户数据接口", notes = "同步账户数据接口")
    @GetMapping(value = "/data")
    public BaseResponse data() {
        return ResultUtil.success(dataService.syncData());
    }

    @ApiOperation(value = "同步商户账户信息接口", notes = "同步商户账户信息接口")
    @PostMapping(value = "/user/info")
    public BaseResponse user(@RequestBody List<UserWallet> userWalletList) {
        userWalletService.syncUserdata(userWalletList);
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步商户账户日志信息接口", notes = "同步商户账户日志信息接口")
    @PostMapping(value = "/user/log")
    public BaseResponse userLog(@RequestBody List<UserWalletLog> userWalletLogList) {
        for (UserWalletLog userWalletLog : userWalletLogList) {
            userWalletLogService.saveUserWalletLog(userWalletLog);
        }
        return ResultUtil.success();
    }

//	@ApiOperation(value = "同步代理账户信息接口", notes = "同步代理账户信息接口")
//	@PostMapping(value = "/agent/info")
//	public BaseResponse agentInfo(@RequestBody List<AgentWallet> agentWalletList) {
//		agentWalletService.syncAgentdata(agentWalletList);
//		return ResultUtil.success();
//	}
//
//	@ApiOperation(value = "同步代理账户日志信息接口", notes = "同步代理账户日志信息接口")
//	@PostMapping(value = "/agent/log")
//	public BaseResponse agentLog(@RequestBody List<AgentWalletLog> agentWalletLogList) {
//		for(AgentWalletLog temp:agentWalletLogList) {
//			agentWalletLogService.saveAgentWalletLog(temp);
//		}
//		return ResultUtil.success();
//	}

    @ApiOperation(value = "同步内部渠道账户日志信息接口", notes = "同步内部渠道接口")
    @PostMapping(value = "/channel/info")
    public BaseResponse channelInfo(@RequestBody List<ChannelWallet> channelWalletList) {
        channelWalletService.syncChanneldata(channelWalletList);
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步内部渠道账户日志信息接口", notes = "同步内部渠道接口")
    @PostMapping(value = "/channel/log")
    public BaseResponse channelLog(@RequestBody List<ChannelWalletLog> channelWalletLogList) {
        for (ChannelWalletLog temp : channelWalletLogList) {
            channelWalletLogService.saveChannelWalletLog(temp);
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步成本底价账户信息接口", notes = "同步成本底价账户信息接口")
    @PostMapping(value = "/cost/info")
    public BaseResponse costInfo(@RequestBody List<CostWallet> costWalletList) {
        costWalletService.syncCostdata(costWalletList);
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步成本底价账户日志信息接口", notes = "同步成本底价账户日志信息接口")
    @PostMapping(value = "/cost/log")
    public BaseResponse costLog(@RequestBody List<CostWalletLog> costWalletLogList) {
        for (CostWalletLog temp : costWalletLogList) {
            costWalletLogService.saveCostWalletLog(temp);
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步手续费账户信息接口", notes = "同步手续费接口")
    @PostMapping(value = "/charge/info")
    public BaseResponse chargeInfo(@RequestBody List<ChargeWallet> chargeWalletList) {
        chargeWalletService.syncChargedata(chargeWalletList);
        return ResultUtil.success();
    }

    @ApiOperation(value = "同步手续费账户日志信息接口", notes = "同步手续费接口")
    @PostMapping(value = "/charge/log")
    public BaseResponse chargeLog(@RequestBody List<ChargeWalletLog> chargeWalletLogList) {
        for (ChargeWalletLog temp : chargeWalletLogList) {
            chargeWalletLogService.saveChargeWalletLog(temp);
        }
        return ResultUtil.success();
    }

}
