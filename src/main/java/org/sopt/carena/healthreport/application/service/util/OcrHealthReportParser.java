package org.sopt.carena.healthreport.application.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sopt.carena.healthreport.adapter.out.web.ocr.response.OcrResponse;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;

public class OcrHealthReportParser {
	private static final Pattern COMMON_PATTERN =
			Pattern.compile(".*?(비해당|\\b\\d+(?:\\.\\d+)?\\b)");
	private static final Pattern HEIGHT_PATTERN =
			Pattern.compile("키.*?(\\d+\\.\\d+)");
	private static final Pattern WEIGHT_PATTERN =
			Pattern.compile("키.*?\\d+\\.\\d+\\s*/\\s*(\\d+\\.\\d+)");
	private static final Pattern BMI_PATTERN =
			Pattern.compile("체질량지수\\s*\\(kg\\s*/\\s*m(?:2|²)\\)\\s*(\\d+\\.\\d+)");
	private static final Pattern BP_PATTERN =
			Pattern.compile("(\\d{2,3})\\s*/\\s*(\\d{2,3})\\s*mmHg");

	//ExtractedTextView
	public static ExtractedTextView parse(final OcrResponse ocrResponse) {
		List<String> lines = extractLines(ocrResponse);

		return ExtractedTextView.of(
				extractHeight(lines),
				extractWeight(lines),
				extractDoubleByKeyword(lines, "허리둘레"),
				extractBmi(lines),
				extractSystolicBp(lines),
				extractDiastolicBp(lines),
				extractDoubleByKeyword(lines, "혈색소"),
				extractDoubleByKeyword(lines, "공복혈당"),
				extractDoubleByKeyword(lines, "총콜레스테롤"),
				extractDoubleByKeyword(lines, "고밀도 콜레스테롤"),
				extractDoubleByKeyword(lines, "저밀도 콜레스테롤"),
				extractDoubleByKeyword(lines, "중성지방"),
				extractDoubleByKeyword(lines, "혈청크레아티닌"),
				extractDoubleByKeyword(lines, "신사구체여과율"),
				extractDoubleByKeyword(lines, "AST"),
				extractDoubleByKeyword(lines, "ALT"),
				extractDoubleByKeyword(lines, "감마지티피")
		);
	}

	private static List<String> extractLines(final OcrResponse ocrResponse) {
		List<String> lines = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();

		ocrResponse.images().stream()
				.filter(image -> image.fields() != null)
				.flatMap(image -> image.fields().stream())
				.forEach(field -> {
					currentLine.append(field.inferText());

					if (field.lineBreak()) {
						lines.add(currentLine.toString().trim());
						currentLine.setLength(0);
					} else {
						currentLine.append(" ");
					}
				});

		if (!currentLine.isEmpty()) {
			lines.add(currentLine.toString().trim());
		}

		return lines;
	}

	private static Double extractDoubleByKeyword(final List<String> lines, final String keyword) {
		Pattern pattern = Pattern.compile(
				Pattern.quote(keyword) + COMMON_PATTERN
		);

		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String value = matcher.group(1);
				if ("비해당".equals(value)) {
					return null;
				}
				return Double.parseDouble(value);
			}
		}
		return null;
	}

	private static Double extractFirstDouble(final List<String> lines, final Pattern pattern) {
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				return Double.parseDouble(matcher.group(1));
			}
		}
		return null;
	}

	private static Double extractHeight(final List<String> lines) {
		return extractFirstDouble(lines, HEIGHT_PATTERN);
	}

	private static Double extractWeight(final List<String> lines) {
		return extractFirstDouble(lines, WEIGHT_PATTERN);
	}

	private static Double extractBmi(final List<String> lines) {
		return extractFirstDouble(lines, BMI_PATTERN);
	}

	private static Integer extractSystolicBp(final List<String> lines) {
		return extractBp(lines, 1);
	}

	private static Integer extractDiastolicBp(final List<String> lines) {
		return extractBp(lines, 2);
	}

	private static Integer extractBp(final List<String> lines, final int groupIndex) {
		for (String line : lines) {
			Matcher matcher = BP_PATTERN.matcher(line);
			if (matcher.find()) {
				return Integer.parseInt(matcher.group(groupIndex));
			}
		}
		return null;
	}
}
