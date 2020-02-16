package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CSVObjectMapperBuilderTest {

    @Test
    public void create_builder() {
        CSVObjectMapperBuilder builder = CSVObjectMapperBuilder.csvObjectMapper();

        assertNotNull(builder);
        assertEquals(CSVObjectMapperBuilder.class, builder.getClass());
    }

    @Test
    public void is_cached_true() {
        CSVObjectMapperBuilder builder = new CSVObjectMapperBuilder();

        // call
        CSVObjectMapperImpl om = (CSVObjectMapperImpl) builder.cacheParsedClasses(true)
                .build();

        assertEquals(CachedCSVClassParser.class, om.getParser().getClass());
    }

    @Test
    public void is_cached_false() {
        CSVObjectMapperBuilder builder = new CSVObjectMapperBuilder();

        // call
        CSVObjectMapperImpl om = (CSVObjectMapperImpl) builder.cacheParsedClasses(false)
                .build();

        assertEquals(CSVClassParserImpl.class, om.getParser().getClass());
    }

    @Test
    public void default_CSV_format() {
        CSVObjectMapperBuilder builder = new CSVObjectMapperBuilder();

        // call
        CSVObjectMapperImpl om = (CSVObjectMapperImpl) builder.build();

        CachedCSVClassParser parser = (CachedCSVClassParser) om.getParser();
        CSVFormatAdapter formatAdapter = ((CSVBeanParserFactoryImpl) parser.getBeanParserFactory()).getDefaultCSVFormatAdapter();

        assertEquals(CSVFormatAdapterEnum.DEFAULT, formatAdapter);
    }

    @Test
    public void custom_default_CSV_format() {
        CSVObjectMapperBuilder builder = new CSVObjectMapperBuilder();

        // call
        CSVObjectMapperImpl om = (CSVObjectMapperImpl) builder.defaultCSVFormat(CSVFormat.ORACLE)
                .build();

        CachedCSVClassParser parser = (CachedCSVClassParser) om.getParser();
        CSVFormatAdapter formatAdapter = ((CSVBeanParserFactoryImpl) parser.getBeanParserFactory()).getDefaultCSVFormatAdapter();

        assertEquals(CSVFormat.ORACLE, formatAdapter.getFormat());
    }

    @Test
    public void build() {
        CSVObjectMapperBuilder builder = new CSVObjectMapperBuilder();

        //call
        CSVObjectMapper om = builder.build();

        assertNotNull(om);
        assertEquals(CSVObjectMapperImpl.class, om.getClass());
    }

}

