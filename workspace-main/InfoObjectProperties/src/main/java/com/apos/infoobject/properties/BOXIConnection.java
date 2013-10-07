/**
 * 
 */
package com.apos.infoobject.properties;

import org.apache.log4j.Logger;

import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;

/**
 * @author Yuri Goron
 * 
 */
public class BOXIConnection {
	static Logger Log = Logger.getLogger(BOXIConnection.class.getName());
	public static final String CMS_INFOSTORE = "InfoStore";
	private IEnterpriseSession enterpriseSession;
	private IInfoStore infoStore;

	/**
	 * @param connectionInfo
	 */
	public BOXIConnection(BOXIConnectionInfo connectionInfo) {
		Log.debug("Creating Connection for CMS:"+connectionInfo.getCmsName());
		enterpriseSession = getEnterpriseSession(connectionInfo);
		
	}

	/**
	 * Gets Enterprise Session
	 * 
	 * @param connectionInfo
	 * @return
	 */
	private IEnterpriseSession getEnterpriseSession(BOXIConnectionInfo connectionInfo) {
		try {

			IEnterpriseSession enterpriseSession = null;
			// if (connectionInfo.getCmsToken().equalsIgnoreCase("")) {
			
			if (connectionInfo.isUseSerializedSession()) {
				Log.debug("CMS Logon using Serialized Session");
//				enterpriseSession = CrystalEnterprise.getSessionMgr().getSession(connectionInfo.getCmsToken());
				enterpriseSession = CrystalEnterprise.getSessionMgr().getSession(connectionInfo.getSerializedSession());
				Log.debug("User :"+enterpriseSession.getUserInfo().getUserName());
			}else{
			if (!connectionInfo.getCmsName().equalsIgnoreCase("")) {
				Log.debug("CMS Logon using users credentials");
				enterpriseSession = CrystalEnterprise.getSessionMgr().logon(connectionInfo.getCmsUser(), connectionInfo.getCmsPassword(), connectionInfo.getCmsName(), connectionInfo.getCmsAuthType());
			} else if (connectionInfo.getCmsToken() != null) {
				Log.debug("CMS Logon Token");
				enterpriseSession = CrystalEnterprise.getSessionMgr().logonWithToken(connectionInfo.getCmsToken());

			}  
			}

			infoStore = (IInfoStore) enterpriseSession.getService("", CMS_INFOSTORE);
			Log.debug("Log on User:" + enterpriseSession.getUserInfo().getUserName());
			return enterpriseSession;

		} catch (Exception ee) {
			System.err.println(ee.getLocalizedMessage());
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return null;
	}

	/**
	 * @return the enterpriseSession
	 */
	public IEnterpriseSession getEnterpriseSession() {
		return enterpriseSession;
	}

	/**
	 * @return the infoStore
	 */
	public IInfoStore getInfoStore() {
		return infoStore;
	}

}
