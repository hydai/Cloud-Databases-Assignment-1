package netdb.software.benchmark.rte.executor.jdbc;

import java.sql.Connection;
import java.sql.Statement;

import netdb.software.benchmark.TransactionType;
import netdb.software.benchmark.rte.txparamgen.TxParamGenerator;
import netdb.software.benchmark.rte.txparamgen.UpdateItemPriceTxnParamGen;
import netdb.software.benchmark.util.JdbcService;

import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;

public class JdbcUpdateTimePriceTxnExecutor extends JdbcTxnExecutor {

	private int[] itemIds = new int[10];
	private double[] itemPrices = new double[10];
	private boolean isCommitted;
	Connection conn;


	@Override
	protected void prepareParams() {
		// prepare your parameter here
		TxParamGenerator pg = new UpdateItemPriceTxnParamGen();
		Object[] param = pg.generateParameter();
		
		for (int idx = 0; idx < 10; idx++)
			itemIds[idx] = (Integer) param[idx];
		
		for (int idx = 0; idx < 10; idx++)
			itemPrices[idx] = (Double) param[10+idx];
	}

	public void executeSql() {

		conn = JdbcService.connect();
		try {

			conn.setAutoCommit(false);
			
			Statement stm = JdbcService.createStatement(conn);

			for (int idx = 0; idx < 10; idx++) {
				String sql = "UPDATE item SET i_price = "
						+ itemPrices[idx] +  " WHERE i_id ="
						+ itemIds[idx];
				stm.executeUpdate(sql);
			}
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcService.disconnect(conn);
		}
	}

	protected SpResultSet createResultSet() {
		/*
		 * TODO The output information is not strictly followed the TPC-C
		 * definition. See the session 2.6.3.4 in TPC-C 5.11 document.
		 */
		Schema sch = new Schema();
		Type statusType = Type.VARCHAR(10);
		sch.addField("status", statusType);

		SpResultRecord rec = new SpResultRecord();
		String status = isCommitted ? "committed" : "abort";
		rec.setVal("status", new VarcharConstant(status, statusType));

		return new SpResultSet(sch, rec);
	}

	@Override
	protected TransactionType getTrasactionType() {
		return TransactionType.UPDATE_TXN;
	}

}