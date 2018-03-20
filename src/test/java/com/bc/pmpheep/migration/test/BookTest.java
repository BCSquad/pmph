package com.bc.pmpheep.migration.test;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.migration.MigrationBook;
import com.bc.pmpheep.test.BaseTest;

@FixMethodOrder(MethodSorters.JVM)
public class BookTest extends BaseTest {
	@Resource
	MigrationBook migrationBook;

	@Test
	@Rollback(false)
	public void addBook() {
		migrationBook.start();
	}
}
