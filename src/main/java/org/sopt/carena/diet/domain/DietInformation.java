package org.sopt.carena.diet.domain;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DietInformation {

    private String documentId;
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
    public DietInformation(
            String documentId,
            String title,
            String reference,
            String referenceUrl,
            List<DietChunk> chunks
    ) {
        this.documentId = documentId;
        this.title = title;
        this.reference = reference;
        this.referenceUrl = referenceUrl;
        this.chunks = new ArrayList<>(chunks);
    }

    public void assignDocumentId(String documentId) {
        this.documentId = documentId;
    }
    public String documentId() { return documentId; }
    public String title() { return title; }
    public List<DietChunk> chunks() {
        return chunks;
    }
}