package netdb.software.benchmark.procedure.vanilladb;

import java.sql.Connection;

import netdb.software.benchmark.procedure.UpdatePriceTxnProcParamHelper;
import org.vanilladb.core.remote.storedprocedure.SpResultSet;
import org.vanilladb.core.server.VanillaDb;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

public class UpdateTxnProc implements StoredProcedure {

	private UpdatePriceTxnProcParamHelper paramHelper = new UpdatePriceTxnProcParamHelper();
	private Transaction tx;

	public UpdateTxnProc() {
	}

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
		for (int i = 0; i < 10; i++) {
			String cmd = "UPDATE item SET i_price = " + paramHelper.getItemPrice() + " WHERE i_id = "
					+ paramHelper.getItemId();
			VanillaDb.newPlanner().executeUpdate(cmd, tx);
			paramHelper.next();
		}
	}
}
