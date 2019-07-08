package io.agileinfra.platform.store.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author louis.gueye@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
public class PlatformStoreExampleJob implements CommandLineRunner {

	private final DataSource dataSource;

	@Override
	public void run(String... args) throws SQLException {
		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("create_ddl.sql"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("crud.sql"));
		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("drop_ddl.sql"));
	}
}
