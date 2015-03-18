package netdb.software.benchmark.procedure;

import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class UpdateItemPriceTxnParamHelper extends StoredProcedureParamHelper {

	private int[] itemIds = new int[10];
	private double[] itemPrices = new double[10];

	public int[] getItemIds(){
		return itemIds;
	}
	
	public double[] getItemPrices(){
		return itemPrices;
	}
	

	@Override
	public void prepareParameters(Object... pars) {
		if (pars.length != 20)
			throw new RuntimeException("Invalid parameters");
		
		for (int idx = 0; idx < 10; idx++)
			itemIds[idx] = (Integer) pars[idx];
		
		for (int idx = 0; idx < 10; idx++)
			itemPrices[idx] = (Double) pars[10+idx];
	}

	@Override
	public SpResultSet createResultSet() {
		/*
		 * TODO The output information is not strictly followed the TPC-C
		 * definition. See the session 2.4.3.5 in TPC-C 5.11 document.
		 */
		Schema sch = new Schema();
		Type statusType = Type.VARCHAR(10);
		sch.addField("status", statusType);

		SpResultRecord rec = new SpResultRecord();
		String status = isCommitted ? "committed" : "abort";
		rec.setVal("status", new VarcharConstant(status, statusType));

		return new SpResultSet(sch, rec);
	}
	
}