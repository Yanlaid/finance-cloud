package com.touceng.ticket;

import com.touceng.common.constant.TCConfigConstant;
import com.touceng.common.utils.HttpClientUtils;
import com.touceng.domain.constant.TouCengDataConstant;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorTest {

   public static String payParam = "{" +
           "    \"agentCode\": null," +
           "    \"agentId\": null," +
           "    \"currency\": \"RMB\"," +
           "    \"totalAmount\": 100.000," +
           "    \"userCode\": \"CC800173\"," +
           "    \"userId\": \"8a81808f65429763016545eb630a0029\"," +
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

    public static String  withdrawParam = "{" +
            "    \"agentCode\": null," +
            "    \"agentId\": null," +
            "    \"currency\": \"RMB\"," +
            "    \"totalAmount\": 100.000," +
            "    \"userCode\": \"CC800173\"," +
            "    \"userId\": \"8a81808f65429763016545eb630a0029\"," +
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


    public static void main(String[] args) {


        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);


        for (int i = 0; i < 500; i++) {
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {

                        String pay = String.format(ThreadPoolExecutorTest.payParam, UUID.randomUUID());
                        HttpClientUtils.reqPost("http://172.20.11.41:9090/v1/payintc/pay/create", pay, null);

                        System.out.println(pay);
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int i = 0; i <500; i++) {
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        String withdraw = String.format(ThreadPoolExecutorTest.withdrawParam, UUID.randomUUID());
                        HttpClientUtils.reqPost( "http://172.20.11.41:9090/v1/payintc/withdraw/create", withdraw, null);

                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }


}