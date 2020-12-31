package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PartyTeachWorkDTO;
import com.lhsz.bandian.jt.entity.PartyTeachWork;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 党校教学工作 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IPartyTeachWorkService extends IService<PartyTeachWork> {
    boolean add(PartyTeachWork partyTeachWork);

    boolean add(PartyTeachWorkAddDTO partyTeachWork);

    void update(PartyTeachWork partyTeachWork);

    void update(PartyTeachWorkUpdateDTO partyTeachWork);

    List<PartyTeachWorkDTO> list(PartyTeachWork partyTeachWork);

    PartyTeachWorkDTO selectById(String id);

    PartyTeachWorkDetailDTO detailById(String id);

    int del(String id);
}
