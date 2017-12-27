package com.example.demo.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/service/demo")
public class DemoController {

	@RequestMapping(method = GET)
	public Map<String, Object> greeting(@RequestParam(value = "name", required = true) String name) {
		Map<String, Object> result = new HashMap<>();
		result.put("name", name);
		result.put("time", Instant.now().toString());

		return result;
	}
}
