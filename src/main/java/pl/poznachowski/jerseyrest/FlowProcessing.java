package pl.poznachowski.jerseyrest;

public interface FlowProcessing {

	String processGET(String accountId, String userId);
	String processPOST(String accountId, String userId, String body);
}
