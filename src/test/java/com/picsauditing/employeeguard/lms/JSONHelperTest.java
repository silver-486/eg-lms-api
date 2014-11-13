package com.picsauditing.employeeguard.lms;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONHelperTest {

	@Test
	public void testToIdList() throws Exception {
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
		String json = "[" + StringUtils.join(expected.toArray(), ',') + "]";
		System.out.println(" json: " + json);
		List<Integer> actual = JSONHelper.toIdList(json);
		assertEquals(expected, actual);
	}
}