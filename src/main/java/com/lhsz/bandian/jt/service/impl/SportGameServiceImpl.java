package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.jt.DTO.request.SportGameAddDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameDetailDTO;
import com.lhsz.bandian.jt.DTO.request.SportGameUpdateDTO;
import com.lhsz.bandian.jt.DTO.response.SportGameDTO;
import com.lhsz.bandian.jt.entity.SportGame;
import com.lhsz.bandian.jt.mapper.SportGameMapper;
import com.lhsz.bandian.jt.service.ISportGameService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.sys.DTO.result.FileUploadDTO;
import com.lhsz.bandian.sys.entity.Attach;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IAttachService;
import com.lhsz.bandian.sys.service.IDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 竞技体育比赛成果 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Service
public class SportGameServiceImpl extends ServiceImpl<SportGameMapper, SportGame> implements ISportGameService {
    @Autowired
    private com.lhsz.bandian.jt.mapper.SportGameMapper SportGameMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IDictDataService iDictDataService;
    @Autowired
    private IAttachService attachService;

    @Override
    public List<SportGameDTO> list(SportGame sportGame) {
        List<SportGameDTO> sportGameDTOS = SportGameMapper.selectMapperList(sportGame);
        return sportGameDTOS;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(SportGameUpdateDTO sportGameUpdateDTO) {
        SportGame sportGame = new SportGame();
        BeanUtils.copyProperties(sportGameUpdateDTO.getData(),sportGame);
        sportGame.setGameId(sportGameUpdateDTO.getData().getId());
        updateById(sportGame);

        if (sportGameUpdateDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            sportGameUpdateDTO.getFiles().forEach(obj -> {
                obj.setObjectId(sportGameUpdateDTO.getData().getGameId());
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveOrUpdateAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void update(SportGameDTO sportGameDTO) {
        SportGame sportGame = new SportGame();
        BeanUtils.copyProperties(sportGameDTO,sportGame);
        sportGame.setGameId(sportGameDTO.getId());
        updateById(sportGame);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(SportGameAddDTO sportGameAddDTO) {
        String gameId = UUID.randomUUID().toString();
        SportGame sportGame = new SportGame();
        sportGameAddDTO.getData().setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        sportGameAddDTO.getData().setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(sportGameAddDTO.getData(),sportGame);
        sportGameAddDTO.getData().setId(sportGame.getGameId());
        save(sportGame);

        if (sportGameAddDTO.getFiles().size() > 0) {
            List<Attach> attachs = new ArrayList<>();
            sportGameAddDTO.getFiles().forEach(obj -> {
                obj.setObjectId(gameId);
                obj.setObjectType("业绩库");
                attachs.add(FileUploadDTO.ToAttach(obj));
            });
            attachService.saveAttachBatch(attachs);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void add(SportGameDTO sportGameDTO) {
        SportGame sportGame = new SportGame();
        sportGameDTO.setIsDeleted(0);
        User loginUserToUser = tokenService.getLoginUserToUser();
        sportGameDTO.setUserId(loginUserToUser.getUserId());
        BeanUtils.copyProperties(sportGameDTO,sportGame);
        sportGameDTO.setId(sportGame.getGameId());

        save(sportGame);
    }

    @Override
    public SportGameDetailDTO detailById(String id) {
        SportGameDetailDTO sportGameDetailDTO = new SportGameDetailDTO();
        SportGameDTO sportGame = SportGameMapper.selectDTO(id);
        sportGameDetailDTO.setData(sportGame);
        List<FileUploadDTO> fileUploadDTOS = attachService.listByObjectId(sportGame.getGameId());
        sportGameDetailDTO.setFiles(fileUploadDTOS);
        return sportGameDetailDTO;

    }

    @Override
    public SportGameDTO selectById(String id) {
        SportGame sportGame = SportGameMapper.selectById(id);
        SportGameDTO sportGameDTO = new SportGameDTO(sportGame);
        return sportGameDTO;
    }

    @Override
    public int del(String id) {
        return SportGameMapper.deleteById(id);
    }
}
