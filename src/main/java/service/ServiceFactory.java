package service;

import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceImpl;
import service.custom.impl.OrderServiceImpl;
import util.ServiceType;

//Factory Design Pattern
//Singleton Design Pattern
public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstance(){
        return instance == null? instance=new ServiceFactory():instance;
    }

    //Bounded Generics
    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            //Type Casting (Cast to T type)
            case CUSTOMER : return (T) new CustomerServiceImpl();
            case ITEM : return (T) new ItemServiceImpl();
            case ORDER : return (T) new OrderServiceImpl();
        }
        return null;
    }
}
