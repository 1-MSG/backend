package spharos.msg.global.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RequiredArgsConstructor
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnlyTransaction = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        return (isReadOnlyTransaction) ?
            DatabaseType.SLAVE :
            DatabaseType.MASTER;
    }
}