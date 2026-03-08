package org.sopt.carena.infrastructure.publicdata.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record SidoCodeResponse(
		Body body
) {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Body(
			Items items,
			int pageNo,
			int totalCount
	) {}

	public record Items(
			@JacksonXmlElementWrapper(useWrapping = false)
			@JacksonXmlProperty(localName = "item")
			List<Item> item
	) {}

	public record Item(
			String siDoNm,
			int siDoCd
	) {}
}