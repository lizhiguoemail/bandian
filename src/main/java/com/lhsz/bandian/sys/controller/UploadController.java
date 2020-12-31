package com.lhsz.bandian.sys.controller;

import com.lhsz.bandian.DTO.HttpResult;
import com.lhsz.bandian.DTO.HttpStatus;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.utils.UploadUtils;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lizhiguo
 * @Date 2020/12/17 17:52
 */
@Api(tags = "附件上传通用接口")
@RestController
@RequestMapping("/systems/upload")
public class UploadController {
    /**
     * 上传
     * @param file
     * @param folderName 需创建文件夹名称
     * @param isDateFolder 是否自动日期创建文件夹 默认true
     * @return 返回上传结果
     * @throws IOException 异常
     */
    @ApiOperation("附件上传")
    @ApiResponses({ @ApiResponse(code = HttpStatus.SUCCEE, message = "操作成功"),
            @ApiResponse(code = HttpStatus.FAIL, message = "服务器内部异常"),
            @ApiResponse(code = HttpStatus.SC_UNAUTHORIZED, message = "权限不足") })
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "MultipartFile", name = "file", value = "文件File", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "folderName", value = "指定文件夹名称", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "isDateFolder", value = "是否自动根据日期创建文件夹", required = false,defaultValue = "true")})
    @PostMapping()
    public HttpResult uploadFile(@RequestParam("file") MultipartFile file,@RequestParam(name="folderName",required=false) String folderName,@RequestParam(name="isDateFolder",defaultValue = "true",required=false) Boolean isDateFolder) throws IOException {
        if (file.isEmpty()) {
            return HttpResult.fail();
        }
        Attach attach = UploadUtils.upload(file,folderName,isDateFolder);
        return UploadUtils.getHttpResult(attach);
    }

}
