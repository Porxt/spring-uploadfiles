package com.softwareallies.uploadfiles.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("storage")
@Setter @Getter
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
}
