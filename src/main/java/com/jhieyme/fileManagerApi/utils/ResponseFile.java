package com.jhieyme.fileManagerApi.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFile {

    private Object id;
    private String name;
    private String url;
    private String type;
    private long size;

}
