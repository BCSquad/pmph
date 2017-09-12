package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.PmphUser;

public interface PmphUserService {
	Integer addPmphUser(PmphUser pmphUser);

	Integer deletePmphUserById(String[] ids);

	Integer updatePmphUserById(PmphUser pmphUser);

	PmphUser getByUsername(String username);

}
