package com.boundaries.io.spring.data.mongodb4586;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializeTest {
	private static ObjectMapper mapper;
	
	public static ObjectMapper getObjectMapper() throws IOException {
		if(mapper == null) {
			mapper = new ObjectMapper();
			mapper.registerModule(GeoJsonModule.serializers());
			mapper.registerModule(GeoJsonModule.deserializers());
			mapper.registerModule(new GeoJsonModule());
 		}		
		return mapper;
 	}
	@Test
	public void testGeoJsonMultiPolygonMapperViaGeoJson() throws IOException   {	
		String validGeoJson = FileUtils.readFileToString(new File("src/test/resources/raw_dallas.geojson"),"UTF-8");
		GeoJsonMultiPolygon geoJsonMultiPolygon =   getObjectMapper().readValue(validGeoJson, GeoJsonMultiPolygon.class);		 
 		String json =  getObjectMapper().writeValueAsString(geoJsonMultiPolygon);		
 		FileUtils.writeStringToFile(new File("target/dallas_error.geojson"), json,"UTF-8");
		Assert.assertEquals(validGeoJson,json);//fails 	
	}
}
