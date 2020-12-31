package com.lhsz.bandian.sys.controller;


import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.utils.UploadUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.lhsz.bandian.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 附件 前端控制器
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Slf4j
@RestController
@RequestMapping("/sys/attach")
@Api(tags = "附件")
public class AttachController extends BaseController {
    @Autowired
    private IAttachService attachService;

    @PostMapping("/upload")
    public HttpResult upload(@RequestParam("file") MultipartFile file, String typeCode, String typeName) throws IOException{
        if (file.isEmpty()) {
            return HttpResult.fail();
        }
        Attach attach = attachService.upload(file, null, typeCode, typeName);
        return UploadUtils.getHttpResult(attach);
    }
    /**
     * ckeditor上传请求
     */
    @PostMapping("/ckeditor/upload")
    @ResponseBody
    public Map<String, Object> uploadCkeditorFile(@RequestParam("file") MultipartFile file)  throws IllegalStateException, IOException{
        Map<String, Object> map = new HashMap<>();
        // 上传并返回新文件名称
        Attach attach = attachService.upload(file, null);
        map.put("url", attach.getFilePath()); //not null
        map.put("uploaded", 1); // allow null
        map.put("uploadedPercent", 1); // allow null
        map.put("error", "error"); // allow null
        return map;
    }

    @DeleteMapping("/{id}")
    public HttpResult deleteAttach(@PathVariable("id") String id) throws IOException{
        if (id.isEmpty()) {
            return HttpResult.fail();
        }
        Set<String> ids = new HashSet<>();
        ids.add(id);
        boolean result = attachService.deleleteByIds(ids);
        return result ? HttpResult.ok() : HttpResult.fail();
    }

    @PostMapping("/delete")
    public HttpResult deleteAttachList(@RequestBody() String ids) throws IOException{
        if (ids.isEmpty()) {
            return HttpResult.fail();
        }
        Set<String> list = new HashSet<>();
        String[] strs = ids.split(",");
        Collections.addAll(list, strs);
        boolean result = attachService.deleleteByIds(list);
        return result ? HttpResult.ok() : HttpResult.fail();
    }
}
