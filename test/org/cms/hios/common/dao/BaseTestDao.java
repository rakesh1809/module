package org.cms.hios.common.dao;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public abstract class BaseTestDao extends DataSourceBasedDBTestCase {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected static ClassPathXmlApplicationContext context;	
	protected static NamedParameterJdbcTemplate jdbcTemplate ;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		context = new ClassPathXmlApplicationContext("/test_spring_context.xml" );
		jdbcTemplate = (NamedParameterJdbcTemplate)context.getBean("jdbcTemplate");
		BasicConfigurator.configure();
		

	}

	@Before
	public void setUp() throws Exception {
		if (context == null)
			setUpClass();
		
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		System.out.println("tear down...");
	    super.tearDown();
	}
	
	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		super.setUpDatabaseConfig(config);
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new MsSqlDataTypeFactory());
	}

	@Override
	protected abstract IDataSet getDataSet() throws Exception;


	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return InsertIdentityOperation.REFRESH;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE;
	}

	@Override
	protected DataSource getDataSource() {
		return (DataSource) context.getBean("dataSource");
	}

}
