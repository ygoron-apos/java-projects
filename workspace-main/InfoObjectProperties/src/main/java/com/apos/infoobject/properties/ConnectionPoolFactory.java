/**
 * 
 */
package com.apos.infoobject.properties;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 * 
 */
public class ConnectionPoolFactory extends BasePoolableObjectFactory<BOXIConnection> {

	private static Logger Log = Logger.getLogger(ConnectionPoolFactory.class);
	private BOXIConnectionInfo boxiConnectionInfo;
	private static boolean isSerSessionLogOff = false;

	public ConnectionPoolFactory(BOXIConnectionInfo boxiConnectionInfo) {
		Log.debug("Constructor. Boxi ConnectionInfo For CMS:"+boxiConnectionInfo.getCmsName());
		this.boxiConnectionInfo = boxiConnectionInfo;

		FileInputStream inputStream = null;
		DataInputStream dataInputStream = null;
		BufferedReader bufferedReader = null;

		try {
			inputStream = new FileInputStream(boxiConnectionInfo.getSerializedSessionFile());
			dataInputStream = new DataInputStream(inputStream);
			bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			String serializedSession = bufferedReader.readLine();
			Log.debug("Readed Serialized Session:" + serializedSession);
			this.boxiConnectionInfo.setSerializedSession(serializedSession);
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception ee) {
			}
			try {
				if (dataInputStream != null)
					dataInputStream.close();
			} catch (Exception ee) {
			}

			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (Exception ee) {
			}
		}

	}

	@Override
	public BOXIConnection makeObject() throws Exception {
		Log.debug("Make New Connection");
		BOXIConnection boxiConnection = new BOXIConnection(boxiConnectionInfo);
		return boxiConnection;
	}

	@Override
	public void destroyObject(BOXIConnection boxiConnection) {
		Log.debug("Destroy Connection");
		try {
			try {

			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
			}
			try {
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
			}

			if (boxiConnection != null) {
				if (this.boxiConnectionInfo.isUseSerializedSession() && !isSerSessionLogOff) {
					boxiConnection.getEnterpriseSession().logoff();
					isSerSessionLogOff = true;
					Log.debug("Serialized Enterprise Session Logoff");
				} else if (!this.boxiConnectionInfo.isUseSerializedSession()) {
					boxiConnection.getEnterpriseSession().logoff();
					Log.debug("Enterprise Session Logoff");
				}

			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

}
