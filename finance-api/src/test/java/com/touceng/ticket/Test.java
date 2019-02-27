package com.touceng.ticket;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.touceng.common.exception.BusinessException;
import com.touceng.common.response.EResultEnum;
import com.touceng.common.utils.PoiExcelReadUtils;

public class Test {

    public static void main(String[] args) {


        String payParam = "{" +
                "    \"agentCode\": null," +
                "    \"agentId\": null," +
                "    \"currency\": \"RMB\"," +
                "    \"totalAmount\": 50.000," +
                "    \"userCode\": \"CC800104\"," +
                "    \"userId\": \"ff8080816531d4440165321ef7ec003a\"," +
                "    \"orderType\": 0," +
                "    \"userType\": 0," +
                "    \"orderList\": [{" +
                "        \"agentShareList\": []," +
                "        \"channelAccountName\": \"合肥侘傺贸易有限公司 \"," +
                "        \"channelAccountNo\": \"2018070601762880\"," +
                "        \"channelName\": \"快付通\"," +
                "        \"orderAmount\": 50.000," +
                "        \"orderNo\": \"%s\"," +
                "        \"orderType\": 0," +
                "        \"productCode\": \"DB0001\"," +
                "        \"productName\": \"支付寶掃碼\"," +
                "        \"profitRate\": {" +
                "            \"feeType\": 0," +
                "            \"floorAgentProfit\": 7," +
                "            \"floorCostProfit\": 4," +
                "            \"retailProfit\": 10" +
                "        }," +
                "        \"withdrawType\": 0" +
                "    }]" +
                "}";

        String withdrawParam = "{" +
                "    \"agentCode\": null," +
                "    \"agentId\": null," +
                "    \"currency\": \"RMB\"," +
                "    \"totalAmount\": 100.000," +
                "    \"userCode\": \"CC800104\"," +
                "    \"userId\": \"ff8080816531d4440165321ef7ec003a\"," +
                "    \"orderType\": 0," +
                "    \"userType\": 0," +
                "    \"orderList\": [{" +
                "        \"agentShareList\": []," +
                "        \"channelAccountName\": \"合肥侘傺贸易有限公司 \"," +
                "        \"channelAccountNo\": \"2018070601762880\"," +
                "        \"channelName\": \"快付通\"," +
                "        \"orderAmount\": 100.000," +
                "        \"orderNo\": \"%s\"," +
                "        \"orderType\": 0," +
                "        \"productCode\": \"DB0001\"," +
                "        \"productName\": \"支付寶掃碼\"," +
                "        \"profitRate\": {" +
                "            \"feeType\": 1," +
                "            \"floorAgentProfit\": 1.5," +
                "            \"floorCostProfit\": 0.5," +
                "            \"retailProfit\": 2" +
                "        }," +
                "        \"withdrawType\": 0" +
                "    }]" +
                "}";



//        String pwd = MD5Utils.md5("admin123");
//        System.out.println(pwd);
//        Map<String,String[]> result =ReflexClazzUtils.getFiledStructMap(MerchantInfoDTO.class);
//
//        List <MerchantInfoDTO> list = new LinkedList<>();
//        list.add(new MerchantInfoDTO());
//
//
//        HSSFWorkbook wb = PoiExcelWriteUtils.createWorkbook("二级商户信息", result.get(TouCengConstant.EXCEL_TITLES),result.get(TouCengConstant.EXCEL_ATTRS), list, DateToolUtils.DATE_FORMAT_DATETIME);
//        PoiExcelWriteUtils.write2File(wb, "/opt/upload/","二级商户信息.xls");

//        try {
//            List<String[]> resultExcel = PoiExcelReadUtils.excelToArrayList("/opt/upload/二级商户信息.xls",1);
//            System.out.print(JSON.toJSONString(resultExcel));
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        // MerchantInfoDTO dto = new MerchantInfoDTO();
//        ReflexClazzUtils.setFiledValue("merchantId","123344",dto);
//        ReflexClazzUtils.setFiledValue("merchantName","789",dto);

        // System.out.println(dto.getMerchantId()+""+dto.getMerchantName()+dto.getSettleBankAccount());


        //获取excel的标题字段和属性
//        Map<String, String[]> result = ReflexClazzUtils.getFiledStructMap(MerchantInfo.class);
//
//        System.out.printf(JSON.toJSONString(result));


        // 读取excel数据
        List<String[]> valuesExcel = null;
        try {
            valuesExcel = PoiExcelReadUtils.excelToArrayList("/Users/touceng/Downloads/testul.xlsx", 1);
        } catch (Exception e) {
            throw new BusinessException(EResultEnum.INSERT_ERROR);
        }

        System.out.println(JSON.toJSONString(valuesExcel));
        List<String> merchantList = new LinkedList<String>();
        for (int i=1,len= valuesExcel.size(); i<len;i++) {
            merchantList.add(valuesExcel.get(i)[0]);

        }
        System.out.println(JSON.toJSONString(merchantList));

    }


}
