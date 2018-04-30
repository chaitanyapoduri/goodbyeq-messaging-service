package com.beatus.goodbyeq.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Utils {
	
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
	
	public static String generateRandomKey(final int numberOfCharacters) {
    	String	randomNumber = RandomStringUtils.randomAlphanumeric(numberOfCharacters);
		return randomNumber;
	}
	
	public static Double calculateTaxAmount(Double amount, Double percentage) {
		double taxAmount = (amount * percentage)/100;
		return taxAmount;
	}
	
/*	public static Double calculateMarginAmount(ItemData itemDataFromDatabase, ItemDTO itemToBeSaved) {
		List<Inventory> inventories = itemDataFromDatabase.getInventories();
		Double marginAmount = Constants.DEFAULT_DOUBLE_VALUE;
		if(inventories != null){
			for(int i=0; i<inventories.size(); i++){
				if(inventories.get(i).getInventoryId().equalsIgnoreCase(itemToBeSaved.getInventoryId())){
					Double unitPrice = inventories.get(i).getBuyPricesPerQuantityType().get(itemToBeSaved.getQuantityType().toString());
					Double actualBuyPriceForQuantity = unitPrice*itemToBeSaved.getQuantity();
					Double actualProductValue = itemToBeSaved.getItemValue() * itemToBeSaved.getQuantity();
					marginAmount = actualProductValue - actualBuyPriceForQuantity;
				}
			}
		}
		return marginAmount;
	}*/

	public static Double calculateTaxOnMargin(Double marginAmount, Double taxPercentage) {
		double taxAmount = (marginAmount * taxPercentage)/100;
		return taxAmount;
	}

    public static byte[] concat(byte[]...arrays)
    {
        // Determine the length of the result array
        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            if (arrays[i] != null)
            {
                totalLength += arrays[i].length;
            }
        }

        // create the result array
        byte[] result = new byte[totalLength];

        // copy the source arrays into the result array
        int currentIndex = 0;
        for (int i = 0; i < arrays.length; i++)
        {
            if (arrays[i] != null)
            {
                System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
                currentIndex += arrays[i].length;
            }
        }

        return result;
    }
    
    private static final ObjectMapper serializeMapper = new ObjectMapper();
    private static final ObjectMapper deserializeMapper = new ObjectMapper();
	static{
	    synchronized (serializeMapper) {
	    	// Map only non null values
	    	serializeMapper.setSerializationInclusion(Include.NON_EMPTY);
	    	serializeMapper.setSerializationInclusion(Include.NON_NULL);
	    	serializeMapper.setSerializationInclusion(Include.NON_DEFAULT);
	    	serializeMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
	    }
	    synchronized (deserializeMapper) {
			deserializeMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
	}
	
    /**
     * Takes java Object and converts it into Json String
     * @param data
     * @return String
     */
    public static String convertJavaToJson(Object data) {
    	// Map only non null values
    	serializeMapper.setSerializationInclusion(Include.NON_EMPTY);
    	serializeMapper.setSerializationInclusion(Include.NON_NULL);
    	serializeMapper.setSerializationInclusion(Include.NON_DEFAULT);
    	serializeMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    	String json = null;
    	try {
    		LOG.debug("FromJava: {}", data.toString());
    		json = serializeMapper.writeValueAsString(data);
    	} catch (JsonGenerationException jge) {
    		LOG.error("JsonGenerationException: {}", jge);
    	} catch (JsonMappingException jme) {
    		LOG.error("JsonMappingException: {}", jme);
    	} catch (IOException ioe) {
    		LOG.error("IOException: {}", ioe);
    	}
    	LOG.debug("ToJson: {}", json);
    	return json;
    }

    public Properties getEmailProperties() throws IOException {
		InputStream inputStream;
		String fileName = null;
		Properties prop = new Properties();
		String defaultEnv = System.getProperty(Constants.PROPERTIES_ENV);
		if(defaultEnv == null) {
			fileName = Constants.PROPERTIES_DEV;
		}
		else {
			fileName = Constants.PROPERTIES_PATH + defaultEnv + Constants.PROPERTIES_EXT;			  
		}
				
		ClassLoader classLoader = getClass().getClassLoader();
		inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("Property file '" + fileName + "' not found in the classpath!");
		}
		return prop;
	}
}
