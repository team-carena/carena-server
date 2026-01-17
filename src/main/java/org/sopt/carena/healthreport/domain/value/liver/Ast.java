package org.sopt.carena.healthreport.domain.value.liver;

import org.sopt.carena.healthreport.domain.status.liver.AstStatus;

public record Ast(
		Double value,
		AstStatus status
) {
	public static Ast from(final Double ast) {
		return new Ast(ast, AstStatus.from(ast));
	}
}