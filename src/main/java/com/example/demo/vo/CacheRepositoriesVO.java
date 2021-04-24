package com.example.demo.vo;

import com.example.demo.dto.FilesByExtensionDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheRepositoriesVO {

    private Map<String, List<FilesByExtensionDTO>> scannedRepositories;

    private static CacheRepositoriesVO cacheRepositories;

    public static CacheRepositoriesVO getInstance() {
        if(cacheRepositories == null) {
            cacheRepositories = new CacheRepositoriesVO();
        }
        return cacheRepositories;
    }

    private CacheRepositoriesVO(){
        this.scannedRepositories = new HashMap<>();
    }

    public void add(String repo, List<FilesByExtensionDTO> files){
        scannedRepositories.put(repo, files);
    }

    public List<FilesByExtensionDTO> get(String repo){
        return scannedRepositories.get(repo);
    }

    public boolean contains(String repo){
        return scannedRepositories.containsKey(repo);
    }

}
