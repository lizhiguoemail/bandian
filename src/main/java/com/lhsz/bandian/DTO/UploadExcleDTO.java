package com.lhsz.bandian.DTO;

import lombok.Data;

import javax.swing.text.StyledEditorKit;
import java.io.Serializable;

/**
 * @author lizhiguo
 * @Date 2020/12/18 17:31
 */
@Data
public class UploadExcleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private boolean updateSupport;

}
