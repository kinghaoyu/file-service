package com.why.fileservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 描述:
 * 文件实体
 *
 * @author why 0005412
 * @date 2021-06-21
 */
@Getter
@Setter
@ToString
public class FileObject {
    private String fileId;
    private String fileName;
    private String fileType;

}