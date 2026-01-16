package org.sopt.carena.diet.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DietInformation {

    private final String title;
    private final String reference;
    private final String referenceUrl;
    private final List<DietChunk> chunks;

    public DietInformation(
            String title,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks
    ) {
        this.title = title;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
        this.chunks = new ArrayList<>(chunks);
    }
    public static DietInformation create(
            String title,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks
    ) {
        return new DietInformation(title, reference, referenceUrl, chunks);
    }

    public String title() {
        return title;
    }
    public List<DietChunk> chunks() {
        return chunks;
    }
}