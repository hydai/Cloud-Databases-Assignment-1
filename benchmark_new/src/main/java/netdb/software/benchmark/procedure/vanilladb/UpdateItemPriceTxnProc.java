package netdb.software.benchmark.procedure.vanilladb;

import java.sql.Connection;

import netdb.software.benchmark.procedure.UpdateItemPriceTxnParamHelper;

import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.server.VanillaDb;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

public class UpdateItemPriceTxnProc implements StoredProcedure {

	private UpdateItemPriceTxnParamHelper paramHelper = new UpdateItemPriceTxnParamHelper();
	private Transaction tx;

	@Override
	public void prepare(Object... pars) {
		paramHelper.prepareParameters(pars);
	}

	@Override
	public SpResultSet execute() {
		tx = VanillaDb.txMgr().transaction(Connection.TRANSACTION_SERIALIZABLE,
				false);
		try {
			executeSql();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			paramHelper.setCommitted(false);
			e.printStackTrace();
		}
		return paramHelper.createResultSet();
	}

	protected void executeSql() {
		for (int idx = 0; idx < 10; idx++) {
			String sql = "UPDATE item SET i_price = "
					+ paramHelper.getItemPrices()[idx] +  " WHERE i_id ="
					+ paramHelper.getItemIds()[idx];
			System.out.println(sql);
			VanillaDb.newPlanner().executeUpdate(sql, tx);
		}
	}
}