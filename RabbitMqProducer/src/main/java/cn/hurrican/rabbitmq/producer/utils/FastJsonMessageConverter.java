package cn.hurrican.rabbitmq.producer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.IOException;
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

    public <T> T fromMessage(Message message, T clazz) {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = new String(message.getBody(), defaultCharset);
            return (T) objectMapper.readValue(json, clazz.getClass());
        } catch (IOException e) {
            logger.error("获取信息体时发生异常：{}", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) {
        byte[] bytes = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            jsonString = objectMapper.writeValueAsString(objectToConvert);
            bytes = jsonString.getBytes(this.defaultCharset);
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(this.defaultCharset);
            if (bytes != null) {
                messageProperties.setContentLength(bytes.length);
            }
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
