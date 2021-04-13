package com.icomsys.cinnamon.core.api.informal.address.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.icomsys.cinnamon.core.api.informal.address.service.InformalAddressCacheService;
import com.icomsys.cinnamon.share.mapper.informal.InformalAddressCacheMapper;

/**
 * @Name InformalAddressCacheService
 * @Description 주소정보 중 캐시가 필요한 데이터 조회 서비스
 * @Author sungbumoh
 * @CreateDate 2020. 8. 28.
 */
@Service
public class InformalAddressCacheServiceImpl implements InformalAddressCacheService {

    @Autowired
    private InformalAddressCacheMapper informalAddressCacheMapper;

    /**
     * @Name listSido
     * @Description 시도 목록 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listSido")
    @Override
    public List<String> listSido() {
        return informalAddressCacheMapper.listSido();
    }

    /**
     * @Name listSigun
     * @Description 시군 목록 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listSigun")
    @Override
    public List<String> listSigun() {
        return informalAddressCacheMapper.listSigun();
    }

    /**
     * @Name listGuList
     * @Description 구 목록 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listGuList")
    @Override
    public List<String> listGuList() {
        return informalAddressCacheMapper.listGuList();
    }

    /**
     * @Name listYpmyun
     * @Description 읍면 목록 조회
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listYpmyun")
    @Override
    public List<String> listYpmyun() {
        return informalAddressCacheMapper.listYpmyun();
    }

    /**
     * @Name listRi
     * @Description 리 목록 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listRi")
    @Override
    public List<String> listRi() {
        return informalAddressCacheMapper.listRi();
    }

    /**
     * @Name listRaw
     * @Description 법정동 목록 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listRaw")
    @Override
    public List<String> listRaw() {
        return informalAddressCacheMapper.listRaw();
    }

    /**
     * @Name listStartSejong
     * @Description 세종으로 시작하는 지역명 조회(캐싱)
     * @Author Song.SJ
     * @CreateDate 2020. 9. 8
     * 
     * @return
     *
     * @ChangeDescription
     */
    @Cacheable(value = "listRaw")
    @Override
    public List<String> listStartSejong() {
        return informalAddressCacheMapper.listStartSejong();
    }

    /**
     * @Name readBuildType
     * @Description 빌딩 유형 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @return
     *
     * @ChangeDescription
     *                    2020. 9. 9 SONG.SJ 여러개의 buildType을 조회 하는 것이므로 readBuildType > listBuildType으로 변경
     */
    @Cacheable(value = "listBuildType")
    @Override
    public List<String> listBuildType() {
        return informalAddressCacheMapper.listBuildType();
    }

    /**
     * @Name listBuildGroup
     * @Description 빌딩 그룹 조회(캐싱)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 28.
     * 
     * @param buildType
     * @return
     *
     * @ChangeDescription
     *                    2020. 9. 9 SONG.SJ 여러개의 buildType을 조회 하는 것이므로 readBuildGroup > listBuildGroup으로 변경
     */
    @Cacheable(value = "listBuildGroup", key = "#buildType")
    @Override
    public List<String> listBuildGroup(String buildType) {
        return informalAddressCacheMapper.listBuildGroup(buildType);
    }

}
