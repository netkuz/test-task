package org.netkuz.washing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Start application")
@DirtiesContext
class WashingApplicationTests extends BaseTest {

	@Test
	void main() {
		assertDoesNotThrow((Executable) WashingApplication::main);
	}

}
