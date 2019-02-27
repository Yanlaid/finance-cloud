package com.touceng.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: (mybatis基础类)
 * @createTime 2018年6月29日 上午10:55:50
 * @copyright: 上海投嶒网络技术有限公司
 */
@Repository
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     * @param recordList
     * @methodDesc: 功能描述: 批量保存
     * @author Wu, Hua-Zheng
     * @createTime 2018年7月11日 上午9:59:05
     * @version v1.0.0
     */
    public int insertList(List<T> recordList);
}
