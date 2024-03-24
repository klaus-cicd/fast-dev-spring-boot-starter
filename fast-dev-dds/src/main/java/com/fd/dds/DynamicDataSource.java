package com.fd.dds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author Klaus
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultDataSource);
        this.setTargetDataSources(targetDataSources);
        DynamicDataSourceManager.allDataSource = targetDataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String currentDataSourceKey = DynamicDataSourceManager.getCurrentDataSourceKey();
        if (log.isDebugEnabled()) {
            log.debug("DynamicDataSource switch to data source: {}", currentDataSourceKey);
        }
        return currentDataSourceKey;
    }

    @Override
    public void setTargetDataSources(@NonNull Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

}
