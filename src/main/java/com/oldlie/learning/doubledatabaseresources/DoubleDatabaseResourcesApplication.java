package com.oldlie.learning.doubledatabaseresources;

import com.oldlie.learning.doubledatabaseresources.database1.Table1;
import com.oldlie.learning.doubledatabaseresources.database1.Table1Repository;
import com.oldlie.learning.doubledatabaseresources.database2.Table2;
import com.oldlie.learning.doubledatabaseresources.database2.Table2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author DELL
 */
@SpringBootApplication
public class DoubleDatabaseResourcesApplication implements ApplicationListener<ContextRefreshedEvent> {

	public static void main(String[] args) {
		SpringApplication.run(DoubleDatabaseResourcesApplication.class, args);
	}

	private boolean isInit = false;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	    if (!isInit) {
	    	this.run();
	    	isInit = true;
		}
	}

	@Autowired
	private Table1Repository table1Repository;

	@Autowired
	private Table2Repository table2Repository;

	private void run() {
		Table1 table1 = new Table1();
		table1.setTaText("table 1");
		table1.setTaTitle("title 1");
		this.table1Repository.save(table1);
		Table2 table2 = new Table2();
		table2.setTaText("table 2");
		this.table2Repository.save(table2);
	}
}
