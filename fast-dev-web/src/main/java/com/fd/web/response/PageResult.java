package com.fd.web.response;

import lombok.*;

import java.util.List;

/**
 * 通用分页响应对象
 *
 * @author Kluas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * Data list
     */
    private List<T> records;

    /**
     * Total data count
     */
    private long total;

    /**
     * page size
     *
     * @default 10
     */
    private long size;

    /**
     * Page no
     */
    private long current;

    public PageResult(List<T> records) {
        this.records = records;
    }

    public static <T> PageResult<T> ok(List<T> dataList) {
        return new PageResult<>(dataList);
    }
}
