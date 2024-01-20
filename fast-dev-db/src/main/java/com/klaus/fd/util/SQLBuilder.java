package com.klaus.fd.util;


import com.klaus.fd.constant.SqlConstant;

/**
 * @author Silas
 */
public class SQLBuilder {

    private final StringBuilder sqlSb;

    public SQLBuilder(StringBuilder sqlSb) {
        this.sqlSb = sqlSb;
    }

    public String build() {
        return this.sqlSb.toString();
    }

    public static SQLBuilder queryBuilder() {
        return new SQLBuilder(new StringBuilder("SELECT"));
    }


    public SQLBuilder select(String... columns) {
        sqlSb.append(SqlConstant.BLANK).append(String.join(",", columns));
        return this;
    }


    public SQLBuilder from(String tbName) {
        sqlSb.append(SqlConstant.FROM).append(tbName);
        return this;
    }

    public SQLBuilder where(String where) {
        sqlSb.append(SqlConstant.BLANK).append(SqlConstant.WHERE).append(where);
        return this;
    }

    public SQLBuilder orderBy(String orderByColumn) {
        sqlSb.append(SqlConstant.BLANK).append(SqlConstant.ORDER_BY).append(orderByColumn);
        return this;
    }

    public SQLBuilder orderByDesc(String orderByColumn) {
        sqlSb.append(SqlConstant.BLANK).append(SqlConstant.ORDER_BY).append(orderByColumn)
                .append(SqlConstant.BLANK).append(SqlConstant.DESC);
        return this;
    }

    public SQLBuilder limit(int pageNo, int pageSize) {
        sqlSb.append(SqlConstant.BLANK).append(SqlConstant.LIMIT)
                .append((pageNo - 1) * pageSize).append(SqlConstant.COMMA).append(pageSize);
        return this;
    }

    public SQLBuilder limit(int count) {
        sqlSb.append(SqlConstant.BLANK).append(SqlConstant.LIMIT).append(count);
        return this;
    }

    public SQLBuilder last(String lastSql) {
        sqlSb.append(SqlConstant.BLANK).append(lastSql);
        return this;
    }
}
