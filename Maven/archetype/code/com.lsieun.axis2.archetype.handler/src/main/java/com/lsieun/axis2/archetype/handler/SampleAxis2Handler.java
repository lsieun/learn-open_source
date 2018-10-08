package com.lsieun.axis2.archetype.handler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.engine.Handler;

public class SampleAxis2Handler implements Handler {
	private HandlerDescription handlerDesc;

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void flowComplete(MessageContext arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public HandlerDescription getHandlerDesc() {
		return handlerDesc;
	}

	@Override
	public String getName() {
		return "SampleAxis2Handler";
	}

	@Override
	public Parameter getParameter(String name) {
		return this.handlerDesc.getParameter(name);
	}

	@Override
	public void init(HandlerDescription handlerDesc) {
		this.handlerDesc = handlerDesc;
	}

	@Override
	public InvocationResponse invoke(MessageContext   
                       msgContext) throws AxisFault {
		return InvocationResponse.CONTINUE;
	}
}
