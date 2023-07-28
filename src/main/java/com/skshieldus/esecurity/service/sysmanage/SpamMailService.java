package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.SpamMailDTO;
import com.skshieldus.esecurity.model.sysmanage.SpamMailSearchDTO;
import java.util.List;

public interface SpamMailService {

    List<SpamMailDTO> selectSpamMailList(SpamMailSearchDTO spamMailSearch);

    SpamMailDTO selectSpamMailView(SpamMailSearchDTO spamMailSearch);

    SpamMailDTO selectSpamMailUserInfo(SpamMailSearchDTO spamMailSearch);

    Boolean updateSpamMail(SpamMailDTO spamMail);

    Boolean insertSpamMail(SpamMailDTO spamMail);

    Boolean deleteSpamMail(SpamMailDTO spamMail);

}