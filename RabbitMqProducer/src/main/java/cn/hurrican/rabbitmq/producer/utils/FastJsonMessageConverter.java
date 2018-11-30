package cn.hurrican.rabbitmq.producer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;

public class FastJsonMessageConverter extends AbstractMessageConverter {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private static Logger logger = LogManager.getLogger(FastJsonMessageConverter.class);

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter() {
        super();
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset : DEFAULT_CHARSET;
    }


    @Override
    public Object fromMessage(Message message) throws MessageConversionException {

        return null;
    }

    @Override
    protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("objectToConvert.getClass() = " + objectToConvert.getClass());
            String jsonString = objectMapper.writeValueAsString(objectToConvert);
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(this.defaultCharset);
            byte[] bytes = jsonString.getBytes(this.defaultCharset);
            messageProperties.setContentLength(bytes.length);

            return new Message(bytes, messageProperties);
        } catch (UnsupportedEncodingException e) {
            logger.error("发生异常：{}", e);
            throw new MessageConversionException("Failed to convert Message content", e);
        } catch (JsonProcessingException e) {
            logger.error("发生异常：{}", e);
            e.printStackTrace();
            throw new MessageConversionException("Failed to convert Message content", e);
        }

    }
}
