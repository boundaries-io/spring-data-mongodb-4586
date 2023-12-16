package com.boundaries.io.spring.data.mongodb4586;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.io.FileUtils;
import org.geojson.MultiPolygon;
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
		
		GeoJsonMultiPolygon  geoJsonMultiPolygon2 =  getObjectMapper().readValue(json, GeoJsonMultiPolygon.class);
		Assert.assertEquals(geoJsonMultiPolygon,geoJsonMultiPolygon2);	
	}
	
	/**
	 * 
	 * https://github.com/opendatalab-de/geojson-jackson
	 * GeoJson POJOs for Jackson
	 * A small package of all GeoJson POJOs (Plain Old Java Objects)
	 * for serializing and deserializing of objects via JSON Jackson Parser. 
	 * This libary conforms to the 2008 GeoJSON specification.
	 * 
	 * @throws IOException
	 * @throws InvalidShapeException
	 * @throws ParseException
	 */
	@Test
	public void testMultiPolygonMapperViaGeoJson() throws IOException , ParseException {
		String dallas = FileUtils.readFileToString(new File("src/test/resources/raw_dallas.geojson"),"UTF-8");
		MultiPolygon multiPolygon =  getObjectMapper().readValue(dallas, MultiPolygon.class);
		String json =   getObjectMapper().writeValueAsString(multiPolygon);
		Assert.assertEquals(dallas,json);//passes
	
		MultiPolygon multiPolygon2 =  getObjectMapper().readValue(json, MultiPolygon.class);
		Assert.assertEquals(multiPolygon,multiPolygon2);//passes	
	}
	

}
