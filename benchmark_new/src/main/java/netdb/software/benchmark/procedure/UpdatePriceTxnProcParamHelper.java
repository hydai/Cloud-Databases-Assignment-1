package netdb.software.benchmark.procedure;

import java.util.LinkedList;

import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class UpdatePriceTxnProcParamHelper extends StoredProcedureParamHelper {

	private LinkedList<Integer> itemId;
	private LinkedList<Integer> itemPrice;
	int currentIndex;
	public UpdatePriceTxnProcParamHelper() {
		currentIndex = 0;
	}
	public int getItemId(){
		return itemId.indexOf(currentIndex);
	}
	
	public int getItemPrice(){
		return itemPrice.indexOf(currentIndex);
	}
	
	public void next() {
		currentIndex++;
	}

	@Override
	public void prepareParameters(Object... pars) {
		itemId = new LinkedList<Integer>();
		itemPrice = new LinkedList<Integer>();
		for (int i = 0; i < pars.length; i+=2) {
			itemId.add((Integer)pars[i]);
			itemPrice.add((Integer)pars[i+1]);
		}
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
