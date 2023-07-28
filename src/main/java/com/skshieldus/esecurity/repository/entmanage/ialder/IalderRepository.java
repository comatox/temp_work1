package com.skshieldus.esecurity.repository.entmanage.ialder;

import com.skshieldus.esecurity.config.datasource.annotation.IalderMapper;
import java.util.List;
import java.util.Map;

@IalderMapper
public interface IalderRepository {

    List<Map<String, Object>> selectVisitNacIfView(String ioEmpId);

}
