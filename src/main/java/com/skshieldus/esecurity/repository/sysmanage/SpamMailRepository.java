package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.SpamMailDTO;
import com.skshieldus.esecurity.model.sysmanage.SpamMailSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SpamMailRepository {

    List<SpamMailDTO> selectSpamMailList(SpamMailSearchDTO spamMailSearch);

    SpamMailDTO selectSpamMailView(SpamMailSearchDTO spamMailSearch);

    SpamMailDTO selectSpamMailUserInfo(SpamMailSearchDTO spamMailSearch);

    int updateSpamMail(SpamMailDTO spamMail);

    int insertSpamMail(SpamMailDTO spamMail);

    int deleteSpamMail(SpamMailDTO spamMail);

}