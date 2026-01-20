package org.sopt.carena.infrastructure.llm.client;

import java.util.List;
import java.util.stream.Collectors;

import org.sopt.carena.infrastructure.exception.LLMResultDeserializationException;
import org.sopt.carena.infrastructure.llm.dto.RecommendMealPromptProperties;
import org.sopt.carena.infrastructure.llm.dto.RecommendedMealResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RagChatClient {
	private static final String LINE_SEPARATOR = "\n\n----------\n\n";
	private final RecommendMealPromptProperties recommendMealPromptProperties;
	private final ChatClient chatClient;
	private final ObjectMapper objectMapper;

	public RecommendedMealResult getRecommendedMeal(String healthState, List<Document> documents) {
		String systemPrompt = recommendMealPromptProperties.systemPrompt();

		String userPrompt = buildUserPrompt(healthState, documents);

		log.info("프롬프트 생성 성공, LLM 호출");

		String rawTextResponse = chatClient.prompt()
				.system(systemPrompt)
				.user(userPrompt)
				.call()
				.content();

		log.debug(rawTextResponse);

		return parse(rawTextResponse);
	}

	private String buildUserPrompt(String healthState, List<Document> documents) {
		String context = documents.stream()
				.map(Document::getText)
				.collect(Collectors.joining(LINE_SEPARATOR));

		return recommendMealPromptProperties.userPrompt()
				.replace("{healthState}", healthState)
				.replace("{context}", context);
	}

	private RecommendedMealResult parse(String response) {
		try {
			return objectMapper.readValue(response, RecommendedMealResult.class);
		} catch (JsonProcessingException e) {
			log.error("LLM 호출 결과 역직렬화 실패: "+e.getMessage());
			throw new LLMResultDeserializationException();
		}
	}
}
