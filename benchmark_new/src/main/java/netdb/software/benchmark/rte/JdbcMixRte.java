package netdb.software.benchmark.rte;

import netdb.software.benchmark.TxnResultSet;
import netdb.software.benchmark.rte.executor.jdbc.JdbcSampleTxnExecutor;
import netdb.software.benchmark.rte.executor.jdbc.JdbcTxnExecutor;
import netdb.software.benchmark.rte.executor.jdbc.JdbcUpdateItemPriceTxnExecutor;
import netdb.software.benchmark.util.RandomValueGenerator;

public class JdbcMixRte extends RemoteTerminalEmulator {
	private int txRadio = 75;
	
	public JdbcMixRte(Object... args) {
		super(args);
	}

	@Override
	public TxnResultSet executeTxnCycle() {
		RandomValueGenerator rvg = new RandomValueGenerator();
		int randomTarget = rvg.number(1, 100);
		
		JdbcTxnExecutor txnExecutor = null;
		if (randomTarget <= txRadio) {
			txnExecutor = new JdbcSampleTxnExecutor();
		} else {
			txnExecutor = new JdbcUpdateItemPriceTxnExecutor();
		}
		return txnExecutor.execute();
	}

}
