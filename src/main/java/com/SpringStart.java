package com;

import javax.xml.ws.Endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yasen.soapContractLast.GameEndPoint;

@SpringBootApplication
public class SpringStart {

	private static GameEndPoint gameEndPoint;

	@Autowired
	public void setGameEndPoint(GameEndPoint gameEndPoint) {
		SpringStart.gameEndPoint = gameEndPoint;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringStart.class, args);
		Endpoint.publish("http://localhost:8082/gameendpoint", gameEndPoint);

	}

}
