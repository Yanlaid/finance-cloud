//
//165196.000
//163396.000
//
//付款：
//
//{
//    "agentCode": "CC800102",
//    "agentId": "ff8080816531d444016532042c5d001e",
//    "currency": "RMB",
//    "totalAmount": 1800,
//    "userCode": "CC800125",
//    "userId": "ff808081653d2fb601654141cf1a0023",
//    "orderType": 1,
//    "orderList": [{
//        "agentShareList": [],
//        "channelAccountName": "合肥侘傺贸易有限公司 ",
//        "channelAccountNo": "2018070601762880",
//        "channelName": "快付通",
//        "orderAmount": 1800,
//        "orderNo": "kft200000003",
//        "orderType": 1,
//        "productCode": "DB00015",
//        "productName": "付款",
//        "profitRate": {
//            "feeType": 0,
//            "floorAgentProfit": 7,
//            "floorCostProfit": 4,
//            "retailProfit": 10
//        },
//        "withdrawType": 0
//    }]
//}
//
//
//{
//    "agentCode": "CC800102",
//    "agentId": "ff8080816531d444016532042c5d001e",
//    "currency": "RMB",
//    "totalAmount": 1800,
//    "userCode": "CC800125",
//    "userId": "ff808081653d2fb601654141cf1a0023",
//    "orderType": 1,
//    "orderList": [{
//        "agentShareList": [],
//        "channelAccountName": "合肥侘傺贸易有限公司 ",
//        "channelAccountNo": "2018070601762880",
//        "channelName": "快付通",
//        "orderAmount": 1800,
//        "orderNo": "kft2000000031",
//        "orderType": 1,
//        "productCode": "DB00015",
//        "productName": "付款",
//        "profitRate": {
//            "feeType": 1,
//            "floorAgentProfit": 1.5,
//            "floorCostProfit": 0.5,
//            "retailProfit": 2
//        },
//        "withdrawType": 0
//    }]
//}
//
//
//
//{
//    "agentCode": "CC800102",
//    "agentId": "ff8080816531d444016532042c5d001e",
//    "currency": "RMB",
//    "totalAmount": 1800,
//    "userCode": "CC800125",
//    "userId": "ff808081653d2fb601654141cf1a0023",
//    "orderType": 1,
//    "orderList": [{
//        "agentShareList": [],
//        "channelAccountName": "合肥侘傺贸易有限公司 ",
//        "channelAccountNo": "2018070601762880",
//        "channelName": "快付通",
//        "orderAmount": 1800,
//        "orderNo": "kft2000000032",
//        "orderType": 1,
//        "productCode": "DB00015",
//        "productName": "付款",
//        "profitRate": {
//            "feeType": 1,
//            "floorAgentProfit": 1.5,
//            "floorCostProfit": 0.5,
//            "retailProfit": 2
//        },
//        "withdrawType": 1
//    }]
//}
//
//
//163396.000
//
//
//付款回调
//
//{
//    "orderList": [{
//        "orderAmount": 1800,
//        "currency": "RMB",
//        "orderNo": "kft2000000031"
//    }],
//    "userId": "ff808081653d2fb601654141cf1a0023"
//}
//
//
//
//
//
//
//参数
//
//收款：内扣-无代理商-费用类型固定额
// {
//    "agentCode": null,
//    "agentId": null,
//    "currency": "RMB",
//    "totalAmount":50000,
//    "userCode": "CC800125",
//    "userId": "ff808081653d2fb601654141cf1a0023",
//    "orderType": 0,
//    "orderList": [{
//        "agentShareList": [],
//        "channelAccountName": "合肥侘傺贸易有限公司 ",
//        "channelAccountNo": "2018070601762880",
//        "channelName": "快付通",
//        "orderAmount": 50000,
//        "orderNo": "kft00000048",
//        "orderType": 0,
//        "productCode": "DB0001",
//        "productName": "支付寶掃碼",
//        "profitRate": {
//            "feeType": 1,//费用类型-feeType【0-费率；1-固定额】
//            "floorAgentProfit": 1.5,//元
//            "floorCostProfit": 0.5,//元
//            "retailProfit": 2//元
//        },
//        "withdrawType": 0 //付款【内扣-0/外扣-1】-产品通道/运营查询
//    }]
//}
//
//
//
//收款：外扣-有代理商-费用类型 千分位 费率
// {
//    "agentCode": "100001",
//    "agentId": "100001",
//    "currency": "RMB",
//    "totalAmount":50000,
//    "userCode": "CC800125",
//    "userId": "ff808081653d2fb601654141cf1a0023",
//    "orderType": 0,
//    "orderList": [{
//        "agentShareList": [],
//        "channelAccountName": "合肥侘傺贸易有限公司 ",
//        "channelAccountNo": "2018070601762880",
//        "channelName": "快付通",
//        "orderAmount": 60000,
//        "orderNo": "kft000000481",
//        "orderType": 0,
//        "productCode": "DB0001",
//        "productName": "支付寶掃碼",
//        "profitRate": {
//            "feeType": 0,
//            "floorAgentProfit": 7,
//            "floorCostProfit": 4,
//            "retailProfit": 10
//        },
//        "withdrawType": 1
//    }]
//}