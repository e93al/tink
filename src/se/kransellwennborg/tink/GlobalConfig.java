package se.kransellwennborg.tink;

import se.kransellwennborg.tink.dao.MysqlConnectionProvider;

public class GlobalConfig {
//	public final static boolean IS_PW_CONNECTED = true;

	public static boolean isPwConnected () {
		return !MysqlConnectionProvider.host.equals("localhost");
		
	}
}