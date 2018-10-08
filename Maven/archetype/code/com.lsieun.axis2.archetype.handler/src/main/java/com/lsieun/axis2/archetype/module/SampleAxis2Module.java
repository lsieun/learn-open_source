package com.lsieun.axis2.archetype.module;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class SampleAxis2Module implements Module {
    @Override
	public void applyPolicy(Policy policy, AxisDescription ad) throws AxisFault {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean canSupportAssertion(Assertion ass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void engageNotify(AxisDescription ad) throws AxisFault {
		// TODO Auto-generated method stub		
	}

	@Override
	public void init(ConfigurationContext cc, AxisModule am) throws AxisFault {
		// TODO Auto-generated method stub		
	}

	@Override
	public void shutdown(ConfigurationContext cc) throws AxisFault {
		// TODO Auto-generated method stub	
	}   
}
