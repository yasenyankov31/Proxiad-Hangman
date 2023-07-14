package com.yasen.soap;

import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import com.game_classes.interfaces.jpaRepositories.RankingRepository;
import com.game_classes.interfaces.modelInterfaces.TopPlayerStats;
import com.game_classes.interfaces.modelInterfaces.UserRankData;
import com.game_classes.interfaces.services.RankingService;
import com.game_classes.interfaces.services.UserService;

@WebServiceServerTest
class StatisticsEndPointTest {

	private static final String NAME_SPACE = "http://www.game_classes.com/soap-models";

	private static final Map<String, String> NAMESPACE_MAPPING = createMapping();

	@Autowired
	private MockWebServiceClient client;

	@MockBean
	private RankingRepository rankingRepository;

	@MockBean
	private RankingService rankingService;

	@MockBean
	private UserService userService;

	private List<TopPlayerStats> topPlayerStatsTestList = new ArrayList<>();

	private List<UserRankData> rankDataTestClassList = new ArrayList<>();

	Page<UserRankData> rankDataTestClassPaged;

	@BeforeEach
	public void setup() {
		Date date = new Date(0);
		Pageable pageable = PageRequest.of(1, 1);

		TopPlayerStatsTestClass testClass = new TopPlayerStatsTestClass("test", 1, 2, date);
		topPlayerStatsTestList.add(testClass);
		UserRankDataTestClass rankDataTestClass = new UserRankDataTestClass("Won", "word", "abcd", 2, date);
		rankDataTestClassList.add(rankDataTestClass);
		rankDataTestClassPaged = new PageImpl<>(rankDataTestClassList, pageable, 0);
	}

	@Test
  void getRankingGameDataTest() throws IOException {
    when(rankingService.topTenOfAllTime()).thenReturn(topPlayerStatsTestList);

    when(rankingService.topTenOfTheMonth()).thenReturn(topPlayerStatsTestList);
    StringSource request =
        new StringSource(
            "<tns:RankingRequest xmlns:tns='" + NAME_SPACE + "'>?</tns:RankingRequest>");

    StringSource expectedResponse =
        new StringSource(
            "<ns2:RankingDataSoapList xmlns:ns2=\""
                + NAME_SPACE
                + "\"><ns2:RankingDataSoap>"
                + "<ns2:rankType>monthly</ns2:rankType>"
                + "<ns2:userNames>test</ns2:userNames>"
                + "<ns2:winCounts>1</ns2:winCounts>"
                + "</ns2:RankingDataSoap>"
                + "<ns2:RankingDataSoap>"
                + "<ns2:rankType>allTime</ns2:rankType>"
                + "<ns2:userNames>test</ns2:userNames>"
                + "<ns2:winCounts>1</ns2:winCounts>"
                + "</ns2:RankingDataSoap>"
                + "</ns2:RankingDataSoapList>");

    client
        .sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(validPayload(new ClassPathResource("hangman.xsd")))
        .andExpect(payload(expectedResponse))
        .andExpect(
            xpath("/ns2:RankingDataSoapList/ns2:RankingDataSoap[1]/ns2:rankType", NAMESPACE_MAPPING)
                .evaluatesTo("monthly"));
  }

	@Test
  void getRankingUserDataTest() throws IOException {
    when(rankingService.getUserInfo("test123", 0)).thenReturn(rankDataTestClassPaged);
    when(rankingService.getUserInfo("test123", null)).thenReturn(rankDataTestClassPaged);
    when(!userService.checkIfUserExist("test123")).thenReturn(true);
    StringSource request =
        new StringSource(
            "<tns:UserRankingRequest xmlns:tns='"
                + NAME_SPACE
                + "'>"
                + "<tns:username>test123</tns:username>"
                + "<tns:pageNum>0</tns:pageNum>"
                + "</tns:UserRankingRequest>");

    StringSource expectedResponse =
        new StringSource(
            "<ns2:UserProfileSoap xmlns:ns2=\""
                + NAME_SPACE
                + "\">"
                + "<ns2:statusValues>1</ns2:statusValues>"
                + "<ns2:userRankDataPaged>"
                + "<ns2:UserRankDataList>"
                + "<ns2:gameStatus>Won</ns2:gameStatus>"
                + "<ns2:word>word</ns2:word>"
                + "<ns2:lettersUsed>abcd</ns2:lettersUsed>"
                + "<ns2:attemptsLeft>2</ns2:attemptsLeft>"
                + "<ns2:startDate>1970-01-01+02:00</ns2:startDate>"
                + "</ns2:UserRankDataList>"
                + "</ns2:userRankDataPaged>"
                + "<ns2:winCount>1</ns2:winCount>"
                + "<ns2:lossCount>0</ns2:lossCount>"
                + "</ns2:UserProfileSoap>");

    client
        .sendRequest(withPayload(request))
        .andExpect(noFault())
        .andExpect(validPayload(new ClassPathResource("hangman.xsd")))
        .andExpect(payload(expectedResponse))
        .andExpect(
            xpath("/ns2:UserProfileSoap/ns2:statusValues", NAMESPACE_MAPPING).evaluatesTo("1"));
  }

	@Test
	void getRankingUserDataInvalidUsernameTest() throws IOException {
	    when(rankingService.getUserInfo("test123", 0)).thenReturn(rankDataTestClassPaged);
	    when(rankingService.getUserInfo("test123", null)).thenReturn(rankDataTestClassPaged);
	    StringSource request =
	        new StringSource(
	            "<tns:UserRankingRequest xmlns:tns='"
	                + NAME_SPACE
	                + "'>"
	                + "<tns:username>test123</tns:username>"
	                + "<tns:pageNum>0</tns:pageNum>"
	                + "</tns:UserRankingRequest>");

	    StringSource expectedFaultResponse =
	            new StringSource("<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
	            		+ "<faultcode xmlns:ns0=\"http://www.game_classes.com/soap-models\">"
	            		+ "ns0:User with this username doesn't exist!"
	            		+ "</faultcode>"
	            		+ "<faultstring xml:lang=\"en\">@faultString</faultstring></SOAP-ENV:Fault>");

	    client
	        .sendRequest(withPayload(request))// Asserting a fault response
	        .andExpect(payload(expectedFaultResponse));
	}

	private static Map<String, String> createMapping() {
		Map<String, String> mapping = new HashMap<>();
		mapping.put("ns2", NAME_SPACE);
		return mapping;
	}
}
