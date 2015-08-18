package com.simpleql.server;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.simpleql.shared.datamodel.DateElementCounter;
import com.simpleql.shared.datamodel.DateResolution;

public interface ServerStubAsync {

	void getNextLevelValues(String token, DateResolution type, String value,
			AsyncCallback<DateElementCounter[]> callback);

}
