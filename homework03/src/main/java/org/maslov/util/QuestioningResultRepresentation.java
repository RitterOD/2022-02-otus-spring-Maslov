package org.maslov.util;

import org.maslov.model.QuestioningResult;
import org.springframework.stereotype.Service;

@Service
public class QuestioningResultRepresentation {
    public static void printToSystemOut(QuestioningResult result) {
        System.out.println(result.toString());
    }
}
