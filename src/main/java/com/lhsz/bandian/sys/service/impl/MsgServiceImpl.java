package com.lhsz.bandian.sys.service.impl;

import com.lhsz.bandian.sys.entity.Msg;
import com.lhsz.bandian.sys.mapper.MsgMapper;
import com.lhsz.bandian.sys.service.IMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-02
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}
