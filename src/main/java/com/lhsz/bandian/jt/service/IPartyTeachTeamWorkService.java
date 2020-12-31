package com.lhsz.bandian.jt.service;

import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkAddDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkDetailDTO;
import com.lhsz.bandian.jt.DTO.request.PartyTeachTeamWorkUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.PartyTeachTeamWorkDTO;
import com.lhsz.bandian.jt.entity.PartyTeachTeamWork;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 党校教师团队工作 服务类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
public interface IPartyTeachTeamWorkService extends IService<PartyTeachTeamWork> {
    boolean add(PartyTeachTeamWork partyTeachTeamWork);

    boolean add(PartyTeachTeamWorkAddDTO partyTeachTeamWork);

    void update(PartyTeachTeamWork partyTeachTeamWork);

    void update(PartyTeachTeamWorkUpdateDTO partyTeachTeamWork);

    List<PartyTeachTeamWorkDTO> list(PartyTeachTeamWork partyTeachTeamWork);

    PartyTeachTeamWorkDTO selectById(String id);

    PartyTeachTeamWorkDetailDTO detailById(String id);

    int del(String id);

}
