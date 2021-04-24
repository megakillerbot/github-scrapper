package com.example.demo.service;

import com.example.demo.dto.FileInfoDTO;
import com.example.demo.dto.FilesByExtensionDTO;
import com.example.demo.util.DTOUtil;
import com.example.demo.util.RestUtil;
import com.example.demo.util.ScrapperUtils;
import com.example.demo.vo.CacheRepositoriesVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class GithubScrapperService {

    private static final String GITHUB = "https://github.com";
    private static final String GITHUB_RAW = "https://raw.githubusercontent.com";
    private static final List<String> IMAGE_TYPES = Arrays.asList(ImageIO.getReaderFormatNames());
    private static final String KEY_TAG = "#repo-content-pjax-container";

    public String execute(String repo, boolean refresh) {
        List<FilesByExtensionDTO> groupedFiles;
        CacheRepositoriesVO repositories = CacheRepositoriesVO.getInstance();
        if(repositories.contains(repo) && !refresh) {
            groupedFiles = repositories.get(repo);
        } else {
            List<FileInfoDTO> allFiles = readDirFiles(repo);
            groupedFiles = groupFiles(allFiles);
            repositories.add(repo, groupedFiles);
        }
        try {
            return DTOUtil.toJson(groupedFiles);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private List<FilesByExtensionDTO> groupFiles(List<FileInfoDTO> files){
        Map<String, FilesByExtensionDTO> filesMap = new HashMap<>();
        files.forEach(f -> {
            String ext = f.getExtension();
            if(filesMap.containsKey(ext)){
                filesMap.get(ext).addBytes(f.getBytes());
                filesMap.get(ext).addLines(f.getLines());
                filesMap.get(ext).increment();
            } else {
                filesMap.put(ext, new FilesByExtensionDTO(ext, 1L, f.getLines(), f.getBytes()));
            }
        });
        return new ArrayList<>(filesMap.values());
    }

    private List<FileInfoDTO> readDirFiles(String dir){
        List<FileInfoDTO> files = new ArrayList<>();
        String response = new RestTemplate().getForObject(dir, String.class);
        if(Boolean.FALSE.equals(isEmpty(response))) {
            List<Integer> matches = ScrapperUtils.findMatches(response, KEY_TAG);
            List<String> urls = new ArrayList<>();
            matches.forEach(m -> {
                int start = ScrapperUtils.findFirst(response, m, "href=\"") + 6;
                int end = ScrapperUtils.findFirst(response, start, "\"");
                urls.add(response.substring(start, end));
            });
            urls.forEach(u -> {
                if (ScrapperUtils.isFile(u)) {
                    files.add(readFile(u));
                } else {
                    files.addAll(readDirFiles(GITHUB.concat(u)));
                }
            });
        }
        return files;
    }

    private FileInfoDTO readFile(String url){
        String rawUrl = GITHUB_RAW.concat(url.replaceFirst("/blob", ""));
        ResponseEntity<String> response = RestUtil.get(rawUrl);
        String extension = ScrapperUtils.getExtension(url);
        Long size = response.getHeaders().getContentLength();
        boolean isBodyEmpty = response.getBody() != null && response.getBody().length() > 0;
        boolean notImage = !IMAGE_TYPES.contains(extension);
        long lines = isBodyEmpty && notImage ? response.getBody().split("\r\n|\r|\n").length : 0;
        return new FileInfoDTO(extension, size, lines);
    }

    public boolean validateRepo(String url){
        return url.contains(GITHUB);
    }

}
