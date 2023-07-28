package com.skshieldus.esecurity.repository.entmanage.calder;

import com.skshieldus.esecurity.config.datasource.annotation.CalderMapper;
import java.util.List;
import java.util.Map;

@CalderMapper
public interface CalderRepository {

    List<Map<String, Object>> selectVisitNacIfView(String ioEmpId);

}
