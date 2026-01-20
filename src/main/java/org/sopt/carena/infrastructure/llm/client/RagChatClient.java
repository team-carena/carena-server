package org.sopt.carena.infrastructure.llm.client;

import java.util.List;
import java.util.stream.Collectors;

import org.sopt.carena.infrastructure.llm.dto.RecommendedMealResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RagChatClient {
	private final ChatClient chatClient;
	private final ObjectMapper objectMapper;

	public RecommendedMealResult getRecommendedMeal(String healthState, List<Document> documents) {
		// 예외 추가

		String systemPrompt = """
				너는 사용자의 건강검진 정보를 바탕으로 가장 적합한 식단 메뉴를 추천하는 헬스케어 식단 추천 AI다.
				
				규칙:
				- CONTEXT와 USER_STATE에 포함된 정보만 사용한다
				- USER_STATE는 사용자의 현재 건강 상태를 의미한다
				- CONTEXT는 식단 및 영양에 대한 참고 정보이다
				- 의학적 진단이나 치료 목적의 조언은 하지 않는다
				- 건강 관리 목적의 식단 추천만 수행한다
				- 출력은 반드시 JSON 형식으로 "meal"과 "description" 두 필드만을 포함한다
				- 설명, 부연 문장, 기호, 따옴표, 줄바꿈 등을 포함하지 않는다
				- 한국어 메뉴명으로 작성한다
				- 메뉴가 불명확할 경우 가장 보편적이고 안전한 메뉴 하나를 선택한다
				
				출력 예시:
				{"meal": [메뉴 이름], "description": [메뉴 추천 이유]}
				""";

		String userPrompt = buildUserPrompt(healthState, documents);

		String rawTextResponse = chatClient.prompt()
				.system(systemPrompt)
				.user(userPrompt)
				.call()
				.content();

		return parse(rawTextResponse);
	}

	private String buildUserPrompt(String healthState, List<Document> documents) {
		documents.forEach(document -> System.out.println(document.getText()));
		String context = documents.stream()
				.map(Document::getText)
				.collect(Collectors.joining("\n\n---\n\n"));

		return """
				다음은 사용자의 건강 상태와 관련된 참고 정보이다.
				[USER_STATE]
				%s
				
				[CONTEXT]
				%s
				
				위 정보를 모두 고려하여 사용자에게 가장 적합한 식단 메뉴 하나와 추천 이유를 JSON 형식으로 작성하라.
				
				출력 예시:
				{"meal": [메뉴 이름], "description": [메뉴 추천 이유]}
				""".formatted(healthState, context);
	}

	private RecommendedMealResult parse(String response) {
		try {
			return objectMapper.readValue(response, RecommendedMealResult.class);
		} catch (Exception e) {
			throw new IllegalStateException(
					"LLM 응답을 RecommendedMealResult로 변환 실패: " + response, e
			);
		}
	}
}
