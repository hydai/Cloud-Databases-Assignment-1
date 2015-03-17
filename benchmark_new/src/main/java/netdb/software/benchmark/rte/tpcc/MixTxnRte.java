package netdb.software.benchmark.rte.tpcc;

import netdb.software.benchmark.TxnResultSet;
import netdb.software.benchmark.rte.RemoteTerminalEmulator;
import netdb.software.benchmark.rte.executor.SampleTxnExecutor;
import netdb.software.benchmark.rte.executor.TransactionExecutor;
import netdb.software.benchmark.rte.executor.UpdateItemPriceTxnExecutor;
import netdb.software.benchmark.rte.txparamgen.SampleTxnParamGen;
import netdb.software.benchmark.rte.txparamgen.UpdateTxnParamGen;
import netdb.software.benchmark.util.RandomValueGenerator;

public class MixTxnRte extends RemoteTerminalEmulator {

	private int txRadio = 75;
	private SampleTxnParamGen sampleTxnParamGem;
	private UpdateTxnParamGen updateTxnParamGen;

	public MixTxnRte(Object[] connArgs) {
		super(connArgs);
		sampleTxnParamGem = new SampleTxnParamGen();
		updateTxnParamGen = new UpdateTxnParamGen();
	}

	@Override
	protected TxnResultSet executeTxnCycle() {
		RandomValueGenerator rvg = new RandomValueGenerator();
		int randomTarget = rvg.number(1, 100);

		TransactionExecutor tx;
		if (randomTarget <= txRadio) {
			tx = new SampleTxnExecutor(sampleTxnParamGem);
		} else {
			tx = new UpdateItemPriceTxnExecutor(updateTxnParamGen);
		}
		return tx.execute(conn);
	}

}