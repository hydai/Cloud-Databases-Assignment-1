package netdb.software.benchmark.rte;

import netdb.software.benchmark.TestingParameters;
import netdb.software.benchmark.TxnResultSet;
import netdb.software.benchmark.rte.executor.SampleTxnExecutor;
import netdb.software.benchmark.rte.executor.TransactionExecutor;
import netdb.software.benchmark.rte.txparamgen.SampleTxnParamGen;
import netdb.software.benchmark.rte.txparamgen.UpdateItemPriceTxnParamGen;
import netdb.software.benchmark.util.RandomValueGenerator;

public class SampleRte extends RemoteTerminalEmulator {

	private RandomValueGenerator pg = new RandomValueGenerator();
	
	
	public SampleRte(Object[] connArgs) {
		super(connArgs);
	}

	@Override
	protected TxnResultSet executeTxnCycle() {
		TransactionExecutor tx = null;
		if (pg.nextDouble() > TestingParameters.UPDATE_PERCENTAGE)
			tx = new SampleTxnExecutor(new SampleTxnParamGen());
		else
			tx = new SampleTxnExecutor(new UpdateItemPriceTxnParamGen());
		return tx.execute(conn);
	}

}
