package com.yasen.soapContractLast;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.game_classes.models.game.RankingData;
import com.yasen.soapContractLast.soap_models.UserProfileDtoCL;

@WebService
public interface GameEndPoint {
	@WebMethod
	Map<String, RankingData> getAllRankingData();

	@WebMethod
	UserProfileDtoCL getRankingUserData(@WebParam(name = "username") String username,
			@WebParam(name = "pageNum") Integer pageNum);
}
