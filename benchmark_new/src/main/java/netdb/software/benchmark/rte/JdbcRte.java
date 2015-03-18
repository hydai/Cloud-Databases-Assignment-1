package netdb.software.benchmark.rte;

import netdb.software.benchmark.TestingParameters;
import netdb.software.benchmark.TxnResultSet;
import netdb.software.benchmark.rte.executor.jdbc.JdbcSampleTxnExecutor;
import netdb.software.benchmark.rte.executor.jdbc.JdbcTxnExecutor;
import netdb.software.benchmark.rte.executor.jdbc.JdbcUpdateTimePriceTxnExecutor;
import netdb.software.benchmark.util.RandomValueGenerator;

public class JdbcRte extends RemoteTerminalEmulator {

	private RandomValueGenerator pg = new RandomValueGenerator();
	
	public JdbcRte(Object... args) {
		super(args);
	}

	@Override
	public TxnResultSet executeTxnCycle() {
	
		JdbcTxnExecutor txnExecutor = null;
		
		if (pg.nextDouble() > TestingParameters.UPDATE_PERCENTAGE)
			txnExecutor = new JdbcSampleTxnExecutor();
		else
			txnExecutor = new JdbcUpdateTimePriceTxnExecutor();
		
		return txnExecutor.execute();
	}

}
