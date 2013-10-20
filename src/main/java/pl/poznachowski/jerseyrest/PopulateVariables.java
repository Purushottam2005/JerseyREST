package pl.poznachowski.jerseyrest;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class PopulateVariables extends AbstractMessageTransformer {

	@Override
	public MuleMessage transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		Object[] args = message.getPayload(Object[].class);
		message.setInvocationProperty("accountID", args[0]);
		message.setInvocationProperty("userID", args[1]);

		// For POST method
		if (args.length > 2) {
			message.setPayload(args[2]);
		}

		return message;
	}

}
