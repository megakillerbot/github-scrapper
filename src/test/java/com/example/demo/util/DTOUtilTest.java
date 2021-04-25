package com.example.demo.util;

import com.example.demo.dto.FileInfoDTO;
import com.example.demo.dto.FilesByExtensionDTO;
import com.example.demo.dto.RequestDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DTOUtilTest {

    @Test
    public void cicleTest() throws IOException {
        FilesByExtensionDTO expected = new FilesByExtensionDTO("ext", 1L, 100L, 2048L);
        String json = DTOUtil.toJson(expected);
        FilesByExtensionDTO result = (FilesByExtensionDTO) DTOUtil.fromJson(json, FilesByExtensionDTO.class);
        Assert.assertEquals(expected, result);
    }

    @Test(expected = IOException.class)
    public void fromJsonError() throws IOException {
        FileInfoDTO file = new FileInfoDTO("ext", 1024L, 10L);
        DTOUtil.fromJson(DTOUtil.toJson(file), RequestDTO.class);
    }

}