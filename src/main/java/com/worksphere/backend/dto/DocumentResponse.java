package com.worksphere.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponse {

    private Long id;

    private  String documentName;

    private  String documentType;

    private  String fileUrl;
}
