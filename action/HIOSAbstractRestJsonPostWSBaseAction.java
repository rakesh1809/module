package org.cms.hios.common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cms.hios.common.exception.HIOSException;
import org.cms.hios.common.exception.HiosRuntimeException;
import org.cms.hios.common.transfer.json.JsonResponse;
import org.cms.hios.common.transfer.json.JsonResponseObj;
import org.cms.hios.common.util.CommonUtilFunctions;
import org.cms.hios.common.util.ResponseStatusEnum;
import org.jboss.soa.esb.message.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class HIOSAbstractRestJsonPostWSBaseAction extends HIOSAbstractWSBaseAction{

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected Gson getJsonHandler(){
		return gson;
	}

	protected abstract List<JsonResponseObj> executeJsonService(HashMap<String, Object> message, Map<String, String[]> Params) throws HiosRuntimeException, HIOSException ;

	@Override
	protected String executeAction(HashMap<String, Object> message) throws HiosRuntimeException, HIOSException {
		long startTime = System.currentTimeMillis();
		Map<String, String[]> params = (HashMap<String, String[]>) message.get("params");
		//HttpRequest req = HttpRequest.getRequest(message);
		//Map<String, String[]> params =  new HashMap<String, String[]>();//req.getQueryParams();
		// create json response
		JsonResponse jsonResponse = new JsonResponse();
		// get data from impl.
		List<JsonResponseObj> resultList;

		try {
			resultList = executeJsonService(message, params);
			jsonResponse.setList(resultList);
			if(isResponseErrored(resultList)){
				jsonResponse.setStatus(ResponseStatusEnum.SUCCESS_WITH_ERROR.getStatus());
			} else{
				jsonResponse.setStatus(ResponseStatusEnum.SUCCESS.getStatus());
			}
		} catch (HiosRuntimeException hiosRuntimeException) {
			jsonResponse.setStatus(ResponseStatusEnum.FAILURE.getStatus());
			jsonResponse.setErrorCode(hiosRuntimeException.getMessage());
			jsonResponse.setErrorDescription(hiosRuntimeException.getMessage().toString());
		} catch (HIOSException hiosException) {
			jsonResponse.setStatus(ResponseStatusEnum.FAILURE.getStatus());
			jsonResponse.setErrorCode(hiosException.getExceptionType().getErrorMsg());
			jsonResponse.setErrorDescription(hiosException.getExceptionType().getErrorMsg());
		}

		String response = getJsonHandler().toJson(jsonResponse);
		//message.getBody().add(response);
		//setContentType(message);
		logger.info(this.getClass().getName() +": processed in "
				+ (System.currentTimeMillis() - startTime) + " ms.");
		return response;
	}

	private boolean isResponseErrored(List<JsonResponseObj> resultList) {
		for (JsonResponseObj jsonResponseObj : resultList) {
			if((CommonUtilFunctions.isNotNullAndNotEmpty(jsonResponseObj.getError() ))){
				return true;
			}
		}
		return false;
	}

	protected void setContentType(Message message) {
	}
}
