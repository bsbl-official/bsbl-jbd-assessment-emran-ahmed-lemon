package com.ems.common.base;

import java.util.List;

public interface BaseService<REQ, RES, ID> {

    RES create(REQ request);

    RES update(ID id, REQ request);

    void delete(ID id);

    RES findById(ID id);

    List<RES> findAll();
}
