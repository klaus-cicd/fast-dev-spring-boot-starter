package com.klaus.fd.comm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Silas
 */
@Data
@NoArgsConstructor
public class TestEntity {

    private String username;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private Timestamp timestamp;
}
