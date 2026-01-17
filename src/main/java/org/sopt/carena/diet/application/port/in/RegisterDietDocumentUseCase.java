package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.application.dto.command.CreateDietCommand;

public interface RegisterDietDocumentUseCase {
    void register(CreateDietCommand command);
}
