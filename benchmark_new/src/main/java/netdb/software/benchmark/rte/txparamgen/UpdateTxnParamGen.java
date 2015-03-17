package netdb.software.benchmark.rte.txparamgen;

import java.util.LinkedList;

import netdb.software.benchmark.TpccConstants;
import netdb.software.benchmark.TransactionType;
import netdb.software.benchmark.util.RandomValueGenerator;

public class UpdateTxnParamGen implements TxParamGenerator {

	public UpdateTxnParamGen() {

	}

	@Override
	public TransactionType getTxnType() {
		return TransactionType.UPDATE_TXN;
	}


	@Override
	public Object[] generateParameter() {
		RandomValueGenerator rvg = new RandomValueGenerator();
		LinkedList<Object> paramList = new LinkedList<Object>();
		for (int i = 0; i < 10; i++) {
			// item id
			paramList.add(rvg.number(1, TpccConstants.NUM_ITEMS));
			// item price
			paramList.add(rvg.number(1, TpccConstants.NUM_ITEMS));
		}
		return paramList.toArray();
	}

}
