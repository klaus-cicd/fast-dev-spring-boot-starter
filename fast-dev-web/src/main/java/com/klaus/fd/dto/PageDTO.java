package com.klaus.fd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Klaus
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {

    /**
     * 页码
     *
     * @default 1
     */
    private Integer page;

    /**
     * 每页条数
     *
     * @default 10
     */
    private Long size;
}
