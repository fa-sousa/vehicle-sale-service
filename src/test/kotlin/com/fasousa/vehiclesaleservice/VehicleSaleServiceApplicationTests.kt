package com.fasousa.vehiclesaleservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest(
	properties = [
		"spring.datasource.url=jdbc:h2:mem:vehicle_sale_service_test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.jpa.show-sql=false",
		"spring.jpa.open-in-view=false",
		"spring.sql.init.mode=never",
		"server.port=0"
	]
)
@ActiveProfiles("test")
class VehicleSaleServiceApplicationTests {

	@Test
	fun contextLoads() {
	}
}

