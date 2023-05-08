package util;

import java.io.IOException;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import model.implementation.IUser;
import model.implementation.ITicket;
import model.implementation.IEvent;
import storage.BookingStorage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class StorageInitializer implements BeanPostProcessor {
    private String userDataPath;
    private String ticketDataPath;
    private String eventDataPath;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("Execute postProcessBeforeInitialization for bean {}", beanName);
        if (bean instanceof BookingStorage bookingStorage) {
            try {
                bookingStorage.loadData(IUser.class, userDataPath);
                bookingStorage.loadData(ITicket.class, ticketDataPath);
                bookingStorage.loadData(IEvent.class, eventDataPath);
            } catch (IOException e) {
                throw new RuntimeException("Error in bean post processor");
            }
        }
        return bean;
    }
}