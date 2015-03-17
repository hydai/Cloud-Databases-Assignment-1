package netdb.software.benchmark.rte.executor.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;

import netdb.software.benchmark.TransactionType;
import netdb.software.benchmark.rte.txparamgen.TxParamGenerator;
import netdb.software.benchmark.rte.txparamgen.UpdateTxnParamGen;
import netdb.software.benchmark.util.JdbcService;

import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;

public class JdbcUpdateItemPriceTxnExecutor extends JdbcTxnExecutor {
	private LinkedList<Integer> itemIds;
	private LinkedList<Integer> itemPrices;
	private boolean isCommitted;
	Connection conn;

	public JdbcUpdateItemPriceTxnExecutor() {
		
	}

	@Override
	protected void prepareParams() {
		itemIds = new LinkedList<Integer>();
		itemPrices = new LinkedList<Integer>();
		// prepare your parameter here
		TxParamGenerator pg = new UpdateTxnParamGen();
		Object[] params = pg.generateParameter();
		for (int i = 0; i < params.length; i+=2) {
			itemIds.add((Integer)params[i]);
			itemPrices.add((Integer)params[i+1]);
		}
	}

	public void executeSql() {

		conn = JdbcService.connect();
		try {

			conn.setAutoCommit(false);
			for (int i = 0; i < 10; i++) {
				Statement stm = JdbcService.createStatement(conn);
				String sql = "UPDATE item SET i_price = " + itemPrices.indexOf(i)
							+ " WHERE i_id = " + itemIds.indexOf(i);
				JdbcService.executeUpdateQuery(sql, stm);;
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
		Type intType = Type.INTEGER;
		sch.addField("status", statusType);
		sch.addField("item_id", intType);

		SpResultRecord rec = new SpResultRecord();
		String status = isCommitted ? "committed" : "abort";
		rec.setVal("status", new VarcharConstant(status, statusType));
		rec.setVal("item_id", new IntegerConstant(itemIds.indexOf(0)));

		return new SpResultSet(sch, rec);
	}

	@Override
	protected TransactionType getTrasactionType() {
		return TransactionType.UPDATE_TXN;
	}

}
