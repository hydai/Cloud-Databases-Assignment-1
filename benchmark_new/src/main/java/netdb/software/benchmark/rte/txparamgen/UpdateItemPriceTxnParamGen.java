package netdb.software.benchmark.rte.txparamgen;

import java.util.LinkedList;

import netdb.software.benchmark.TpccConstants;
import netdb.software.benchmark.TransactionType;
import netdb.software.benchmark.rte.txparamgen.TxParamGenerator;
import netdb.software.benchmark.util.RandomNonRepeatGenerator;
import netdb.software.benchmark.util.RandomValueGenerator;

public class UpdateItemPriceTxnParamGen implements TxParamGenerator {
	
	@Override
	public TransactionType getTxnType() {
		return TransactionType.UPDATE_TXN;
	}


	@Override
	public Object[] generateParameter() {
		RandomValueGenerator rg = new RandomValueGenerator();
		RandomNonRepeatGenerator rgN = new RandomNonRepeatGenerator(TpccConstants.NUM_ITEMS);
		LinkedList<Object> paramList = new LinkedList<Object>();

		// Randomly select 10 non-repeat IDs
		for (int i = 0; i < 10; i++)
			paramList.add(rgN.next());
		
		// Randomly generate 10 new prices
		for (int i = 0; i < 10; i++)
			paramList.add(rg.nextDouble()*100000);

		return paramList.toArray();
	}
	
}
