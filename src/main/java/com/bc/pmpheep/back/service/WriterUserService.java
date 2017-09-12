package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.WriterUser;

public interface WriterUserService {
	Integer addWriterUser(WriterUser writerUser);

	Integer deleteWriterUserById(String[] ids);

	Integer updateWriterUserById(WriterUser writerUser);

	WriterUser getWriterUserByUsername(String username);

}
