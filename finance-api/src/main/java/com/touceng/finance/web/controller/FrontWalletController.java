package com.touceng.finance.web.controller;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseController;
import com.touceng.common.base.PageInfo;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.log.ELogTypeEnum;
import com.touceng.common.log.LogAnnotation;
import com.touceng.common.log.OperateLog;
import com.touceng.common.rabbitMq.RabbitMQConfig;
import com.touceng.common.rabbitMq.RabbitMQSender;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.common.utils.DateToolUtils;
import com.touceng.common.utils.PoiExcelWriteUtils;
import com.touceng.common.utils.ReflexClazzUtils;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.entity.finance.UserWalletLog;
import com.touceng.domain.vo.WalletLogVO;
import com.touceng.finance.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tc_dev on 2018/9/6.
 */

@Api(value = "商户账户同步管理接口", description = "商户账户同步管理接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/front/")
public class FrontWalletController extends BaseController {

    @Autowired
    private IUserWalletLogService iUserWalletLogService;
    @Autowired
    private IUserWalletService iUserWalletService;

    @Autowired
    private ICostWalletService iCostWalletService;

    @Autowired
    private ICostWalletLogService iCostWalletLogService;

    @Autowired
    private IChannelWalletLogService iChannelWalletLogService;

    @Autowired
    private IChannelWalletService iChannelWalletService;

    @Autowired
    private IChargeWalletLogService iChargeWalletLogService;

    @Autowired
    private IChargeWalletService iChargeWalletService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    //@LogAnnotation(module = "流水查询", function = "商户流水查询", projectName = "touceng-finance", type = ELogTypeEnum.query)
    @ApiOperation(value = "商户账户日志查询接口", notes = "商户账户日志查询接口")
    @PostMapping(value = "/user/wallet/log")
    public BaseResponse queryUserWalletLog(@RequestBody ListPageDTO listPageDTO) {

        if(StringUtils.isNoneBlank(listPageDTO.getCode())){
            rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));
        }

        return ResultUtil.success(iUserWalletLogService.pageUserWalletLog(listPageDTO));
    }

    @ApiOperation(value = "子商户账户日志查询接口", notes = "子商户账户日志查询接口")
    @PostMapping(value = "/subuser/wallet/log")
    public BaseResponse querySubUserWalletLog(@RequestBody ListPageDTO listPageDTO) {

        return ResultUtil.success(iUserWalletLogService.pageSubUserWalletLog(listPageDTO));
    }

    @ApiOperation(value = "子商户账户日志导出接口", notes = "子商户账户日志导出接口")
    @PostMapping(value = "/subuser/wallet/log/export")
    public void exportSubUserWalletLog(HttpServletResponse response, @RequestBody ListPageDTO listPageDTO) {

        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<WalletLogVO> pageRes = iUserWalletLogService.pageSubUserWalletLog(listPageDTO);
        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(WalletLogVO.class);

        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())) {

            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("子商户流水报表", result.get(TouCengConstant.EXCEL_TITLES), result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response, "子商户流水报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-子商户流水导出-[异常信息]:" + e.getMessage());
            }

        }
    }




    @ApiOperation(value = "测试导出接口", notes = "测试导出接口")
    @GetMapping(value = "/user/wallet/log/test")
    public void exportUserWalletLogTest(HttpServletResponse response) {

        ListPageDTO listPageDTO = new ListPageDTO();
        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<WalletLogVO> pageRes = iUserWalletLogService.pageUserWalletLog(listPageDTO);
        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())) {
            Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(WalletLogVO.class);

            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("商户流水报表", result.get(TouCengConstant.EXCEL_TITLES), result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response, "商户流水报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-商户流水导出-[异常信息]:" + e.getMessage());
            }

        }
    }


    @ApiOperation(value = "商户账户日志导出接口", notes = "商户账户日志导出接口")
    @PostMapping(value = "/user/wallet/log/export")
    public void exportUserWalletLog(HttpServletResponse response, @RequestBody ListPageDTO listPageDTO) {

        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<WalletLogVO> pageRes = iUserWalletLogService.pageUserWalletLog(listPageDTO);
        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())) {
            Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(WalletLogVO.class);

            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("商户流水报表", result.get(TouCengConstant.EXCEL_TITLES), result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response, "商户流水报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-商户流水导出-[异常信息]:" + e.getMessage());
            }

        }
    }


    @ApiOperation(value = "商户账户查询接口", notes = "商户账户查询接口")
    @PostMapping(value = "/user/wallet")
    public BaseResponse queryUserWallet(@RequestBody ListPageDTO listPageDTO) {

        return ResultUtil.success(iUserWalletService.pageUserWallet(listPageDTO));
    }

    @ApiOperation(value = "账户成本查询接口", notes = "商户账户成本查询接口")
    @PostMapping(value = "/cost/wallet")
    public BaseResponse queryCostWallet(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));

        return ResultUtil.success(iCostWalletService.pageCostWallet(listPageDTO));
    }

    @LogAnnotation(module = "流水查询", function = "商户流水查询", projectName = "touceng-finance", type = ELogTypeEnum.query)
    @ApiOperation(value = "账户成本流水查询接口", notes = "账户成本流水查询接口")
    @PostMapping(value = "/cost/wallet/log")
    public BaseResponse queryCostWalletLog(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));

        return ResultUtil.success(iCostWalletLogService.pageCostWallet(listPageDTO));
    }


    @ApiOperation(value = "账户手续费查询接口", notes = "账户手续费查询查询接口")
    @PostMapping(value = "/charge/wallet")
    public BaseResponse queryChargeWallet(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));

        return ResultUtil.success(iChargeWalletService.pageChargeWallet(listPageDTO));
    }

    @ApiOperation(value = "账户手续费流水查询接口", notes = "账户手续费流水查询接口")
    @PostMapping(value = "/charge/wallet/log")
    public BaseResponse queryChargeWalletLog(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));
        return ResultUtil.success(iChargeWalletLogService.pageChargeWallet(listPageDTO));
    }

    @ApiOperation(value = "渠道手续费查询接口", notes = "渠道手续费查询查询接口")
    @PostMapping(value = "/channel/wallet")
    public BaseResponse queryChannelWallet(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));
        return ResultUtil.success(iChannelWalletService.pageChannelWallet(listPageDTO));
    }

    @ApiOperation(value = "渠道手续费流水查询接口", notes = "渠道手续费流水查询接口")
    @PostMapping(value = "/channel/wallet/log")
    public BaseResponse queryChannelWalletLog(@RequestBody ListPageDTO listPageDTO) {
        rabbitMQSender.send(RabbitMQConfig.TC_MQ_OPERATE_RECORD_QUEUE, JSON.toJSONString(new OperateLog(JSON.toJSONString(listPageDTO), listPageDTO.getCode())));
        return ResultUtil.success(iChannelWalletLogService.pageChannelWallet(listPageDTO));
    }


}
