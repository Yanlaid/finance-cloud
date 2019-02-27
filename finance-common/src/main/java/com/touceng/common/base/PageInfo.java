package com.touceng.common.base;

import com.github.pagehelper.Page;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: (分页结果对象)
 * @createTime 2018年6月29日 上午11:04:17
 * @copyright: 上海投嶒网络技术有限公司
 */
@Data
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 3001653027446790756L;

    // 当前页
    private int pageNum;

    // 每页的数量
    private int pageSize;

    // 总记录数
    private long total;

    // 总页数
    private int pages;

    // 结果集
    private List<T> list;

    public PageInfo() {
    }

    /**
     * @param list
     * @methodDesc: 功能描述: 功能描述:(包装Page对象)
     * @author Wu, Hua-Zheng
     * @createTime 2018年6月29日 上午10:54:45
     * @version v1.0.0
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PageInfo(List<T> list) {

        if (CollectionUtils.isEmpty(list)) {
            this.list = new ArrayList<>();
        } else {

            if (list instanceof Page) {
                Page page = (Page) list;
                this.pageNum = page.getPageNum();
                this.pageSize = page.getPageSize();
                this.pages = page.getPages();
                this.list = page;
                this.total = page.getTotal();
            } else if (list instanceof Collection) {
                this.pageNum = 1;
                this.pageSize = list.size();
                this.pages = 1;
                this.list = list;
                this.total = list.size();
            }

        }

    }


}
