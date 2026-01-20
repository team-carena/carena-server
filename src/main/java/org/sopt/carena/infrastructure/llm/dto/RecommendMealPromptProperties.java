package org.sopt.carena.infrastructure.llm.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rag.prompt.meal")
public record RecommendMealPromptProperties(
		String systemPrompt,
		String userPrompt
) {
}
