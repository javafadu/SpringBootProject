package com.tpe.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class BeanController {
    
	String beanKey;
	String beanValue;
	String beanAra="-";
	
		
	
    public BeanController(String beanKey, String beanValue, String beanAra) {
		this.beanKey = beanKey;
		this.beanValue = beanValue;
		this.beanAra="-";
	}
    
    public BeanController() {
    	
    }

    
   


	@Autowired
    private ApplicationContext applicationContext;
    
    @RequestMapping("/bean")
    @ResponseBody
//    public List<BeanController> getBeans(){
//        
//    	 BeanController beanObje = new BeanController(beanKey, beanValue, beanAra);
//    	 List<BeanController> beanListe = new ArrayList<BeanController>();
//    	
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        
//   
//        
//        for (String beanName : beanNames) {
//            
//        	beanObje.beanKey=beanName;
//        	beanObje.beanValue=applicationContext.getBean(beanName).toString();
//        	beanObje.beanAra="##";
//        	beanListe.add(beanObje);
//        }
//        return beanListe;
//    }
    public Map<String,String> getBeans(){

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        Map<String,String> map=new HashMap<>();

        for (String beanName : beanNames) {

            map.put(beanName, applicationContext.getBean(beanName).toString());
        }
        return map;
    }
    
    
    
}