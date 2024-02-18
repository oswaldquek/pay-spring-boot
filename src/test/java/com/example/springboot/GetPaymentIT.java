package com.example.springboot;

import com.example.springboot.dao.PaymentDao;
import com.example.springboot.entity.Payment;
import com.example.springboot.model.ChargeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetPaymentIT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private PaymentDao paymentDao;

	@BeforeEach
	void setup() {
		LocalDate date = LocalDate.now();
		IntStream.rangeClosed(1,20).forEach(i -> paymentDao.addPayment(
				new Payment(Integer.toUnsignedLong(i), UUID.randomUUID().toString(),
						date.minusMonths(i), 1000, ChargeStatus.CREATED)));
	}

	@Test
	void shouldGetDefaultOfFivePayments() throws Exception {
		mvc.perform(get("/payments"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.results", hasSize(5)))
				.andExpect(jsonPath("$.results[*].id", hasItems(1,2,3,4,5)));
	}

	@Test
	void shouldGetTenPayments() throws Exception {
		mvc.perform(get("/payments").queryParam("limit", "10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.results", hasSize(10)))
				.andExpect(jsonPath("$.results[*].id", hasItems(1,2,3,4,5,6,7,8,9,10)))
				.andExpect(jsonPath("$.pagination.next_url", is(("/payments?limit=10&after_id=10"))));
	}

	@Test
	void testAfterIdParams() throws Exception {
		mvc.perform(get("/payments").queryParam("limit", "4"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.results", hasSize(4)))
				.andExpect(jsonPath("$.results[*].id", hasItems(1,2,3,4)))
				.andExpect(jsonPath("$.pagination.next_url", is(("/payments?limit=4&after_id=4"))));

		mvc.perform(get("/payments").queryParam("limit", "4").queryParam("after_id", "4"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.results", hasSize(4)))
				.andExpect(jsonPath("$.results[*].id", hasItems(5,6,7,8)))
				.andExpect(jsonPath("$.pagination.next_url", is(("/payments?limit=4&after_id=8"))));

		mvc.perform(get("/payments").queryParam("limit", "4").queryParam("after_id", "8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.results", hasSize(4)))
				.andExpect(jsonPath("$.results[*].id", hasItems(9,10,11,12)))
				.andExpect(jsonPath("$.pagination.next_url", is(("/payments?limit=4&after_id=12"))));
	}
}
