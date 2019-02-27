package com.touceng.finance.web.controller;

import com.alibaba.fastjson.JSON;
import com.touceng.common.base.BaseController;
import com.touceng.common.base.PageInfo;
import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.log.OperateLog;
import com.touceng.common.rabbitMq.RabbitMQConfig;
import com.touceng.common.response.BaseResponse;
import com.touceng.common.response.ResultUtil;
import com.touceng.common.utils.DateToolUtils;
import com.touceng.common.utils.PoiExcelWriteUtils;
import com.touceng.common.utils.ReflexClazzUtils;
import com.touceng.domain.dto.query.ListPageDTO;
import com.touceng.domain.vo.ChannelReportVO;
import com.touceng.domain.vo.ShareProfitVO;
import com.touceng.domain.vo.UserVolumLogVo;
import com.touceng.finance.service.IDataReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by tc_dev on 2018/9/10.
 */

@Api(value = "渠道报表管理接口", description = "渠道报表管理接口")
@RestController
@CrossOrigin
@RequestMapping("/v1/report/")
public class DateReportController extends BaseController {
    @Autowired
    private IDataReportService iDataReportService;

    @ApiOperation(value = "渠道报表查询接口", notes = "渠道报表查询接口")
    @PostMapping(value = "/channel/report")
    public BaseResponse queryChannelReport(@RequestBody ListPageDTO listPageDTO) {
        return ResultUtil.success(iDataReportService.pageChannelReport(listPageDTO));
    }

    @ApiOperation(value = "渠道报表日志导出接口", notes = "渠道报表日志导出接口")
    @PostMapping(value = "/channel/report/export")
    public BaseResponse exportUserWalletLog(HttpServletResponse response, @RequestBody ListPageDTO listPageDTO) {


        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<ChannelReportVO> pageRes = iDataReportService.pageChannelReport(listPageDTO);
        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())) {
            Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(ChannelReportVO.class);
            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("渠道报表", result.get(TouCengConstant.EXCEL_TITLES), result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response, "渠道报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-商户流水导出-[异常信息]:" + e.getMessage());
            }
        }
        return ResultUtil.success();
    }


    @ApiOperation(value = "分润报表查询接口", notes = "分润报表查询接口")
    @PostMapping(value = "/shareprofit/report")
    public BaseResponse queryShareProfitReport(@RequestBody ListPageDTO listPageDTO) {
        return ResultUtil.success(iDataReportService.pageShareProfitReport(listPageDTO));
    }

    @ApiOperation(value = "分润报表日志导出接口", notes = "分润报表日志导出接口")
    @PostMapping(value = "/shareprofit/report/export")
    public BaseResponse exportShareProfitReport(HttpServletResponse response, @RequestBody ListPageDTO listPageDTO) {


        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<ShareProfitVO> pageRes = iDataReportService.pageShareProfitReport(listPageDTO);
        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())) {
            Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(ShareProfitVO.class);

            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("分润日报表", result.get(TouCengConstant.EXCEL_TITLES), result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response, "分润日报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-商户流水导出-[异常信息]:" + e.getMessage());
            }

        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "商户交易量统计查询接口", notes = "商户交易量统计查询接口")
    @PostMapping(value = "/user/volum/report")
    public BaseResponse queryUserVolumReport(@RequestBody ListPageDTO listPageDTO) {
        return ResultUtil.success(iDataReportService.pageUserVolumReport(listPageDTO));
    }

    @ApiOperation(value = "商户交易量统计导出接口", notes = "商户交易量统计导出接口")
    @PostMapping(value = "/user/volum/report/export")
    public BaseResponse exportUserVolumReport(HttpServletResponse response, @RequestBody  ListPageDTO listPageDTO) {

        listPageDTO.setPageSize(TouCengConstant.MAX_EXCEL_PAGE_SIZE);
        PageInfo<UserVolumLogVo> pageRes= iDataReportService.pageUserVolumReport(listPageDTO);
        if (null != pageRes && !CollectionUtils.isEmpty(pageRes.getList())){
            Map<String,String[]> result = ReflexClazzUtils.getFiledStructMap(UserVolumLogVo.class);

            HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("商户交易量报表日报表", result.get(TouCengConstant.EXCEL_TITLES),result.get(TouCengConstant.EXCEL_ATTRS), pageRes.getList(), DateToolUtils.DATE_FORMAT_DATETIME);
            try {
                PoiExcelWriteUtils.write(wb, response,"商户交易量报表日报表");
            } catch (IOException e) {
                throw new BusinessException("【FrontWalletController】-error-商户交易量报表流水导出-[异常信息]:"+e.getMessage());
            }

        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "商户分润流水查询接口", notes = "商户分润流水查询接口")
    @PostMapping(value = "/agent/share/list")
    public BaseResponse queryChannelWalletLog(@RequestBody ListPageDTO listPageDTO) {
        return ResultUtil.success(iDataReportService.queryAgentShareList(listPageDTO));
    }

    @ApiOperation(value = "订单分润流水明细查询接口", notes = "订单分润流水明细查询接口")
    @GetMapping(value = "/agent/share/item")
    public BaseResponse queryChannelWalletLog(@RequestParam String orderNo) {
        return ResultUtil.success(iDataReportService.findAgentShareItem(orderNo));
    }


}
